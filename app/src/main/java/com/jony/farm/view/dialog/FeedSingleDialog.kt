package com.jony.farm.view.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Paint
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.combodia.basemodule.ext.color
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.ext.visable
import com.combodia.httplib.config.Constant
import com.jony.farm.R
import com.jony.farm.model.entity.AnimalEntity
import com.jony.farm.util.DeviceUtil
import com.jony.farm.viewmodel.FarmViewModel
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.dialog_feedsingle.*


/**
 *Author:ganzhe
 *时间:2020/12/25 11:09
 *描述:This is VersionUpdateDialog
 */
class FeedSingleDialog(private val viewModel: FarmViewModel, private val lifecycleOwner: LifecycleOwner, context: Context, private val singleAnimalEntity: AnimalEntity) :
        Dialog(
                context,
                R.style.dialog_center_full
        ) {
    /**
     * 默认喂今天所需
     */
    var type = 1
    var totalNeed = 0

    init {

        setCanceledOnTouchOutside(true)
        setCanceledOnTouchOutside(true)
        val view = View.inflate(context, R.layout.dialog_feedsingle, null)
        val window = window
        window?.setContentView(view)
        window?.setGravity(Gravity.CENTER)
        window?.setLayout(
                DeviceUtil.getScreenW(context) - DeviceUtil.dip2px(context, 20f),
                ViewGroup.LayoutParams.WRAP_CONTENT
        )
        //设置描边
        val tv1paint = tv_inventory.paint
        tv1paint.strokeWidth = 2f
        tv1paint.style = Paint.Style.STROKE
        val tv2paint = tv_need.paint
        tv2paint.strokeWidth = 2f
        tv2paint.style = Paint.Style.STROKE
        //默认设置今日所需
        setNeedTv(type)

        //观察数据
        observe()

        /**
         * 选项框
         */
        val typeAdapter = TypeAdapter()
        recy_type.run {
            adapter = typeAdapter
            layoutManager = LinearLayoutManager(context)
        }
        val list = listOf(TypeEntity(context.getString(R.string.dialog_all_fodder), true), TypeEntity(context.getString(R.string.dialog_today_fodder), false))
        typeAdapter.setList(list)

        typeAdapter.setOnItemClickListener { _, _, position ->
            typeAdapter.data.map { it.select = false }
            typeAdapter.data[position].select = true
            typeAdapter.notifyDataSetChanged()
            tv_type.text = typeAdapter.data[position].name
            recy_type.visable(false)

            /**
             * 切换喂养类型
             */
            type = position
            setNeedTv(type)
        }
        /**
         * 点击ll_type
         */
        ll_type.setOnClickListener {
            recy_type.visable(!recy_type.isVisible)
        }
        /**
         * 点击喂养按钮
         */
        iv_feednow.setOnClickListener {
            Feed()
        }

    }

    //0 一次喂饱所需  1 今天所需
    @SuppressLint("SetTextI18n")
    private fun setNeedTv(index: Int) {
        var sum = 0
        if (index == 0) {
            if (!singleAnimalEntity.isFull){
                sum = singleAnimalEntity.cycleDay*singleAnimalEntity.needFodder - singleAnimalEntity.feedTime
            }
        }
        if (index == 1) {
            if (singleAnimalEntity.needFeedToday){
                sum = singleAnimalEntity.needFodder
            }
        }
        tv_need.text = "X$sum"
        totalNeed = sum
    }
    //0 一次喂饱所需  1 今天所需
    private fun Feed(){
        /*//是否需要喂养
        var isNeedFeed = false
        allAnimals.forEach {
            isNeedFeed = isNeedFeed||it.needFeedToday
        }
        //如果不需要喂养  提示return
        if (!isNeedFeed){
            toast("No need feed!")
            return
        }*/

        //传参数 FeedType
        // 2:所有动物喂1天的量
        //3:所有动物一次性喂所有选哟的
        //AnimalID list中随便一个的大类id
        var FeedType= 0
        if (type == 1){
            FeedType =0
        }
        if (type ==0){
            FeedType = 1
        }
        /**
         * 判断需要的饲料数
         */
        if (totalNeed == 0){
            toast("已喂")
            return
        }

        val BuyID = singleAnimalEntity.id
        val map = HashMap<String,Int>()
        map["FeedType"] = FeedType
        map["BuyID"] = BuyID
        viewModel.feedSingleAnimals(map,singleAnimalEntity,FeedType)

    }


    @SuppressLint("SetTextI18n")
    private fun observe() {
        viewModel.run {
            memberLiveData.observe(lifecycleOwner, { members ->
                members.filter { it.userID == MMKV.defaultMMKV().decodeInt(Constant.KEY_USER_ID) }
                        .map {
                            tv_inventory.text = "X" + it.fodder.toInt()
                        }
            })

        }
    }


    data class TypeEntity(val name: String, var select: Boolean)

    class TypeAdapter : BaseQuickAdapter<TypeEntity, BaseViewHolder>(R.layout.item_type) {

        override fun convert(holder: BaseViewHolder, item: TypeEntity) {
            val tv = holder.getView<TextView>(R.id.tv_type)
            tv.text = item.name
            //   tv.isSelected = item.select
            if (item.select) {
                tv.setTextColor(context.color(R.color.color_title3))
            } else {
                tv.setTextColor(context.color(R.color.color_title5))
            }
        }
    }


}