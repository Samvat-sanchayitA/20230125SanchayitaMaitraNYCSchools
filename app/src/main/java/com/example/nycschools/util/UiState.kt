package com.example.nycschools.util

sealed class UiState <T> (
    val data : T ? =  null,
    val error : Throwable ?= null
){
    class Success<T>(data: T) : UiState<T>(data)
    class Loading<T>(data: T? = null) : UiState<T>(data)
    class Error<T>(throwable: Throwable, data: T? = null) : UiState<T>(data, throwable)
}
