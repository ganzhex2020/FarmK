package com.jony.farm.config

import android.os.Environment
import com.jony.farm.BuildConfig
import java.io.File

/**
 *Author:ganzhe
 *时间:2021/4/14 19:10
 *描述:This is Const
 */
object Const {

   //文件下载路径
   val PATH_FILE_DOWNLOAD: String? = Environment.getExternalStorageDirectory().toString() + File.separator + "download" + File.separator
   val PATH_FILE_PICTURE: String? = "${Environment.getExternalStorageDirectory()}/${Environment.DIRECTORY_DCIM}"

   val APP_ID = BuildConfig.APP_ID
   val COMPANY = BuildConfig.COMPANY
   val BASE_URL = BuildConfig.BASE_URL
   val IMAGE_URL = BuildConfig.IMAGE_URL
   val SOCKET_URL = BuildConfig.SOCKET_URL
   ////
   val TOTALAMOUNT = 25


   //=======================app=============
   const val MODULE_HOST_APP = "app"
   const val MODULE_PATH_APP_USERINFO = "userinfo"
   const val MODULE_PATH_APP_FARM = "farm"
   const val MODULE_PATH_APP_RECHARGE = "recharge"
   const val MODULE_PATH_APP_WITHDRAW = "withdraw"
   const val MODULE_PATH_APP_BANKCARDLIST = "bankcardlist"
   const val MODULE_PATH_APP_BINDCARD = "bindcard"
   const val MODULE_PATH_APP_ANIMALHISTORY = "animalhistory"
   const val MODULE_PATH_APP_FODDERDETAIL = "fodderdetail"
   const val MODULE_PATH_APP_GCDETAIL = "gcdetail"
   const val MODULE_PATH_APP_LCDETAIL = "lcdetail"
   const val MODULE_PATH_APP_CHANGEPWD = "changepwd"
   const val MODULE_PATH_APP_INVITE = "invite"
   const val MODULE_PATH_APP_TEAMMEMBER = "teammember"
   const val MODULE_PATH_APP_TEAMFUND = "teamfund"
   const val MODULE_PATH_APP_AGENCYINCOME = "agencyincome"
   const val MODULE_PATH_APP_BLOCKNEWS = "blocknews"
   const val MODULE_PATH_APP_BLOCKNEWSDETAIL = "blocknewsdetail"
   const val MODULE_PATH_APP_RANK = "rank"
   const val MODULE_PATH_APP_FAQ = "faq"
   const val MODULE_PATH_APP_LUCKDRAW = "luckdraw"
   const val MODULE_PATH_APP_COMMUNITYCHAT = "communitychat"
   const val MODULE_PATH_APP_SUBFARM = "subfarm"
   const val MODULE_PATH_APP_CHAT = "chat"
   const val MODULE_PATH_APP_LANGUAGE = "chat"
   const val MODULE_PATH_APP_TEAMPROMOTE = "teampromote"
   const val MODULE_PATH_APP_AGENTARCH = "agentarch"
   const val MODULE_PATH_APP_MODIFYBANKCARD = "modifybankcard"
   const val MODULE_PATH_APP_SMS = "sms"
   const val MODULE_PATH_APP_FUNDPWD = "fundpwd"
   const val MODULE_PATH_APP_SETTING = "setting"
   const val MODULE_PATH_APP_MSGDETAIL = "msgdetail"
   const val MODULE_PATH_APP_LCBANK = "lcbank"


   ////==========================Interceptor============
   const val LOGININTERCEPTOR = "loginInterceptor"
   const val TRUENAMEINTERCEPTOR = "truenameInterceptor"


}