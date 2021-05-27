package com.jony.farm.util

import android.content.Context
import android.content.Intent
import com.jony.farm.config.Const

import com.jony.farm.ui.activity.FarmActivity
import com.jony.farm.ui.activity.LoginActivity
import com.jony.farm.ui.activity.RegisterActivity
import com.xiaojinzi.component.impl.Router

/**
 *Author:ganzhe
 *时间:2020/10/30 14:14
 *描述:This is RouteUtil
 */

object RouteUtil {
    @JvmStatic
    fun start2Farm(context:Context,index: Int){
        val intent = Intent()
        intent.setClass(context,FarmActivity::class.java)
        intent.putExtra("index",index)
       /* val bundle = Bundle()
        bundle.putParcelable("article",article)
        intent.putExtras(bundle)*/
        context.startActivity(intent)
    }
    @JvmStatic
    fun start2Login(context: Context){
        val intent:Intent = Intent()
        intent.setClass(context,LoginActivity::class.java)
        context.startActivity(intent)
    }
    @JvmStatic
    fun start2Register(context: Context){
        val intent:Intent = Intent()
        intent.setClass(context,RegisterActivity::class.java)
        context.startActivity(intent)
    }
    @JvmStatic
    fun start2UserInfo(context: Context){
        Router
            .with(context)
            .host(Const.MODULE_HOST_APP)
            .path(Const.MODULE_PATH_APP_USERINFO)
            .forward()
    }

    @JvmStatic
    fun start2Farm(context: Context){
        Router
            .with(context)
            .host(Const.MODULE_HOST_APP)
            .path(Const.MODULE_PATH_APP_FARM)
            .forward()
    }

    @JvmStatic
    fun start2Recharge(context: Context){
        Router
                .with(context)
                .host(Const.MODULE_HOST_APP)
                .path(Const.MODULE_PATH_APP_RECHARGE)
                .forward()
    }

    @JvmStatic
    fun start2Withdraw(context: Context){
        Router
                .with(context)
                .host(Const.MODULE_HOST_APP)
                .path(Const.MODULE_PATH_APP_WITHDRAW)
                .forward()
    }

    @JvmStatic
    fun start2BankCardList(context: Context){
        Router
                .with(context)
                .host(Const.MODULE_HOST_APP)
                .path(Const.MODULE_PATH_APP_BANKCARDLIST)
                .forward()
    }

    @JvmStatic
    fun start2BindCard(context: Context){
        Router
                .with(context)
                .host(Const.MODULE_HOST_APP)
                .path(Const.MODULE_PATH_APP_BINDCARD)
                .forward()
    }

    @JvmStatic
    fun start2AnimalHistory(context: Context){
        Router
            .with(context)
            .host(Const.MODULE_HOST_APP)
            .path(Const.MODULE_PATH_APP_ANIMALHISTORY)
            .forward()
    }

    @JvmStatic
    fun start2ChangePwd(context: Context){
        Router
            .with(context)
            .host(Const.MODULE_HOST_APP)
            .path(Const.MODULE_PATH_APP_CHANGEPWD)
            .forward()
    }

    @JvmStatic
    fun start2Invite(context: Context){
        Router
                .with(context)
                .host(Const.MODULE_HOST_APP)
                .path(Const.MODULE_PATH_APP_INVITE)
                .forward()
    }
    @JvmStatic
    fun start2Fund(context: Context,tradeType:Int){
        Router
                .with(context)
                .host(Const.MODULE_HOST_APP)
                .path(Const.MODULE_PATH_APP_FUNDDETAIL)
                .putInt("tradeType",tradeType)
                .forward()
    }

    @JvmStatic
    fun start2FodderDetail(context: Context,tradeType:Int){
        Router
                .with(context)
                .host(Const.MODULE_HOST_APP)
                .path(Const.MODULE_PATH_APP_FODDERDETAIL)
                .putInt("tradeType",tradeType)
                .forward()
    }

    @JvmStatic
    fun start2MyTeam(context: Context){
        Router
                .with(context)
                .host(Const.MODULE_HOST_APP)
                .path(Const.MODULE_PATH_APP_TEAMMEMBER)
                .forward()
    }

    @JvmStatic
    fun start2TeamFund(context: Context){
        Router
                .with(context)
                .host(Const.MODULE_HOST_APP)
                .path(Const.MODULE_PATH_APP_TEAMFUND)

                .forward()
    }

    @JvmStatic
    fun start2AgencyIncome(context: Context){
        Router
                .with(context)
                .host(Const.MODULE_HOST_APP)
                .path(Const.MODULE_PATH_APP_AGENCYINCOME)
                .forward()
    }

    @JvmStatic
    fun start2BlockNews(context: Context){
        Router
                .with(context)
                .host(Const.MODULE_HOST_APP)
                .path(Const.MODULE_PATH_APP_BLOCKNEWS)
                .forward()
    }

    @JvmStatic
    fun start2BlockNewsDetail(context: Context,Id:Int){
        Router
                .with(context)
                .host(Const.MODULE_HOST_APP)
                .path(Const.MODULE_PATH_APP_BLOCKNEWSDETAIL)
                .putInt("Id",Id)
                .forward()
    }
    @JvmStatic
    fun start2Rank(context: Context){
        Router
                .with(context)
                .host(Const.MODULE_HOST_APP)
                .path(Const.MODULE_PATH_APP_RANK)
                .forward()
    }

    @JvmStatic
    fun start2Faq(context: Context){
        Router
                .with(context)
                .host(Const.MODULE_HOST_APP)
                .path(Const.MODULE_PATH_APP_FAQ)
                .forward()
    }

    @JvmStatic
    fun start2Luckdraw(context: Context){
        Router
                .with(context)
                .host(Const.MODULE_HOST_APP)
                .path(Const.MODULE_PATH_APP_LUCKDRAW)
                .forward()
    }

    @JvmStatic
    fun start2CommunityChat(context: Context){
        Router
                .with(context)
                .host(Const.MODULE_HOST_APP)
                .path(Const.MODULE_PATH_APP_COMMUNITYCHAT)
                .forward()
    }

    @JvmStatic
    fun start2SubFarm(context: Context,userId:Int,balance:Double,lc:Double){
        Router
                .with(context)
                .host(Const.MODULE_HOST_APP)
                .path(Const.MODULE_PATH_APP_SUBFARM)
                .putInt("userId",userId)
                .putDouble("balance",balance)
                .putDouble("lc",lc)
                .forward()
    }



}