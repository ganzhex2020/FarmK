package com.jony.farm.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userEntity")
data class UserEntity(
    @PrimaryKey
    val userID: Int = 0,
    val balance: Double,
    val freezeStatus: Int,
    val headImg: String,
    val isNormalUser: Int,
    val lastLoginAddress: String,
    val loginIP: String,
    val nickName: String,
    val phone: String,
    val rebateRate: Double,
    val sessionID: String,
    val state: Int,
    val ticket: String,
    val trueName: String,
    val userName: String,
    val userType: Int
)
@Entity(tableName = "memberEntity")
data class MemberEntity(
    @PrimaryKey
    val userID: Int,
    val appID: Int,
    val balance: Double,
    val deviceID: Int,
    val email: String,
    val fodder: Double,
    val freezeStatus: Int,
    val headImg: String,
    val isNormalUser: Int,
    val lCoin: Double,
    val layer: Int,
    val loginIP: String,
    val nickName: String,
    val parentID: Int,
    val parentName: String,
    val password: String,
    val path: String,
    val qq: String,
    val rank: Int,
    val rebateRate: Double,
    val registerIP: String,
    val telephone: String,
    val trueName: String,
    val userName: String,
    val userState: Int,
    val userType: Int,
    val withdrawalPassword: String
)

data class BannerEntity(
    val endTime: Any,
    val id: Int,
    val promImgMB: String,
    val promImgMBEn: String,
    val promImgPC: String,
    val promImgPCEn: String,
    val startTime: Any,
    val subTitle: String,
    val title: String
)

/**
 *
AnimalID
动物id
AnimalName
用户名
SellType
销售类型（前端不用管）
State
可用不可用（前端不用管）
CycleDay
成熟期（天）
ProfitRate
收益率
BuyStartTime
开始销售时间
BuyEndTime
结束销售时间
ShowOrder
显示顺序
Discreption
描述
NeedFodder
每天需要几个饲料
SaleState
销售状态：1销售中
2销售没开始
3断货了
 */

data class KindEntity(
    val addTime: String="",
    val animalID: Int=0,
    val animalName: String="",
    val buyEndTime: String="",
    val buyStartTime: String="",
    val cycleDay: Int=0,
    val discreption: String="",
    val needFodder: Int=0,
    val price: Int=0,
    val profitRate: Double=0.0,
    val saleState: Int=0,
    val sellCount: Int=0,
    val sellType: Int=0,
    val showOrder: Int=0,
    val state: Int=0
)/*{
    constructor() : this("")
}*/

/**
 *
ID
购买ID
UserID
用户ID
UserName
用户名
AnimalID
动物ID
AnimalName
动物名字
Price
价格
CycleDay
成熟周期
PlusDay
延长天数
ProfitRate
收益率
BuyDate
购买时间
LeftSeconde
成熟距离秒数
NeedFodder
每天需要多少饲料
FeedTime
已经喂了多少
NeedFeedToday
今天需要喂养吗
State
0成长中   1等待收益  2已收益
OrderNumber
订单号
 */
data class AnimalEntity(
        val animalID: Int = 0,
        val animalName: String = "",
        val buyDate: String = "",
        val cycleDay: Int =0,
        val feedTime: Int=0,
        val id: Int=0,
        var needFeedToday: Boolean=false,
        val needFodder: Int=0,
        val orderNumber: String="",
        val price: Int=0,
        val leftSeconde:Double = 0.0,
        val profitRate: Double=0.0,
        val state: Int=0,
        val userID: Int=0,
        val userName: String="",
        var isFull:Boolean = false
)

data class YueEntity(
    val item1: Double, //Qc == balance
    val item2: Double,  //lc
    val item3: Double   //fodder
)

/**
 * 支付大类
 */
data class PayTypeEntity(
    val payList: List<PayEntity> = emptyList(),
    val payType: Int = 0,
    var isSelect:Boolean = false
)
/**
 * 支付小类
 */
data class PayEntity(
    val bankAccount: String,
    val bankList: String,
    val bankNumber: String,
    val extendValue: String,
    val id: Int,
    val imageUrl: String,
    val isFixAmount: String,
    val maxAmount: Int,
    val mixAmount: Int,
    val platName: String,
    val platformNameID: Int,
    val submitLink: String,
    val tradeFeeRate: Int,
    val type: Int,
    val usePlatForm: Int,
    var isSelect:Boolean = false
)


data class AmountEntity(val amount:Int,var isSelect:Boolean)

data class OnLinePayEntity(val jumpUrl:String)

data class BankCardEntity(
    val addTime: String,
    val bankAccount: String,
    val bankAddress: String,
    val bankCode: Any,
    val bankID: Int,
    val bankName: String,
    val bankNumber: String,
    val city: String,
    val disBack: String,
    val disLogo: String,
    val id: Int,
    var isDefault: Boolean,
    val isWithdrawal: Int,
    val path: Any,
    val province: String,
    val siteNumber: Int,
    val state: Int,
    val updateTime: String,
    val userName: String
)

/**
 *
FB
facebool次数
Tiw
推特次数
CTApp
聊天app次数
Sign
签到次数
LuckDraw
转盘剩余次数


 */
data class ShareCountEntity(
    val ctApp: Int,
    val fb: Int,
    val luckDraw: Int,
    val sign: Int,
    val tiw: Int
)

data class AccountDetailEntity(
    val addTime: String,
    val balance: Double,
    val id: Int,
    val memo: String,
    val orderNumber: String,
    val path: String,
    val tradeAmount: Double,
    val tradeType: Int,
    val userName: String,
    val userType: Int
)

data class TeamMemberEntity(
        val UserId:Int,
        val UserName:String,
        val MemberCount:Int,
        val Balance:Double,
        val TeamBalance:Double,
        val Layer:Int,
        val AddTime:String
)
