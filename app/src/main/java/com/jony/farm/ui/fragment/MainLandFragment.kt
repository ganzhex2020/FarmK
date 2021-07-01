package com.jony.farm.ui.fragment

import com.combodia.basemodule.base.BaseVMFragment
import com.combodia.basemodule.ext.toast
import com.combodia.httplib.config.Constant
import com.jony.farm.R
import com.jony.farm.model.entity.BankCardEntity
import com.jony.farm.viewmodel.BindCardViewModel
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.fragment_mainland.*
import kotlinx.android.synthetic.main.fragment_pickpay.iv_bindcard
import org.koin.android.viewmodel.ext.android.getSharedViewModel

/**
 *Author:ganzhe
 *时间:2021/4/27 16:06
 *描述:This is PickPayFragment
 */
class MainLandFragment:BaseVMFragment<BindCardViewModel>() {

    private var title:String = ""
    var bankCard : BankCardEntity? = null


    override fun initVM(): BindCardViewModel = getSharedViewModel()

    override fun getLayoutResId(): Int = R.layout.fragment_mainland

    override fun initView() {
        title = arguments?.getString("title").toString()
        bankCard = arguments?.getParcelable("bankCard")

        //判断是否修改
        if (bankCard !=null){
            et_bankId.setText(""+bankCard!!.bankID)
            et_bankNumber.setText(bankCard!!.bankNumber)
            et_province.setText(bankCard!!.province)
            et_city.setText(bankCard!!.city)
            et_bankAddress.setText(bankCard!!.bankAddress)
        }


        iv_bindcard.setOnClickListener {
            if (bankCard!=null){
                modifyCard()
            }else{
                bindCard()
            }
        }

    }

    private fun bindCard(){
        val bankId = et_bankId.text.toString().trim()
        val bankNumber = et_bankNumber.text.toString().trim()
        val province = et_province.text.toString().trim()
        val city = et_city.text.toString().trim()
        val bankAddress = et_bankAddress.text.toString().trim()
        if (bankId.isBlank()){
            toast("请输入银行")
            return
        }
        if (bankNumber.isBlank()){
            toast("请输入卡号")
            return
        }
        if (province.isBlank()){
            toast("请输入省份")
            return
        }
        if (city.isBlank()){
            toast("请输入城市")
            return
        }
        if (bankAddress.isBlank()){
            toast("请输入开户地址")
            return
        }
        val map = HashMap<String,Any>()
        map["bankID"] = bankId
        map["bankNumber"] = bankNumber
        map["province"] = province
        map["city"] = city
        map["bankAddress"] = bankAddress
        map["isDefault"] = true

        mViewModel.bindCard(map)
    }

    fun modifyCard(){
        val bankId = et_bankId.text.toString().trim()
        val bankNumber = et_bankNumber.text.toString().trim()
        val province = et_province.text.toString().trim()
        val city = et_city.text.toString().trim()
        val bankAddress = et_bankAddress.text.toString().trim()
        if (bankId.isBlank()){
            toast("请输入银行")
            return
        }
        if (bankNumber.isBlank()){
            toast("请输入卡号")
            return
        }
        if (province.isBlank()){
            toast("请输入省份")
            return
        }
        if (city.isBlank()){
            toast("请输入城市")
            return
        }
        if (bankAddress.isBlank()){
            toast("请输入开户地址")
            return
        }
        val map = HashMap<String,Any>()
        map["ID"] = bankCard!!.id
        map["BankID"] = bankId
        map["BankNumber"] =  bankNumber
        map["Province"] = province
        map["City"] = city
        map["BankAddress"] = bankAddress

        mViewModel.modifyCard(map)
    }

    override fun initData() {

    }



    override fun startObserve() {
        mViewModel.memberLiveData.observe(viewLifecycleOwner,{ list ->
            list.filter { it.userID == MMKV.defaultMMKV().decodeInt(Constant.KEY_USER_ID) }
                .map {

                  //  LogUtils.error("MainLand:owner")
                    tv_owner.text = it.trueName
                }
        })
    }
}