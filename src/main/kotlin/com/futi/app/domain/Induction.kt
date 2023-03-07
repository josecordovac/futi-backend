package com.futi.app.domain

import com.fasterxml.jackson.annotation.JsonInclude
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
class Induction: Serializable {

    var induction_id: Int? = null
    var title: String? = null
    var description: String? = null
    var image : String? = null

}