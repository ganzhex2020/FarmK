package com.jony.farm.ui.activity

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.combodia.basemodule.base.BaseVMActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.ui.adapter.ZnMsgAdapter
import com.jony.farm.util.DeviceUtil
import com.jony.farm.util.RouteUtil
import com.jony.farm.util.bus.sendEvent
import com.jony.farm.view.VerticalDecoration
import com.jony.farm.viewmodel.SmsViewModel
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_sms.*
import kotlinx.android.synthetic.main.activity_sms.ll_parent
import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel


@RouterAnno(
    host = Const.MODULE_HOST_APP,
    path = Const.MODULE_PATH_APP_SMS,
    interceptorNames = [Const.LOGININTERCEPTOR]
)
class SmsActivity : BaseVMActivity<SmsViewModel>() {

    private val mAdapter by lazy { ZnMsgAdapter() }
    var pageIndex = 1
    private val pageSize = 10

    override fun initVM(): SmsViewModel = getViewModel()

    override fun getLayoutResId(): Int = R.layout.activity_sms

    override fun initView() {

        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_sms)
        initRecy()
    }

    private fun initRecy() {
        recy.run {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@SmsActivity)
        }
        val mDivider = ContextCompat.getDrawable(this, R.color.transparent)
        recy.addItemDecoration(VerticalDecoration(this, mDivider, DeviceUtil.dip2px(this, 10f)))
        mAdapter.loadMoreModule.run {
            preLoadNumber = 3
            setOnLoadMoreListener {
                pageIndex++
                mViewModel.getZnMsg(pageIndex, pageSize)
            }
        }
        mAdapter.addChildClickViewIds(R.id.tv_detail)
        mAdapter.setOnItemChildClickListener { _, view, position ->
            if (view.id == R.id.tv_detail) {
                for (i in mAdapter.data.indices) {
                    if (position == i) {
                        mAdapter.data[position].readed = 1
                    }
                }
                mAdapter.notifyItemChanged(position)
            }
            var count =0
            for (i in mAdapter.data.indices){
                if (mAdapter.data[i].readed == 0){
                    count++
                }
            }
            sendEvent(count)

            RouteUtil.start2MsgDetail(this,mAdapter.data[position])
        }

        refreshLayout.setOnRefreshListener {
            pageIndex = 1
            mViewModel.getZnMsg(pageIndex, pageSize)
            it.finishRefresh(2000)
        }
    }

    override fun initData() {
        mViewModel.getZnMsg(pageIndex, pageSize)
    }

    override fun startObserve() {
        mViewModel.smsLiveData.observe(this, {
            if (pageIndex == 1) {
                mAdapter.setList(it)
            } else {
                mAdapter.addData(it)
            }

            if (it.size < pageSize) {
                mAdapter.loadMoreModule.loadMoreEnd()
            } else {
                mAdapter.loadMoreModule.loadMoreComplete()
            }
        })
    }
}