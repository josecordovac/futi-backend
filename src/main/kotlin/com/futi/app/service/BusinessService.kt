package com.futi.app.service

import com.futi.app.domain.Business
import com.futi.app.domain.Enclosure
import com.futi.app.domain.Response
import org.springframework.web.bind.annotation.RequestParam


interface BusinessService {

    fun search(date: String?, fromHour: Int?, toHour: Int?, latitude: Double?, longitude: Double?, pageSize: Int?, favorite: Int?
               , playersIds: String?, districtIds: String?): Response<HashMap<String, Any>>

    fun searchByEnclosureId(id: Int, date: String, fromHour: Int?, toHour: Int?, playersIds: String?, latitude: Double?, longitude: Double?): Response<Enclosure>
    fun favorite(enclosureId: Int, favorite: Int): Response<Boolean?>

}