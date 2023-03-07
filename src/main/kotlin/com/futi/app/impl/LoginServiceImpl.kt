package com.futi.app.impl

import com.futi.app.config.security.Authorization
import com.futi.app.dao.EnclosureMapper
import com.futi.app.dao.LoginMapper
import com.futi.app.dao.UserMapper
import com.futi.app.domain.Response
import com.futi.app.dto.LoginDTO
import com.futi.app.service.LoginService
import com.futi.app.utils.*
import com.futi.app.utils.Constants.CLIENT_WEB
import com.futi.app.utils.Constants.DEFAULT_PASSWORD
import com.futi.app.utils.Constants.DEFAULT_VERIFIED_SOCIAL
import com.futi.app.utils.Constants.DISTRICT_NONE
import com.futi.app.utils.Constants.MESSAGE_FORBIDDEN
import com.futi.app.utils.Constants.ROLE_ADMIN
import com.futi.app.utils.Function
import com.futi.app.utils.Messages.FAILURE_BAD_CREDENTIALS
import com.futi.app.utils.Messages.FAILURE_NO_MAIL
import com.futi.app.utils.Messages.FAILURE_USER_ALREADY_REGISTERED
import com.futi.app.utils.Messages.RECOVER_PASSWORD_SUBJECT
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.common.exceptions.InvalidClientException
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.HttpClientErrorException
import java.lang.management.ManagementFactory
import java.util.*
import javax.naming.InvalidNameException


@Service("loginService")
class LoginServiceImpl : LoginService {

    val className = this.javaClass.simpleName ?: "LoginServiceImpl"

    @Autowired
    lateinit var loginMapper: LoginMapper

    @Autowired
    lateinit var userMapper: UserMapper

    @Autowired
    lateinit var enclosureMapper: EnclosureMapper

    @Autowired
    lateinit var tokenEndpoint: TokenEndpoint

    @Autowired
    lateinit var defaultOAuth2RequestFactory: DefaultOAuth2RequestFactory


    @Autowired
    lateinit var encoder: BCryptPasswordEncoder

    @Autowired
    lateinit var function: Function

    @Autowired
    lateinit var authorization: Authorization

    @Transactional("transactionManager")
    override fun socialLogin(loginDTO: LoginDTO): Response<OAuth2AccessToken?> {

        loginMapper.getUserBySocialID(loginDTO.socialId!!)?.let { user ->
            return try {
                ResponseFactory.succesfull(getToken(user.username!!, DEFAULT_PASSWORD, loginDTO.client_id!!, loginDTO.client_secret!!, loginDTO.grant_type!!))
            } catch (e: Exception) {
                e.printStackTrace()
                ResponseFactory.error(className, e.message)
            }
        }

        return try {

            loginDTO.email?.let { mail ->

                var valueSocialId = function.getValueTypeSocial(loginDTO.provider!!)

                loginMapper.getUserByMail(mail)?.let { found ->
                    found.id?.let { loginMapper.createRegister(loginDTO.socialId!!, it, valueSocialId) }
                } ?: run {

                    loginDTO.password = encoder.encode(DEFAULT_PASSWORD)
                    loginDTO.district = DISTRICT_NONE
                    loginDTO.verified = DEFAULT_VERIFIED_SOCIAL
                    loginMapper.createUserSocial(loginDTO)
                    loginDTO.id?.let { loginMapper.createRegister(loginDTO.socialId!!, it, valueSocialId) }
                }

                return ResponseFactory.succesfull(getToken(mail, DEFAULT_PASSWORD, loginDTO.client_id!!, loginDTO.client_secret!!, loginDTO.grant_type!!))

            } ?: kotlin.run {
                return ResponseFactory.failure(FAILURE_NO_MAIL)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            ResponseFactory.error(className, e.message)
        }

    }

    @Transactional("transactionManager")
    override fun register(user: com.futi.app.domain.User): Response<OAuth2AccessToken?> {

        return try {
            //  val json = Base64.getDecoder().decode(data)
            //  val user = Gson().fromJson(String(json), com.futi.app.domain.User::class.java)
            val usedPassword = user.password
            user.password = encoder.encode(usedPassword)
            loginMapper.createUser(user)

            SafeLet.ifLet(user.id, user.enclosure) { (id, enclosure) ->
                enclosureMapper.createEnclosureUser(id as Long, enclosure as Int)
            }

            SafeLet.ifLet(user.id, user.firstName, user.email) { (id, name, email) ->
                val code = Mailing.generateCode()
                userMapper.createVerificationCode(id as Long, code)
                Mailing.run {
                    val body = getBody(TYPE_VALIDATION_CODE, user.firstName!!, code)
                    sendMail(Messages.VERIFIED_CODE_SUBJECT, body!!, user.email!!)
                }
            }

            return ResponseFactory.succesfull(getToken(user.email!!, usedPassword!!, user.client_id.toString(), user.client_secret.toString(), user.grant_type.toString()))

        } catch (e: Exception) {
            when (e) {
                is DuplicateKeyException -> ResponseFactory.failure(FAILURE_USER_ALREADY_REGISTERED)
                else -> ResponseFactory.error(className, e.message)
            }

        }
    }


    override fun internalLogin(username: String, password: String, clientId: String, clientSecret: String, grantType: String, socialId: Int): Response<OAuth2AccessToken?> {
        return try {

            ResponseFactory.succesfull(getToken(username, password, clientId, clientSecret, grantType))
        } catch (e: Exception) {
            when (e) {
                is InvalidGrantException -> ResponseFactory.failure(FAILURE_BAD_CREDENTIALS)
                is InvalidClientException -> ResponseFactory.forbidden()
                else -> ResponseFactory.error(className, e.message)
            }
        }
    }


    override fun externalLogin(username: String, password: String, clientId: String, clientSecret: String, grantType: String, socialId: Int): Response<OAuth2AccessToken?> {
        return try {
            ResponseFactory.succesfull(getToken(username, password, clientId, clientSecret, grantType))
        } catch (e: Exception) {
            when (e) {
                is InvalidGrantException -> ResponseFactory.failure(FAILURE_BAD_CREDENTIALS)
                else -> ResponseFactory.error(className, e.message)
            }
        }
    }

    override fun refresh(refreshToken: String, clientId: String, clientSecret: String, grantType: String): Response<OAuth2AccessToken?> {
        return try {
            ResponseFactory.succesfull(getTokenWithRefresh(refreshToken, clientId, clientSecret, grantType))
        } catch (e: Exception) {
            when (e) {
                else -> ResponseFactory.error(className, e.message)
            }
        }
    }

    override fun forgotPassword(email: String): Response<Boolean> {
        try {
            userMapper.getByUsername(email)?.let { it ->

                val password = Function.getPasswordRandom()

                val body = Mailing.getBody(Mailing.TYPE_CHANGE_PASSWORD, it.firstName!!, password)

                userMapper.updatePasswordForForgot(it.id!!, encoder.encode(password))
                Mailing.run {
                    sendMail(RECOVER_PASSWORD_SUBJECT, body!!, email)
                }
                return ResponseFactory.succesfull(true, Messages.SEND_EMAIL)
            }
            return ResponseFactory.failure(Messages.FAILURE_EMAIL_NOT_FOUND)
        } catch (e: Exception) {
            return ResponseFactory.error(className, e.message)
        }
    }

    private fun getToken(username: String, password: String, clientId: String, clientSecret: String, grantType: String): OAuth2AccessToken? {

        val parameters = HashMap<String, String>()
        parameters["username"] = username
        parameters["password"] = password
        parameters["client_id"] = clientId
        parameters["client_secret"] = clientSecret
        parameters["grant_type"] = grantType

        val authorizationRequest = defaultOAuth2RequestFactory.createAuthorizationRequest(parameters)
        authorizationRequest.isApproved = true


        val authorities = HashSet<GrantedAuthority>()
        authorities.add(SimpleGrantedAuthority("USER"))
        authorizationRequest.authorities = authorities

        val resourceIds = HashSet<String>()
        resourceIds.add("api")
        authorizationRequest.resourceIds = resourceIds

        // Create principal and auth token
        val userPrincipal = User(clientId, clientSecret, true, true, true, true, authorities)
        val principal = UsernamePasswordAuthenticationToken(userPrincipal, clientSecret, authorities)

        return tokenEndpoint.postAccessToken(principal, parameters).body

    }

    private fun getTokenWithRefresh(refreshToken: String, clientId: String, clientSecret: String, grantType: String): OAuth2AccessToken? {

        val parameters = HashMap<String, String>()
        parameters["refresh_token"] = refreshToken
        parameters["client_id"] = clientId
        parameters["client_secret"] = clientSecret
        parameters["grant_type"] = grantType

        val authorizationRequest = defaultOAuth2RequestFactory.createAuthorizationRequest(parameters)
        authorizationRequest.isApproved = true

        val authorities = HashSet<GrantedAuthority>()
        authorities.add(SimpleGrantedAuthority("USER"))
        authorizationRequest.authorities = authorities

        val resourceIds = HashSet<String>()
        resourceIds.add("api")
        authorizationRequest.resourceIds = resourceIds
        println("INGRESO")
        // Create principal and auth token
        val userPrincipal = User(clientId, clientSecret, true, true, true, true, authorities)
        val principal = UsernamePasswordAuthenticationToken(userPrincipal, clientSecret, authorities)
        println("INGRESO 2")
        return tokenEndpoint.postAccessToken(principal, parameters).body

    }
}
