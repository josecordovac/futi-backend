package com.futi.app.controller

import com.futi.app.domain.Configuration
import com.futi.app.domain.Response
import com.futi.app.domain.User
import com.futi.app.service.ConfigurationService
import com.futi.app.service.UserService
import com.futi.app.utils.Constants
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.websocket.server.PathParam

@RestController
@Api(value = "Setting", description = "Configuration Controller", tags =  ["Setting"] )
class ConfigurationController {

    @Autowired
    lateinit var configurationService: ConfigurationService

    @RequestMapping(value = ["${Constants.VERSION_1}/configuration"], method = [RequestMethod.GET])
    fun getConfiguration(): Response<Configuration?> {
        return configurationService.getConfiguration()
    }

    @RequestMapping(value = ["${Constants.VERSION_1}/configuration/client"], method = [RequestMethod.GET])
    fun getClientConfiguration(): Response<Configuration?> {
        return configurationService.getClientConfiguration()
    }


}