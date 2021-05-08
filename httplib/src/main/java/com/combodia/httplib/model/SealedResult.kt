package com.combodia.httplib.model

sealed class SealedResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : SealedResult<T>()
    data class Error(val exception: ResultException) : SealedResult<Nothing>()
}


/*
inline fun <T : Any> SealedResult<T>.checkSuccess(success: (T) -> Unit) {
    if (this is SealedResult.Success) {
        success(data)
    }
}

inline fun <T:Any>SealedResult<T>.checkError(error: (ResultException) -> Unit) {
    if (this is SealedResult.Error) {
        error(exception)
    }
}*/
