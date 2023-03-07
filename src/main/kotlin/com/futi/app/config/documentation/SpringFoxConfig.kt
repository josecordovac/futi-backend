package com.futi.app.config.documentation

import com.futi.app.utils.Constants.VERSION_1
import com.futi.app.utils.Constants.VERSION_2
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RequestMethod
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.PathSelectors.regex
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.builders.ResponseMessageBuilder
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.Collections.singletonList
import springfox.documentation.service.SecurityReference

@Configuration
@EnableSwagger2
class SpringFoxConfig {

    val response = listOf(
            ResponseMessageBuilder().code(200).message("OK").build(),
            ResponseMessageBuilder().code(400).message("Controlled error in service").build(),
            ResponseMessageBuilder().code(500).message("Service error").build()
    )

    @Bean
    fun getV1Documentation(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .groupName(VERSION_1)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.futi.app.controller"))
                .paths(regex("/v1.*"))
                .build()
                .securitySchemes(singletonList(securitySchema()))
                .securityContexts(singletonList(securityContext()))
                .pathMapping("/")
                .useDefaultResponseMessages(false)
                .produces(setOf("application/json"))
                .apiInfo(apiInfo(VERSION_1))
                .globalResponseMessage(RequestMethod.GET, response)
                .globalResponseMessage(RequestMethod.POST, response)
                .globalResponseMessage(RequestMethod.PUT, response)
                .globalResponseMessage(RequestMethod.DELETE, response)
    }


    @Bean
    fun getV2Documentation(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .groupName(VERSION_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.futi.app.controller"))
                .paths(regex("/v2.*"))
                .build()
                .securitySchemes(singletonList(securitySchema()))
                .securityContexts(singletonList(securityContext()))
                .pathMapping("/")
                .useDefaultResponseMessages(false)
                .produces(setOf("application/json"))
                .apiInfo(apiInfo(VERSION_2))
                .globalResponseMessage(RequestMethod.GET, response)
                .globalResponseMessage(RequestMethod.POST, response)
                .globalResponseMessage(RequestMethod.PUT, response)
                .globalResponseMessage(RequestMethod.DELETE, response)
    }

    private fun securitySchema(): ApiKey {
        return ApiKey(OAUTH_SCHEMA, AUTHORIZATION_HEADER, "header")
    }


    private fun securityContext(): SecurityContext {
        return SecurityContext
                .builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build()
    }

    private fun defaultAuth(): List<SecurityReference> {
        val authorizationScopes = arrayOfNulls<AuthorizationScope>(2)
        authorizationScopes[0] = AuthorizationScope("read", "Lectura")
        authorizationScopes[1] = AuthorizationScope("write", "Escritura")
        return singletonList(SecurityReference(OAUTH_SCHEMA, authorizationScopes))
    }

    private fun apiInfo(version: String): ApiInfo {
        return ApiInfo(
                "Futi",
                "Futi Web Service",
                version,
                null,
                Contact("Leonardo Castañeda", "http://www.futiapps.com", "futiapps@gmail.com"),
                null,
                null,
                emptyList())
    }

    companion object{
        const val AUTHORIZATION_HEADER = "Authorization"
        const val OAUTH_SCHEMA = "OAuth2"
    }

}