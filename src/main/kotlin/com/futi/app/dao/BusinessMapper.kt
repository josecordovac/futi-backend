package com.futi.app.dao

import com.futi.app.domain.Business
import com.futi.app.domain.Enclosure
import com.futi.app.utils.Queries
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Select

interface BusinessMapper {

    @Select(Queries.SP_SEARCH)
    fun search(vday: String?, from_hour: Int?, to_hour: Int?, players: String?, districts: String?, favorite: Int?, user_id: Long?): List<Business>

    @Select(Queries.SEARCH_BUSINESS)
    fun search2(day: Int, fromHour: Int, toHour: Int, playersIds: List<String>?, districtIds: List<String>?, startLimit: Int, endLimit: Int, orderBy: Int?): List<Business>

    @Select(Queries.SP_SEARCH_ENCLOSURE)
    fun searchByEnclosureId(id: Int, userId: Long): Enclosure

    @Select(Queries.COUNT_SEARCH_BUSINESS)
    fun selectCountEnclosure(day: Int, playersIds: List<String>?, districtIds: List<String>?): Int

    @Insert("REPLACE INTO enclosure_favorite (enclosureid, userid)VALUES(#{enclosureId},#{userId})")
    fun saveFavorite(enclosureId: Int, userId: Long)

    @Delete("DELETE FROM enclosure_favorite WHERE enclosureid = #{enclosureId} and userid = #{userId}")
    fun removeFavourite(enclosureId: Int, userId: Long)
}