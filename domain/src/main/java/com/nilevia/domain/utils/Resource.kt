package com.nilevia.domain.utils

data class Resource<T>(val status: Status, val data: T?= null) {
    enum class Status{
        LOADING, SUCCESS, ERROR
    }
}