package com.futi.app.config.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.error.DefaultOAuth2ExceptionRenderer
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import org.springframework.security.web.access.AccessDeniedHandler
import javax.sql.DataSource


@Configuration
@EnableAuthorizationServer
class AuthorizationServer : AuthorizationServerConfigurerAdapter() {

    @Autowired
    lateinit var dataSourceOauth: DataSource

    @Autowired
    private val authenticationManager: AuthenticationManager? = null


    @Throws(Exception::class)
    override fun configure(oauthServer: AuthorizationServerSecurityConfigurer?) {
        oauthServer?.checkTokenAccess("permitAll()")?.checkTokenAccess("isAuthenticated()")?.allowFormAuthenticationForClients();
        //oauthServer?.tokenKeyAccess("permitAll()")?.checkTokenAccess("isAuthenticated()")
        //oauthServer?.allowFormAuthenticationForClients()
        //oauthServer?.accessDeniedHandler(accessDeniedHandler())
    }

    private fun accessDeniedHandler(): AccessDeniedHandler {
        val accessDeniedHandler = OAuth2AccessDeniedHandler()
        accessDeniedHandler.setExceptionRenderer(exceptionRenderer())
        return accessDeniedHandler
    }

    @Throws(Exception::class)
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer?) {
        endpoints?.tokenStore(JdbcTokenStore(dataSourceOauth))
                //?.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                ?.tokenEnhancer(tokenEnhancer())
                ?.authenticationManager(this.authenticationManager)
                ?.tokenServices(tokenServices())
        //?.pathMapping("/oauth/token", "/login/external")
    }

    @Bean
    @Primary
    fun tokenServices(): AuthorizationServerTokenServices {
        val tokenServices = DefaultTokenServices()
        tokenServices.setTokenStore(JdbcTokenStore(dataSourceOauth))
        tokenServices.setTokenEnhancer(tokenEnhancer())
        tokenServices.setSupportRefreshToken(true)
        return tokenServices
    }

    @Bean
    fun tokenEnhancer(): TokenEnhancer {
        return CustomTokenEnhancer()
    }

    @Bean
    fun exceptionRenderer(): DefaultOAuth2ExceptionRenderer {
        return CustomOAuthExceptionRenderer()
    }

    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer?) {
        clients?.jdbc(dataSourceOauth)
        //clients?.jdbc(dataSourceOauth)
        /*clients?.jdbc(dataSourceOauth)?.withClient("futi")
                ?.authorizedGrantTypes("password", "refresh_token")
                ?.authorities("USER")
                ?.scopes("read", "write")
                ?.resourceIds("futiapi")
                ?.secret("winmorefuti")?.and()?.build();*/
    }


}