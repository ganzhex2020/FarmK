package com.jony.farm.ui.activity

import android.content.Intent
import android.os.Build
import com.combodia.basemodule.base.BaseActivity
import com.combodia.httplib.config.Constant.KEY_LANGUAGE
import com.combodia.httplib.config.LanguageType
import com.combodia.httplib.ext.LanguageUtil
import com.jony.farm.R
import com.jony.farm.app.MyApp
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.activity_language.*


//@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_LANGUAGE)
class LanguageActivity:BaseActivity() {
    override fun getLayoutResId(): Int = R.layout.activity_language

    override fun initView() {

        tv_chinese.setOnClickListener {
            changeLanguage(LanguageType.CHINESE.language)
        }
        tv_english.setOnClickListener {
            changeLanguage(LanguageType.ENGLISH.language)
        }

    }

    override fun initData() {

    }

    private fun changeLanguage(language: String) {
        val kv = MMKV.defaultMMKV()
        kv.encode(KEY_LANGUAGE,language)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            LanguageUtil.changeAppLanguage(MyApp.CONTEXT, language)
        }

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}