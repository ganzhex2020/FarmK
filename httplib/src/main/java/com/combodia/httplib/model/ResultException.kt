package com.combodia.httplib.model

class ResultException (var errCode: Int, var errorMsg: String?) : Exception(errorMsg)
