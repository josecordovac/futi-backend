package com.futi.app.domain

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class Enclosure {

    var id: Int? = null
    var bannerUrls: String? = null
    var name: String? = null
    var logoUrl: String? = null
    var favorite: Int? = null
    var distance: Double? = null
    var rating: Int? = null
    var ratingCount: Int? = null
    var affiliated: Boolean? = null
    var soccerFields: List<Field>? = null
    var serviceIds: String? = null
    var address: String? = null
    var latitude: Double? = null
    var longitude: Double? = null

    //NO AFILIADO
    var phone: String? = null
    var url: String? = null
    var pricePeHour: Double? = null
    var districtId: Int? = null
    var operatingHours: OperationHour? = null

    //WEB
    var imageUrl: String? = null
    var playersIds: String? = null
    var ball: Boolean? = null
    var bathrooms: Boolean? = null
    var dressing_room: Boolean? = null
    var equipment: Boolean? = null
    var garage: Boolean? = null
    var kiosk: Boolean? = null
    var showers: Boolean? = null
    var vest: Boolean? = null
    var enclosureId: Int? = null
    var social: String? = null
    var email: String? = null
    var documentnum: String? = null
    var district: Int? = null
    var direction: String? = null
    var pitches: List<Field>? = null
    var addresses: Address? = null


}