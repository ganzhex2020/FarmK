package com.combodia.httplib.ext


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
            errorMessage = "连接异常"
        }
        is SocketException -> {
            errorCode = StatusCode.Unknown.code
            errorMessage = "网络连接错误"
        }
        is SocketTimeoutException -> {
            errorCode = StatusCode.Unknown.code
            errorMessage = "网络超时"
        }
        is HttpException -> {
            errorCode = throwable.code()
            when (throwable.code()) {
                300, 301, 302, 303, 304, 305, 306, 307 -> errorMessage = "资源被重定向"
                400 -> errorMessage = "请求语法错误"
                401 -> errorMessage = "身份认证过期"
                403 -> errorMessage = "服务器已拒绝，请先登录"
                404 -> errorMessage = "服务器未发现资源"
                405 -> errorMessage = "异地登录"
                500 -> errorMessage = "服务器内部错误"
                501 -> errorMessage = "服务器不支持"
                502 -> errorMessage = "网关错误"
                503 -> errorMessage = "服务器超负载"
                504 -> errorMessage = "服务器网关超时"
                505 -> errorMessage = "服务器不支持请求的协议"
                else -> throwable.message()
            }
        }
        is JSONException -> {
            errorCode = StatusCode.Unknown.code
            errorMessage = "解析异常"
        }
        is SSLException -> {
            errorCode = StatusCode.Unknown.code
            errorMessage = "数字格式化异常"
        }
        is UnknownHostException -> {
            errorCode = StatusCode.Unknown.code
            errorMessage = "网络异常"
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


