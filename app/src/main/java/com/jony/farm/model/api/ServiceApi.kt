package com.jony.farm.model.api


import com.combodia.httplib.model.BaseResult
import com.jony.farm.model.entity.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

/**
 *Author:ganzhe
 *时间:2020/10/21 8:06 PM
 *描述:This is ServiceApi
 */
interface ServiceApi {


    /**
     * 获取banner数据
     *//*
    @GET("/banner/json")
    suspend fun getBanner(): BaseResult<List<BannerEntity>>*/



    /**
     * 注册
     */
    @POST("members/OpenLinkAccount")
    suspend fun register(@Body requestBody: RequestBody):BaseResult<Any>

    /**
     * 登录
     */
    @POST("members/Login")
    suspend fun login(@Body requestBody: RequestBody):BaseResult<UserEntity>

    /**
     * 轮播图
     */
    @GET("promotionpics/{appid}")
    suspend fun getBanner(@Path("appid") appid: Int):BaseResult<List<BannerEntity>>

    /**
     *  获取市场销售的动物列表
     */
    @GET("animals/sales")
    suspend fun getAnimalList():BaseResult<List<KindEntity>>

    /**
     * 获取动物剩余个数
     */
    @GET("animals/leftcount/{animalId}")
    suspend fun getAnimalLeft(@Path("animalId") animalId: Int):BaseResult<Int>

    /**
     * 获取用户信息
     */
    @GET("members")
    suspend fun getMembers():BaseResult<MemberEntity>

    /**
     * 购买动物
     */
    @POST("animals/buy")
    suspend fun buyAnimal(@Body requestBody: RequestBody):BaseResult<Any>

    /**
     * 退出登录
     */
    @DELETE("members/loginoff")
    suspend fun signOut():BaseResult<Any>

    /**
     * 农场的已购买动物列表
     */
    @GET("animals/anmbuyfeeding")
    suspend fun getFarmAllAnimal():BaseResult<List<AnimalEntity>>

    /**
     * 余额接口
     *
     */
    @GET("members/balance")
    suspend fun getUserYue():BaseResult<YueEntity>

    /**
     * 喂养动物
     */
    @POST("animals/feed")
    suspend fun feedAnimals(@Body requestBody: RequestBody):BaseResult<Any>
    

    /**
     * 获取支付类型列表
     */
    @GET("members/GetPayType/1")
    suspend fun getPayTypeList():BaseResult<List<PayTypeEntity>>

    /**
     * 充值支付
     */
    @POST("members/PayOnline")
    suspend fun onlinePay(@Body requestBody: RequestBody):BaseResult<OnLinePayEntity>

    /**
     * 分享、签到、转盘
     */
    @POST("members/sharefodder")
    suspend fun shareFodder(@Body requestBody: RequestBody):BaseResult<Any>


    /**
     * 银行卡列表
     */
    @GET("banks/getall")
    suspend fun getBankCardList():BaseResult<List<BankCardEntity>>

    /**
     * 从默认银行卡提现
     */
    @POST("members/withdrawl")
    suspend fun drawMoney(@Body requestBody: RequestBody):BaseResult<Any>

    /**
     * 设置默认卡
     */
    @PUT("bankaccounts")
    suspend fun setDefaultCard(@Body requestBody: RequestBody):BaseResult<Any>

    /**
     * 创建银行卡账户
     */
    @POST("bankaccounts")
    suspend fun createBankCard(@Body requestBody: RequestBody):BaseResult<Any>

    /**
     * 真实身份认证
     */
    @POST("members/setCredentialsInfo")
    suspend fun setCreditInfo(@Body requestBody: RequestBody):BaseResult<Any>

    /**
     * 购买的动物历史
     */
    @GET("animals/anmbuyhistory/{pageSize}/{pageIndex}")
    suspend fun getAnimalHistory(@Path("pageSize") pageSize: Int, @Path("pageIndex") pageIndex: Int):BaseResult<List<AnimalEntity>>

    /**
     * 修改用户信息
     */
    @PUT("members")
    suspend fun updateUserInfo(@Body requestBody: RequestBody):BaseResult<Any>

    /**
     * 上传头像到图片服务器
     */
    @Multipart
    @POST
    suspend fun uploadHead1(@Url url: String, @Part("type") body: RequestBody, @Part part: MultipartBody.Part):BaseResult<String>
    /**
     * 修改用户信息中的头像地址
     */
    @PUT("members/headimg")
    suspend fun uploadHead2(@Body body: RequestBody):BaseResult<String>

    /**
     *修改登录密码
     */
    @POST("members/password")
    suspend fun updatePwd(@Body requestBody: RequestBody):BaseResult<Any>

    /**
     * 查询可以分享的次数
     */
    @GET("members/sharecount")
    suspend fun getShareCount():BaseResult<ShareCountEntity>

    /**
     *  分享、签到、转盘
     */
    @POST("members/sharefodder")
    suspend fun getSharefodder(@Body requestBody: RequestBody):BaseResult<ShareCountEntity>

    /**
     * 资金明细流水
     */
    @POST("members/FundChanges/{pageIndex}")
    suspend fun getAccountDetail(@Path("pageIndex") pageIndex: Int,@Body body: RequestBody):BaseResult<List<AccountDetailEntity>>

    //查询存款：tradeType一定要传入1
    // 查询提款：tradeType一定要传入2
    // 查询转账：tradeType一定要传入3_4
    @POST("members/Trades/{pageIndex}")
    suspend fun getAccountType(@Path("pageIndex") pageIndex: Int,@Body body: RequestBody):BaseResult<List<AccountDetailEntity>>

    /**
     * 团队成员列表
     */
    @POST("agentmanage/GetUserMemberlst/{pageSize}/{pageIndex}")
    suspend fun getTeamMemberList(@Path("pageSize") pageSize:Int,@Path("pageIndex") pageIndex:Int,@Body body: RequestBody):BaseResult<List<TeamMemberEntity>>


    /**
     * 收获动物
     *
    SaleType
    int
    1:一键销售
    2:销售某一个
    BuyID
    int
    购买动物id,具体的某个动物ID
    如saleType是2，此字段必填
    AnimalID
    int
    动物ID,动物大类型ID
    如saleType是1，此字段比必填
     */
    @POST("animals/gather")
    suspend fun gather(@Body body: RequestBody):BaseResult<Any>

}