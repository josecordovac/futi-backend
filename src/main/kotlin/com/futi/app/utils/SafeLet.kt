package com.futi.app.utils

object SafeLet {
    inline fun <T: Any> guardLet(vararg elements: T?, closure: () -> Nothing): List<T> {
        return if (elements.all { it != null }) {
            elements.filterNotNull()
        } else {
            closure()
        }
    }

    inline fun <T: Any> ifLet(vararg elements: T?, closure: (List<T>) -> Unit) {
        if (elements.all { it != null }) {
            closure(elements.filterNotNull())
        }
    }
}