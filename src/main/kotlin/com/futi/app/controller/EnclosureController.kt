package com.futi.app.controller

import com.futi.app.domain.Enclosure
import com.futi.app.domain.Response
import com.futi.app.service.EnclosureService
import com.futi.app.service.FavouriteService
import com.futi.app.utils.Constants
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@Api(value = "Enclosures", description = "Controller of the enclosures", tags = ["Enclosures"])
class EnclosureController {

    @Autowired
    lateinit var enclosureService: EnclosureService

    @Autowired
    lateinit var favouriteService: FavouriteService

//    @RequestMapping(value = ["${Constants.VERSION_1}/enclosure/{idRecinto}"], method = [RequestMethod.GET])
//    fun getEnclosure(@PathVariable idRecinto: Int): Response<Enclosure> {
//        return enclosureService.getEnclosure(idRecinto)
//    }

    @GetMapping(value = ["${Constants.VERSION_1}/${Constants.WEB}/enclosure/{id}"])
    fun getEnclosure(@PathVariable id: Int): Response<Enclosure> {
        return enclosureService.getEnclosureWeb(id)
    }

    @RequestMapping(value = ["${Constants.VERSION_1}/enclosures"], method = [RequestMethod.GET])
    fun getEnclosures(@RequestParam(required = false) latitud: Double?,
                      @RequestParam(required = false) longitud: Double?): Response<List<Enclosure>> {
        return enclosureService.getEnclosures(latitud, longitud)
    }

    @RequestMapping(value = ["${Constants.VERSION_1}/enclosure/find/{keyword}"], method = [RequestMethod.GET])
    fun findEnclosure(@PathVariable keyword: String): Response<List<Enclosure>> {
        return enclosureService.findEnclosures(keyword)
    }

    @RequestMapping(value = ["${Constants.VERSION_1}/enclosure/favourite/add/{idRecinto}"], method = [RequestMethod.GET])
    fun addFavourite(@PathVariable idRecinto: Int): Response<Boolean?> {
        return favouriteService.addFavourite(idRecinto)
    }

    @RequestMapping(value = ["${Constants.VERSION_1}/enclosure/favourite/remove/{idRecinto}"], method = [RequestMethod.GET])
    fun removeFavourite(@PathVariable idRecinto: Int): Response<Boolean?> {
        return favouriteService.removeFavourite(idRecinto)
    }

}