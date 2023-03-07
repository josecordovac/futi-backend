package com.futi.app.controller

import com.futi.app.domain.Response
import com.futi.app.domain.User
import com.futi.app.dto.LoginDTO
import com.futi.app.service.LoginService
import com.futi.app.utils.Constants
import com.futi.app.utils.Constants.INTERNAL
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@Api(value = "Login", description = "Controller for user access", tags = ["Login"])
class LoginController {

    @Autowired
    lateinit var loginService: LoginService

    /**
     * LOGIN CUSTOM MAIL
     */

    @PostMapping(value = ["${Constants.VERSION_1}/login/internal"])
    fun loginInternal(@RequestBody loginDTO: LoginDTO): Response<OAuth2AccessToken?> {
        return loginService.internalLogin(loginDTO.email.toString(), loginDTO.password.toString(), loginDTO.client_id.toString(), loginDTO.client_secret.toString(), loginDTO.grant_type.toString(), INTERNAL)
    }

    @PostMapping(value = ["${Constants.VERSION_1}/${Constants.WEB}/login/internal"])
    fun loginInternalWeb(@RequestBody loginDTO: LoginDTO): Response<OAuth2AccessToken?> {
        return loginService.internalLogin(loginDTO.email.toString(), loginDTO.password.toString(), loginDTO.client_id.toString(), loginDTO.client_secret.toString(), loginDTO.grant_type.toString(), INTERNAL)
    }

    /**
     * LOGIN FACEBOOK
     */

    @PostMapping(value = ["${Constants.VERSION_1}/login/registerSocial"])
    fun loginSocial(@RequestBody loginDTO: LoginDTO): Response<OAuth2AccessToken?> {
        return loginService.socialLogin(loginDTO)
    }

    @PostMapping(value = ["${Constants.VERSION_1}/login/forgotPassword"])
    fun forgotPassword(@RequestBody data: Map<String, String>): Response<Boolean> {
        return loginService.forgotPassword(data["email"].toString())
    }

    /**
     * REGISTER
     */

    @RequestMapping(value = ["${Constants.VERSION_1}/login/register"], method = [RequestMethod.POST])
    fun register(@RequestBody user: User): Response<OAuth2AccessToken?> {
        return loginService.register(user);
    }

    /**
     * REFRESH TOKEN
     */

    @PostMapping(value = ["${Constants.VERSION_1}/login/refresh"])
    fun refresh(@RequestBody user: User): Response<OAuth2AccessToken?> {
        return loginService.refresh(user.refreshToken.toString(), user.client_id.toString(), user.client_secret.toString(), user.grant_type.toString())
    }

}