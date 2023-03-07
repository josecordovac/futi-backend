package com.futi.app.config.security

import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import javax.servlet.*
import javax.servlet.http.HttpServletRequest

import javax.servlet.http.HttpServletResponse

@Order(Ordered.HIGHEST_PRECEDENCE)
class CorsFilter : Filter {

    override fun destroy() {
        super.destroy()
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val servletResponse = response as HttpServletResponse
        val servletRequest = request as HttpServletRequest
        response.setHeader("Access-Control-Allow-Origin", "*")
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS")
        response.setHeader("Access-Control-Allow-Headers", "*")
        response.setHeader("Access-Control-Allow-Credentials", "true")
        response.setHeader("Access-Control-Max-Age", "180")
        chain?.doFilter(servletRequest, servletResponse)
    }

    override fun init(filterConfig: FilterConfig?) {
        super.init(filterConfig)
    }
}