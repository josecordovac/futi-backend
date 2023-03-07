package com.futi.app.service

import com.futi.app.domain.Field
import com.futi.app.domain.Response

interface BookingService {
    fun getBooking(day: String): Response<List<Field>>
}