package com.futi.app.impl

import com.futi.app.config.security.Authorization
import com.futi.app.dao.*
import com.futi.app.domain.Configuration
import com.futi.app.domain.Response
import com.futi.app.domain.Schedule
import com.futi.app.service.ConfigurationService
import com.futi.app.utils.Constants
import com.futi.app.utils.Messages
import com.futi.app.utils.ResponseFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service("configurationService")
class ConfigurationServiceImpl : ConfigurationService {

    val className = this.javaClass.simpleName ?: "ConfigurationServiceImpl"

    @Autowired
    lateinit var authorization: Authorization

    @Autowired
    lateinit var inductionMapper: InductionMapper

    @Autowired
    lateinit var districtMapper: DistrictMapper

    @Autowired
    lateinit var scheduleMapper: ScheduleMapper

    @Autowired
    lateinit var bookingMapper: BookingMapper

    @Autowired
    lateinit var parameterMapper: ParameterMapper


    override fun getConfiguration(): Response<Configuration?> {
        return try {

            val configuration = Configuration().apply {
                induccion = inductionMapper.getInduction()
                districts = districtMapper.getDistricts(Constants.PROVINCE_LIMA)
                players = parameterMapper.getParameters(Constants.PARAMETER_TYPE_PLAYER)
                services = parameterMapper.getParameters(Constants.PARAMETER_TYPE_SERVICE)
                orderOptions = parameterMapper.getParameters(Constants.PARAMETER_TYPE_ORDEROPTION)
            }
            ResponseFactory.succesfull(configuration)

        } catch (e: Exception) {
            ResponseFactory.error(className, e.message)
        }
    }


    override fun getClientConfiguration(): Response<Configuration?> {
        return try {
            authorization.getUser()?.id?.let { id ->

                val configuration = Configuration().apply {
                    tiposReserva = bookingMapper.getBookingTypes()
                    horarios = scheduleMapper.getEnclosureSchedule(id)
                    horarios?.forEach { h ->
                        h.horaApertura?.let { h.descApertura = Schedule.formatMinutes(it.toLong()) }
                        h.horaCierre?.let { h.descCierre = Schedule.formatMinutes(it.toLong()) }
                    }
                }
                return ResponseFactory.succesfull(configuration)

            }
            ResponseFactory.failure(Messages.FAILURE_CLIENT_CONFIG)
        } catch (e: Exception) {
            ResponseFactory.error(className, e.message)
        }
    }
}