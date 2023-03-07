package com.futi.app.config.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.provider.ClientDetailsService

import javax.sql.DataSource
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory



@Configuration
@EnableWebSecurity
class WebSecurityConfiguration : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var dataSourceFuti: DataSource

    @Autowired
    lateinit var clientDetailsService: ClientDetailsService

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userDetailsService())?.passwordEncoder(encoder())
    }

    @Throws(Exception::class)
    override fun userDetailsService(): UserDetailsService {
        return JdbcDaoImpl().apply {
            setDataSource(dataSourceFuti)
            usersByUsernameQuery = "SELECT username, password, enabled FROM users WHERE username = ?"
            setAuthoritiesByUsernameQuery("SELECT username, role FROM users WHERE username = ?")
        }
    }

    @Bean
    fun encoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun defaultOAuth2RequestFactory(): DefaultOAuth2RequestFactory {
        return DefaultOAuth2RequestFactory(clientDetailsService)
    }


    override fun configure(web: WebSecurity?) {
        web?.ignoring()?.antMatchers("/v2/api-docs", "/configuration/**", "/swagger-resources/**",  "/swagger-ui.html", "/webjars/**", "/api-docs/**");
    }
}
