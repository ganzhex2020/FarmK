package com.jony.farm.ui.activity

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.ext.toast
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.ui.adapter.BankCardListAdapter
import com.jony.farm.util.DeviceUtil
import com.jony.farm.util.RouteUtil
import com.jony.farm.view.VerticalDecoration
import com.jony.farm.viewmodel.BankCardListViewModel
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_bankcardlist.*
import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/4/26 19:47
 *描述:This is BankCardListActivity
 */
@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_BANKCARDLIST, interceptorNames = [Const.LOGININTERCEPTOR])
class BankCardListActivity :BaseVMActivity<BankCardListViewModel>(){

    private val bankCardListAdapter by lazy { BankCardListAdapter() }

    override fun initVM(): BankCardListViewModel = getViewModel()



    override fun getLayoutResId(): Int = R.layout.activity_bankcardlist

    override fun initView() {

        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_bankcard)

        tv_add_card.setOnClickListener { RouteUtil.start2BindCard(this) }

        initRecy()
    }

    private fun initRecy(){
        recy_bankcard.run {
            adapter = bankCardListAdapter
            layoutManager = LinearLayoutManager(this@BankCardListActivity)
        }
        val mDivider = ContextCompat.getDrawable(this, R.color.transparent)
        recy_bankcard.addItemDecoration(VerticalDecoration(this, mDivider, DeviceUtil.dip2px(this, 10f)))
        bankCardListAdapter.addChildClickViewIds(R.id.tv_set_default)
        bankCardListAdapter.setOnItemChildClickListener { _, view, position ->
            if (view.id == R.id.tv_set_default){
                val map = HashMap<String,Any>()
                map["isDefault"] = 1
                map["bankID"] = bankCardListAdapter.data[position].bankID
                mViewModel.setDefaultCard(position,map)
            }
        }
    }

    override fun initData() {
        mViewModel.getBankCardList()
    }

    override fun startObserve() {
        mViewModel.run {
            bankcardsLiveData.observe(this@BankCardListActivity, {
                bankCardListAdapter.setList(it)
            })

            setdefaultLiveData.observe(this@BankCardListActivity,{map ->
                if (map["state"] as Boolean){
                    toast("set success")
                    bankCardListAdapter.data.forEach { it.isDefault = false }
                    val position = map["position"] as Int
                    bankCardListAdapter.data[position].isDefault = true
                    bankCardListAdapter.notifyDataSetChanged()
                }

            })
        }
    }
}