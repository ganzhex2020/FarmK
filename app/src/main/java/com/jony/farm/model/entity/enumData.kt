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
    Tiger(7),
    Mouse(8)
}

enum class AnimalKindType(val id: Int,@StringRes val label: Int){
    Hen(1,R.string.hen),
    Rabbit(2,R.string.rabbit),
    Sheep(3,R.string.sheep),
    Dog(4,R.string.dog),
    Cow(5,R.string.cow),
    Horse(6,R.string.horse),
    Tiger(7,R.string.tiger),
    Mouse(8,R.string.mouse);

    companion object{
        @StringRes
        fun getAnimalLabel(id: Int):Int{
            AnimalKindType.values().forEach {
                if (it.id == id){
                    return it.label
                }
            }
            return 0
        }
    }

}

enum class PayType(val id:Int,@StringRes val label:Int){

    PAYONLINE(1, R.string.payonline),
    BANKTRANSFER(4, R.string.banktransfer)

}

enum class BindCardType(val id:Int,@StringRes val label:Int){
    MAINLAND(2,R.string.mainland),
    PICKPAY(5,R.string.pickpay)
}

enum class TradType(val id: Int,@StringRes val tradeLabel: Int){
    ANIMAL(1,R.string.fund_trad1),
    DEPOSIT(2,R.string.fund_trad2),
    PROMOTIONS(3,R.string.fund_trad3),
    CORRECT(4,R.string.fund_trad4),
    COMMISSION(5,R.string.fund_trad5),
    AGENT(6,R.string.fund_trad6),
    WITHDRAW(12,R.string.fund_trad7),
    PROFIT(13,R.string.fund_trad8),
    CANCELWITH(14,R.string.fund_trad9),
    CORRECTLC(15,R.string.fund_trad10),
    SHAREFB(20,R.string.fund_trad11),
    SHARETWITTER(21,R.string.fund_trad12),
    SHAREGROUP(22,R.string.fund_trad13),
    FEED(23,R.string.fund_trad14),
    SIGN(24,R.string.fund_trad15),
    SLXZ(25,R.string.fund_trad25),
    LUCKDRAW(26,R.string.fund_trad26);


    companion object{

        fun getTradLabel(id: Int):Int{
            TradType.values().forEach {
                if (it.id == id){
                    return it.tradeLabel
                }
            }
            return 0
        }
    }
}

enum class TradItem(val id: Int,@StringRes val tradeLabel: Int){
    ITEM1(1,R.string.fund_item1),
    ITEM4(4,R.string.fund_item4),
    ITEM5(5,R.string.fund_item5),
    ITEM6(6,R.string.fund_item6),
    ITEM7(7,R.string.fund_item7),
    ITEM8(8,R.string.fund_item8),
    ITEM14(14,R.string.fund_item14),
    ITEM17(17,R.string.fund_item17),
    ITEM19(19,R.string.fund_item19),
    ITEM20(20,R.string.fund_item20),
    ITEM26(26,R.string.fund_item26),
    ITEM31(31,R.string.fund_item31),
    ITEM32(32,R.string.fund_item32),
    ITEM34(34,R.string.fund_item34);


    companion object{

        fun getTradItemLabel(id: Int):Int{
            TradItem.values().forEach {
                if (it.id == id){
                    return it.tradeLabel
                }
            }
            return 0
        }
    }
}

enum class TradState(val id: Int,@StringRes val tradeLabel: Int){
    STATE1(1,R.string.fund_state1),
    STATE2(2,R.string.fund_state2),
    STATE3(3,R.string.fund_state3),
    STATE4(4,R.string.fund_state4),
    STATE5(5,R.string.fund_state5),
    STATE6(6,R.string.fund_state6),
    STATE7(7,R.string.fund_state7),
    STATE8(8,R.string.fund_state8),
    STATE9(9,R.string.fund_state9),
    STATE10(10,R.string.fund_state10),
    STATE100(100,R.string.fund_state100);


    companion object{

        fun getTradStateLabel(id: Int):Int{
            TradState.values().forEach {
                if (it.id == id){
                    return it.tradeLabel
                }
            }
            return 0
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
            TeamTrade.values().forEach {
                if (it.id == id){
                    return it.tradeName
                }
            }
            return ""
        }
    }
}

enum class StateTrade(val id: Int,val tradeName: String){
    DCL(1,"待处理"),
    SHZ(2,"审核中"),
    YQX(3,"已取消"),
    YRK(4,"已入款"),
    SHCG(5,"审核成功"),
    YCK(6,"已出款"),
    JJCK(7,"拒绝出款"),
    QXSB(8,"取消失败"),
    WWCTJ(9,"未完成提交");


    companion object{
        fun getTradeName(id: Int):String{
            StateTrade.values().forEach {
                if (it.id == id){
                    return it.tradeName
                }
            }
            return ""
        }
    }

}

