package com.futi.app.dao

import com.futi.app.domain.User
import com.futi.app.dto.LoginDTO
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Select

interface LoginMapper {

    /** SELECTS */
    //@Select("select u.usuario_id as idUsuario, u.usuario, u.contrasena from usuario u INNER JOIN registro r on u.usuario_id = r.usuario_id where social_id = #{identifier}")
    @Select("SELECT u.user_id AS id, u.username, u.password FROM users u INNER JOIN registration r ON u.user_id = r.user_id WHERE r.social_id = #{identifier}")
    fun getUserBySocialID(identifier: String): User?

    /** SELECTS */
    @Select("SELECT user_id AS id, username, password FROM users WHERE email = #{mail}")
    fun getUserByMail(mail: String): User?

    /**INSERTS */
    @Insert("INSERT INTO users (name, lastname, birthday, gender, email, username, password, telephone,district_id) VALUES(#{firstName}, #{lastName}, #{birthday}, #{gender}, #{email}, #{email}, #{password}, #{phone},#{district})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "usuario_id")
    fun createUser(user: User)

    /**INSERTS */
    @Insert("INSERT INTO users (name, lastname, email, username,picture,password,district_id,verified) VALUES(#{firstName}, #{lastName}, #{email}, #{email}, #{picture}, #{password},#{district},#{verified})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "usuario_id")
    fun createUserSocial(loginDTO: LoginDTO)

    @Insert("INSERT INTO registration (social_id, user_id, social_network_id) VALUES(#{socialId}, #{userId}, #{socialNetworkId})")
    fun createRegister(socialId: String, userId: Long, socialNetworkId: Int)

}