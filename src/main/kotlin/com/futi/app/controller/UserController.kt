package com.futi.app.controller


import com.futi.app.domain.Response
import com.futi.app.domain.Resume
import com.futi.app.domain.User
import com.futi.app.service.UserService
import com.futi.app.utils.Constants
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@Api(value = "User", description = "Controller for system users", tags = ["User"])
class UserController {

    @Autowired
    lateinit var userService: UserService


    @RequestMapping(value = ["${Constants.VERSION_1}/user/settings"], method = [RequestMethod.GET])
    fun getUserSettings(): Response<Resume?> {
        return userService.getUserSettings()
    }

    /**
     * Email
     */


    @PostMapping(value = ["${Constants.VERSION_1}/user/email/verifyCode"])
    fun validateEmail(@RequestBody data: Map<String, String>): Response<Boolean> {
        return userService.validateEmail(data["code"].toString())
    }

    @GetMapping(value = ["${Constants.VERSION_1}/user/info"])
    fun getUserInfo(): Response<User?> {
        return userService.getUserInfo()
    }

    @PostMapping(value = ["${Constants.VERSION_1}/user/updatePassword"])
    fun updatePassword(@RequestBody data: Map<String, String>): Response<Boolean> {
        return userService.updatePassword(data["password"].toString())
    }

    @RequestMapping(value = ["${Constants.VERSION_1}/user/email/resendCode"], method = [RequestMethod.GET])
    fun resendEmail(): Response<Boolean?> {
        return userService.resendEmail()
    }

}
