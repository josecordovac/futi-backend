package com.futi.app.dao

import com.futi.app.domain.Booking
import com.futi.app.domain.BookingType
import org.apache.ibatis.annotations.Select
import java.util.*

interface BookingMapper {

    /** SELECTS */
    @Select("select tipo_id as idTipo, nombre, color from reserva_tipo order by orden asc")
    fun getBookingTypes(): List<BookingType>

    /** SELECTS */
    @Select("select reserva_id as idReserva, tipo_id as idTipo, hora_inicio as horaInicio, hora_fin as horaFin, precio, pagada from reserva where campo_id = #{campoId} and dia = #{dia}")
    fun getBookingByDay(fieldId: Long, day: String): List<Booking>

}