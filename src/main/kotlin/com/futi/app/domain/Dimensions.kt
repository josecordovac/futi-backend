package com.futi.app.domain

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class Dimensions {
    var width: Double? = null
    var length: Double? = null
}