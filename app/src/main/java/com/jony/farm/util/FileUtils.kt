package com.jony.farm.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.TextUtils
import androidx.annotation.NonNull
import androidx.core.net.toFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.math.BigDecimal
import kotlin.random.Random

object FileUtils {



    /**
     * 检测sdcard是否可用
     *
     * @return true为可用，否则为不可用
     */
    @JvmStatic
    fun isSDCardAvailable():Boolean{
        val status = Environment.getExternalStorageState()
        return status == Environment.MEDIA_MOUNTED
    }

    @JvmStatic
    fun isCheckSDCardWarning(): Boolean {
        return !isSDCardAvailable()
    }

    private const val APP_FOLDER_NAME = "FarmK"

    fun getAppPicturePath(): String {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            // full path
            "${Environment.getExternalStorageDirectory().absolutePath}/" +
                    "${Environment.DIRECTORY_PICTURES}/$APP_FOLDER_NAME/"
        } else {
            // relative path
            "${Environment.DIRECTORY_PICTURES}/$APP_FOLDER_NAME/"
        }
    }



    fun Long.formatMemorySize(): String {
        val kiloByte = this / 1024.toDouble()

        val megaByte = kiloByte / 1024
        if (megaByte < 1) {
            return kiloByte.toBigDecimal().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "K"
        }

        val gigaByte = megaByte / 1024
        if (gigaByte < 1) {
            return megaByte.toBigDecimal().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "M"
        }

        val teraBytes = megaByte / 1024
        if (teraBytes < 1) {
            return megaByte.toBigDecimal().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "G"
        }

        return teraBytes.toBigDecimal().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "T"
    }

    /**
     * 计算文件大小
     */
    fun calFileSize(path: String):String{
        val file = createFile(path)
        val length = file?.length()?:0
        return if (length >= 1048576) {
            "${(length / 1048576)}MB"
        } else if (length >= 1024) {
            "${(length / 1024)}KB"
        } else if (length < 1024) {
            "$length"+"B"
        } else {
            "0KB"
        }
    }

    fun calFileSize(file: File):String{
     //   val file = createFile(path)
        val length = file?.length()?:0
        return if (length >= 1048576) {
            "${(length / 1048576)}MB"
        } else if (length >= 1024) {
            "${(length / 1024)}KB"
        } else if (length < 1024) {
            "$length"+"B"
        } else {
            "0KB"
        }
    }


    @JvmStatic
    fun createDir(path:String):Boolean{
        if (isCheckSDCardWarning()) {
            return false
        }

        if (TextUtils.isEmpty(path)) {
            return false
        }
        val dir =  File(path)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return true
    }

    @JvmStatic
    fun createFile( path:String, filename:String): File? {
        if (!createDir(path)) {
            return null
        }

        if (TextUtils.isEmpty(filename)) {
            return null
        }

        var file:File? = null
        file =  File(path, filename)
        if (file.exists()) {
            return file
        }

        try {
            file.createNewFile()
        } catch ( e: IOException) {
            return null
        }
        return file
    }

    @JvmStatic
    fun createFile(absolutePath:String):File?{
        if (TextUtils.isEmpty(absolutePath)) {
            return null
        }
        if (isCheckSDCardWarning()) {
            return null
        }
        val file =  File(absolutePath)
        if (file.exists()) {
            return file
        } else {
            val dir = file.parentFile
            if (!dir.exists()) {
                dir.mkdirs()
            }

            try {
                file.createNewFile()
            } catch ( e:IOException) {
                return null

            }
        }
        return file
    }

    @JvmStatic
    fun isFileExist(filePath:String):Boolean{
        if (TextUtils.isEmpty(filePath))
            return false

        val file =  File(filePath)
        return file.exists() && file.isFile
    }

    @JvmStatic
    fun deleteFile(filePath:String){
        if (TextUtils.isEmpty(filePath))
            return

        val file =  File(filePath)
        if (file.exists()) {
            file.delete()
        }
    }

    @JvmStatic
    fun createNewFile(path: String,name:String):File?{
        if (isCheckSDCardWarning()) {
            return null
        }
        val file =  File(path, name)
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile()
        } catch ( e:IOException) {
            e.printStackTrace()
        }
        return file
    }

    // 程序sdcard目录
    @JvmStatic
    fun getSDCardAppCachePath( context: Context):String?{
        val file = context.externalCacheDir ?: return null
        return file.absolutePath
    }

    @JvmStatic
    fun getExternalStorageDirectory(context:Context):String?{
        val file = context.getExternalFilesDir(null) ?: return null
        return file.absolutePath
    }

    private fun uriToFileQ(context: Context, uri: Uri): File? =
        if (uri.scheme == ContentResolver.SCHEME_FILE)
            uri.toFile()
        else if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            //把文件保存到沙盒
            val contentResolver = context.contentResolver
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor?.let {
                if (it.moveToFirst()) {
                    val ois = context.contentResolver.openInputStream(uri)
                    val displayName =
                        it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    ois?.let {
                        File(
                            context.externalCacheDir!!.absolutePath,
                            "${Random.nextInt(0, 9999)}$displayName"
                        ).apply {
                            val fos = FileOutputStream(this)

                            //   android.os.FileUtils.copy(ois, fos)
                            fos.close()
                            it.close()
                        }
                    }
                } else null

            }

        } else null




    fun getFileFromUri(
        context: Context, uri: Uri, selection: String? = null,
        selectionArgs: kotlin.Array<String>? = null
    ): File? =
        context.contentResolver.query(uri, arrayOf("_data"), selection, selectionArgs, null)?.let {
            if (it.moveToFirst()) {
                it.getColumnIndex(MediaStore.Images.Media.DATA).let { index->
                    if(index == -1 ) null else File(it.getString(index))
                }
            } else null
        }
}