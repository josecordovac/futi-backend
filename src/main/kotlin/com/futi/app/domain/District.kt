package com.futi.app.domain

import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
class District: Serializable {

    var id: Int? = null
    var name: String? = null
    var description: String? = null
    var ubigeo: Int? = null
    var enclosures: Int? = null

}