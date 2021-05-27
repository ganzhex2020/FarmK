package com.jony.farm.ui.activity

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.combodia.basemodule.base.BaseVMActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.ui.adapter.BlockNewsAdapter
import com.jony.farm.util.DeviceUtil
import com.jony.farm.util.RouteUtil
import com.jony.farm.view.VerticalDecoration
import com.jony.farm.viewmodel.BlockNewsViewModel
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_blocknews.*
import kotlinx.android.synthetic.main.activity_blocknews.ll_parent
import kotlinx.android.synthetic.main.activity_blocknews.refreshLayout
import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/5/17 19:19
 *描述:This is BlockNewsActivity
 */

@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_BLOCKNEWS)
class BlockNewsActivity:BaseVMActivity<BlockNewsViewModel>() {

    private val blockAdapter by lazy { BlockNewsAdapter() }
    var pageIndex = 1
    private val pageSize = 15


    override fun initVM(): BlockNewsViewModel = getViewModel()

    override fun getLayoutResId(): Int = R.layout.activity_blocknews

    override fun initView() {

        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_blocknews)

        initRecy()

    }

    private fun initRecy(){
        recy_blocknews.run {
            adapter = blockAdapter
            layoutManager = LinearLayoutManager(this@BlockNewsActivity)
        }
        val mDivider = ContextCompat.getDrawable(this, R.color.transparent)
        recy_blocknews.addItemDecoration(VerticalDecoration(this, mDivider, DeviceUtil.dip2px(this, 10f)))
        blockAdapter.loadMoreModule.run {
            preLoadNumber = 3
            setOnLoadMoreListener {
                pageIndex++
                mViewModel.getNewsList(pageIndex)
            }
        }
        blockAdapter.addChildClickViewIds(R.id.tv_detail)
        blockAdapter.setOnItemChildClickListener { _, view, position ->
            if (view.id == R.id.tv_detail){
                RouteUtil.start2BlockNewsDetail(this,blockAdapter.getItem(position).id)
            }
        }
        refreshLayout.setOnRefreshListener {
            pageIndex = 1
            mViewModel.getNewsList(pageIndex)
            it.finishRefresh(2000)
        }
    }

    override fun initData() {
      //  val list = listOf(1,2,3,4,5,6,7,8,9,10)
      //  blockAdapter.setList(list)
        mViewModel.getNewsList(pageIndex)
    }
    override fun startObserve() {
        mViewModel.newsListLiveData.observe(this,{ list ->
            if (pageIndex == 1){
                blockAdapter.setList(list)
            }else{
                blockAdapter.addData(list)
            }
            if (list.size<pageSize){
                blockAdapter.loadMoreModule.loadMoreEnd()
            }else{
                blockAdapter.loadMoreModule.loadMoreComplete()
            }

        })
    }
}