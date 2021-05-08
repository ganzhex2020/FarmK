package com.jony.farm.util

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

    fun getScreenH(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm.heightPixels
    }

    fun getScreenW(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }

}