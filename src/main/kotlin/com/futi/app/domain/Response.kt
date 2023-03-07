package com.futi.app.domain

import com.fasterxml.jackson.annotation.JsonInclude


@JsonInclude(JsonInclude.Include.NON_NULL)
class Response<T> {
    var code: Int? = null
    var message: String? = null
    var data: T? = null
}