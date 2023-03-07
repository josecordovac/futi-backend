package com.futi.app.service

import com.futi.app.domain.Response
import com.futi.app.domain.Resume
import com.futi.app.domain.User

interface UserService {

    fun getUser(username: String): User?
    fun getUserInfo(): Response<User?>
    fun validateEmail(code: String): Response<Boolean>
    fun resendEmail(): Response<Boolean?>

    fun getUserSettings(): Response<Resume?>
    fun updatePassword(password: String): Response<Boolean>

}