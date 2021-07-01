package com.jony.farm.ui.activity

import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.config.Const.LOGININTERCEPTOR
import com.jony.farm.config.Const.TRUENAMEINTERCEPTOR
import com.jony.farm.model.entity.BankCardEntity
import com.jony.farm.util.RouteUtil
import com.jony.farm.viewmodel.WithDrawViewModel
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_withdraw.*
import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/4/26 17:18
 *描述:This is WithDrawActivity
 */

@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_WITHDRAW, interceptorNames = [LOGININTERCEPTOR, TRUENAMEINTERCEPTOR])
class WithDrawActivity : BaseVMActivity<WithDrawViewModel>() {

    var defaultCard: BankCardEntity? = null

    override fun initVM(): WithDrawViewModel = getViewModel()


    override fun getLayoutResId(): Int = R.layout.activity_withdraw

    override fun initView() {

        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_withdraw)



    }

    override fun initData() {
        iv_submit.setOnClickListener {
            drawMoney()
        }
        iv_bankcard_right.setOnClickListener {
            RouteUtil.start2BankCardList(this)
        }
        tv_withdraw_history.setOnClickListener {
            RouteUtil.start2LcDetail(this)
        }
    }

    private fun drawMoney(){
        val amount = et_amount.text.toString().trim()
        val passwd = et_fundpwd.text.toString().trim()

        if (defaultCard == null){
            toast("未设置默认卡")
            return
        }
        if (amount.isBlank()){
            toast("请输入提现金额")
            return
        }
        if (passwd.isBlank()){
            toast("请输入资金密码")
            return
        }
        val map = HashMap<String,Any>()
        map["tradeAmount"] = amount
        map["withdrawalPassword"] = passwd
        map["bankID"] = 0

        mViewModel.drawMoney(map)
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getBankCardList()
    }

    override fun startObserve() {
        mViewModel.run {
            bankcardsLiveData.observe(this@WithDrawActivity, { bankcards ->
                LogUtils.error(bankcards)
                bankcards.forEach {
                    if (it.isDefault) {
                        defaultCard = it
                    }
                }

                defaultCard?.let {
                    tv_bank_name.text = it.bankName
                    tv_bankcard_account.text = "**** **** **** "+it.bankNumber.takeLast(4)
                }

            })

        }
    }
}