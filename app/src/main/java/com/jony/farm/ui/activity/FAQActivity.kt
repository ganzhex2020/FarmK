package com.jony.farm.ui.activity

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.combodia.basemodule.base.BaseVMActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.ui.adapter.FaqAdapter
import com.jony.farm.util.DeviceUtil
import com.jony.farm.view.VerticalDecoration
import com.jony.farm.viewmodel.FAQViewModel
import com.xiaojinzi.component.anno.RouterAnno

import kotlinx.android.synthetic.main.activity_faq.*
import kotlinx.android.synthetic.main.activity_faq.ll_parent
import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/5/18 13:57
 *描述:This is FAQActivity
 */
@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_FAQ)
class FAQActivity:BaseVMActivity<FAQViewModel>() {

    private val faqAdapter by lazy { FaqAdapter() }

    override fun initVM(): FAQViewModel = getViewModel()



    override fun getLayoutResId(): Int = R.layout.activity_faq

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_faq)

        initRecy()
    }

    private fun initRecy(){
        recy_faq.run {
            adapter = faqAdapter
            layoutManager = LinearLayoutManager(this@FAQActivity)
        }
        val mDivider = ContextCompat.getDrawable(this, R.color.transparent)
        recy_faq.addItemDecoration(VerticalDecoration(this, mDivider, DeviceUtil.dip2px(this, 10f)))
        faqAdapter.loadMoreModule.run {
            preLoadNumber = 3
            setOnLoadMoreListener {
            //    pageIndex++
            //    mViewModel.getAnimalHistory(pageIndex, pageSize)
            }
        }
    }

    override fun initData() {
        val list = listOf(1,2,3,4,5,6)
        faqAdapter.setList(list)
    }

    override fun startObserve() {

    }
}