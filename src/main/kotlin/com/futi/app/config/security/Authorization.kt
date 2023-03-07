package com.futi.app.config.security



import com.futi.app.domain.User
import com.futi.app.service.UserService
import com.futi.app.utils.Constants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder


class Authorization {

    @Autowired
    lateinit var userService: UserService

    fun getUser(): User? {
        val authentication = SecurityContextHolder.getContext().authentication
        val authUser = authentication.principal as org.springframework.security.core.userdetails.User
        return userService.getUser(authUser.username ?: Constants.EMPTY)
    }

}