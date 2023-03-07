package com.futi.app.service

import com.futi.app.domain.Response
import com.futi.app.domain.User
import com.futi.app.dto.LoginDTO
import org.springframework.security.oauth2.common.OAuth2AccessToken

interface LoginService {

    fun externalLogin(username: String, password: String, clientId: String, clientSecret: String, grantType: String, socialId: Int): Response<OAuth2AccessToken?>
    fun internalLogin(username: String, password: String, clientId: String, clientSecret: String, grantType: String, socialId: Int): Response<OAuth2AccessToken?>
    //fun internalLogin(username: String, password: String, clientId: String, clientSecret: String, grantType: String, socialId: Int): Response<User?>

    //fun socialLogin2(loginDTO: LoginDTO): Response<OAuth2AccessToken?>
    fun socialLogin(loginDTO: LoginDTO): Response<OAuth2AccessToken?>

    //fun socialLogin(identifier: String, data: String, clientId: String, clientSecret: String, grantType: String, socialId: Int): Response<OAuth2AccessToken?>
    fun register(user: User): Response<OAuth2AccessToken?>

    //fun register(data: String, clientId: String, clientSecret: String, grantType: String): Response<OAuth2AccessToken?>
    fun refresh(refreshToken: String, clientId: String, clientSecret: String, grantType: String): Response<OAuth2AccessToken?>

    fun forgotPassword(email: String): Response<Boolean>

}