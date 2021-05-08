package com.jony.farm.model.repository
//
//
//
//import com.combodia.httplib.factory.RetrofitClient
//import com.jony.farm.config.Const
//import com.jony.farm.model.api.ServiceApi
//import com.jony.farm.util.MapUtils
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.runBlocking
//import kotlinx.coroutines.test.TestCoroutineDispatcher
//import kotlinx.coroutines.test.setMain
//import org.junit.After
//import org.junit.Before
//import org.junit.Test
//
//
//class ServiceApiTest {
//
//    private val testDispatcher = TestCoroutineDispatcher()
// //   private val mainThreadSurrogate = newSingleThreadContext("UI thread")
//    private val serviceApi = RetrofitClient.createApi(ServiceApi::class.java, Const.BASE_URL)
//
//    @Before
//    fun setUp() {
//        Dispatchers.setMain(testDispatcher)
//
//    }
//
//    @After
//    fun tearDown() {
//
//        testDispatcher.cleanupTestCoroutines()
//
//    }
//    @Test
//    fun getAnimalList() = runBlocking<Unit>{
//
//        launch(Dispatchers.IO){
//            val result = serviceApi.getAnimalList()
//            println(result)
//        }
//    }
//
//    @Test
//    fun getAnimalLeft() = runBlocking <Unit>{
//        launch(Dispatchers.IO){
//            val result = serviceApi.getAnimalLeft(1)
//            println(result)
//        }
//
//    }
//
//    @Test
//    fun register() = runBlocking<Unit>{
//        val map = HashMap<String,Any>()
//        map["userName"] = "ganzhe999"
//        map["password"] = "123456"
//        map["appID"] = 24
//        map["safeLevel"] = 2
//        map["2"] = 2
//        map["lid"] = 0
//        map["aid"] = 0
//
//
//        launch (Dispatchers.IO){
//            val body = MapUtils.map2JsonRequestBody(map)
//            val result = serviceApi.register(body)
//            println(result)
//        }
//
//    }
//
//    @Test
//    fun login() = runBlocking <Unit>{
//        val map = HashMap<String,Any>()
//        map["userName"] = "ganzhe666"
//        map["password"] = "123456"
//        map["appID"] = 24
//        map["prefix"] = "HENGDAKG"
//        map["device"] = 0
//
//        launch (Dispatchers.IO){
//            val body = MapUtils.map2JsonRequestBody(map)
//            val result = serviceApi.login(body)
//            println(result)
//        }
//    }
//
//
//
//    @Test
//    fun getBanner() = runBlocking<Unit> {
//
//        launch(Dispatchers.IO) {
//            val result = serviceApi.getBanner(24)
//            println(result)
//        }
//    }
//
//
//}