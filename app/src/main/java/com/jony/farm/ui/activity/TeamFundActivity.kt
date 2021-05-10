package com.jony.farm.ui.activity

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.utils.LogUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.ui.adapter.TeamFundAdapter
import com.jony.farm.util.DeviceUtil
import com.jony.farm.view.VerticalDecoration
import com.jony.farm.viewmodel.TeamFundViewModel
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_funddetail.*
import kotlinx.android.synthetic.main.activity_teamfund.*
import kotlinx.android.synthetic.main.activity_teamfund.ll_parent
import kotlinx.android.synthetic.main.activity_teamfund.radio_group
import kotlinx.android.synthetic.main.activity_teamfund.recy
import kotlinx.android.synthetic.main.activity_teamfund.refreshLayout
import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/5/7 21:01
 *描述:This is TeamFundActivity
 */

@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_TEAMFUND, interceptorNames = [Const.LOGININTERCEPTOR, Const.TRUENAMEINTERCEPTOR])
class TeamFundActivity:BaseVMActivity<TeamFundViewModel>() {

    private var pageIndex = 1
    private val pageSize = 30
    private var tradeType = 1

    private val teamtradAdapter by lazy { TeamFundAdapter() }


    override fun initVM(): TeamFundViewModel = getViewModel()

    override fun getLayoutResId(): Int = R.layout.activity_teamfund

    override fun initView() {

        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_teamfund)

        initRecy()
        onClick()
    }

    private fun initRecy(){
        recy.run {
            adapter = teamtradAdapter
            layoutManager = LinearLayoutManager(this@TeamFundActivity)
        }
        val mDivider = ContextCompat.getDrawable(this, R.color.color_333)
        recy.addItemDecoration(VerticalDecoration(this, mDivider, DeviceUtil.dip2px(this, 0.5f)))
        teamtradAdapter.loadMoreModule.run {
            preLoadNumber = 3
            setOnLoadMoreListener {
                pageIndex++
                getTeamData()
            }
        }
    }

    fun getTeamData(){
        val map = HashMap<String,Any>()
        map["tradeType"] = tradeType
        mViewModel.teamcashtrade(pageIndex,map)
    }

    private fun onClick(){
        radio_group.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_btn1 -> {
                    LogUtils.error("radio_btn1")
                    pageIndex = 1
                    tradeType = 1
                    getTeamData()
                }
                R.id.radio_btn2 -> {
                    LogUtils.error("radio_btn2")
                    pageIndex = 1
                    tradeType = 2
                    getTeamData()
                }
            }
        }
        refreshLayout.setOnRefreshListener {
            pageIndex = 1
            getTeamData()
            it.finishRefresh(2000)
        }
    }

    override fun initData() {
        getTeamData()

    }

    override fun startObserve() {
        mViewModel.teamtradeLiveData.observe(this,{

            if (pageIndex == 1) {
                teamtradAdapter.setList(it)
            } else {
                teamtradAdapter.addData(it)
            }
            if (it.size < pageSize) {
                teamtradAdapter.loadMoreModule.loadMoreEnd()
            } else {
                teamtradAdapter.loadMoreModule.loadMoreComplete()
            }

        })
    }
}