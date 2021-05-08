package com.combodia.httplib.model

/**
 *Author:ganzhe
 *时间:2020/10/21 4:45 PM
 *描述:This is StateType
 */
enum class LoadStateType {
    SUCCESS,
    ERROR,
    EMPTY,
    LOADING
}
enum class RefreshType{
    START,
    END
}
enum class LoadMoreType{
    COMPLETED,
    END,
    FAIL
}