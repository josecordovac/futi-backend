package com.futi.app.domain

import com.google.gson.annotations.SerializedName

class Version {
    @SerializedName("app_version")
    var appVersion: String? = null
    var mandatory: Boolean? = null
    var configuration: String? = null
    var enclosure: Long? = null
}