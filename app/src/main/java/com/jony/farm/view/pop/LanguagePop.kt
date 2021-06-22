package com.jony.farm.view.pop

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import com.combodia.httplib.config.Constant
import com.combodia.httplib.config.LanguageType
import com.combodia.httplib.ext.LanguageUtil
import com.jony.farm.R
import com.jony.farm.app.MyApp
import com.jony.farm.ui.activity.MainActivity
import com.jony.farm.util.DeviceUtil
import com.tencent.mmkv.MMKV


class LanguagePop(activity:Activity): PopupWindow() {

    init {
        val view = View.inflate(activity, R.layout.pop_language, null)
        contentView = view
        width = DeviceUtil.dip2px(activity,100f)
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        isFocusable = true
        isTouchable = true
        isOutsideTouchable = true

        this.setOnDismissListener {
            DeviceUtil.setBackgroundAlpha(activity,1f)
        }

        val tv_chinese = view.findViewById<TextView>(R.id.tv_chinese)
        val tv_english = view.findViewById<TextView>(R.id.tv_english)

        tv_chinese.setOnClickListener {
            changeLanguage(activity, LanguageType.CHINESE.language)
            dismiss()
        }
        tv_english.setOnClickListener {
            changeLanguage(activity, LanguageType.ENGLISH.language)
            dismiss()
        }

    }

    private fun changeLanguage(context: Context,language: String) {
        val kv = MMKV.defaultMMKV()
        kv.encode(Constant.KEY_LANGUAGE,language)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            LanguageUtil.changeAppLanguage(context, language)
        }

        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }


}