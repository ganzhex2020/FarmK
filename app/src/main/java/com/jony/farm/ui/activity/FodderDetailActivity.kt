package com.jony.farm.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.combodia.basemodule.base.BaseVMActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.ui.adapter.FundDetailAdapter
import com.jony.farm.viewmodel.FodderDetailViewModel
import com.xiaojinzi.component.Component
import com.xiaojinzi.component.anno.AttrValueAutowiredAnno
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_fodderdetail.*
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



    @JvmField
    @AttrValueAutowiredAnno("tradeType")
    var tradeType: Int = 0
    private var pageIndex = 1

    private val accountDetailAdapter by lazy{ FundDetailAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        Component.inject(this)
        super.onCreate(savedInstanceState)
    }


    override fun initVM(): FodderDetailViewModel = getViewModel()


    override fun getLayoutResId(): Int = R.layout.activity_fodderdetail

    override fun initView() {

        FundDetailAdapter.tradeType = tradeType

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
            adapter = accountDetailAdapter
            layoutManager = LinearLayoutManager(this@FodderDetailActivity)
        }
        accountDetailAdapter.loadMoreModule.run {
            preLoadNumber = 3
            setOnLoadMoreListener{
                pageIndex++
                mViewModel.getFodderDetail(pageIndex,tradeType)
            }
        }
    }

    override fun initData() {
        mViewModel.getFodderDetail(pageIndex, tradeType)
    }

    override fun startObserve() {
        mViewModel.accountDetailLiveData.observe(this,{
            if (pageIndex == 1){
                accountDetailAdapter.setList(it)
            }else{
                accountDetailAdapter.addData(it)
            }
            if (it.size < 30) {
                accountDetailAdapter.loadMoreModule.loadMoreEnd()
            } else {
                accountDetailAdapter.loadMoreModule.loadMoreComplete()
            }
        })
    }
}