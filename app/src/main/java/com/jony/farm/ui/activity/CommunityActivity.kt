package com.jony.farm.ui.activity

import com.combodia.basemodule.base.BaseVMActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.viewmodel.CommunityViewModel
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_community.*
import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/5/18 16:59
 *描述:This is CommunityActivity
 */
@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_COMMUNITYCHAT,interceptorNames = [Const.LOGININTERCEPTOR])
class CommunityActivity:BaseVMActivity<CommunityViewModel>() {


    override fun initVM(): CommunityViewModel = getViewModel()



    override fun getLayoutResId(): Int = R.layout.activity_community

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_communitychat)

    }

    override fun initData() {

    }

    override fun startObserve() {

    }
}