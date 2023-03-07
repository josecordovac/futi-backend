package com.futi.app.dao

import com.futi.app.domain.Induction
import org.apache.ibatis.annotations.Select

interface InductionMapper {

    /** SELECTS */
    @Select("select induccion_id as idInduccion, titulo, descripcion, imagen from induccion")
    fun getInduccion(): List<Induction>

    /** SELECTS */
    @Select("SELECT * FROM induction")
    fun getInduction(): List<Induction>


}