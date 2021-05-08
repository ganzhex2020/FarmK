package com.jony.farm.ui.activity

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.ext.toast
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.model.entity.AmountEntity
import com.jony.farm.ui.adapter.AmountAdapter
import com.jony.farm.ui.adapter.PayAdapter
import com.jony.farm.ui.adapter.PayTypeAdapter
import com.jony.farm.viewmodel.RechargeViewModel
import com.xiaojinzi.component.anno.RouterAnno
import com.youth.banner.util.LogUtils
import kotlinx.android.synthetic.main.activity_recharge.*
import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel
import java.util.*
import kotlin.collections.HashMap

/**
 *Author:ganzhe
 *时间:2021/4/25 13:09
 *描述:This is RechargeActivity
 */

@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_RECHARGE, interceptorNames = [Const.LOGININTERCEPTOR])
class RechargeActivity:BaseVMActivity<RechargeViewModel>() {

    private val paytypeAdapter by lazy { PayTypeAdapter() }
    private val payAdapter by lazy { PayAdapter() }
    private val amountAdapter by lazy { AmountAdapter() }

    private val amountList = listOf(100, 300, 500, 1000, 2000, 3000, 5000, 10000)


    override fun initVM(): RechargeViewModel = getViewModel()



    override fun getLayoutResId(): Int = R.layout.activity_recharge

    override fun initView() {

        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)

        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_recharge)

        initRecy()
    }

    private fun initRecy(){
        recy_type1.run {
            adapter = paytypeAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        }

        recy_type2.run {
            adapter = payAdapter
            layoutManager = GridLayoutManager(context, 3)
        }
        payAdapter.setOnItemClickListener { _, _, position ->
            payAdapter.data.forEach { item->
                item.isSelect = false
            }
            payAdapter.data[position].isSelect = true
            payAdapter.notifyDataSetChanged()
        }
        recy_amount.run {
            adapter = amountAdapter
            layoutManager = GridLayoutManager(context, 4)
        }
        amountAdapter.setOnItemClickListener { _, _, position ->
            amountAdapter.data.forEach { item ->
                item.isSelect = false
            }
            amountAdapter.data[position].isSelect = true
            amountAdapter.notifyDataSetChanged()
            et_amount.setText(amountAdapter.data[position].amount.toString())
        }

        iv_submit.setOnClickListener {
            pay()
        }

    }

    private fun  pay(){
        //获取选择的支付大类
        var payType = 0
        paytypeAdapter.data.forEach {
            if (it.isSelect){
                payType = it.payType
            }
        }

        //是否选择支付小类
        var isSelectPayType = false
        var selectIndex = -1
        for ( i in 0 until payAdapter.data.size){
            if (payAdapter.data[i].isSelect){
                isSelectPayType = true
                selectIndex = i
                break
            }
        }
        if (!isSelectPayType){
            toast("请选择支付类型")
            return
        }
        val amountStr = et_amount.text.toString().trim()
        if (amountStr.isBlank()){
            toast("请选择支付金额")
            return
        }
        //处理充值数据
        val map = HashMap<String,Any>()
        map["ID"] = payAdapter.data[selectIndex].id
        map["amount"] = amountStr
        map["platformID"] = payAdapter.data[selectIndex].platformNameID
        map["fuyan"] = ""
        map["payType"] = payType

        mViewModel.pay(map)
    }

    override fun initData() {
        mViewModel.getPayTypeList()

    }


    override fun startObserve() {
        mViewModel.run {
            payTypeLiveData.observe(this@RechargeActivity, { list ->
                if (list.isNotEmpty()) {
                    list[0].isSelect = true
                    paytypeAdapter.setList(list)

                    payAdapter.setList(list[0].payList)

                    //amountadapter 设置
                    val amounts = mutableListOf<AmountEntity>()
                    amountList.map { item ->
                        val entity = AmountEntity(item, false)
                        amounts.add(entity)
                    }
                   // amounts[0].isSelect = true
                    amountAdapter.setList(amounts)
                }

            })
            payOnLineLiveData.observe(this@RechargeActivity,{
                LogUtils.e(it.jumpUrl)
                clear()
            })
        }
    }

    private fun clear(){
        et_amount.setText("")
        amountAdapter.data.forEach {
            it.isSelect = false
        }
        paytypeAdapter.data.map {
            it.isSelect = false
        }
        amountAdapter.notifyDataSetChanged()
        paytypeAdapter.notifyDataSetChanged()
    }


}