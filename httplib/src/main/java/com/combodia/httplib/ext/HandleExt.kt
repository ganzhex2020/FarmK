package com.combodia.httplib.ext


import com.combodia.httplib.R
import com.combodia.httplib.config.Constant
import com.combodia.httplib.config.Constant.HTTP_SUCCESS_CODE
import com.combodia.httplib.model.BaseResult
import com.combodia.httplib.model.ResultException
import com.combodia.httplib.model.SealedResult
import com.combodia.httplib.model.StatusCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import org.json.JSONException
import retrofit2.HttpException
import zlc.season.claritypotion.ClarityPotion
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException


fun handleException(throwable: Throwable): ResultException {
    var errorCode: Int
    var errorMessage = ""
    when (throwable) {
        is ConnectException -> {
            errorCode = StatusCode.Unknown.code
            errorMessage = ClarityPotion.clarityPotion.getString(R.string.exception_msg1)
        }
        is SocketException -> {
            errorCode = StatusCode.Unknown.code
            errorMessage = ClarityPotion.clarityPotion.getString(R.string.exception_msg2)
        }
        is SocketTimeoutException -> {
            errorCode = StatusCode.Unknown.code
            errorMessage = ClarityPotion.clarityPotion.getString(R.string.exception_msg3)
        }
        is HttpException -> {
            errorCode = throwable.code()
            when (throwable.code()) {
                300, 301, 302, 303, 304, 305, 306, 307 -> errorMessage = ClarityPotion.clarityPotion.getString(R.string.exception_msg4)
                400 -> errorMessage = ClarityPotion.clarityPotion.getString(R.string.exception_msg5)
                401 -> errorMessage = ClarityPotion.clarityPotion.getString(R.string.exception_msg6)
                403 -> errorMessage = ClarityPotion.clarityPotion.getString(R.string.exception_msg7)
                404 -> errorMessage = ClarityPotion.clarityPotion.getString(R.string.exception_msg8)
                405 -> errorMessage = ClarityPotion.clarityPotion.getString(R.string.exception_msg9)
                500 -> errorMessage = ClarityPotion.clarityPotion.getString(R.string.exception_msg10)
                501 -> errorMessage = ClarityPotion.clarityPotion.getString(R.string.exception_msg11)
                502 -> errorMessage = ClarityPotion.clarityPotion.getString(R.string.exception_msg12)
                503 -> errorMessage = ClarityPotion.clarityPotion.getString(R.string.exception_msg13)
                504 -> errorMessage = ClarityPotion.clarityPotion.getString(R.string.exception_msg14)
                505 -> errorMessage = ClarityPotion.clarityPotion.getString(R.string.exception_msg15)
                else -> throwable.message()
            }
        }
        is JSONException -> {
            errorCode = StatusCode.Unknown.code
            errorMessage = ClarityPotion.clarityPotion.getString(R.string.exception_msg16)
        }
        is SSLException -> {
            errorCode = StatusCode.Unknown.code
            errorMessage = ClarityPotion.clarityPotion.getString(R.string.exception_msg17)
        }
        is UnknownHostException -> {
            errorCode = StatusCode.Unknown.code
            errorMessage = ClarityPotion.clarityPotion.getString(R.string.exception_msg18)
        }
        else -> {
            errorCode = StatusCode.Unknown.code
            errorMessage = throwable.message.toString()
        }
    }
    return ResultException(errorCode, errorMessage)
}


suspend fun <T : Any> callRequest(call: suspend () -> SealedResult<T>): SealedResult<T> {
    return try {
        call()
    } catch (e: Exception) {
        //这里统一处理异常
        e.printStackTrace()
        SealedResult.Error(handleException(e))
    }
}

suspend fun <T : Any> handleResponse(
    response: BaseResult<T>,
    successBlock: (suspend CoroutineScope.() -> Unit)? = null,
    errorBlock: (suspend CoroutineScope.() -> Unit)? = null
): SealedResult<T> {
    return coroutineScope {
        if (response.code == HTTP_SUCCESS_CODE) {
            successBlock?.let { it() }
            SealedResult.Success (response.data)
        } else {
            errorBlock?.let { it() }
            SealedResult.Error(ResultException(response.code, response.message))
        }

    }

}


