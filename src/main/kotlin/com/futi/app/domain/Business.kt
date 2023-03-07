package com.futi.app.domain

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class Business {

    var id: Long? = null
    var distance: Double? = null
    var imageUrl: String? = null
    var districtId: Int? = null
    var affiliated: Boolean? = null
    var name: String? = null
    var playersIds: String? = null
    var rating: Int? = null
    var fromHour: Int? = 7
    var toHour: Int? = 22
    var available: Boolean? = true
    var price: Double? = null
    var pricePerPlayer: Double? = null
    var promoPrice: Double? = null
    var promoPricePerPlayer: Double? = null
    var latitude: Double? = null
    var longitude: Double? = null
    var hasMorePrices: Boolean? = false
    var rangeDistance: Int? = 10

}