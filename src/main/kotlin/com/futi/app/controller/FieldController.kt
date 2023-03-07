package com.futi.app.controller

import com.futi.app.domain.Enclosure
import com.futi.app.domain.Field
import com.futi.app.domain.Response
import com.futi.app.service.FieldService
import com.futi.app.utils.Constants
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(value = "Fields", description = "Field Controller", tags =  ["Fields"] )
class FieldController {

    @Autowired
    lateinit var fieldService: FieldService

    @RequestMapping(value = ["${Constants.VERSION_1}/fields"], method = [RequestMethod.GET])
    fun getFields(): Response<List<Field>> {
        return fieldService.getFields()
    }

}