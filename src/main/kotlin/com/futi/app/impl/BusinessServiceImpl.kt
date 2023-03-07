package com.futi.app.impl

import com.futi.app.config.security.Authorization
import com.futi.app.dao.BusinessMapper
import com.futi.app.dao.FieldMapper
import com.futi.app.domain.*
import com.futi.app.service.BusinessService
import com.futi.app.utils.*
import com.futi.app.utils.Function
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BusinessServiceImpl : BusinessService {

    val className = this.javaClass.simpleName ?: "BusinessServiceImpl"

    @Autowired
    lateinit var
            businessMapper: BusinessMapper

    @Autowired
    lateinit var fieldMapper: FieldMapper

    @Autowired
    lateinit var authorization: Authorization

    override fun search(date: String?, fromHour: Int?, toHour: Int?, latitude: Double?, longitude: Double?, pageSize: Int?, favorite: Int?
                        , playersIds: String?, districtIds: String?): Response<HashMap<String, Any>> {

        var enclosures: List<Business>? = null
        var rangeDistance: Int = 10

        authorization.getUser()?.id?.let {
            enclosures = businessMapper.search(date, fromHour, toHour, playersIds, districtIds, favorite, it)
        }

        if (latitude != null && longitude != null) {
            enclosures?.forEach {
                SafeLet.ifLet(it.latitude, it.longitude) { (nlat, nlon) ->
                    it.distance = Function.getDistance(nlat, latitude, nlon, longitude, 0.0, 0.0, Constants.KILOMETROS)
                }
            }
        }

        enclosures?.forEach {
            rangeDistance = it.rangeDistance!!
            if (it.affiliated == false){
                it.districtId = null
                it.playersIds = null
                it.rating = null
                it.fromHour = null
                it.toHour = null
                it.available = null
                it.pricePerPlayer = null
                it.latitude = null
                it.longitude = null
                it.hasMorePrices = null
            }
            it.rangeDistance = null;
        }


        val result = HashMap<String, Any>()

        if (date == null && fromHour == null && toHour == null && (favorite == 0 || favorite == null)){
            result["results"] = enclosures!!.filter { it.distance!! < rangeDistance}
        }else {
            result["results"] = enclosures!!
        }

        return ResponseFactory.succesfull(result)
    }

    override fun searchByEnclosureId(id: Int, date: String, fromHour: Int?, toHour: Int?, playersIds: String?, latitude: Double?, longitude: Double?): Response<Enclosure> {

        val enclosure: Enclosure

        authorization.getUser()?.id.let {
            enclosure = businessMapper.searchByEnclosureId(id, it!!)
        }

        SafeLet.ifLet(latitude, longitude) { (inputLatitude, inputLongitude) ->
            enclosure.distance = Function.getDistance(enclosure.latitude!!, inputLatitude, enclosure.longitude!!, inputLongitude, 0.0, 0.0, Constants.KILOMETROS)
        }

        if (enclosure.affiliated == true) {
            enclosure.soccerFields = fieldMapper.findFieldsByEnclosureId(id, date, fromHour, toHour, playersIds)
            enclosure.districtId = null
            enclosure.phone = null
            enclosure.url = null
            enclosure.pricePeHour = null
            enclosure.operatingHours = null
        }else{
            val operationHour: OperationHour = OperationHour()
            operationHour.open = 10.00
            operationHour.close = 23.00
            enclosure.operatingHours = operationHour
            enclosure.pricePeHour = 120.00
            enclosure.bannerUrls = null;
            enclosure.logoUrl = null;
            enclosure.rating = null;
            enclosure.ratingCount = null;
            enclosure.latitude = null;
            enclosure.longitude = null;
        }

        return ResponseFactory.succesfull(enclosure)
    }

    override fun favorite(enclosureId: Int, favorite: Int): Response<Boolean?> {

        return try {
            authorization.getUser()?.id.let {
                if (favorite == 1){
                    businessMapper.saveFavorite(enclosureId, it!!)
                }else{
                    businessMapper.removeFavourite(enclosureId, it!!)
                }
            }
            ResponseFactory.succesfull()
        } catch (e: Exception) {
            ResponseFactory.error(className, e.message)
        }

    }
}