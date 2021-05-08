package com.jony.farm.ui.activity


import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.combodia.basemodule.base.BaseVMActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.ui.adapter.AnHistoryAdapter
import com.jony.farm.util.DeviceUtil
import com.jony.farm.view.VerticalDecoration
import com.jony.farm.viewmodel.AnimalHistoryViewModel
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_animalhistory.*
import kotlinx.android.synthetic.main.activity_animalhistory.ll_parent
import kotlinx.android.synthetic.main.activity_animalhistory.refreshLayout
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/4/28 18:13
 *描述:This is AnimalHistoryActivity
 */
@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_ANIMALHISTORY, interceptorNames = [Const.LOGININTERCEPTOR])
class AnimalHistoryActivity:BaseVMActivity<AnimalHistoryViewModel> (){


    var pageIndex = 1
    private val pageSize = 10

    private val anHistoryAdapter by lazy { AnHistoryAdapter() }


    override fun initVM(): AnimalHistoryViewModel = getViewModel()

    override fun getLayoutResId(): Int = R.layout.activity_animalhistory

    override fun initView() {

        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_animalhistory)

        initRecy()
    }

    private fun initRecy(){
        recy_anhistory.run {
            adapter = anHistoryAdapter
            layoutManager = LinearLayoutManager(this@AnimalHistoryActivity)
        }
        val mDivider = ContextCompat.getDrawable(this, R.color.transparent)
        recy_anhistory.addItemDecoration(VerticalDecoration(this, mDivider, DeviceUtil.dip2px(this, 10f)))
        anHistoryAdapter.loadMoreModule.run {
            preLoadNumber = 3
            setOnLoadMoreListener {
                pageIndex++
                mViewModel.getAnimalHistory(pageIndex, pageSize)
            }
        }
        refreshLayout.setOnRefreshListener {
            pageIndex = 1
            mViewModel.getAnimalHistory(pageIndex, pageSize)
            it.finishRefresh(2000)
        }

    }

    override fun initData() {
        mViewModel.getAnimalHistory(pageIndex, pageSize)
    }

    override fun startObserve() {

        mViewModel.run {
            animalHistoryLiveData.observe(this@AnimalHistoryActivity,{
               //anHistoryAdapter.setList(it)
                if (pageIndex == 1){
                    anHistoryAdapter.setList(it)
                }else{
                    anHistoryAdapter.addData(it)
                }

                if (it.size<pageSize){
                    anHistoryAdapter.loadMoreModule.loadMoreEnd()
                }else{
                    anHistoryAdapter.loadMoreModule.loadMoreComplete()
                }
            })
        }
    }
}