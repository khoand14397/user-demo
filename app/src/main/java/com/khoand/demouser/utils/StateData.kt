package com.khoand.demouser.utils

data class StateData<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T?): StateData<T> {
            return StateData(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T? = null): StateData<T> {
            return StateData(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T? = null): StateData<T> {
            return StateData(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}