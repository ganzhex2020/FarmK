package com.jony.farm.ui.activity

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.utils.GlideUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.ui.adapter.RankAdapter
import com.jony.farm.util.DeviceUtil
import com.jony.farm.view.VerticalDecoration
import com.jony.farm.viewmodel.RankViewModel
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_rank.*
import kotlinx.android.synthetic.main.activity_rank.ll_parent
import kotlinx.android.synthetic.main.activity_rank.refreshLayout

import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/5/17 20:45
 *描述:This is RankActivity
 */
@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_RANK)
class RankActivity:BaseVMActivity<RankViewModel>() {

    private var pageIndex = 1
    private val pageSize = 20

    private val rankAdapter by lazy { RankAdapter() }

    override fun initVM(): RankViewModel = getViewModel()



    override fun getLayoutResId(): Int = R.layout.activity_rank

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_leaderboard)

        initRecy()
    }

    private fun initRecy(){
        recy_rank.run {
            adapter = rankAdapter
            layoutManager = LinearLayoutManager(this@RankActivity)
        }
        val mDivider = ContextCompat.getDrawable(this, R.color.transparent)
        recy_rank.addItemDecoration(VerticalDecoration(this, mDivider, DeviceUtil.dip2px(this, 10f)))
        rankAdapter.loadMoreModule.run {
            preLoadNumber = 3
            setOnLoadMoreListener {
                pageIndex++
                mViewModel.getRankData(pageIndex)
            }
        }
        refreshLayout.setOnRefreshListener {
            pageIndex = 1
            mViewModel.getRankData(pageIndex)
            it.finishRefresh(2000)
        }
    }

    override fun initData() {
      //  val list = listOf<Int>(1,2,3,4,5,6,7)
      //  rankAdapter.setList(list)
        mViewModel.getRankData(pageIndex)

    }
    override fun startObserve() {
        mViewModel.rankLiveData.observe(this,{ list ->
            if (pageIndex == 1){
                /**
                 * 设置前3名
                 */
                if (list.isNotEmpty()){
                    tv_rank1_name.text = list[0].showName
                    tv_rank1_amount.text = "$"+list[0].tradeAmount.toString()
                    GlideUtils.loadAvatar(iv_rank1,list[0].headImg,R.mipmap.ic_avatar_default)
                }
                if (list.size>=2){
                    tv_rank2_name.text = list[1].showName
                    tv_rank2_amount.text = "$"+list[1].tradeAmount.toString()
                    GlideUtils.loadAvatar(iv_rank2,list[1].headImg,R.mipmap.ic_avatar_default)
                }
                if (list.size>=3){
                    tv_rank3_name.text = list[2].showName
                    tv_rank3_amount.text = "$"+list[2].tradeAmount.toString()
                    GlideUtils.loadAvatar(iv_rank3,list[2].headImg,R.mipmap.ic_avatar_default)
                }
                //设置列表数据
                if (list.size>3){
                    rankAdapter.setList(list.subList(3,list.size))
                }

            }else{
                rankAdapter.addData(list)
            }
            if (list.size<pageSize){
                rankAdapter.loadMoreModule.loadMoreEnd()
            }else{
                rankAdapter.loadMoreModule.loadMoreComplete()
            }

            /*if (pageIndex == 1){
                rankAdapter.setList(it)
            }else{
                rankAdapter.addData(it)
            }
            if (it.size<pageSize){
                rankAdapter.loadMoreModule.loadMoreEnd()
            }else{
                rankAdapter.loadMoreModule.loadMoreComplete()
            }*/
        })
    }
}