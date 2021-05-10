package com.jony.farm.model.entity

import androidx.annotation.StringRes
import com.jony.farm.R

/**
 *Author:ganzhe
 *时间:2021/4/14 17:05
 *描述:This is emunData
 */

enum class AnimalType(val id: Int) {

    Hen(1),
    Rabbit(2),
    Sheep(3),
    Dog(4),
    Cow(5),
    Horse(6),
    Tiger(7)


}

enum class PayType(val id:Int,@StringRes val label:Int){

    PAYONLINE(1, R.string.payonline),
    BANKTRANSFER(4, R.string.banktransfer)

}

enum class BindCardType(val id:Int,val label:String){
    PICKPAY(5,"PICKPAY")
}

enum class TradType(val id: Int,val tradeName: String){
    ANIMAL(1,"领养动物"),
    DEPOSIT(2,"金币充值"),
    PROMOTIONS(3,"彩金活动"),
    CORRECT(4,"GC修正"),
    COMMISSION(5,"佣金"),
    LUCKDRAW(11,"转盘"),
    WITHDRAW(12,"LC提现"),
    PROFIT(13,"动物收益"),
    CANCELWITH(14,"取消出款"),
    CORRECTLC(15,"LC修正"),
    SHAREFB(20,"分享FB"),
    SHARETWITTER(21,"分享Twitter"),
    SHAREGROUP(22,"分享Chat"),
    FEED(23,"饲料投喂"),
    SIGN(24,"每日签到");

    companion object{
        fun getTradeName(id: Int):String{
            TradType.values().forEach {
                if (it.id == id){
                    return it.tradeName
                }
            }
            return ""
        }
    }

}

enum class TeamTrade(val id: Int,val tradeName: String){
    RG(1,"人工存入"),
    FY(5,"附言充值"),
    FD(6,"返点"),
    YJ(7,"佣金"),
    ZXQK(8,"在线取款"),
    RGCK(14,"人工出款"),
    HD(17,"活动"),
    DC(20,"多次出款费用"),
    XZ(19,"修正"),
    YHKZZ(26,"银行卡转账");


    companion object{
        fun getTradeName(id: Int):String{
            TradType.values().forEach {
                if (it.id == id){
                    return it.tradeName
                }
            }
            return ""
        }
    }

}