package com.jony.farm.ui.activity

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.combodia.basemodule.base.BaseVMActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.ui.adapter.FodderDetailAdapter
import com.jony.farm.ui.adapter.FundDetailAdapter
import com.jony.farm.util.DeviceUtil
import com.jony.farm.view.VerticalDecoration
import com.jony.farm.viewmodel.FodderDetailViewModel
import com.xiaojinzi.component.Component
import com.xiaojinzi.component.anno.AttrValueAutowiredAnno
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_fodderdetail.*
import kotlinx.android.synthetic.main.activity_fodderdetail.ll_parent
import kotlinx.android.synthetic.main.activity_fodderdetail.recy
import kotlinx.android.synthetic.main.activity_funddetail.*
import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/4/29 17:56
 *描述:This is FodderDetailActivity
 */
@RouterAnno(
    host = Const.MODULE_HOST_APP,
    path = Const.MODULE_PATH_APP_FODDERDETAIL,
    interceptorNames = [Const.LOGININTERCEPTOR]
)
class FodderDetailActivity:BaseVMActivity<FodderDetailViewModel>() {



//    @JvmField
//    @AttrValueAutowiredAnno("tradeType")
//    var tradeType: Int = 0
    private var pageIndex = 1

    private val fodderDetailAdapter by lazy{ FodderDetailAdapter() }

 /*   override fun onCreate(savedInstanceState: Bundle?) {
        Component.inject(this)
        super.onCreate(savedInstanceState)
    }*/


    override fun initVM(): FodderDetailViewModel = getViewModel()


    override fun getLayoutResId(): Int = R.layout.activity_fodderdetail

    override fun initView() {

        //FundDetailAdapter.tradeType = tradeType

        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_fodderdetail)

        initRecy()
    }

    private fun initRecy(){
        recy.run {
            adapter = fodderDetailAdapter
            layoutManager = LinearLayoutManager(this@FodderDetailActivity)
        }
        val mDivider = ContextCompat.getDrawable(this, R.color.color_diver)
        recy.addItemDecoration(VerticalDecoration(this, mDivider, DeviceUtil.dip2px(this, 0.5f)))
        fodderDetailAdapter.loadMoreModule.run {
            preLoadNumber = 3
            setOnLoadMoreListener{
                pageIndex++
                mViewModel.getFodderDetail(pageIndex)
            }
        }
    }

    override fun initData() {
        mViewModel.getFodderDetail(pageIndex)
    }

    override fun startObserve() {
        mViewModel.accountDetailLiveData.observe(this,{
            if (pageIndex == 1){
                fodderDetailAdapter.setList(it)
            }else{
                fodderDetailAdapter.addData(it)
            }
            if (it.size < 15) {
                fodderDetailAdapter.loadMoreModule.loadMoreEnd()
            } else {
                fodderDetailAdapter.loadMoreModule.loadMoreComplete()
            }
        })
    }
}