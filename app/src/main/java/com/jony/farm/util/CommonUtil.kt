package com.jony.farm.util

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.combodia.httplib.config.LanguageType
import com.combodia.httplib.ext.LanguageUtil
import com.jony.farm.R
import com.jony.farm.model.entity.AnimalType

/**
 *Author:ganzhe
 *时间:2021/4/14 17:11
 *描述:This is CommonUtil
 */
object CommonUtil {

    @JvmStatic
    fun getFarmGif(animalId: Int, isMature: Boolean): Int {
        return when (animalId) {
            AnimalType.Hen.id -> {
                when (isMature) {
                    true -> R.mipmap.hen2
                    false -> R.mipmap.hen1
                }
            }
            AnimalType.Rabbit.id -> {
                when (isMature) {
                    true -> R.mipmap.rabbit2
                    false -> R.mipmap.rabbit1
                }
            }
            AnimalType.Sheep.id -> {
                when (isMature) {
                    true -> R.mipmap.sheep2
                    false -> R.mipmap.sheep1
                }
            }
            AnimalType.Dog.id -> {
                when (isMature) {
                    true -> R.mipmap.dog2
                    false -> R.mipmap.dog1
                }
            }
            AnimalType.Cow.id -> {
                when (isMature) {
                    true -> R.mipmap.cow2
                    false -> R.mipmap.cow1
                }
            }
            AnimalType.Horse.id -> {
                when (isMature) {
                    true -> R.mipmap.horse2
                    false -> R.mipmap.horse1
                }
            }
            AnimalType.Tiger.id -> {
                when (isMature) {
                    true -> R.mipmap.tiger2
                    false -> R.mipmap.tiger1
                }
            }
            AnimalType.Mouse.id -> {
                when (isMature) {
                    true -> R.mipmap.mouse2
                    false -> R.mipmap.mouse1
                }
            }
            else -> 0
        }
    }

    @JvmStatic
    fun getImgByAnimalId(animalId: Int): Int {

        return when (animalId) {
            AnimalType.Hen.id -> R.mipmap.ic_hen
            AnimalType.Rabbit.id -> R.mipmap.ic_rabbit
            AnimalType.Sheep.id -> R.mipmap.ic_sheep
            AnimalType.Dog.id -> R.mipmap.ic_dog
            AnimalType.Cow.id -> R.mipmap.ic_cow
            AnimalType.Horse.id -> R.mipmap.ic_house
            AnimalType.Tiger.id -> R.mipmap.ic_tiger
            AnimalType.Mouse.id -> R.mipmap.ic_mouse
            else -> 0
        }
    }

    @JvmStatic
    fun getKindImgByAnimalId(animalId: Int): Int {

        return when (animalId) {
            AnimalType.Hen.id -> R.mipmap.ic_farm_hen
            AnimalType.Rabbit.id -> R.mipmap.ic_farm_rabbit
            AnimalType.Sheep.id -> R.mipmap.ic_farm_sheep
            AnimalType.Dog.id -> R.mipmap.ic_farm_dog
            AnimalType.Cow.id -> R.mipmap.ic_farm_cow
            AnimalType.Horse.id -> R.mipmap.ic_farm_house
            AnimalType.Tiger.id -> R.mipmap.ic_farm_tiger
            AnimalType.Mouse.id -> R.mipmap.ic_farm_mouse
            else -> 0
        }
    }

    @JvmStatic
    fun getNameByAnimalId(animalId: Int): String {

        return when (animalId) {
            AnimalType.Hen.id -> AnimalType.Hen.name
            AnimalType.Rabbit.id -> AnimalType.Rabbit.name
            AnimalType.Sheep.id -> AnimalType.Sheep.name
            AnimalType.Dog.id -> AnimalType.Dog.name
            AnimalType.Cow.id -> AnimalType.Cow.name
            AnimalType.Horse.id -> AnimalType.Horse.name
            AnimalType.Tiger.id -> AnimalType.Tiger.name
            else -> ""
        }
    }

    @JvmStatic
    fun getLocalVersionName(context: Context): String {
        val manager: PackageManager = context.packageManager
        val info: PackageInfo =
            manager.getPackageInfo(context.packageName, 0)
        return info.versionName
    }

    /**
     * 字符串分割 用于多语言 "\\|" 分割
     *
     * @param str
     * @param index
     * @return
     */
    @JvmStatic
    fun strSplit(str: String, index: Int): String {

        val arry = str.split("|").toTypedArray()
        return if (index < arry.size) {
            arry[index]
        } else {
            arry[0]
        }
    }

    /**
     * 多语言获取彩种名
     *
     * @param gameName
     * @return
     */
    fun getGameName(gameName: String): String {
        val language: String = LanguageUtil.getDefaultLanguage()

        return if (language == LanguageType.CHINESE.language) {
            strSplit(gameName, 0)
        } else if (language == LanguageType.ENGLISH.language) {
            strSplit(gameName, 1)
        } else {
            strSplit(gameName, 0)
        }
    }

    /**
     * 旋转
     *
     * @param type : 1.向上 2.向下
     */
    fun rote(type: Int, view: View) {

        var animation: Animation? = null
        if (1 == type) {
            animation = AnimationUtils.loadAnimation(view.context, R.anim.rote_playtype_up)
        } else if (2 == type) {
            animation = AnimationUtils.loadAnimation(view.context, R.anim.rote_playtype_down)
        }
        val lin = LinearInterpolator()
        animation!!.interpolator = lin
        animation.fillAfter = true
        view.startAnimation(animation)

    }

}