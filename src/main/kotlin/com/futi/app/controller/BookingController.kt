package com.futi.app.controller

import com.futi.app.domain.Booking
import com.futi.app.domain.Enclosure
import com.futi.app.domain.Field
import com.futi.app.domain.Response
import com.futi.app.service.BookingService
import com.futi.app.service.EnclosureService
import com.futi.app.service.FavouriteService
import com.futi.app.utils.Constants
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@Api(value = "Bookings", description = "Controller for reservations", tags =  ["Bookings"] )
class BookingController {

    @Autowired
    lateinit var bookingService: BookingService

    @RequestMapping(value = ["${Constants.VERSION_1}/booking"], method = [RequestMethod.GET])
    fun getBookingByDay(@RequestParam dia: String): Response<List<Field>> {
        return bookingService.getBooking(dia)
    }


}