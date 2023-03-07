package com.futi.app.dto

class LoginDTO {

    var id: Long? = null
    var socialId: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var password: String? = null
    var client_id: String? = null
    var client_secret: String? = null
    var grant_type: String? = null
    var provider: String? = null
    var picture: String? = null
    var district: Int? = null
    var verified: Int? = null

    override fun toString(): String {
        return "LoginDTO(id=$id, socialId=$socialId, firstName=$firstName, lastName=$lastName, email=$email, password=$password, client_id=$client_id, client_secret=$client_secret, grant_type=$grant_type, provider=$provider, picture=$picture, district=$district)"
    }


}