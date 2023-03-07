package com.futi.app.config.security

import com.futi.app.utils.Constants
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.web.session.SessionManagementFilter

@Configuration
@EnableResourceServer
class ResourceServer : ResourceServerConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {

        http.csrf().disable()
        http.httpBasic().disable()
        http.addFilterBefore(corsFilter(), SessionManagementFilter::class.java)  // Only for development
        http.anonymous().and()
                .authorizeRequests()
                .antMatchers("/${Constants.VERSION_1}/login/**").permitAll()
                .antMatchers("/${Constants.VERSION_1}/profile/**").permitAll()
                .antMatchers("/${Constants.VERSION_1}/version").permitAll()
                .antMatchers("/${Constants.VERSION_1}/configuration").permitAll()
                .antMatchers("/${Constants.VERSION_1}/register").permitAll()
                .antMatchers("/${Constants.VERSION_1}/business/*").hasAnyAuthority("USER")
                //.antMatchers("/${Constants.VERSION_1}/user/*").hasAnyAuthority("ADMIN")
                .antMatchers("/${Constants.VERSION_1}/user/*").permitAll()
                //WEB
                .antMatchers("/${Constants.VERSION_1}/oauth/**").permitAll()
                .antMatchers("/${Constants.VERSION_1}/${Constants.WEB}/enclosure/**").permitAll()
                .antMatchers("/${Constants.VERSION_1}/${Constants.WEB}/login/**").permitAll()
                //MOVIL
                .antMatchers("/${Constants.VERSION_1}/${Constants.MOVIL}/enclosure/**").permitAll()
                .antMatchers("/oauth/**").permitAll()
                .antMatchers("/${Constants.VERSION_1}/configuration/client").access("hasAuthority('CLIENT')")

                //.antMatchers("/spring-security-rest/**").permitAll()
                //.antMatchers("/swagger-ui.html").permitAll()
                //.antMatchers("/springfox/**").permitAll()
                //.antMatchers("/v2/api-docs/**").permitAll()
                //.antMatchers("/swagger-resources/configuration/ui").permitAll()
                //.antMatchers("/${Constants.VERSION_1}/users").access("hasAuthority('ADMIN')")
                //.antMatchers("/${Constants.VERSION_1}/project", "/${Constants.VERSION_1}/projects").access("hasAuthority('USER')")
                //.antMatchers(HttpMethod.POST,"/${Constants.VERSION_1}/user").permitAll()
                //.antMatchers(HttpMethod.POST, "/${Constants.VERSION_1}/project/**/select").permitAll()
                //.antMatchers("/**").permitAll()  // permite todos los recursos
                //.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().fullyAuthenticated()

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

    }

    @Bean
    fun corsFilter(): CorsFilter? {
        return CorsFilter()
    }

    @Throws(Exception::class)
    override fun configure(resources: ResourceServerSecurityConfigurer?) {
        resources?.resourceId("futiapi")
    }

}