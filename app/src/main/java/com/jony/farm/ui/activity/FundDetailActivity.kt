package com.jony.farm.ui.activity

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.utils.LogUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.ui.adapter.FundDetailAdapter
import com.jony.farm.util.DeviceUtil
import com.jony.farm.view.VerticalDecoration
import com.jony.farm.viewmodel.FundDetailViewModel
import com.xiaojinzi.component.Component
import com.xiaojinzi.component.anno.AttrValueAutowiredAnno
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_funddetail.*
import kotlinx.android.synthetic.main.activity_funddetail.ll_parent
import kotlinx.android.synthetic.main.activity_funddetail.refreshLayout
import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/5/3 20:57
 *描述:This is FundDetailActivity
 */
@RouterAnno(
        host = Const.MODULE_HOST_APP,
        path = Const.MODULE_PATH_APP_FUNDDETAIL,
        interceptorNames = [Const.LOGININTERCEPTOR]
)
class FundDetailActivity : BaseVMActivity<FundDetailViewModel>() {

    private var pageIndex = 1
    private var index = 0 //0 全部流水 1 充值 2 提现


    @JvmField
    @AttrValueAutowiredAnno("tradeType")
    public var tradeType: Int = 0   //1 qc  2 gc


    private val accountDetailAdapter by lazy { FundDetailAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        Component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun initVM(): FundDetailViewModel = getViewModel()

    override fun getLayoutResId(): Int = R.layout.activity_funddetail

    override fun initView() {

        FundDetailAdapter.tradeType = tradeType

        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_funddetail)

        initRecy()
        onClick()
    }

    private fun initRecy() {
        recy.run {
            adapter = accountDetailAdapter
            layoutManager = LinearLayoutManager(this@FundDetailActivity)
        }
        val mDivider = ContextCompat.getDrawable(this, R.color.transparent)
        recy.addItemDecoration(VerticalDecoration(this, mDivider, DeviceUtil.dip2px(this, 10f)))
        accountDetailAdapter.loadMoreModule.run {
            preLoadNumber = 3
            setOnLoadMoreListener {
                pageIndex++
                mViewModel.getAccountDetail(pageIndex, tradeType, index)
            }
        }
    }

    private fun onClick() {
        radio_group.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_btn1 -> {
                    LogUtils.error("radio_btn1")
                    index = 0
                    pageIndex = 1
                    FundDetailAdapter.type = 0
                    mViewModel.getAccountDetail(pageIndex, tradeType, index)
                }
                R.id.radio_btn2 -> {
                    LogUtils.error("radio_btn2")
                    index = 1
                    pageIndex = 1
                    FundDetailAdapter.type = 1
                    mViewModel.getAccountDetail(pageIndex, tradeType, index)
                }
                R.id.radio_btn3 -> {
                    LogUtils.error("radio_btn3")
                    index = 2
                    pageIndex = 1
                    FundDetailAdapter.type = 2
                    mViewModel.getAccountDetail(pageIndex, tradeType, index)
                }
            }
        }
        refreshLayout.setOnRefreshListener {
            pageIndex = 1
            mViewModel.getAccountDetail(pageIndex, tradeType, index)
            it.finishRefresh(2000)
        }
    }

    override fun initData() {
        mViewModel.getAccountDetail(pageIndex, tradeType, index)
    }

    override fun startObserve() {
        mViewModel.run {
            accountDetailLiveData.observe(this@FundDetailActivity, {
                if (pageIndex == 1) {
                    accountDetailAdapter.setList(it)
                } else {
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

}