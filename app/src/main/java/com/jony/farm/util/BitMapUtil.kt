package com.jony.farm.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.provider.MediaStore
import android.widget.ImageView
import com.combodia.basemodule.utils.LogUtils
import com.jony.farm.config.Const.PATH_FILE_PICTURE

import okio.IOException
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


object BitMapUtil {

    fun saveBitmap(context: Context,fileName:String,bitmap: Bitmap){
        /*try {
            val file = File(path)
            if (file.exists()){
                file.delete()
            }
            //outputStream获取文件的输出流对象
            //writer获取文件的Writer对象
            //printWriter获取文件的PrintWriter对象
            val fos :OutputStream = file.outputStream()
            //压缩格式为JPEG图像，压缩质量为80%
            bitmap.compress(Bitmap.CompressFormat.JPEG,80,fos)
            fos.flush()
            fos.close()

        }catch (e:Exception){
            e.printStackTrace()
        }*/
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        val path = FileUtils.getAppPicturePath()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, path)
        } else {
            val fileDir = File(path)
            if (!fileDir.exists()){
                fileDir.mkdir()
            }
            contentValues.put(MediaStore.MediaColumns.DATA, path + fileName)
        }
        val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        uri?.also {
            val outputStream = context.contentResolver.openOutputStream(it)
            outputStream?.also { os ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
                os.close()

            }
        }
    }

    fun readBitmapByByteArray(path:String): Bitmap {
        val bytes = File(path).readBytes()
        return BitmapFactory.decodeByteArray(bytes,0,bytes.size)
    }
    fun readBitmapByInPutStream(path: String):Bitmap{
        val fis = File(path).inputStream()
        val bitmap = BitmapFactory.decodeStream(fis)
        fis.close()
        return bitmap
    }

    fun Image2Bitmap(imageView: ImageView):Bitmap{
        return (imageView.drawable as BitmapDrawable).bitmap
    }

    /**
     * 按质量压缩，并将图像生成指定的路径
     *
     * @param imgPath
     * @param outPath
     * @param maxSize      目标将被压缩到小于这个大小（KB）。
     * @param needsDelete  是否压缩后删除原始文件
     * @throws IOException
     */
    @Throws(IOException::class)
    fun compressByQuality(
        imgPath: String,
        maxSize: Int,
        needsDelete: Boolean
    ) :String{
        val outPath = compressAndGenImage(
            readBitmapByByteArray(imgPath),
            maxSize
        )

        // Delete original file
        if (needsDelete) {
            val file = File(imgPath)
            if (file.exists()) {
                file.delete()
            }
        }
        return outPath
    }

    /**
     * 按质量压缩，并将图像生成指定的路径
     *
     * @param bm
     * @param outPath
     * @param maxSize 目标将被压缩到小于这个大小（KB）。
     * @throws IOException
     */
    @Throws(IOException::class)
    fun compressAndGenImage(bm: Bitmap,  maxSize: Int):String {
        val os = ByteArrayOutputStream()
        // scale
        var options = 100
        // Store the bitmap into output stream(no compress)
        bm.compress(Bitmap.CompressFormat.JPEG, options, os)
        // Compress by loop
        while (os.toByteArray().size / 1024 > maxSize) {
            // Clean up os
            os.reset()
            // interval 10
            options -= 10
            bm.compress(Bitmap.CompressFormat.JPEG, options, os)
        }
        val format = SimpleDateFormat("yyyyMMdd_HHmmss")
        val date = Date(System.currentTimeMillis())
        val filename: String = format.format(date)
        val file = File(PATH_FILE_PICTURE, "$filename.png")
        LogUtils.error("上传的文件大小：${FileUtils.calFileSize(file)}")

        // Generate compressed image file
        val fos = FileOutputStream(file)
        fos.write(os.toByteArray())
        fos.flush()
        fos.close()

        return "$PATH_FILE_PICTURE/$filename.png"
    }

    /**
     * 压缩图片（质量压缩）
     *
     * @param bm       图片格式 jpeg,png,webp
     * @param quality  图片的质量,0-100,数值越小质量越差
     * @param maxSize  压缩后图片的最大kb值
     */
    fun compressByQuality(bm: Bitmap, quality: Int, maxSize: Int): File? {
        var quality = quality
        val bos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, quality, bos) // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        var length = bos.toByteArray().size.toLong()
        while (length / 1024 > maxSize) { // 循环判断如果压缩后图片是否大于minSize,大于继续压缩
            bos.reset() // 重置bos即清空bos
            quality -= 5 // 每次都减少5
            bm.compress(Bitmap.CompressFormat.JPEG, quality, bos) // 这里压缩options%，把压缩后的数据存放到baos中
            length = bos.toByteArray().size.toLong()
        }
        val format = SimpleDateFormat("yyyyMMdd_HHmmss")
        val date = Date(System.currentTimeMillis())
        val filename: String = format.format(date)
        val file =
            File(PATH_FILE_PICTURE, "$filename.png")
        try {
            val fos = FileOutputStream(file)
            try {
                fos.write(bos.toByteArray())
                fos.flush()
                fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        recycleBitmap(bm)
        return file
    }

    fun recycleBitmap(vararg bitmaps: Bitmap) {
        if (bitmaps == null) {
            return
        }
        for (bm in bitmaps) {
            if (null != bm && !bm.isRecycled) {
                bm.recycle()
            }
        }
    }

    /**
     * 通过像素压缩图像，这将改变图像的宽度/高度。用于获取缩略图
     *
     * @param imgPath   image path
     * @param pixelW    目标宽度像素
     * @param pixelH    高度目标像素
     * @return
     */
    fun ratio(imgPath: String?, pixelW: Float, pixelH: Float): Bitmap? {
        val newOpts = BitmapFactory.Options()
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
        newOpts.inJustDecodeBounds = true
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565
        // 获取位图信息，但请注意位图现在为空
        var bitmap = BitmapFactory.decodeFile(imgPath, newOpts)
        newOpts.inJustDecodeBounds = false
        val w = newOpts.outWidth
        val h = newOpts.outHeight
        // 想要缩放的目标尺寸,现在大部分手机都是1080*1920，参考值可以让宽高都缩小一倍
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        var be = 1 // be=1表示不缩放
        if (w > h && w > pixelW) { // 如果宽度大的话根据宽度固定大小缩放
            be = (newOpts.outWidth / pixelW).toInt()
        } else if (w < h && h > pixelH) { // 如果高度高的话根据宽度固定大小缩放
            be = (newOpts.outHeight / pixelH).toInt()
        }
        if (be <= 0) be = 1
        newOpts.inSampleSize = be // 设置缩放比例
        // 开始压缩图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(imgPath, newOpts)
        // 压缩好比例大小后再进行质量压缩
//      return compressByQuality(bitmap, 100,1000); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap
    }

    /**
     * 压缩图像的大小，这将修改图像宽度/高度。用于获取缩略图
     *
     * @param bm
     * @param pixelW  target pixel of width
     * @param pixelH  target pixel of height
     * @return
     */
    fun ratio(bm: Bitmap, pixelW: Float, pixelH: Float): Bitmap? {
        val os = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, os)
        if (os.toByteArray().size / 1024 > 1024) { // 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            os.reset() // 重置baos即清空baos
            bm.compress(Bitmap.CompressFormat.JPEG, 50, os) // 这里压缩50%，把压缩后的数据存放到baos中
        }
        var `is` = ByteArrayInputStream(os.toByteArray())
        val newOpts = BitmapFactory.Options()
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565
        var bitmap = BitmapFactory.decodeStream(`is`, null, newOpts)
        newOpts.inJustDecodeBounds = false
        val w = newOpts.outWidth
        val h = newOpts.outHeight
        // 想要缩放的目标尺寸,现在大部分手机都是1080*1920，参考值可以让宽高都缩小一倍
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        var be = 1 // be=1表示不缩放
        if (w > h && w > pixelW) { // 如果宽度大的话根据宽度固定大小缩放
            be = (newOpts.outWidth / pixelW).toInt()
        } else if (w < h && h > pixelH) { // 如果高度高的话根据宽度固定大小缩放
            be = (newOpts.outHeight / pixelH).toInt()
        }
        if (be <= 0) be = 1
        newOpts.inSampleSize = be // 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        `is` = ByteArrayInputStream(os.toByteArray())
        bitmap = BitmapFactory.decodeStream(`is`, null, newOpts)
        // 压缩好比例大小后再进行质量压缩
        // return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap
    }

    /**
     * 将位图存储到指定的图像路径中
     *
     * @param bm
     * @param outPath
     * @throws FileNotFoundException
     */
    @Throws(FileNotFoundException::class)
    fun storeImage(bm: Bitmap, outPath: String?) {
        val os = FileOutputStream(outPath)
        bm.compress(Bitmap.CompressFormat.JPEG, 100, os)
    }

    /**
     * 从指定的图像路径获取位图
     * @param imgPath
     * @return
     */
    fun getBitmap(imgPath: String?): Bitmap? {
        val newOpts = BitmapFactory.Options()
        newOpts.inJustDecodeBounds = false
        newOpts.inPurgeable = true
        newOpts.inInputShareable = true
        newOpts.inSampleSize = 1
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565 //设置RGB
        return BitmapFactory.decodeFile(imgPath, newOpts)
    }

}