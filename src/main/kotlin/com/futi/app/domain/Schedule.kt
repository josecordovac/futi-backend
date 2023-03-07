package com.futi.app.domain

import java.util.concurrent.TimeUnit

class Schedule {

    var dia: Int? = null
    var horaApertura: Int? = null
    var horaCierre: Int? = null
    var descApertura: String? = null
    var descCierre: String? = null

    companion object{
        fun formatMinutes(time: Long): String {
            val hours = TimeUnit.MINUTES.toHours(time)
            val minutes = TimeUnit.MINUTES.toMinutes(time) - TimeUnit.HOURS.toMinutes(hours)
            return String.format("%02d:%02d", hours, minutes)
        }
    }

}