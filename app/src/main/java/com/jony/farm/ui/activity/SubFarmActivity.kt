package com.jony.farm.ui.activity

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.utils.GlideUtils
import com.combodia.basemodule.utils.LogUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.model.entity.AnimalEntity
import com.jony.farm.model.entity.KindEntity
import com.jony.farm.ui.adapter.AnimalAdapter
import com.jony.farm.ui.adapter.FarmKindAdapter
import com.jony.farm.util.MathUtil
import com.jony.farm.viewmodel.SubFarmViewModel
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_subfarm.*
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/5/25 18:44
 *描述:This is SubLineFarmActivity
 */

@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_SUBFARM, interceptorNames = [Const.LOGININTERCEPTOR])
class SubFarmActivity:BaseVMActivity<SubFarmViewModel>() {
    private var userId:Int = 0
    private var balance:Double = 0.0
    private var lc:Double = 0.0

    private val TOTALAMOUNT = 25

    private val allAnimalList = mutableListOf<AnimalEntity>()
    private val showAnimalList = mutableListOf<AnimalEntity>()
    private val kindList = mutableListOf<KindEntity>()
    private val kindAdapter by lazy { FarmKindAdapter() }
    private val animalAdapter by lazy { AnimalAdapter() }

    private var kindSelectIndex = -1

    override fun initVM(): SubFarmViewModel = getViewModel()

    override fun getLayoutResId(): Int = R.layout.activity_subfarm

    override fun initView() {

        userId = intent.getIntExtra("userId",0)
        balance = intent.getDoubleExtra("balance",0.0)
        lc = intent.getDoubleExtra("lc",0.0)

        tv_qc.text = MathUtil.getTwoBigDecimal(balance)
        tv_lc.text = MathUtil.getTwoBigDecimal(lc)
        iv_farm_back.setOnClickListener { onBackPressed() }

        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)

        GlideUtils.loadRoundImage(iv_avatar, R.mipmap.ic_avatar_default)
        progress_bar.max = 100
        progress_bar.progress = 40

        initRecy()
    }

    private fun initRecy(){
        recy_kind.run {
            adapter = kindAdapter
            layoutManager = LinearLayoutManager(this@SubFarmActivity)
        }
        kindAdapter.setOnItemClickListener { _, _, position ->
            if (kindSelectIndex == -1) {
                return@setOnItemClickListener
            }
            if (position == kindSelectIndex) {
                return@setOnItemClickListener
            }
            kindSelectIndex = position
           // showAnimalList.clear()
           // showAnimalList.addAll(getShowAnimals(kindAdapter.getItem(position).animalID))
            animalAdapter.setList(getShowAnimals(kindAdapter.getItem(position).animalID))
        }

        recy_animal.run {
            adapter = animalAdapter
            layoutManager = GridLayoutManager(this@SubFarmActivity, 5)
        }
    }

    fun getShowAnimals(kindId:Int):List<AnimalEntity>{
        var haveList = allAnimalList.filter { it.animalID == kindId }
        val tagList = mutableListOf<Int>()
        for (i in 0 until TOTALAMOUNT) {
            tagList.add(0)
        }
        LogUtils.error("选中的动物数量:" + haveList.size)
        if (haveList.isNotEmpty()) {
            if (haveList.size >= TOTALAMOUNT - 2) {
                haveList = haveList.subList(0, TOTALAMOUNT - 2)
            }

            var x = 0
            while (x < haveList.size) {
                val random = (2 until TOTALAMOUNT).random()
                if (tagList[random] == 0) {
                    tagList[random] = 1
                    x++
                }
            }
            LogUtils.error("taglist:$tagList")
            x = 0
            val resultList = mutableListOf<AnimalEntity>()
            for (i in 0 until TOTALAMOUNT) {
                if (tagList[i] == 1) {
                    resultList.add(haveList[x])
                    x++
                } else {
                    /**
                     * 空的animal 占位
                     */
                    resultList.add(AnimalEntity())
                }
            }
           return resultList
        }
        return showAnimalList
    }

    override fun initData() {
        mViewModel.getData(userId)
    }

    override fun startObserve() {
        mViewModel.animalsLiveData.observe(this,{ list ->
            //所有动物
            allAnimalList.addAll(list)
            /**
             * 分动物大类
             */
            val animalSet = HashSet<KindEntity>()
            allAnimalList.map {
                val kindEntity = KindEntity(animalID = it.animalID)
                animalSet.add(kindEntity)
            }
            kindList.addAll(animalSet.toList().sortedBy { it.animalID })
            kindAdapter.setList(kindList)
            kindSelectIndex = 0
            animalAdapter.setList(getShowAnimals(kindList.first().animalID))
        })
    }
}