package com.jony.farm.ui.activity

import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.ext.toast
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.model.entity.ShareCountEntity
import com.jony.farm.view.LuckPanAnimEndCallBack
import com.jony.farm.viewmodel.LuckDrawViewModel
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_luckdraw.*
import kotlinx.android.synthetic.main.fragment_luckdraw.*
import kotlinx.android.synthetic.main.fragment_luckdraw.iv_go
import kotlinx.android.synthetic.main.fragment_luckdraw.ll_parent
import kotlinx.android.synthetic.main.fragment_luckdraw.luckView
import kotlinx.android.synthetic.main.fragment_luckdraw.refreshLayout
import kotlinx.android.synthetic.main.fragment_luckdraw.tv_leftCount
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/5/18 16:39
 *描述:This is LuckDrawActivity
 */
@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_LUCKDRAW,interceptorNames = [Const.LOGININTERCEPTOR])
class LuckDrawActivity:BaseVMActivity<LuckDrawViewModel>() {

    private val numbers = arrayOf("10$", "11$", "12$", "13$", "14$", "15$", "16$", "17$")
    var shareCountEntity: ShareCountEntity?=null

    override fun initVM(): LuckDrawViewModel = getViewModel()

    override fun getLayoutResId(): Int = R.layout.activity_luckdraw

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)

        refreshLayout.setOnRefreshListener {
            mViewModel.getShareCount()
            it.finishRefresh(2000)
        }


        luckView.setItems(numbers)
        luckView.luckPanAnimEndCallBack = LuckPanAnimEndCallBack { str -> toast("恭喜你获得$str") }

        iv_back.setOnClickListener {
            onBackPressed()
        }

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
            sharecountLiveData.observe(this@LuckDrawActivity, {
                shareCountEntity = it
                tv_leftCount.text = "Free Times Todays:${it.luckDraw}/3"
            })
            sharefodderLiveData.observe(this@LuckDrawActivity, { map ->
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