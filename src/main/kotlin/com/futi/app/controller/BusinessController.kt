package com.futi.app.controller

import com.futi.app.domain.Business
import com.futi.app.domain.Enclosure
import com.futi.app.domain.Response
import com.futi.app.domain.request.RequestFavorite
import com.futi.app.service.BusinessService
import com.futi.app.utils.Constants
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.websocket.server.PathParam

@RestController
@CrossOrigin(origins = ["*"])
@Api(value = "Business", description = "Controller for business access", tags = ["Business"])
class BusinessController {

    @Autowired
    lateinit var businessService: BusinessService

    @GetMapping(value = ["${Constants.VERSION_1}/business"])
    fun search(@RequestParam("date") date: String?
               , @RequestParam("fromHour") fromHour: Int?, @RequestParam("toHour") toHour: Int?
               , @RequestParam("latitude") latitude: Double?, @RequestParam("longitude") longitude: Double?
               , @RequestParam("pageSize", defaultValue = "1000") pageSize: Int?, @RequestParam("playersIds") playersIds: String?,
               @RequestParam("districtIds") districtIds: String?, @RequestParam("favorite") favorite: Int?): Response<HashMap<String, Any>> {

        return businessService.search(date, fromHour, toHour, latitude, longitude, pageSize, favorite, playersIds, districtIds)
    }

    @GetMapping(value = ["${Constants.VERSION_1}/business/{id}"])
    fun searchByEnclosureId(@PathVariable("id") id: Int,
                            @RequestParam("date") date: String, @RequestParam("fromHour") fromHour: Int?,
                            @RequestParam("toHour") toHour: Int?, @RequestParam("playersIds") playersIds: String?,
                            @RequestParam("latitude") latitude: Double?, @RequestParam("longitude") longitude: Double?):
            Response<Enclosure> {

        return businessService.searchByEnclosureId(id, date, fromHour, toHour, playersIds, latitude, longitude)
    }

    @PutMapping(value = ["${Constants.VERSION_1}/business/{id}/favorite"])
    fun setFavoriteEnclosure(@PathVariable("id") id: Int,
                            @RequestBody requestFavorite: RequestFavorite):
            Response<Boolean?> {

        return businessService.favorite(id, requestFavorite.favorite)
    }

}