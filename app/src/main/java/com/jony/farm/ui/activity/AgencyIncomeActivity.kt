package com.jony.farm.ui.activity

import com.combodia.basemodule.base.BaseActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_agencyincome.*
import kotlinx.android.synthetic.main.layout_common_header.*

/**
 *Author:ganzhe
 *时间:2021/5/8 12:26
 *描述:This is AgencyIncomeActivity
 */
@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_AGENCYINCOME, interceptorNames = [Const.LOGININTERCEPTOR, Const.TRUENAMEINTERCEPTOR])
class AgencyIncomeActivity :BaseActivity(){


    override fun getLayoutResId(): Int = R.layout.activity_agencyincome

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_agencyincome)
    }

    override fun initData() {

    }
}