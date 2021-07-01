package com.jony.farm.ui.activity

import com.combodia.basemodule.base.BaseVMActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.model.entity.ZnxEntity
import com.jony.farm.viewmodel.SmsViewModel
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_msgdetail.*

import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel

@RouterAnno(
    host = Const.MODULE_HOST_APP,
    path = Const.MODULE_PATH_APP_MSGDETAIL,
    interceptorNames = [Const.LOGININTERCEPTOR]
)
class MsgDetailActivity:BaseVMActivity<SmsViewModel>() {

    var znx:ZnxEntity? = null

    override fun initVM(): SmsViewModel = getViewModel()
    
    override fun getLayoutResId(): Int = R.layout.activity_msgdetail

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_sms)

        znx = intent.getParcelableExtra("znx")

        if (znx !=null){
            tv_title.text = znx!!.messageTitle
            tv_content.text = znx!!.messageContent
            tv_time.text = znx!!.addTime
        }
    }

    override fun initData() {
        if (znx !=null){
            mViewModel.setRead(znx!!.id)
        }
    }
    override fun startObserve() {

    }
}