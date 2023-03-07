package com.futi.app.domain

import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable
import java.sql.Timestamp

@JsonInclude(JsonInclude.Include.NON_NULL)
class User : Serializable {

    var id: Long? = null
    var firstName: String? = null
    var lastName: String? = null
    var birthday: String? = null
    var district: Long? = null
    var phone: String? = null
    var email: String? = null
    var username: String? = null
    var password: String? = null
    var gender: String? = null
    var creationDate: Timestamp? = null
    var changePassword: Boolean = false
    var verified: Boolean? = null
    var role: String? = null
    var active: Boolean? = null
    var grant_type: String? = null
    var client_id: String? = null;
    var client_secret: String? = null
    var refreshToken: String? = null
    var provider: String? = null
    var socialId: String? = null
    var enclosure: Int? = null
    var enclosures: List<Enclosure>? = null


    companion object {
        @JvmStatic
        private val serialVersionUID: Long = -3871125667806923927
    }

    override fun toString(): String {
        return "User(id=$id, firstName=$firstName, lastName=$lastName, birthday=$birthday, district=$district, phone=$phone, email=$email, username=$username, password=$password, gender=$gender, creationDate=$creationDate, changePassword=$changePassword, verified=$verified, role=$role, active=$active, grant_type=$grant_type, client_id=$client_id, client_secret=$client_secret, refreshToken=$refreshToken, provider=$provider, socialId=$socialId, enclosure=$enclosure)"
    }

}