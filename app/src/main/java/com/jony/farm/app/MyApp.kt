package com.jony.farm.app


import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import com.combodia.basemodule.base.BaseApplication
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.config.Constant.KEY_LANGUAGE
import com.combodia.httplib.ext.LanguageUtil
import com.jony.farm.BuildConfig
import com.jony.farm.R
import com.jony.farm.config.AuthonManager
import com.jony.farm.di.appModule
import com.jony.farm.util.ActivityManager
import com.mob.MobSDK
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mmkv.MMKV
import com.xiaojinzi.component.Component
import com.xiaojinzi.component.Config
import com.xiaojinzi.component.impl.application.ModuleManager
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import okhttp3.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import kotlin.properties.Delegates

/**
 *Author:ganzhe
 *时间:2020/10/21 7:56 PM
 *描述:This is MyApp
 */
class MyApp: BaseApplication() {


    companion object {
        var CONTEXT: Context by Delegates.notNull()

    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext

        initComponent()
        initKoin()
        setLanguage()
        MobSDK.init(this)
        AuthonManager.authon()
        sexRxError()
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.transparent, R.color.white) //全局设置主题颜色
            ClassicsHeader(context) //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        }

        this.registerActivityLifecycleCallbacks(object:ActivityLifecycleCallbacks{
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                ActivityManager.getInstance().addActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {
                ActivityManager.getInstance().removeActivity(activity)
            }
        })
    }

    /**
     * 初始化组件化Component框架
     */
    private fun initComponent() {
        // 初始化
        Component.init(
                BuildConfig.DEBUG,
                Config.with(this)
                        .defaultScheme("router")
                        // 使用内置的路由重复检查的拦截器, 如果为 true,
                        // 那么当两个相同的路由发生在指定的时间内后一个路由就会被拦截
                        .useRouteRepeatCheckInterceptor(true)
                        // 1000 是默认的, 表示相同路由拦截的时间间隔
                        .routeRepeatCheckDuration(1000)
                        // 是否打印日志提醒你哪些路由使用了 Application 为 Context 进行跳转
                        .tipWhenUseApplication(true)
                        // 这里表示使用 ASM 字节码技术加载模块, 默认是 false
                        // 如果是 true 请务必配套使用 Gradle 插件, 下一步就是可选的配置 Gradle 插件
                        // 如果是 false 请直接略过下一步 Gradle 的配置
                        //       .optimizeInit(true)
                        // 自动加载所有模块, 打开此开关后下面无需手动注册了
                        // 但是这个依赖 optimizeInit(true) 才会生效
                        //      .autoRegisterModule(true) // 1.7.9+
                        .build()
        )
        // 如果你依赖了 rx 版本,需要配置这句代码,否则删除这句
        //    RxErrorIgnoreUtil.ignoreError()
        // 注册其他业务模块,注册的字符串是上面各个业务模块配置在 build.gradle 中的 HOST
        ModuleManager.getInstance()
            .registerArr("app")
        // 自动加载所有模块, 此功能需要打开开关 optimizeInit(true).
        // 如果你同时也打开了开关 autoRegisterModule(true), 那么这句代码也可省略了, 因为初始化的时候自动帮你注册了
        // ModuleManager.getInstance().autoRegister(); // 1.7.9+
        // 你也可以让框架
        if (BuildConfig.DEBUG) {
            // 框架还带有检查重复的路由和重复的拦截器等功能,在 `debug` 的时候开启它
            Component.check()
        }
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApp)
            androidFileProperties()
            modules(appModule)

        }
    }

    private fun sexRxError() {
        RxJavaPlugins.setErrorHandler {
            if (it is UndeliverableException) {
                //do nothing
            } else {
                LogUtils.error(it)
            }
        }
    }

    /**
     * 对于7.0以下，需要在Application创建的时候进行语言切换
     */
    private fun setLanguage(){
        val kv = MMKV.defaultMMKV()
        val language = LanguageUtil.getDefaultLanguage()
        kv.encode(KEY_LANGUAGE,language)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            LanguageUtil.changeAppLanguage(CONTEXT,language)
        }
    }




}