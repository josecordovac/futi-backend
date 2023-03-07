package com.futi.app.dao

import com.futi.app.domain.Field
import com.futi.app.domain.FieldSchedule
import com.futi.app.utils.Queries
import org.apache.ibatis.annotations.Result
import org.apache.ibatis.annotations.Results
import org.apache.ibatis.annotations.Select

interface FieldMapper {


    /** SELECTS */
    @Select("select c.campo_id as idCampo, c.nombre\n" +
            "from campo c \n" +
            "INNER JOIN recinto r on c.recinto_id = r.recinto_id \n" +
            "INNER JOIN usuario_recinto ur ON ur.recinto_id = r.recinto_id\n" +
            "WHERE ur.usuario_id = #{idUsuario} and c.activo = 1")
    fun getFieldsBooking(userId: Long): List<Field>

    /** SELECTS */
    @Select("select c.campo_id as idCampo, c.recinto_id as idRecinto, r.distrito_id as idDistrito, c.superficie_id as idSuperficie, c.deporte_id as idDeporte, c.nombre, d.nombre as distrito\n" +
            "from campo c \n" +
            "INNER JOIN recinto r on c.recinto_id = r.recinto_id \n" +
            "INNER JOIN distrito d ON r.distrito_id = d.distrito_id \n" +
            "INNER JOIN superficie s on c.superficie_id = s.superficie_id\n" +
            "INNER JOIN deporte de on c.deporte_id = de.deporte_id\n" +
            "INNER JOIN usuario_recinto ur ON ur.recinto_id = r.recinto_id\n" +
            "WHERE ur.usuario_id = #{idUsuario} and c.activo = 1")
    fun getFields(userId: Long): List<Field>

    @Select(Queries.SP_FIND_FIELD_ENCLOSURE)
    fun findFieldsByEnclosureId(id: Int, day: String, fromHour: Int?, toHour: Int?, playersIds: String?): List<Field>

    @Select(Queries.FIND_FIELD_ENCLOSURE)
    fun findFieldsByEnclosureId2(id: Int, day: Int): List<Field>


    @Select(Queries.FIND_FIELD_ENCLOSURE_WEB)
    @Results(value = [
        Result(property = "dimensions.width", column = "width"),
        Result(property = "dimensions.length", column = "length")
    ])
    fun findFieldsByEnclosureWebId(id: Int): List<Field>

    @Select(Queries.FIND_FIELD)
    @Results(value = [
        Result(property = "start", column = "start_time"),
        Result(property = "end", column = "end_time")
    ])
    fun findFieldScheduleWebId(id: Int): List<FieldSchedule>

}