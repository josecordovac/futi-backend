package com.futi.app.dao

import com.futi.app.domain.Enclosure
import com.futi.app.utils.Queries
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Result
import org.apache.ibatis.annotations.Results
import org.apache.ibatis.annotations.Select

interface EnclosureMapper {

    /** SELECTS */
    @Select("select r.recinto_id as idRecinto, r.distrito_id as idDistrito, r.nombre, d.nombre as distrito, r.direccion, r.correo, r.telefono1, r.telefono2, r.longitud, r.latitud, cochera, vestidor, banios, duchas, kiosco, chaleco, balon, equipamiento from recinto r INNER JOIN distrito d ON r.distrito_id = d.distrito_id INNER JOIN detalle_recinto dr ON r.recinto_id = dr.recinto_id where activo = 1 and r.recinto_id = #{id}")
    fun getEnclosure(idRecinto: Int): Enclosure

    /** SELECTS */
    @Select(Queries.SEARCH_ENCLOSURE)
    @Results(value = [
        Result(property = "addresses.latitude", column = "latitude"),
        Result(property = "addresses.longitude", column = "longitude"),
        Result(property = "addresses.street", column = "direction"),
        Result(property = "addresses.district", column = "district")
    ])
    fun getEnclosureWeb(id: Int): Enclosure


    @Select("select r.recinto_id as idRecinto, r.distrito_id as idDistrito, r.nombre, d.nombre as distrito, r.direccion, r.correo, r.telefono1, r.telefono2, r.longitud, r.latitud from recinto r INNER JOIN distrito d ON r.distrito_id = d.distrito_id where activo = 1")
    fun getEnclosures(): List<Enclosure>

    /** SELECTS */
    @Select("select r.recinto_id as idRecinto, r.distrito_id as idDistrito, r.nombre, d.nombre as distrito, r.direccion, r.correo, r.telefono1, r.telefono2, r.longitud, r.latitud from recinto r INNER JOIN distrito d ON r.distrito_id = d.distrito_id where activo = 1 and r.nombre like #{keyword}")
    fun findEnclosures(keyword: String): List<Enclosure>

    /** SELECTS */
    @Select("SELECT enclosure_id  AS enclosureId FROM user_enclosure WHERE user_id = #{userId}")
    fun findByUserId(userId: Long): List<Enclosure>

    /** INSERT */
    @Insert("INSERT INTO user_enclosure (user_id,enclosure_id)VALUES(#{userId},#{enclosureId})")
    fun createEnclosureUser(userId: Long, enclosureId: Int)

}
