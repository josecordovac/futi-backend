package com.futi.app.service

import com.futi.app.domain.Enclosure
import com.futi.app.domain.Response

interface EnclosureService {

    fun getEnclosure(id: Int): Response<Enclosure>
    fun getEnclosureWeb(id: Int): Response<Enclosure>
    fun getEnclosures(latitud: Double?, longitud: Double?): Response<List<Enclosure>>
    fun findEnclosures(keyword: String): Response<List<Enclosure>>

}