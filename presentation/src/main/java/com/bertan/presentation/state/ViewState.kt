package com.bertan.presentation.state

sealed class ViewState<out T> {
    object Loading : ViewState<Nothing>()
    data class Success<T>(val data: T) : ViewState<T>()
    data class Error(val message: String, val exception: Throwable? = null) : ViewState<Nothing>()
}