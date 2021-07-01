package com.jony.farm.ui.activity

import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.ext.toast
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.model.entity.ShareCountEntity
import com.jony.farm.view.LuckPanAnimEndCallBack
import com.jony.farm.view.dialog.LuckResultDialog
import com.jony.farm.view.dialog.LuckTipDialog
import com.jony.farm.viewmodel.LuckDrawViewModel
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_luckdraw.*

import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/5/18 16:39
 *描述:This is LuckDrawActivity
 */
@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_LUCKDRAW,interceptorNames = [Const.LOGININTERCEPTOR])
class LuckDrawActivity:BaseVMActivity<LuckDrawViewModel>() {

    //private val numbers = arrayOf("10$", "11$", "12$", "13$", "14$", "15$", "16$", "17$")



    enum class BackState{
        OUT,
        WZJ,
        ZJ
    }

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


      //  luckView.setItems(numbers)
        luckView.luckPanAnimEndCallBack = LuckPanAnimEndCallBack { index ->
            when(index){
                0 ->{
                    val luckTipDialog = LuckTipDialog(this,"很遗憾未中奖!","",BackState.WZJ)
                    luckTipDialog.show()
                }
                else ->{
                    val luckTipDialog = LuckTipDialog(this,"恭喜您!","抽奖奖励",BackState.ZJ)
                    luckTipDialog.show()
                }
            }
        }

        iv_back.setOnClickListener {
            onBackPressed()
        }

        iv_go.setOnClickListener {
            if (shareCountEntity == null){
                toast(getString(R.string.pls_refresh))
                return@setOnClickListener
            }
            if (shareCountEntity?.luckDraw==0){
                //toast(getString(R.string.luckdraw_nocount))
                val luckTipDialog = LuckTipDialog(this,"今日抽奖次数已用完!","明日再来噢!",BackState.OUT)
                luckTipDialog.show()
                return@setOnClickListener
            }
            mViewModel.getSharefodder()

        }

        iv_record.setOnClickListener {
            mViewModel.getLuckResult(1)
            val recordDialog = LuckResultDialog(this,mViewModel,this)
            recordDialog.show()
        }
    }

    override fun initData() {
        mViewModel.getShareCount()
    }
    override fun startObserve() {
        mViewModel.run {
            sharecountLiveData.observe(this@LuckDrawActivity, {
                shareCountEntity = it
                tv_leftCount.text = String.format(this@LuckDrawActivity.getString(R.string.luckdraw_left),it.luckDraw.toString()+"/3")//"Free Times Todays:${it.luckDraw}/3"
            })
            sharefodderLiveData.observe(this@LuckDrawActivity, { map ->
                val num = map["num"] as Int
                val shaCount = map["shareCountEntity"] as ShareCountEntity
                shareCountEntity = shaCount
                tv_leftCount.text = String.format(this@LuckDrawActivity.getString(R.string.luckdraw_left),shaCount.luckDraw.toString()+"/3")//"Free Times Todays:${shaCount.luckDraw}/3"
                if (num in 1..8){
                    luckView.setLuckNumber(num-1)
                    luckView.startGo()
                }

            })

        }
    }
}