package com.jony.farm.model.repository


import com.combodia.httplib.ext.callRequest
import com.combodia.httplib.ext.handleResponse
import com.combodia.httplib.model.BaseResult
import com.combodia.httplib.model.SealedResult
import com.jony.farm.model.api.ServiceApi
import com.jony.farm.model.entity.*
import com.jony.farm.util.MapUtils
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

/**
 *Author:ganzhe
 *时间:2020/10/23 22:43
 *描述:This is RemoteDataSource
 */
class RemoteDataSource(private val serviceApi: ServiceApi) {


    //==================test api======================================================
   /* suspend fun testlogin() : BaseResult<UserInfo> {
        return serviceApi.login("ganzhex2020@gmail.com","jony")
    }
    private suspend fun reqtest(page: Int) =
        handleResponse(serviceApi.getHomeArticles(page))
    suspend fun test(page: Int): SealedResult<WanListResponse<List<Article>>> =
        callRequest { reqtest(page) }

    //================================================================================

    //登录
    suspend fun login(userName:String,password:String) : BaseResult<UserInfo> {
        return serviceApi.login(userName,password)
    }

    //获取首页banner
    private suspend fun reqBanner() =
        handleResponse(serviceApi.getBanner())
    suspend fun getBanner(): SealedResult<List<HomeBanner>> =
        callRequest { reqBanner() }

    //获取首页顶置文章
    *//*suspend fun getTopArticles():BaseResult<List<Article>>{
        return serviceApi.getTopArticles()
    }*//*
    private suspend fun reqTopArticles() =
        handleResponse(serviceApi.getTopArticles())
    suspend fun getTopArticles(): SealedResult<List<Article>> =
        callRequest {
            //测试并发请求   中的一个抛出异常 会什么结果
         //   throw RuntimeException("曹尼玛")
         //   delay(15000)
            reqTopArticles()
        }

    //获取首页文章
    *//*suspend fun getHomeArticles(page:Int):BaseResult<WanListResponse<List<Article>>>{
        throw RuntimeException("曹尼玛")
        return serviceApi.getHomeArticles(page)
    }*//*
    private suspend fun reqHomeArticles(page: Int) =
        handleResponse(serviceApi.getHomeArticles(page = page))
    suspend fun getHomeArticles(page:Int): SealedResult<WanListResponse<List<Article>>> =
        callRequest { reqHomeArticles(page) }


    //项目分类
    suspend fun getProjectType():BaseResult<List<Classify>>{
        return serviceApi.getProjectType()
    }

    //获取最新项目列表
    suspend fun getLatestProjectList(pageNo:Int):BaseResult<WanListResponse<List<Article>>>{
        return serviceApi.getLatestProject(pageNo)
    }
    //获取分类下的项目列表
    suspend fun getProjectList(pageNo: Int,cid:Int):BaseResult<WanListResponse<List<Article>>>{
        return serviceApi.getProjectList(pageNo,cid)
    }
    //收藏站内文章
    suspend fun collect(id:Int):BaseResult<Any>{
        return serviceApi.collect(id)
    }
    //取消收藏站内文章
    suspend fun unCollect(id:Int):BaseResult<Any>{
        return serviceApi.unCollect(id)
    }

    suspend fun getSystemClassify():BaseResult<List<Classify>>{
        return serviceApi.getSystemClassify()
    }
*/

    //获取首页banner
    private suspend fun reqBanner(appId: Int) =
            handleResponse(serviceApi.getBanner(appId))
    suspend fun getBanner(appId:Int): SealedResult<List<BannerEntity>> =
            callRequest { reqBanner(appId) }

    /**
     * 动物列表
     */
    private suspend fun reqAnimalList() = handleResponse(serviceApi.getAnimalList())
    suspend fun getAnimalList():SealedResult<List<KindEntity>> =
            callRequest { reqAnimalList() }

    /**
     * 登录
     */
    suspend fun login(requestBody: RequestBody):BaseResult<UserEntity>{
        return serviceApi.login(requestBody)
    }

    /**
     * 退出登录
     */
    suspend fun signOut():BaseResult<Any> = serviceApi.signOut()
    /**
     * 动物余额查询
     */
    suspend fun reqAnimalLeftCount(animalId:Int) =  handleResponse(serviceApi.getAnimalLeft(animalId))
    suspend fun getAnimalLeftCount(animalId:Int):SealedResult<Int> = callRequest {
        reqAnimalLeftCount(animalId)
    }

    suspend fun getAnimalLeft(animalId: Int):BaseResult<Int> = serviceApi.getAnimalLeft(animalId)

    /**
     * 个人信息
     */
    private suspend fun reqMembers() =  handleResponse(serviceApi.getMembers())
    suspend fun getMembers():SealedResult<MemberEntity> = callRequest {
        reqMembers()
    }

    /**
     * 用户余额
     */
    suspend fun reqYue() =  handleResponse(serviceApi.getUserYue())
    suspend fun getYue():SealedResult<YueEntity> = callRequest {
        reqYue()
    }

    /**
     * 购买动物
     */
    suspend fun buyAnimal(animalId: Int,count:Int):BaseResult<Any>{
        val map = hashMapOf<String,Int>("id" to animalId,"count" to count)
        val requestBody = MapUtils.map2JsonRequestBody(map)
        return serviceApi.buyAnimal(requestBody)
    }

    /**
     * 农场已购买的动物列表
     */
    suspend fun reqFarmAllAnimal() =  handleResponse(serviceApi.getFarmAllAnimal())
    suspend fun getFarmAllAnimal():SealedResult<List<AnimalEntity>> = callRequest {
        reqFarmAllAnimal()
    }

    /**
     * 喂养动物
     */
    suspend fun feedAnimals(map:Map<String,Int>):BaseResult<Any>{
        val requestBody = MapUtils.map2JsonRequestBody(map)
        return serviceApi.feedAnimals(requestBody)
    }
    /**
     * 获取支付大类
     */
    suspend fun getPayTypeList():BaseResult<List<PayTypeEntity>>{
        return serviceApi.getPayTypeList()
    }

    /**
     * 充值支付
     */
    suspend fun onlinePay(requestBody: RequestBody):BaseResult<OnLinePayEntity>{
        return serviceApi.onlinePay(requestBody)
    }

    /**
     * 银行卡列表
     */
    suspend fun getBankCardList():BaseResult<List<BankCardEntity>>{
        return serviceApi.getBankCardList()
    }

    /**
     * 从默认银行卡提现
     */
    suspend fun drawMoney(body: RequestBody):BaseResult<Any>{
        return serviceApi.drawMoney(body)
    }

    /**
     * 设置默认卡
     */
    suspend fun setDefaultCard(body: RequestBody):BaseResult<Any>{
        return serviceApi.setDefaultCard(body)
    }

    /**
     * 创建银行卡账户
     */

    suspend fun createBankCard(requestBody: RequestBody):BaseResult<Any>{
        return serviceApi.createBankCard(requestBody)
    }

    /**
     * 真实身份认证
     */
   // private suspend fun reqCreditInfo(requestBody: RequestBody) =  handleResponse()
    suspend fun setCreditInfo(requestBody: RequestBody):BaseResult<Any> = serviceApi.setCreditInfo(requestBody)

    /**
     * 购买的动物历史
     */
    suspend fun getAnimalHistory(pageSize:Int,pageIndex:Int):BaseResult<List<AnimalEntity>>{
        return serviceApi.getAnimalHistory(pageSize, pageIndex)
    }

    /**
     * 修改用户信息
     */
    suspend fun updateUserInfo(requestBody: RequestBody):BaseResult<Any>{
        return serviceApi.updateUserInfo(requestBody)
    }

    /**
     * 上传头像到图片服务器
     */
    suspend fun uploadHead1(url: String,body: RequestBody,part: MultipartBody.Part):BaseResult<String>{
        return serviceApi.uploadHead1(url, body, part)
    }

    /**
     * 修改用户信息中的头像地址
     */
    suspend fun uploadHead2(@Body body: RequestBody):BaseResult<String>{
        return serviceApi.uploadHead2(body)
    }
    /**
     *修改登录密码
     */
    suspend fun updatePwd(requestBody: RequestBody):BaseResult<Any>{
      return  serviceApi.updatePwd(requestBody)
    }

    /**
     * 查询可以分享的次数
     */
    suspend fun getShareCount():BaseResult<ShareCountEntity>{
        return serviceApi.getShareCount()
    }

    /**
     *  分享、签到、转盘
     */
    suspend fun getSharefodder(requestBody: RequestBody):BaseResult<ShareCountEntity>{
        return serviceApi.getSharefodder(requestBody)
    }

    /**
     * 账户资金明细流水
     */
    suspend fun getAccountDetail(pageIndex:Int,body: RequestBody):BaseResult<List<AccountDetailEntity>>{
        return serviceApi.getAccountDetail(pageIndex,body)
    }
    /**
     * 充值 提现 流水
     */
    suspend fun getAccountType(pageIndex: Int,body: RequestBody):BaseResult<List<AccountDetailEntity>>{
        return serviceApi.getAccountType(pageIndex, body)
    }
    /**
     * 团队成员列表
     */
    suspend fun getTeamMemberList(pageSize:Int,pageIndex:Int,body: RequestBody):BaseResult<List<TeamMemberEntity>>{
        return serviceApi.getTeamMemberList(pageSize, pageIndex,body)
    }

    /**
     * 收获动物
     */
    suspend fun gather(body: RequestBody):BaseResult<Any>{
        return serviceApi.gather(body)
    }
}