package com.combodia.httplib.model

class BaseResult<out T> (
    val code: Int,
    val message:String,
    val data:T,
    val extendData:Any
)
