package com.futi.app.domain

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class Parameter {

    var id: Int? = null
    var typeId: Int? = null
    var description: String? = null
    var imageUrl: String? = null
    var value: Int? = null

    override fun toString(): String {
        return "Parameter(id=$id, typeId=$typeId, description=$description, imageUrl=$imageUrl)"
    }


}