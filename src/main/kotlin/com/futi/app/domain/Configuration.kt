package com.futi.app.domain

import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
class Configuration : Serializable {

    // Usuarios

    var distritos: List<District>? = null
    var induccion: List<Induction>? = null

    var districts: List<District>? = null
    var induction: List<Induction>? = null

    // Clientes
    var horarios: List<Schedule>? = null
    var tiposReserva: List<BookingType>? = null

    //  Parametros
    var players: List<Parameter>? = null
    var services: List<Parameter>? = null
    var orderOptions: List<Parameter>? = null

}