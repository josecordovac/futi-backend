package com.futi.app.impl

import com.futi.app.config.security.Authorization
import com.futi.app.dao.BookingMapper
import com.futi.app.dao.FieldMapper
import com.futi.app.domain.Field
import com.futi.app.domain.Response
import com.futi.app.domain.Schedule
import com.futi.app.service.BookingService
import com.futi.app.utils.Messages
import com.futi.app.utils.ResponseFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service("bookingServiceImpl")
class BookingServiceImpl : BookingService {

    val className = this.javaClass.simpleName ?: "BookingServiceImpl"

    @Autowired
    lateinit var authorization: Authorization

    @Autowired
    lateinit var bookingMapper: BookingMapper

    @Autowired
    lateinit var fieldMapper: FieldMapper


    override fun getBooking(day: String): Response<List<Field>> {
        try {
            authorization.getUser()?.id?.let { id ->
                val fields = fieldMapper.getFieldsBooking(id)
                fields.forEach { f ->
                    val bookings = bookingMapper.getBookingByDay(f.idCampo!!, day)
                    bookings.forEach { r ->
                        r.horaInicio?.let { r.descInicio = Schedule.formatMinutes(it.toLong()) }
                        r.horaFin?.let { r.descFin = Schedule.formatMinutes(it.toLong()) }
                    }
                    f.reservas = bookings
                }
                return ResponseFactory.succesfull(fields)
            }
            return ResponseFactory.failure(Messages.FAILURE_ADD_FROM_FAVOURITE)
        } catch (e: Exception) {
            return ResponseFactory.error(className, e.message)
        }
    }

}