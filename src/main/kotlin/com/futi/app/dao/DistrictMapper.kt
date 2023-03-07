package com.futi.app.dao

import com.futi.app.domain.District
import com.futi.app.domain.Induction
import org.apache.ibatis.annotations.Select

interface DistrictMapper {


    /** SELECTS */
   // @Select("select d.distrito_id as idDistrito, d.nombre, d.ubigeo, count(r.distrito_id) as recintos from distrito d LEFT JOIN recinto r on r.distrito_id = d.distrito_id where provincia_id = #{idProvincia} group by d.distrito_id")
   // fun getDistricts(idProvincia : Int): List<District>

    /** SELECTS */
    @Select("SELECT d.district_id AS id, d.name AS description, d.ubigeo, COUNT(e.district_id ) AS enclosures FROM district d LEFT JOIN enclosure e ON e.district_id = d.district_id WHERE d.province_id = #{provinceId} group by d.district_id")
    fun getDistricts(provinceId : Int): List<District>


}