package com.jony.farm.ui.activity

import android.animation.*
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.animation.ScaleInAnimation
import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.ext.visable
import com.combodia.basemodule.utils.GlideUtils
import com.combodia.basemodule.utils.LogUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.model.entity.AnimalEntity
import com.jony.farm.ui.adapter.AnimalAdapter
import com.jony.farm.ui.adapter.FarmKindAdapter
import com.jony.farm.ui.adapter.FilAdapter
import com.jony.farm.util.DeviceUtil
import com.jony.farm.util.MapJUtil
import com.jony.farm.util.MathUtil
import com.jony.farm.util.gone
import com.jony.farm.view.dialog.FeedAllDialog
import com.jony.farm.view.dialog.FeedSingleDialog
import com.jony.farm.view.dialog.HelpDialog
import com.jony.farm.view.dialog.QueueDialog
import com.jony.farm.viewmodel.FarmViewModel
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_farm.*
import org.koin.android.viewmodel.ext.android.getViewModel
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.set

/**
 *Author:ganzhe
 *时间:2021/3/11 13:35
 *描述:This is FarmActivity
 */
@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_FARM, interceptorNames = [Const.LOGININTERCEPTOR])
class FarmActivity : BaseVMActivity<FarmViewModel>() {

    private var kindSelectIndex = -1
    private var feedAllDialog: FeedAllDialog? = null
    private var feedSingleDialog: FeedSingleDialog? = null

    private val allAnimalList = mutableListOf<AnimalEntity>()
    private val kindAdapter by lazy { FarmKindAdapter() }
    private val animalAdapter by lazy { AnimalAdapter() }

    private val showList = mutableListOf<AnimalEntity>()
    private val filAdapter by lazy { FilAdapter(this,showList) }


    override fun initVM(): FarmViewModel = getViewModel()

    override fun getLayoutResId(): Int = R.layout.activity_farm

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.transparent)
        }

        ll_parent.setPadding(0, statusBarHeight, 0, 0)

        GlideUtils.loadRoundImage(iv_avatar, R.mipmap.ic_avatar_default)
        progress_bar.max = 100
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
        initMenu()
        onClick()
        val list = listOf(getString(R.string.marquee_tip1),getString(R.string.marquee_tip2),getString(R.string.marquee_tip3),getString(R.string.marquee_tip4),getString(R.string.marquee_tip5),getString(R.string.marquee_tip6))
        tv_marquee.startWithList(list as List<Nothing>?)
    }

    private fun initRecy() {
        recy_kind.run {
            adapter = kindAdapter
            layoutManager = LinearLayoutManager(this@FarmActivity)
        }
        kindAdapter.adapterAnimation = ScaleInAnimation(0.1f)
        kindAdapter.setOnItemClickListener { _, view, position ->
            if (kindSelectIndex == -1) {
                return@setOnItemClickListener
            }
            if (position == kindSelectIndex) {
                return@setOnItemClickListener
            }
            kindSelectIndex = position
            clickKindAnim(view,0.5f)
            mViewModel.getFarmAnimal(kindAdapter.getItem(position).animalID, allAnimalList)
        }

        recy_animal.run {
            adapter = animalAdapter
            layoutManager = GridLayoutManager(this@FarmActivity, 5)
        }

        animalAdapter.addChildClickViewIds(R.id.iv_state)
        animalAdapter.setOnItemChildClickListener { _, _, position ->
            LogUtils.error("点击的位置：" + position + "动物kindId:" + animalAdapter.getItem(position).animalID)
            if (kindSelectIndex == -1) {
                return@setOnItemChildClickListener
            }
            if (animalAdapter.getItem(position).leftSeconde <= 0) {
                //售货它
                LogUtils.error("该收获了")
                val map = HashMap<String, Any>()
                map["SaleType"] = 2
                map["BuyID"] = animalAdapter.getItem(position).id
                val list = mutableListOf<AnimalEntity>()
                list.add(animalAdapter.getItem(position))

                gaMap = map
                gaList = list
                mViewModel.getQueue(kindAdapter.getItem(kindSelectIndex).animalID)
              //  mViewModel.gather(map, list)
              //  val dialog = QueueDialog(this,list)
              //  dialog.show()
            } else {
                //单个喂养
                val singleEntity = animalAdapter.getItem(position)
                feedSingleDialog = FeedSingleDialog(mViewModel, this@FarmActivity, this, singleEntity)
                feedSingleDialog?.show()
                //查询一次余额
                mViewModel.getBalance()
            }
        }

        recy_fil.run {
            adapter = filAdapter
            layoutManager = LinearLayoutManager(this@FarmActivity)
        }
        recy_fil.start()
    }

    private fun clickKindAnim(view: View,mFrom:Float){
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", mFrom, 1.5f,1f)
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", mFrom, 1.5f,1f)

//        scaleX.duration = 300L
//        scaleX.interpolator = DecelerateInterpolator()
//        scaleY.duration = 300L
//        scaleY.interpolator = DecelerateInterpolator()
        val animators = mutableListOf<Animator>()
        animators.add(scaleX)
        animators.add(scaleY)

        val animatorSet = AnimatorSet()
        animatorSet.duration = 500
        animatorSet.playTogether(animators)
        animatorSet.start()

    }
    private var isOpen = true
    private fun initMenu(){
        iv_farm_fodder.setOnClickListener {
            if (isOpen){
                val animator: ObjectAnimator = ObjectAnimator.ofFloat(cl_childMenu, "rotation", 0f, 180f)
                animator.duration = 500
                animator.start()
                isOpen = false

            }else{
                val animator: ObjectAnimator = ObjectAnimator.ofFloat(cl_childMenu, "rotation", 180f, 0f)
                animator.duration = 500
                animator.start()
                isOpen = true
            }
        }
        iv_farm_help.setOnClickListener {
            val helpDialog = HelpDialog(FarmActivity@this)
            helpDialog.show()
        }
        iv_farm_feedall.setOnClickListener {
            val gatherList = allAnimalList.filter { it.animalID == kindAdapter.getItem(kindSelectIndex).animalID }
                .filter { it.leftSeconde <= 0 }
            val map = HashMap<String, Any>()
            map["SaleType"] = 1
            map["AnimalID"] = kindAdapter.getItem(kindSelectIndex).animalID

            gaMap = map
            gaList = gatherList
            if (gatherList.isEmpty()){
                toast("没有需要收获的")
                return@setOnClickListener
            }
            mViewModel.getQueue(kindAdapter.getItem(kindSelectIndex).animalID)
        }
        iv_farm_gatherall.setOnClickListener {
            if (kindSelectIndex == -1) {
                return@setOnClickListener
            }
            val kindAnimals = allAnimalList.filter { it.animalID == kindAdapter.getItem(kindSelectIndex).animalID }
            feedAllDialog = FeedAllDialog(mViewModel, this@FarmActivity, this, kindAnimals)
            feedAllDialog?.show()
            //查询一次余额
            mViewModel.getBalance()
        }
        /*val menuItems = mutableListOf(R.mipmap.ic_farm_help,R.mipmap.ic_farm_feedall,R.mipmap.ic_farm_gatherall)
        archMenu.setMenuItems(menuItems)
        archMenu.setClickItemListener { resId ->
            when(resId){
                R.mipmap.ic_farm_help ->{
                    val helpDialog = HelpDialog(FarmActivity@this)
                    helpDialog.show()
                }
                R.mipmap.ic_farm_gatherall ->{
                    val gatherList = allAnimalList.filter { it.animalID == kindAdapter.getItem(kindSelectIndex).animalID }
                        .filter { it.leftSeconde <= 0 }
                    val map = HashMap<String, Any>()
                    map["SaleType"] = 1
                    map["AnimalID"] = kindAdapter.getItem(kindSelectIndex).animalID

                    gaMap = map
                    gaList = gatherList
                    if (gatherList.isEmpty()){
                        toast("没有需要收获的")
                        return@setClickItemListener
                    }
                    mViewModel.getQueue(kindAdapter.getItem(kindSelectIndex).animalID)
                }
                R.mipmap.ic_farm_feedall ->{
                    if (kindSelectIndex == -1) {
                        return@setClickItemListener
                    }
                    val kindAnimals = allAnimalList.filter { it.animalID == kindAdapter.getItem(kindSelectIndex).animalID }
                    feedAllDialog = FeedAllDialog(mViewModel, this@FarmActivity, this, kindAnimals)
                    feedAllDialog?.show()
                    //查询一次余额
                    mViewModel.getBalance()
                }
            }

        }*/
    }

    private var gaMap:Map<String,Any>? = null
    private var gaList:List<AnimalEntity>? = null

    private fun onClick() {
        iv_farm_back.setOnClickListener {
            onBackPressed()
        }
        iv_marquee_close.setOnClickListener {
            ll_marquee.gone()
        }
      /*  iv_farm_fodder.setOnClickListener {

        }
        iv_farm_help.setOnClickListener {

        }
        iv_farm_feedall.setOnClickListener {
            if (kindSelectIndex == -1) {
                return@setOnClickListener
            }
            val kindAnimals = allAnimalList.filter { it.animalID == kindAdapter.getItem(kindSelectIndex).animalID }
            feedAllDialog = FeedAllDialog(mViewModel, this@FarmActivity, this, kindAnimals)
            feedAllDialog?.show()
            //查询一次余额
            mViewModel.getBalance()
        }
        iv_farm_gatherall.setOnClickListener {

            val gatherList = allAnimalList.filter { it.animalID == kindAdapter.getItem(kindSelectIndex).animalID }
                    .filter { it.leftSeconde <= 0 }
            val map = HashMap<String, Any>()
            map["SaleType"] = 1
            map["AnimalID"] = kindAdapter.getItem(kindSelectIndex).animalID

            gaMap = map
            gaList = gatherList
            if (gatherList.isEmpty()){
                toast("没有需要收获的")
                return@setOnClickListener
            }
            mViewModel.getQueue(kindAdapter.getItem(kindSelectIndex).animalID)

        }*/

        /**
         * bord 操作
         */
        iv_show_bord.setOnClickListener {
            iv_show_bord.visable(false)
            val width = DeviceUtil.dip2px(this, 130f)
            val objectAnimation1 =ObjectAnimator.ofInt(cl_bord, "translationX", 0, width)
            objectAnimation1.duration = 1000
            objectAnimation1.addUpdateListener(animationListener1)
            objectAnimation1.addListener (object:AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    val height1 = DeviceUtil.dip2px(this@FarmActivity, 30f)
                    val height2 = DeviceUtil.dip2px(this@FarmActivity, 100f)
                    val objectAnimation2 =ObjectAnimator.ofInt(cl_bord, "translationY", height1, height2)
                    objectAnimation2.duration = 1000
                    objectAnimation2.addUpdateListener(animationListener2)
                    objectAnimation2.addListener(object:AnimatorListenerAdapter(){
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            iv_close.visable(true)
                        }
                    })
                    objectAnimation2.start()
                }
            })
            objectAnimation1.start()
        }
        //关闭
        iv_close.setOnClickListener {
            iv_close.visable(false)
            val height1 = DeviceUtil.dip2px(this@FarmActivity, 100f)
            val height2 = DeviceUtil.dip2px(this@FarmActivity, 30f)
            val objectAnimation2 =ObjectAnimator.ofInt(cl_bord, "translationY", height1, height2)
            objectAnimation2.duration = 1000
            objectAnimation2.addUpdateListener(animationListener2)
            objectAnimation2.addListener (object:AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    val width1 = DeviceUtil.dip2px(this@FarmActivity, 130f)
                    val width2 = DeviceUtil.dip2px(this@FarmActivity, 0f)
                    val objectAnimation1 =ObjectAnimator.ofInt(cl_bord, "translationX", width1, width2)
                    objectAnimation1.duration = 1000
                    objectAnimation1.addUpdateListener(animationListener1)
                    objectAnimation1.addListener(object:AnimatorListenerAdapter(){
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            iv_show_bord.visable(true)
                        }
                    })
                    objectAnimation1.start()
                }
            })
            objectAnimation2.start()
        }

    }


    private val animationListener1 = ValueAnimator.AnimatorUpdateListener {
        val value:Int = it.animatedValue as Int
        val params = cl_bord.layoutParams
        params.width = value
        cl_bord.layoutParams = params
    }
    private val animationListener2 = ValueAnimator.AnimatorUpdateListener {
        val value:Int = it.animatedValue as Int
        val params = cl_bord.layoutParams
        params.height = value
        cl_bord.layoutParams = params
    }

    override fun initData() {
        mViewModel.getData(true)

        //  animalAdapter.setList(list)
    }


    override fun startObserve() {
        mViewModel.run {
            animalKindLiveData.observe(this@FarmActivity, {
                kindAdapter.setList(it)
                kindSelectIndex = 0
            })
            //所有动物
            allAnimalLiveData.observe(this@FarmActivity, {
                allAnimalList.clear()
                allAnimalList.addAll(it)
            })
            //显示的动物
            showAnimalLiveData.observe(this@FarmActivity, {list ->
                animalAdapter.setList(list)

                showList.clear()
                if (list.isEmpty()){
                    showList.addAll(list)
                }else{
                    showList.addAll(list.filter { it.animalID != 0 })
                }

                filAdapter.notifyDataSetChanged()
            })

            yueLiveData.observe(this@FarmActivity,{list ->
                if (list!=null &&list.isNotEmpty()){
                    tv_qc.text = MathUtil.getTwoBigDecimal(list[0].item1)
                    tv_lc.text = MathUtil.getTwoBigDecimal(list[0].item2)
                }

            })
            feedAllStateLiveData.observe(this@FarmActivity, { map ->
                val state = map["state"] as Boolean
                val list = map["list"] as List<AnimalEntity>
                val FeedType = map["FeedType"] as Int
                if (state) {
                    feedAllDialog?.dismiss()
                }
                //LogUtils.error(list)
                //喂今天 改变今天 needFeedToday的状态
                if (FeedType == 2) {
                    allAnimalList.forEach { x ->
                        list.forEach { y ->
                            if (x.id == y.id) {
                                x.needFeedToday = false
                            }
                        }
                    }

                    animalAdapter.data.forEach {
                        if (it.animalID != 0) {
                            it.needFeedToday = false
                        }
                    }
                }
                //一次性喂饱所有 改变isFull 为true
                if (FeedType == 3) {
                    allAnimalList.forEach { x ->
                        list.forEach { y ->
                            if (x.id == y.id) {
                                x.isFull = true
                                x.needFeedToday = false
                            }
                        }
                    }
                    animalAdapter.data.forEach {
                        if (it.animalID != 0) {
                            it.isFull = true
                            it.needFeedToday = false
                        }
                    }
                }

                animalAdapter.notifyDataSetChanged()
            })

            feedSingleStateLiveData.observe(this@FarmActivity, { map ->
                val state = map["state"] as Boolean
                val entity = map["entity"] as AnimalEntity
                val FeedType = map["FeedType"] as Int
                if (state) {
                    feedSingleDialog?.dismiss()
                }

                //喂今天 改变今天 needFeedToday的状态
                if (FeedType == 0) {
                    allAnimalList.forEach { x ->
                        if (x.id == entity.id) {
                            x.needFeedToday = false
                        }
                    }

                    animalAdapter.data.forEach {
                        if (it.animalID != 0 && it.id == entity.id) {
                            it.needFeedToday = false
                        }
                    }
                }
                //一次性喂饱所有 改变isFull 为true
                if (FeedType == 1) {
                    allAnimalList.forEach { x ->

                        if (x.id == entity.id) {
                            x.isFull = true
                            x.needFeedToday = false
                        }

                    }
                    animalAdapter.data.forEach {
                        if (it.animalID != 0 && it.id == entity.id) {
                            it.isFull = true
                            it.needFeedToday = false
                        }
                    }
                }
                animalAdapter.notifyDataSetChanged()
            })

            gatherLiveData.observe(this@FarmActivity, { list ->
                LogUtils.error("收获的动物列表:$list")
                MapJUtil.listRemove(allAnimalList, list)
                MapJUtil.listRemoveJ(animalAdapter.data, list)
                /*val it = allAnimalList.iterator()
                for (i in list.indices) {
                    while (it.hasNext()) {
                        if (it.next().id == list[i].id) {
                            it.remove()
                        }
                    }
                }
                val iterator = animalAdapter.data.iterator()
                for (i in list.indices) {
                    while (iterator.hasNext()) {
                        if (iterator.next().id == list[i].id) {
                            iterator.remove()
                        }
                    }
                }*/
                animalAdapter.notifyDataSetChanged()
            })
            queueLiveData.observe(this@FarmActivity,{
                val dialog = QueueDialog(this@FarmActivity,it)
                dialog.setOnClickListener {
                    if (gaMap!=null&&gaList!=null){
                        mViewModel.gather(gaMap!!, gaList!!)
                    }
                }
                dialog.show()
            })
        }

    }
    override fun onResume() {
        super.onResume()
        recy_fil.start()
    }


    override fun onPause() {
        super.onPause()
        recy_fil.stop()
    }
}