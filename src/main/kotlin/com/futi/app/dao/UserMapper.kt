package com.futi.app.dao

import com.futi.app.domain.Resume
import com.futi.app.domain.User
import com.futi.app.domain.Verification
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

/**
 * Created by Leo on 09/02/2018.
 */
interface UserMapper {

    //    /** SELECTS */
    @Select("SELECT u.user_id AS id, name AS firstName, lastname, creation_date AS creationDate, gender, email, telephone AS phone, role, verified, birthday,district_id AS district, r.social_id AS socialId, CASE WHEN r.social_network_id =  1 THEN 'facebook' WHEN r.social_network_id =  2 THEN 'gmail' ELSE 'internal' END AS provider,change_password AS changePassword, ue.enclosure_id AS enclosure FROM users u LEFT JOIN registration r ON u.user_id = r.user_id LEFT JOIN user_enclosure ue ON ue.user_id = u.user_id WHERE u.username = #{username} ORDER BY enclosure DESC LIMIT 1")
    fun getUser(username: String): User

    @Select("SELECT verification_id AS id, user_id AS userId, code, creation_date AS creationDate, (UNIX_TIMESTAMP(creation_date)*1000) AS currentTimeMillis FROM verification WHERE user_id = #{userId} ORDER BY creation_date DESC LIMIT 1")
    fun getVerificationCode(userId: Long): Verification?

    @Select("SELECT verified FROM users WHERE id = #{userId}")
    fun getUserSettings(userId: Long): Resume

    /**INSERTS */
    @Update("UPDATE users SET verified = 1 WHERE user_id = #{userId}")
    fun verifyEmail(userId: Long): Int

    @Insert("INSERT INTO verification (user_id, code) VALUES(#{userId}, #{code})")
    fun createVerificationCode(userId: Long, code: String)

    @Select("SELECT u.user_id AS id, name AS firstName FROM users u WHERE u.username = #{username}")
    fun getByUsername(username: String): User

    @Update("UPDATE users SET password = #{password}, change_password = 0 WHERE user_id = #{userId} AND verified = 1")
    fun updatePassword(userId: Long, password: String): Boolean

    @Update("UPDATE users SET password = #{password}, change_password = 1 WHERE user_id = #{userId}")
    fun updatePasswordForForgot(userId: Long, password: String): Boolean

    //@Select("select id_user, name, surename, username, subscription, unlimited, role, enabled, create_at from user")
    //fun getUsers(): List<User>

    /**INSERTS */
    //@Insert("insert into users (name, surename, username, password, birthday, gender, mobile) " +
    //      "values(#{name}, #{surename}, #{username}, #{password}, #{birthday}, #{gender}, , #{mobile})")
    //fun createUser(user: User): Int

    /** UPDATES */
    //@Update("update users set name = #{user.name}, surename = #{user.surename}, password = #{user.password}, birthday = #{user.birthday}, gender = #{user.gender}, mobile = #{user.mobile} where id_user = #{id_user}")
    //fun updateUser(id_user: Int, user: User): Int

    /** DELETES */
    //@Delete("delete from users where id_user = #{id_user}")
    //fun deleteUser(id_user: Int): Int

}
