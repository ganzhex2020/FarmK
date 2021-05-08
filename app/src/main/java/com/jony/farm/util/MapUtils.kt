package com.jony.farm.util

import android.net.Uri
import android.os.Build
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import zlc.season.claritypotion.ClarityPotion
import java.io.File




object MapUtils {

    fun map2JsonRequestBody(map: Map<String, Any>): RequestBody {
        val gson = GsonBuilder().serializeNulls().create()
        val jsonStr = gson.toJson(map)
        return jsonStr.toRequestBody("application/json; charset=utf-8".toMediaType())
    }


    /**
     * filePath 本地路径
     * fileKey  上传key  如：file
     */
    fun file2MultipartBody(filePath: String, fileKey: String): MultipartBody {
        val file = File(filePath) //filePath为图片位置
        val fileBody = file.asRequestBody("image/*".toMediaType())
        return MultipartBody.Builder()
            .addFormDataPart(fileKey, file.name, fileBody)
            .build()
    }

    fun file2RequestBody(filePath: String, fileKey: String): RequestBody {

        //android 10 以上 根据uri找到文件

        val file = File(filePath)/*= if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
           FileUtils.getFileFromUri(ClarityPotion.clarityPotion,Uri.parse(filePath))
        }else{

        }*/
        //filePath为图片位置
        val fileBody = file.asRequestBody("image/*".toMediaType())
        return MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(fileKey, file.name, fileBody)
            .build()
    }

}
