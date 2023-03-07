package com.futi.app.domain

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class Field {

    var idCampo: Long? = null
    var idRecinto: Int? = null
    var idSuperficie: Int? = null
    var idDeporte: Int? = null
    var deporte: String? = null
    var nombre: String? = null
    var ancho: Int? = null
    var largo: Int? = null
    var activo: Boolean? = null
    var capacidad: Int? = null
    var tribuna: Boolean? = null
    var baul: Boolean? = null
    var mallas: Boolean? = null
    var alumbrado: Boolean? = null
    var reservas: List<Booking>? = null

    var id: Int? = null
    var name: String? = null
    var playersId: Int? = null
    var price: Double? = null
    var pricePerPlayer: Double? = null
    var promoPrice: Double? = null
    var promoPricePerPlayer: Double? = null
    var available: Boolean? = null
    var capacitance: String? = null

    var dimensions: Dimensions? = null
    var schedules: List<FieldSchedule>? = null

}

