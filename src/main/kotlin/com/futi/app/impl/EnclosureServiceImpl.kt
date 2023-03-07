package com.futi.app.impl

import com.futi.app.dao.EnclosureMapper
import com.futi.app.dao.FieldMapper
import com.futi.app.domain.Enclosure
import com.futi.app.domain.Response
import com.futi.app.service.EnclosureService
import com.futi.app.utils.ResponseFactory
import com.futi.app.utils.SafeLet
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.math.*
import com.futi.app.utils.Constants
import com.futi.app.utils.Function

@Service("enclosureService")
class EnclosureServiceImpl : EnclosureService {

    val className = this.javaClass.simpleName ?: "EnclosureServiceImpl"

    @Autowired
    lateinit var enclosureMapper: EnclosureMapper

    @Autowired
    lateinit var fieldMapper: FieldMapper

    override fun getEnclosure(idRecinto: Int): Response<Enclosure> {
        return try {
            ResponseFactory.succesfull(enclosureMapper.getEnclosure(idRecinto))
        } catch (e: Exception) {
            return ResponseFactory.error(className, e.message)
        }
    }

    override fun getEnclosureWeb(id: Int): Response<Enclosure> {
        return try {

            val enclosure = enclosureMapper.getEnclosureWeb(id)

            val fields = enclosure.enclosureId?.let { fieldMapper.findFieldsByEnclosureWebId(it) }

            fields?.forEach {
                it.schedules = it.id?.let { fieldId -> fieldMapper.findFieldScheduleWebId(fieldId) }
            }

            enclosure.pitches = fields

            ResponseFactory.succesfull(enclosure)
        } catch (e: Exception) {
            return ResponseFactory.error(className, e.message)
        }
    }

    override fun getEnclosures(latitud: Double?, longitud: Double?): Response<List<Enclosure>> {
        return try {

            val (lat, lon) = SafeLet.guardLet(latitud, longitud) { return ResponseFactory.succesfull(enclosureMapper.getEnclosures()) }

            val enclosures = enclosureMapper.getEnclosures()
            enclosures.forEach {
                SafeLet.ifLet(it.latitude, it.longitude) { (nlat, nlon) ->
                    it.distance = Function.getDistance(nlat, lat, nlon, lon, 0.0, 0.0)
                }
            }
            return ResponseFactory.succesfull(enclosures)

        } catch (e: Exception) {
            return ResponseFactory.error(className, e.message)
        }
    }

    override fun findEnclosures(keyword: String): Response<List<Enclosure>> {
        return try {
            ResponseFactory.succesfull(enclosureMapper.findEnclosures("%$keyword%"))
        } catch (e: Exception) {
            return ResponseFactory.error(className, e.message)
        }
    }


}