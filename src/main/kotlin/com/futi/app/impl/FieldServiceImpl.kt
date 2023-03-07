package com.futi.app.impl

import com.futi.app.dao.FieldMapper
import com.futi.app.domain.Enclosure
import com.futi.app.domain.Field
import com.futi.app.domain.Response
import com.futi.app.service.FieldService
import com.futi.app.utils.ResponseFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service("fieldService")
class FieldServiceImpl : FieldService {

    val className = this.javaClass.simpleName ?: "FieldServiceImpl"

    @Autowired
    lateinit var fieldMapper: FieldMapper

    override fun getFields(): Response<List<Field>> {
        return try {
            ResponseFactory.succesfull(fieldMapper.getFields(1))
        } catch (e: Exception) {
            return ResponseFactory.error(className, e.message)
        }
    }

    override fun findFieldsByEnclosureId(id: Int, day: String, fromHour: Int?, toHour: Int?, playersIds: String?): List<Field> {
        return fieldMapper.findFieldsByEnclosureId(id, day, fromHour, toHour, playersIds)
    }
}