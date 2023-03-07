package com.futi.app.dao

import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert

interface FavouriteMapper {


    /**INSERTS */
    @Insert("replace into favorite (usuario_id, recinto_id) values(#{idUsuario}, #{idRecinto})")
    fun addFavourite(idUsuario: Long, idRecinto: Int)

    /** DELETES */
    @Delete("delete from favorite where usuario_id = #{idUsuario} and recinto_id = #{idRecinto}")
    fun removeFavourite(idUsuario: Long, idRecinto: Int)

}