package com.futi.app.domain

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class Resume {
    var verified : Boolean? = false
}