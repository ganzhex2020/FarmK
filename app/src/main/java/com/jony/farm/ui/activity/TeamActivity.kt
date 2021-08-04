package com.jony.farm.ui.activity

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.utils.LogUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.ui.adapter.TeamAdapter
import com.jony.farm.util.DeviceUtil
import com.jony.farm.util.RouteUtil
import com.jony.farm.view.VerticalDecoration
import com.jony.farm.viewmodel.TeamViewModel
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_funddetail.*
import kotlinx.android.synthetic.main.activity_team.*
import kotlinx.android.synthetic.main.activity_team.ll_parent
import kotlinx.android.synthetic.main.activity_team.recy
import kotlinx.android.synthetic.main.activity_team.refreshLayout
import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/5/4 16:01
 *描述:This is TeamActivity
 */
@RouterAnno(
        host = Const.MODULE_HOST_APP,
        path = Const.MODULE_PATH_APP_TEAMMEMBER,
        interceptorNames = [Const.LOGININTERCEPTOR]
)
class TeamActivity : BaseVMActivity<TeamViewModel>() {
    private val pageSize = 30
    var pageIndex = 1
    var layer = 1

    private val teamAdapter by lazy { TeamAdapter() }

    override fun initVM(): TeamViewModel = getViewModel()

    override fun getLayoutResId(): Int = R.layout.activity_team

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_team)

        initRecy()
        onClick()
    }

    private fun initRecy() {
        recy.run {
            adapter = teamAdapter
            layoutManager = LinearLayoutManager(this@TeamActivity)
        }
        val mDivider = ContextCompat.getDrawable(this, R.drawable.horizonal_xian)
        recy.addItemDecoration(VerticalDecoration(this, mDivider, DeviceUtil.dip2px(this, 0.5f)))
        teamAdapter.loadMoreModule.run {
            preLoadNumber = 3
            setOnLoadMoreListener {
                pageIndex++
                mViewModel.getTeamMemberList(pageSize, pageIndex, layer)
            }
        }
        teamAdapter.addChildClickViewIds(R.id.iv_go)
        teamAdapter.setOnItemChildClickListener { _, view, position ->
            val itemData = teamAdapter.getItem(position)
            if (view.id == R.id.iv_go){
                RouteUtil.start2SubFarm(this,itemData.userId,itemData.balance,itemData.lc)
            }
        }

        refreshLayout.setOnRefreshListener {
            pageIndex = 1
            mViewModel.getTeamMemberList(pageSize, pageIndex, layer)
            it.finishRefresh(2000)
        }
    }

    private fun onClick() {
        /*radio_group.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_btn1 -> {
                    pageIndex = 1
                    layer = 1
                    mViewModel.getTeamMemberList(pageSize, pageIndex,layer)
                }
                R.id.radio_btn2 -> {
                    pageIndex = 1
                    layer = 2
                    mViewModel.getTeamMemberList(pageSize, pageIndex, layer)
                }
                R.id.radio_btn3 -> {
                    pageIndex = 1
                    layer = 3
                    mViewModel.getTeamMemberList(pageSize, pageIndex, layer)
                }
            }
        }*/
        tv_level1.setOnClickListener {

            val param1 = tv_level1.layoutParams
            param1.width = DeviceUtil.dip2px(this,120f)
            param1.height = DeviceUtil.dip2px(this,80f)
            tv_level1.layoutParams = param1

            val param2 = tv_level2.layoutParams
            param2.width = DeviceUtil.dip2px(this,90f)
            param2.height = DeviceUtil.dip2px(this,60f)
            tv_level2.layoutParams = param2

            val param3 = tv_level3.layoutParams
            param3.width = DeviceUtil.dip2px(this,90f)
            param3.height = DeviceUtil.dip2px(this,60f)
            tv_level3.layoutParams = param3

            pageIndex = 1
            layer = 1
            mViewModel.getTeamMemberList(pageSize, pageIndex,layer)
        }
        tv_level2.setOnClickListener {

            val param2 = tv_level2.layoutParams
            param2.width = DeviceUtil.dip2px(this,120f)
            param2.height = DeviceUtil.dip2px(this,80f)
            tv_level2.layoutParams = param2

            val param1 = tv_level1.layoutParams
            param1.width = DeviceUtil.dip2px(this,90f)
            param1.height = DeviceUtil.dip2px(this,60f)
            tv_level1.layoutParams = param1

            val param3 = tv_level3.layoutParams
            param3.width = DeviceUtil.dip2px(this,90f)
            param3.height = DeviceUtil.dip2px(this,60f)
            tv_level3.layoutParams = param3

            pageIndex = 1
            layer = 2
            mViewModel.getTeamMemberList(pageSize, pageIndex,layer)
        }

        tv_level3.setOnClickListener {

            val param3 = tv_level3.layoutParams
            param3.width = DeviceUtil.dip2px(this,120f)
            param3.height = DeviceUtil.dip2px(this,80f)
            tv_level3.layoutParams = param3

            val param1 = tv_level1.layoutParams
            param1.width = DeviceUtil.dip2px(this,90f)
            param1.height = DeviceUtil.dip2px(this,60f)
            tv_level1.layoutParams = param1

            val param2 = tv_level2.layoutParams
            param2.width = DeviceUtil.dip2px(this,90f)
            param2.height = DeviceUtil.dip2px(this,60f)
            tv_level2.layoutParams = param2

            pageIndex = 1
            layer = 3
            mViewModel.getTeamMemberList(pageSize, pageIndex,layer)

        }
    }

    override fun initData() {
        mViewModel.getTeamMemberList(pageSize, pageIndex, layer)
    }

    override fun startObserve() {
        mViewModel.run {
            teamLiveData.observe(this@TeamActivity, {
                if (pageIndex == 1) {
                    teamAdapter.setList(it)
                    if (it.isEmpty()){
                        teamAdapter.setEmptyView(R.layout.layout_empty_myteam)
                    }
                } else {
                    teamAdapter.addData(it)
                }
                if (it.size < pageSize) {
                    teamAdapter.loadMoreModule.loadMoreEnd()
                } else {
                    teamAdapter.loadMoreModule.loadMoreComplete()
                }
            })
        }
    }
}