package com.futi.app.config.security

import com.futi.app.service.UserService
import com.futi.app.utils.Constants.DEFAULT_PASSWORD
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import java.util.HashMap
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication


class CustomTokenEnhancer : TokenEnhancer {

    @Autowired
    lateinit var userService: UserService

    override fun enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): OAuth2AccessToken {

        val authUser = authentication.principal as User
        val additionalInfo = HashMap<String, Any>()
        println(authUser.username)
        userService.getUser(authUser.username)?.let {
            additionalInfo["user"] = it
        }
        (accessToken as DefaultOAuth2AccessToken).additionalInformation = additionalInfo
        return accessToken
    }


}