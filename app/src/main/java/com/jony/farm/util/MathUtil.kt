package com.jony.farm.util

import java.math.BigDecimal

object MathUtil {

    @JvmStatic
    fun getTwoBigDecimal(money: Double): String {
        val d1 = BigDecimal(money.toString())
        val d2 = BigDecimal(1.toString())
        // 四舍五入,保留2位小数
        return d1.divide(d2, 2, BigDecimal.ROUND_DOWN).toString()
    }
}