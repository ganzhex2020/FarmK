package com.jony.farm.ui.activity

import com.combodia.basemodule.base.BaseVMActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.viewmodel.AgentRecrViewModel
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_agentarch.*
import kotlinx.android.synthetic.main.activity_agentarch.ll_parent
import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel

@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_AGENTARCH, interceptorNames = [Const.LOGININTERCEPTOR])
class AgentRecrActivity:BaseVMActivity<AgentRecrViewModel>() {


    override fun initVM(): AgentRecrViewModel = getViewModel()

    override fun getLayoutResId(): Int = R.layout.activity_agentarch

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_agentarch)

        iv_goagent.setOnClickListener {
            mViewModel.tobeAgent()
        }
    }

    override fun initData() {

    }

    override fun startObserve() {

    }
}