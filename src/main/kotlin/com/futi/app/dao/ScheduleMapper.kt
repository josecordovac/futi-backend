package com.futi.app.dao

import com.futi.app.domain.Field
import com.futi.app.domain.Schedule
import org.apache.ibatis.annotations.Select

interface ScheduleMapper {



    /** SELECTS */
    @Select("select dia, hora_apertura as horaApertura, hora_cierre as horaCierre from horario_recinto hr \n" +
            "INNER JOIN recinto r on r.recinto_id = hr.recinto_id \n" +
            "INNER JOIN usuario_recinto ur ON ur.recinto_id = r.recinto_id\n" +
            "WHERE ur.usuario_id = #{idUsuario}")
    fun getEnclosureSchedule(idUsuario : Long): List<Schedule>



}