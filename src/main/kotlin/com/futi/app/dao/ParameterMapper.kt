package com.futi.app.dao

import com.futi.app.domain.Parameter
import org.apache.ibatis.annotations.Select

interface ParameterMapper {

    @Select("SELECT * FROM parameters p WHERE p.typeid = #{typeId}")
    fun getParameters(typeId: Int): List<Parameter>

}