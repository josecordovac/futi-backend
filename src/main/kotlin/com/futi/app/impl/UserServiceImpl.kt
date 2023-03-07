package com.futi.app.impl

import com.futi.app.config.security.Authorization
import com.futi.app.dao.EnclosureMapper
import com.futi.app.dao.UserMapper
import com.futi.app.domain.Response
import com.futi.app.domain.Resume
import com.futi.app.domain.User
import com.futi.app.service.UserService
import com.futi.app.utils.Mailing
import com.futi.app.utils.Messages
import com.futi.app.utils.Messages.VERIFIED_CODE_SUBJECT
import com.futi.app.utils.Messages.WELCOME_SUBJECT
import com.futi.app.utils.ResponseFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service("userService")
class UserServiceImpl : UserService {

    val className = this.javaClass.simpleName ?: "UserServiceImpl"

    @Autowired
    lateinit var encoder: BCryptPasswordEncoder

    @Autowired
    lateinit var userMapper: UserMapper

    @Autowired
    lateinit var enclosureMapper: EnclosureMapper

    @Autowired
    lateinit var authorization: Authorization

    @Value("\${futi.code-validity-time}")
    lateinit var validityTime: String


    override fun getUser(username: String): User? {
        return userMapper.getUser(username)
    }

    override fun getUserInfo(): Response<User?> {

        try {

            authorization.getUser()?.let { it ->
                println("DIME")
                it.enclosures = enclosureMapper.findByUserId(it.id!!)
                return ResponseFactory.succesfull(it)
            }

            return ResponseFactory.failure(Messages.FAILURE_USER_NOT_FOUND)
        } catch (e: Exception) {
            return ResponseFactory.error(className, e.message)
        }
    }


    override fun validateEmail(code: String): Response<Boolean> {

        try {

            authorization.getUser()?.let { user ->
                userMapper.getVerificationCode(user.id!!)?.let { verification ->
                    return if (code == verification.code) {
                        verification.currentTimeMillis?.let {
                            return if ((System.currentTimeMillis() - it) <= validityTime.toLong()) {
                                userMapper.verifyEmail(user.id!!)
                                Mailing.run {
                                    var body = getBody(TYPE_WELCOME, user.firstName!!)
                                    sendMail(WELCOME_SUBJECT, body!!, user.email!!)
                                }
                                ResponseFactory.succesfull(Messages.SUCCESSFUL_VERIFIED_CODE)
                            } else {
                                ResponseFactory.failure(Messages.FAILURE_VERIFICATION_EXPIRED)
                            }
                        }
                        ResponseFactory.failure(Messages.FAILURE_VERIFICATION)

                    } else {
                        ResponseFactory.failure(Messages.FAILURE_VERIFICATION_NOT_EQUAL)
                    }
                }
            }
            return ResponseFactory.failure(Messages.FAILURE_VERIFICATION)
        } catch (e: Exception) {
            return ResponseFactory.error(className, e.message)
        }
    }

    override fun resendEmail(): Response<Boolean?> {
        try {
            authorization.getUser()?.let { user ->
                return if (user.verified == false) {
                    user.id?.let { id ->
                        userMapper.getVerificationCode(id)?.let { verification ->
                            verification.creationDate?.time?.let {
                                var code = Mailing.generateCode()
                                if ((System.currentTimeMillis() - it) <= validityTime.toLong()) {
                                    code = verification.code!!
                                } else {
                                    userMapper.createVerificationCode(id, code)
                                }

                                Mailing.run {
                                    val body = getBody(TYPE_VALIDATION_CODE, user.firstName!!, code)
                                    sendMail(VERIFIED_CODE_SUBJECT, body!!, user.email!!)
                                }

                                return ResponseFactory.succesfull()
                            }
                        }
                    }
                    ResponseFactory.failure(Messages.FAILURE_VERIFICATION)
                } else {
                    ResponseFactory.failure(Messages.FAILURE_ALREADY_VERIFIED)
                }
            }
            return ResponseFactory.failure(Messages.FAILURE_VERIFICATION)
        } catch (e: Exception) {
            return ResponseFactory.error(className, e.message)
        }
    }

    override fun getUserSettings(): Response<Resume?> {
        try {
            authorization.getUser()?.id?.let { id ->
                val resume = userMapper.getUserSettings(id)
                return ResponseFactory.succesfull(resume)
            }
            return ResponseFactory.failure(Messages.FAILURE_USER_NOT_FOUND)
        } catch (e: Exception) {
            return ResponseFactory.error(className, e.message)
        }
    }

    override fun updatePassword(password: String): Response<Boolean> {
        try {
            authorization.getUser()?.id?.let { id ->
                userMapper.updatePassword(id, encoder.encode(password))
                return ResponseFactory.succesfull(true)
            }
            return ResponseFactory.failure(Messages.FAILURE_CHANGE_PASSWORD)
        } catch (e: Exception) {
            return ResponseFactory.error(className, e.message)
        }
    }

}
