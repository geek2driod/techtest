package com.techtest.utils

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val message: UiMessage) : Result<T>()
    class Loading<T> : Result<T>()

    fun isSuccess(): Boolean = this is Success
}