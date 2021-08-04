package com.jony.farm.ui.activity

import androidx.core.view.isVisible
import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.model.entity.YueEntity
import com.jony.farm.util.*
import com.jony.farm.viewmodel.LcBankViewModel
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_lcbank.*
import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel

@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_LCBANK, interceptorNames = [Const.LOGININTERCEPTOR])
class LcBankActivity:BaseVMActivity<LcBankViewModel>() {

    var yue:YueEntity?=null

    override fun initVM(): LcBankViewModel = getViewModel()

    override fun getLayoutResId(): Int = R.layout.activity_lcbank

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_lcbank)

        click()
    }

    private fun click(){
        tv_dh_all.setOnClickListener {
            yue?.let {
                et_dh_amount.setText(MathUtil.getTwoBigDecimal(yue!!.item2))
            }
        }
        rl_dh.setOnClickListener {
            if (ll_dh.isVisible){
                ll_dh.gone()
                CommonUtil.rote(2,iv_lc_dh)
            }else{
                ll_dh.visible()
                CommonUtil.rote(1,iv_lc_dh)
            }
        }
        //lc兑换
        tv_lcdh.setOnClickListener {
            val transAmount = et_dh_amount.text.toString().trim()
            val fundPasswd = et_dh_passwd.text.toString().trim()
            if (transAmount.isBlank()){
                toast("请输入兑换金额")
                return@setOnClickListener
            }
            if (fundPasswd.isBlank()){
                toast("请输入资金密码")
                return@setOnClickListener
            }
            if (yue!=null){
                if (transAmount.toInt()>yue!!.item2){
                    toast("兑换金额不能大于总金额")
                    return@setOnClickListener
                }
            }
            val map = HashMap<String,String>()
            map["transAmount"] = transAmount
            map["fundPassword"] = MD5Util.getMD5(fundPasswd)
            mViewModel.lcDh(map)

        }

        tv_zz_all.setOnClickListener {
            yue?.let {
                et_zz_amount.setText(MathUtil.getTwoBigDecimal(yue!!.item2))
            }
        }

        rl_zz.setOnClickListener {
            if (ll_zz.isVisible){
                ll_zz.gone()
                CommonUtil.rote(2,iv_lc_zz)
            }else{
                ll_zz.visible()
                CommonUtil.rote(1,iv_lc_zz)
            }
        }

        tv_lczz.setOnClickListener {
            val toName = et_zz_name.text.toString().trim()
            val transAmount = et_zz_amount.text.toString().trim()
            val fundPassword = et_zz_passwd.text.toString().trim()

            if (toName.isBlank()){
                toast("请输入转账金额")
                return@setOnClickListener
            }
            if (transAmount.isBlank()){
                toast("请输入转账金额")
                return@setOnClickListener
            }
            if (fundPassword.isBlank()){
                toast("请输入资金密码")
                return@setOnClickListener
            }
            if (yue!=null){
                if (transAmount.toInt()>yue!!.item2){
                    toast("转账金额不能大于总金额")
                    return@setOnClickListener
                }
            }

            val map = HashMap<String,String>()
            map["toName"] = toName
            map["transAmount"] = transAmount
            map["fundPassword"] = MD5Util.getMD5(fundPassword)
            mViewModel.lcZz(map)
        }
    }

    override fun initData() {
        mViewModel.getData()
    }

    override fun startObserve() {
        mViewModel.run {
            yueLiveData.observe(this@LcBankActivity,{
                if (it.isNotEmpty()){
                    yue = it.first()
                    et_dh_amount.hint = "${MathUtil.getTwoBigDecimal(it.first().item2)}可用"
                    et_zz_amount.hint = "${MathUtil.getTwoBigDecimal(it.first().item2)}可用"
                    gc_balance.text = MathUtil.getTwoBigDecimal(it.first().item1)
                    lc_balance.text = MathUtil.getTwoBigDecimal(it.first().item2)
                }
            })
            rateLiveData.observe(this@LcBankActivity,{
                LogUtils.error(it)
                tv_dh_rate.text = "1LC=${it.lcRate}GC"
                tv_zz_kgf.text = "矿工费:${it.tranRate}"
            })
            lcDhLiveData.observe(this@LcBankActivity,{
                et_dh_amount.setText("")
                et_dh_passwd.setText("")
            })
            lcZzLiveData.observe(this@LcBankActivity,{
                et_zz_amount.setText("")
                et_zz_passwd.setText("")
            })
        }
    }
}