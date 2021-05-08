package com.combodia.httplib.ext//package com.combodia.httplib.ext

import com.combodia.httplib.config.Constant.HTTP_SUCCESS_CODE
import com.combodia.httplib.model.BaseResult
import com.combodia.httplib.model.ResultException
import com.combodia.httplib.model.SealedResult
import com.combodia.httplib.model.StatusCode

inline fun <T : Any> SealedResult<T>.checkSuccess(success: (T) -> Unit) {
    if (this is SealedResult.Success) {
        success(data)
    }
}

inline fun <T:Any> SealedResult<T>.checkError(error: (ResultException) -> Unit) {
    if (this is SealedResult.Error) {
        error(exception)
    }
}

inline fun <T:Any> BaseResult<T>.checkSuccess(success: (T) -> Unit){
    if (this.code == HTTP_SUCCESS_CODE){
        success(data)
    }
}

inline fun <T:Any> BaseResult<T>.checkError(error: (ResultException) -> Unit) {
    if (this.code != HTTP_SUCCESS_CODE) {
        error(ResultException(code,message))
    }
}

