package com.futi.app.domain

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class FieldSchedule {

    var fieldId: Int? = null
    var day: Int? = null
    var start: Int? = null
    var end: Int? = null
    var price: Double? = null

}