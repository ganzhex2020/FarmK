package com.jony.farm.ui.activity

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.utils.LogUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.ui.adapter.LcDetailAdapter
import com.jony.farm.util.DeviceUtil
import com.jony.farm.view.VerticalDecoration
import com.jony.farm.viewmodel.LcDetailViewModel
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_funddetail.*
import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/5/3 20:57
 *描述:This is FundDetailActivity
 */
@RouterAnno(
        host = Const.MODULE_HOST_APP,
        path = Const.MODULE_PATH_APP_LCDETAIL,
        interceptorNames = [Const.LOGININTERCEPTOR]
)
class LcDetailActivity : BaseVMActivity<LcDetailViewModel>() {

    private var pageIndex = 1
    private var index = 0 //0 全部流水 1 充值 2 提现

    private val lcAdapter by lazy { LcDetailAdapter() }

    override fun initVM(): LcDetailViewModel = getViewModel()

    override fun getLayoutResId(): Int = R.layout.activity_funddetail

    override fun initView() {

        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_lcdetail)

        initRecy()
        onClick()

    }

    private fun initRecy() {
        recy.run {
            adapter = lcAdapter
            layoutManager = LinearLayoutManager(this@LcDetailActivity)
        }
        val mDivider = ContextCompat.getDrawable(this, R.color.color_diver)
        recy.addItemDecoration(VerticalDecoration(this, mDivider, DeviceUtil.dip2px(this, 0.5f)))
        lcAdapter.loadMoreModule.run {
            preLoadNumber = 3
            setOnLoadMoreListener {
                pageIndex++
                mViewModel.getLcDetail(pageIndex,  index)
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
                    LcDetailAdapter.type = 0
                    mViewModel.getLcDetail(pageIndex, index)
                }
                R.id.radio_btn2 -> {
                    LogUtils.error("radio_btn2")
                    index = 1
                    pageIndex = 1
                    LcDetailAdapter.type = 1
                    mViewModel.getLcDetail(pageIndex,index)
                }
                R.id.radio_btn3 -> {
                    LogUtils.error("radio_btn3")
                    index = 2
                    pageIndex = 1
                    LcDetailAdapter.type = 2
                    mViewModel.getLcDetail(pageIndex,index)
                }
            }
        }

        refreshLayout.setOnRefreshListener {
            pageIndex = 1
            mViewModel.getLcDetail(pageIndex,index)
            it.finishRefresh(2000)
        }
    }

    override fun initData() {
        mViewModel.getLcDetail(pageIndex, index)
    }

    override fun startObserve() {
        mViewModel.run {
            lcDetailLiveData.observe(this@LcDetailActivity, {
                if (pageIndex == 1) {
                    lcAdapter.setList(it)
                } else {
                    lcAdapter.addData(it)
                }
                if (it.size < 15) {
                    lcAdapter.loadMoreModule.loadMoreEnd()
                } else {
                    lcAdapter.loadMoreModule.loadMoreComplete()
                }
            })
        }
    }

}