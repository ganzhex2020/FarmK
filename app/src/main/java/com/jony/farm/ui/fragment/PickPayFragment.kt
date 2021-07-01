package com.jony.farm.ui.fragment

import android.text.TextUtils
import com.combodia.basemodule.base.BaseVMFragment
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.config.Constant
import com.jony.farm.R
import com.jony.farm.viewmodel.BindCardViewModel
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.fragment_pickpay.*
import org.koin.android.viewmodel.ext.android.getSharedViewModel

/**
 *Author:ganzhe
 *时间:2021/4/27 16:06
 *描述:This is PickPayFragment
 */
class PickPayFragment:BaseVMFragment<BindCardViewModel>() {

    private var title:String = ""
    private val bankId = 51

    override fun initVM(): BindCardViewModel = getSharedViewModel()

    override fun getLayoutResId(): Int = R.layout.fragment_pickpay

    override fun initView() {
        title = arguments?.getString("title").toString()


        iv_bindcard.setOnClickListener {

            bindCard()
        }

    }

    private fun bindCard(){
        val province: String = title
        val city = ""
        val bankAddress = ""
        val bankNumber = et_code.text.toString().trim()


        if (TextUtils.isEmpty(bankNumber)) {
            toast("Enter Link Code")
            return
        }
        val map = HashMap<String, Any>()
        map["bankID"] = bankId
        map["bankNumber"] = bankNumber
        map["province"] = province
        map["city"] = city
        map["bankAddress"] = bankAddress
        map["isDefault"] = 1

        mViewModel.bindCard(map)
    }

    override fun initData() {

    }



    override fun startObserve() {
        mViewModel.memberLiveData.observe(viewLifecycleOwner,{ list ->
            list.filter { it.userID == MMKV.defaultMMKV().decodeInt(Constant.KEY_USER_ID) }
                .map {
                    LogUtils.error("MainLand:owner")
                }
        })
    }
}