package com.jony.farm.ui.fragment

import com.combodia.basemodule.base.BaseVMFragment
import com.combodia.basemodule.ext.toast
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.model.entity.ShareCountEntity
import com.jony.farm.view.LuckPanAnimEndCallBack
import com.jony.farm.viewmodel.LuckDrawViewModel
import kotlinx.android.synthetic.main.activity_test1.*
import kotlinx.android.synthetic.main.fragment_luckdraw.*
import org.koin.android.viewmodel.ext.android.getViewModel


/**
 *Author:ganzhe
 *时间:2021/4/2 16:29
 *描述:This is RotaryFragment
 */
class LuckDrawFragment :BaseVMFragment<LuckDrawViewModel>() {

    private val numbers = arrayOf("10$", "11$", "12$", "13$", "14$", "15$", "16$", "17$")
    var shareCountEntity: ShareCountEntity?=null


    override fun initVM(): LuckDrawViewModel = getViewModel()


    override fun getLayoutResId(): Int = R.layout.fragment_luckdraw

    override fun initView() {

       // LogUtils.error("高度:"+ getScreenH(cl_pan.context))
        ll_parent.setPadding(0, statusBarHeight, 0, 0)

        refreshLayout.setOnRefreshListener {
            mViewModel.getShareCount()
            it.finishRefresh(2000)
        }


        luckView.setItems(numbers)
        luckView.luckPanAnimEndCallBack = LuckPanAnimEndCallBack { str -> toast("恭喜你获得$str") }

        iv_go.setOnClickListener {
            if (shareCountEntity == null){
                toast("请刷新")
                return@setOnClickListener
            }
            if (shareCountEntity?.luckDraw==0){
                toast("今日抽奖机会已用完")
                return@setOnClickListener
            }
            mViewModel.getSharefodder()
        }

    }

    override fun initData() {
        mViewModel.getShareCount()
    }

    override fun startObserve() {
        mViewModel.run {
            sharecountLiveData.observe(viewLifecycleOwner, {
                shareCountEntity = it
                tv_leftCount.text = "Free Times Todays:${it.luckDraw}/3"
            })
            sharefodderLiveData.observe(viewLifecycleOwner, { map ->
                val num = map["num"] as Int
                val shaCount = map["shareCountEntity"] as ShareCountEntity
                shareCountEntity = shaCount
                tv_leftCount.text = "Free Times Todays:${shaCount.luckDraw}/3"
                if (num in 1..8){
                    luckView.setLuckNumber(num-1)
                    luckView.startAnim()
                }

            })
        }
    }



}