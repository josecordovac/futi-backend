package com.futi.app.domain

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class Address {

    var latitude: Double? = null
    var longitude: Double? = null
    var street: String? = null
    var department: Int? = null
    var province: Int? = null
    var district: Int? = null
    var zipCode: String? = null

}