package com.jony.farm.ui.activity

import android.graphics.BitmapFactory
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.GlideUtils
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.config.Constant
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.model.entity.AnimalEntity
import com.jony.farm.ui.adapter.AnimalAdapter
import com.jony.farm.ui.adapter.FarmKindAdapter
import com.jony.farm.util.DeviceUtil
import com.jony.farm.util.MathUtil
import com.jony.farm.view.FarmSurfaceView
import com.jony.farm.view.GameConfig
import com.jony.farm.view.dialog.FeedAllDialog
import com.jony.farm.view.dialog.FeedSingleDialog
import com.jony.farm.viewmodel.FarmViewModel
import com.tencent.mmkv.MMKV
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_farm.*
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/3/11 13:35
 *描述:This is FarmActivity
 */
@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_FARM,interceptorNames = [Const.LOGININTERCEPTOR])
class FarmActivity:BaseVMActivity<FarmViewModel>() {

    private var kindSelectIndex = -1
    private var feedAllDialog:FeedAllDialog? = null
    private var feedSingleDialog:FeedSingleDialog? = null

    private val allAnimalList = mutableListOf<AnimalEntity>()
    private val kindAdapter by lazy { FarmKindAdapter() }
    private val animalAdapter by lazy { AnimalAdapter() }


    override fun initVM(): FarmViewModel = getViewModel()

    override fun getLayoutResId(): Int = R.layout.activity_farm

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0,statusBarHeight,0,0)

        GlideUtils.loadRoundImage(iv_avatar,R.mipmap.ic_avatar_default)
        progress_bar.max =100
        progress_bar.progress = 40

        /*GameConfig.deviceWidth = DeviceUtil.getDeviceMetrics(this).widthPixels
        GameConfig.deviceHeight = DeviceUtil.getDeviceMetrics(this).heightPixels
        GameConfig.gameBG = BitmapFactory.decodeResource(resources,R.mipmap.ic_juan)
        GameConfig.scaleWidth = 1f//GameConfig.deviceWidth / GameConfig.gameBG.width.toFloat()
        GameConfig.scaleHeight = 1f//GameConfig.deviceHeight*DeviceUtil.getDeviceMetrics(this).density / GameConfig.gameBG.height.toFloat()
        GameConfig.gameBG = DeviceUtil.resizeBitmap(GameConfig.gameBG)
        GameConfig.animalFrame[0] =  DeviceUtil.resizeBitmap(BitmapFactory.decodeResource(resources,R.mipmap.a_0))
        GameConfig.animalFrame[1] =  DeviceUtil.resizeBitmap(BitmapFactory.decodeResource(resources,R.mipmap.a_1))
        GameConfig.animalFrame[2] =  DeviceUtil.resizeBitmap(BitmapFactory.decodeResource(resources,R.mipmap.a_2))*/


     //   val gameView = FarmSurfaceView(this)
    //    mycusview.addView(gameView)
        initRecy()
        onClick()

    }

    private fun initRecy(){
        recy_kind.run {
            adapter = kindAdapter
            layoutManager = LinearLayoutManager(this@FarmActivity)
        }
        kindAdapter.setOnItemClickListener { _, _, position ->
            if (kindSelectIndex == -1){
                return@setOnItemClickListener
            }
            if (position == kindSelectIndex){
                return@setOnItemClickListener
            }
            kindSelectIndex = position
            mViewModel.getFarmAnimal(kindAdapter.getItem(position).animalID,allAnimalList)
        }

        recy_animal.run {
            adapter = animalAdapter
            layoutManager = GridLayoutManager(this@FarmActivity,5)
        }
        animalAdapter.setOnItemClickListener { _, _, position ->
            LogUtils.error("点击的位置："+position+"动物kindId:"+animalAdapter.getItem(position).animalID)
            if (kindSelectIndex == -1){
                return@setOnItemClickListener
            }
            if (animalAdapter.getItem(position).leftSeconde<=0){
                //售货它
                LogUtils.error("该收获了")
                val map = HashMap<String,Any>()
                map["SaleType"] = 2
                map["BuyID"] = animalAdapter.getItem(position).id
                val list = mutableListOf<AnimalEntity>()
                list.add(animalAdapter.getItem(position))
                mViewModel.gather(map,list)
            }else{
                //单个喂养
                val singleEntity = animalAdapter.getItem(position)
                feedSingleDialog = FeedSingleDialog(mViewModel,this@FarmActivity,this,singleEntity)
                feedSingleDialog?.show()
                //查询一次余额
                mViewModel.getBalance()
            }
        }
    }

    private fun onClick(){
        iv_farm_back.setOnClickListener {
            onBackPressed()
        }
        iv_farm_fodder.setOnClickListener {

        }
        iv_farm_help.setOnClickListener {

        }
        iv_farm_feedall.setOnClickListener {
            if (kindSelectIndex == -1){
                return@setOnClickListener
            }
            val kindAnimals = allAnimalList.filter { it.animalID == kindAdapter.getItem(kindSelectIndex).animalID }
            feedAllDialog = FeedAllDialog(mViewModel,this@FarmActivity,this,kindAnimals)
            feedAllDialog?.show()
            //查询一次余额
            mViewModel.getBalance()
        }
        iv_farm_gatherall.setOnClickListener {
            val gatherList = allAnimalList.filter { it.animalID == kindAdapter.getItem(kindSelectIndex).animalID  }
                    .filter { it.leftSeconde<=0 }
            val map = HashMap<String,Any>()
            map["SaleType"] = 1
            map["AnimalID"] = kindAdapter.getItem(kindSelectIndex).animalID
            mViewModel.gather(map,gatherList)
        }
    }

    override fun initData() {
        mViewModel.getData(true)

      //  animalAdapter.setList(list)
    }



    override fun startObserve() {
        mViewModel.run {
            animalKindLiveData.observe(this@FarmActivity,{
                kindAdapter.setList(it)
                kindSelectIndex = 0
            })
            //所有动物
            allAnimalLiveData.observe(this@FarmActivity,{
                allAnimalList.clear()
                allAnimalList.addAll(it)
            })
            //显示的动物
            showAnimalLiveData.observe(this@FarmActivity,{
                animalAdapter.setList(it)
            })
            //member 用户信息 livedata
            memberLiveData.observe(this@FarmActivity,{members->
                members.filter { it.userID == MMKV.defaultMMKV().decodeInt(Constant.KEY_USER_ID) }
                        .map {
                            tv_qc.text = MathUtil.getTwoBigDecimal(it.balance)
                            tv_lc.text = MathUtil.getTwoBigDecimal(it.lCoin)
                        }

            })
            feedAllStateLiveData.observe(this@FarmActivity,{map ->
                val state = map["state"] as Boolean
                val list = map["list"] as List<AnimalEntity>
                val FeedType = map["FeedType"] as Int
                if (state){
                    feedAllDialog?.dismiss()
                }
                //LogUtils.error(list)
                //喂今天 改变今天 needFeedToday的状态
                if (FeedType == 2){
                    allAnimalList.forEach { x->
                        list.forEach { y ->
                            if (x.id == y.id){
                                x.needFeedToday = false
                            }
                        }
                    }

                    animalAdapter.data.forEach {
                        if (it.animalID!=0){
                            it.needFeedToday = false
                        }
                    }
                }
                //一次性喂饱所有 改变isFull 为true
                if (FeedType == 3){
                    allAnimalList.forEach { x->
                        list.forEach { y ->
                            if (x.id == y.id){
                                x.isFull = true
                                x.needFeedToday = false
                            }
                        }
                    }
                    animalAdapter.data.forEach {
                        if (it.animalID!=0){
                            it.isFull = true
                            it.needFeedToday = false
                        }
                    }
                }

                animalAdapter.notifyDataSetChanged()
            })

            feedSingleStateLiveData.observe(this@FarmActivity,{map ->
                val state = map["state"] as Boolean
                val entity = map["entity"] as AnimalEntity
                val FeedType = map["FeedType"] as Int
                if (state){
                    feedSingleDialog?.dismiss()
                }

                //喂今天 改变今天 needFeedToday的状态
                if (FeedType == 0){
                    allAnimalList.forEach { x->
                            if (x.id == entity.id){
                                x.needFeedToday = false
                            }
                    }

                    animalAdapter.data.forEach {
                        if (it.animalID!=0&&it.id == entity.id){
                            it.needFeedToday = false
                        }
                    }
                }
                //一次性喂饱所有 改变isFull 为true
                if (FeedType == 1){
                    allAnimalList.forEach { x->

                            if (x.id == entity.id){
                                x.isFull = true
                                x.needFeedToday = false
                            }

                    }
                    animalAdapter.data.forEach {
                        if (it.animalID!=0&&it.id == entity.id){
                            it.isFull = true
                            it.needFeedToday = false
                        }
                    }
                }
                animalAdapter.notifyDataSetChanged()
            })

            gatherLiveData.observe(this@FarmActivity,{ list ->
                LogUtils.error("收获的动物列表:$list")
                val it = allAnimalList.iterator()
                while (it.hasNext()){
                    for (i in list.indices){
                        if (it.next().id == list[i].id){
                            it.remove()
                        }
                    }
                }

                val iterator = animalAdapter.data.iterator()
                while (iterator.hasNext()){
                    for (i in list.indices){
                        if (iterator.next().id == list[i].id){
                            iterator.remove()

                        }
                    }
                }
                animalAdapter.notifyDataSetChanged()
            })
        }

    }
}