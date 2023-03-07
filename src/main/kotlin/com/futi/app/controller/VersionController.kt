package com.futi.app.controller

import com.futi.app.domain.Configuration
import com.futi.app.domain.Response
import com.futi.app.domain.Version
import com.futi.app.service.UserService
import com.futi.app.service.VersionService
import com.futi.app.utils.Constants
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(value = "Version", description = "Controller for versioning", tags =  ["Version"] )
class VersionController {

    @Autowired
    lateinit var versionService: VersionService

    @RequestMapping(value = ["${Constants.VERSION_1}/version"], method = [RequestMethod.GET])
    fun getConfiguration(): Response<Version?> {
        return versionService.getVersions()
    }


}