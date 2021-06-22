package com.jony.farm.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.DisplayMetrics
import android.view.WindowManager
import com.jony.farm.view.GameConfig


object DeviceUtil {

    @JvmStatic
    fun getDeviceMetrics(context: Context):DisplayMetrics{
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMetrics)
        return outMetrics
    }

    @JvmStatic
    fun resizeBitmap(bitmap: Bitmap):Bitmap{
        val width = bitmap.width
        val height = bitmap.height

        val matrix = Matrix()
        matrix.postScale(GameConfig.scaleWidth, GameConfig.scaleHeight)

        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
    }

    @JvmStatic
    fun resizeBitmap(bitmap: Bitmap, w: Float, h: Float):Bitmap{
        val width = bitmap.width
        val height = bitmap.height
        val scaleWidth:Float = (w /width)
        val scaleHeight:Float = (h /height)
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)

        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    @JvmStatic
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    @JvmStatic
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    @JvmStatic
    fun getScreenH(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm.heightPixels
    }
    @JvmStatic
    fun getScreenW(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }

    // 将px值转换为sp值
    @JvmStatic
    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    // 将sp值转换为px值
    @JvmStatic
    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }


    /**
     * 设置页面的透明度
     * @param bgAlpha 1表示不透明
     */
    @JvmStatic
    fun setBackgroundAlpha(activity: Activity, bgAlpha: Float) {
        val lp = activity.window.attributes
        lp.alpha = bgAlpha
        if (bgAlpha == 1f) {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND) //不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND) //此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        activity.window.attributes = lp
    }
}