package com.futi.app.utils

import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.*
import kotlin.streams.asSequence


@Component
object Function {
    fun getValueTypeSocial(socialName: String): Int {
        return when {
            socialName.trim { it <= ' ' } == Constants.SOCIAL_FACEBOOK -> {
                Constants.FACEBOOK
            }
            socialName.trim { it <= ' ' } == Constants.SOCIAL_GMAIL -> {
                Constants.GMAIL
            }
            else -> -1
        }
    }

    fun getPasswordRandom(): String {
        return Random().ints(10, 0, Constants.ALLOWED_CHARS.length - 1).asSequence().map(Constants.ALLOWED_CHARS::get).joinToString("")
    }

    fun getDay(date: String): Int {
        val inputDate = SimpleDateFormat("yyyy-MM-dd").parse(date)
        val calendar = Calendar.getInstance()
        calendar.time = inputDate
        return calendar.get(Calendar.DAY_OF_WEEK) - 1
    }

    fun getDistance(lat1: Double, lat2: Double, lon1: Double, lon2: Double, el1: Double, el2: Double, unitMeasurement: Int = 0): Double {

        val r = 6371 // Radius of the earth

        val latDistance = Math.toRadians(lat2 - lat1)
        val lonDistance = Math.toRadians(lon2 - lon1)
        val a = sin(latDistance / 2) * sin(latDistance / 2) + (cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2))
                * sin(lonDistance / 2) * sin(lonDistance / 2))
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        var distance = r.toDouble() * c * 1000.0 // convert to meters

        val height = el1 - el2

        distance = distance.pow(2.0) + height.pow(2.0)

        return when (unitMeasurement) {
            Constants.KILOMETROS -> BigDecimal(sqrt(distance) / 1000).setScale(2, RoundingMode.HALF_EVEN).toDouble()
            else -> {
                BigDecimal(sqrt(distance)).setScale(2, RoundingMode.HALF_EVEN).toDouble()
            }
        }
    }

}