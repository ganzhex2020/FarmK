package com.combodia.basemodule.ext

import android.content.Context
import android.content.pm.PackageInfo
import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter

/**
 *Author:ganzhe
 *时间:2020/10/25 16:18
 *描述:This is CommonExt
 */

const val SET_THEME = "set_theme"
const val MY_PAGE_SET_THEME_COLOR = "my_page_set_theme_color"
const val HOME_PAGE_CUT = "home_page_cut"
const val MAIN_PLAZA_CUT = "main_plaza_cut"
const val UPDATE_COLLECT_STATE = "update_collect_state"

//获取包名
fun Context.packageInfo(): PackageInfo = this.packageManager.getPackageInfo(this.packageName, 0)

//获取颜色
fun Context.color(colorRes: Int) = ContextCompat.getColor(this, colorRes)
fun View.color(colorRes: Int) = context.color(colorRes)

//设置字体
fun Context.text(textRes: Int) = this.resources.getString(textRes)
fun View.text(textRes: Int) = context.text(textRes)

//获取主题属性id
fun TypedValue.resourceId(resId: Int, theme: Resources.Theme): Int {
    theme.resolveAttribute(resId, this, true)
    return this.resourceId
}

//加载子布局
fun ViewGroup.inflate(@LayoutRes layoutId: Int, attachToRoot: Boolean = true): View {
    if (layoutId == -1) {
        return this
    }
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}
fun View.visable(isVisable:Boolean){
    if (isVisable){
        this.visibility = View.VISIBLE
    }else{
        this.visibility = View.GONE
    }
}


fun String?.htmlToSpanned() =
    if (this.isNullOrEmpty()) ""
    else HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)


