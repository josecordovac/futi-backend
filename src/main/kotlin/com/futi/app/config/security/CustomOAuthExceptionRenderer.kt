package com.futi.app.config.security

import org.springframework.http.HttpEntity
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception
import org.springframework.security.oauth2.provider.error.DefaultOAuth2ExceptionRenderer
import org.springframework.web.context.request.ServletWebRequest


class CustomOAuthExceptionRenderer : DefaultOAuth2ExceptionRenderer() {

    override fun setMessageConverters(messageConverters: MutableList<HttpMessageConverter<*>>?) {
        super.setMessageConverters(messageConverters)
    }

    override fun handleHttpEntityResponse(responseEntity: HttpEntity<*>?, webRequest: ServletWebRequest?) {
        super.handleHttpEntityResponse(responseEntity, webRequest)

        val exception = responseEntity?.body as OAuth2Exception


    }

}