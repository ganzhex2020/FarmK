package com.combodia.httplib.ext

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import android.util.Log
import com.combodia.httplib.config.Constant.KEY_LANGUAGE
import com.combodia.httplib.config.LanguageType
import com.tencent.mmkv.MMKV
import java.util.*

object LanguageUtil {
    val kv = MMKV.defaultMMKV()
    @JvmStatic
    fun getDefaultLanguage():String{
        var language = kv.decodeString(KEY_LANGUAGE,"")
        if (language.isBlank()){
            language = getSystemLocale()?.language
            if (language!= LanguageType.CHINESE.language){
                language = LanguageType.ENGLISH.language
            }
        }
        return language?:""

    }


    /**
     * 获取系统local
     *
     * @return
     */
    fun getSystemLocale(): Locale?{
        val locale = Resources.getSystem().configuration.locale
        Log.d("SNN", "系统获取  ：getLanguage : " + locale.language + locale.country)
        return locale
    }

    /**
     * 切换语言关键类
     *
     * @param context
     * @param newLanguage
     */
    fun changeAppLanguage(context: Context, newLanguage: String) {

        val resources = context.resources
        val configuration = resources.configuration
        //获取想要切换的语言类型
        val locale: Locale = getLocaleByLanguage(newLanguage)
        configuration.setLocale(locale)
        // updateConfiguration
        val dm = resources.displayMetrics
        resources.updateConfiguration(configuration, dm)
    }

    fun getLocaleByLanguage(language: String): Locale {
        //获取语言状态，得到语言类型，
        val locale: Locale = if (language == LanguageType.CHINESE.language) {
            Locale.SIMPLIFIED_CHINESE
        } else {
            Locale.ENGLISH
        }
        Log.d("getLocaleByLanguage","getLocaleByLanguage: " + locale.displayName)
        return locale
    }

    //判断系统版本，给出不同处理方式
    fun attachBaseContext(context: Context, language: String): Context {
        Log.d("LanguageUtil", "attachBaseContext: " + Build.VERSION.SDK_INT)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else {
            context
        }
    }

    //加载资源文件
    @TargetApi(Build.VERSION_CODES.N)
    fun updateResources(context: Context, language: String): Context {
        val resources = context.resources
        val locale = getLocaleByLanguage(language)
        val configuration = resources.configuration
        configuration.setLocale(locale)
        configuration.setLocales(LocaleList(locale))
        return context.createConfigurationContext(configuration)
    }
}