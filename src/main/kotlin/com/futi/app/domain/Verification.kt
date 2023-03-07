package com.futi.app.domain

import java.sql.Timestamp

class Verification {

    var id: Long? = null
    var userId: Long? = null
    var code: String? = null
    var creationDate: Timestamp? = null
    var currentTimeMillis:Long? = null;

}