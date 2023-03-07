package com.futi.app.service

import com.futi.app.domain.Enclosure
import com.futi.app.domain.Field
import com.futi.app.domain.Response

interface FieldService {

    fun getFields(): Response<List<Field>>

    fun findFieldsByEnclosureId(id: Int, day: String, fromHour: Int?, toHour: Int?, playersIds: String?): List<Field>

}