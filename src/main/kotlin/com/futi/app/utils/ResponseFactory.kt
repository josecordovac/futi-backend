package com.futi.app.utils

import com.futi.app.domain.Response
import com.futi.app.utils.Constants.EMPTY

object ResponseFactory {

    fun <T> succesfull(response: T, reference: String = EMPTY): Response<T> {
        return Response<T>().apply {
            code = Constants.CODE_OK
            message = if (reference == EMPTY) Constants.MESSAGE_OK else reference
            data = response
        }
    }

    fun <T> succesfull(reference: String = EMPTY): Response<T> {
        return Response<T>().apply {
            code = Constants.CODE_OK
            message = if (reference == EMPTY) Constants.MESSAGE_OK else reference
        }

    }

    fun <T> failure(reference: String): Response<T> {
        return Response<T>().apply {
            code = Constants.CODE_FAILURE
            message = reference
        }
    }

    fun <T> forbidden(reference: String = EMPTY): Response<T> {
        return Response<T>().apply {
            code = Constants.CODE_FORBIDDEN
            message = if (reference == EMPTY) Constants.MESSAGE_FORBIDDEN else reference
        }
    }

    fun <T> error(reference: String, exception: String?): Response<T> {
        return Response<T>().apply {
            code = Constants.CODE_ERROR
            message = "${Constants.MESSAGE_ERROR} : $reference : $exception"
        }
    }

    fun <T> notFound(reference: String?): Response<T> {
        return Response<T>().apply {
            code = Constants.CODE_NOT_FOUND
            message = "${Constants.MESSAGE_NOT_FOUND} : $reference"
        }
    }

}