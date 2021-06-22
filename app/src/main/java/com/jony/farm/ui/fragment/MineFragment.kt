package com.jony.farm.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.combodia.basemodule.base.BaseVMFragment
import com.combodia.basemodule.ext.visable
import com.combodia.basemodule.utils.GlideUtils
import com.combodia.httplib.config.Constant.KEY_LOGIN_STATE
import com.combodia.httplib.config.Constant.KEY_USER_ID
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.ui.activity.LanguageActivity
import com.jony.farm.util.DeviceUtil
import com.jony.farm.util.MathUtil
import com.jony.farm.util.RouteUtil
import com.jony.farm.view.pop.LanguagePop
import com.jony.farm.viewmodel.MineViewModel
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.layout_fundcenter.*
import kotlinx.android.synthetic.main.layout_security.*
import kotlinx.android.synthetic.main.layout_team.*
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/4/15 14:45
 *描述:This is MineFragment
 */
class MineFragment:BaseVMFragment<MineViewModel>() {

    private lateinit var languagePop:LanguagePop

    private val kv = MMKV.defaultMMKV()

    override fun initVM(): MineViewModel = getViewModel()


    override fun getLayoutResId(): Int = R.layout.fragment_mine

    override fun initView() {

        ll_parent.setPadding(0,statusBarHeight,0,0)

        if (kv.decodeBool(KEY_LOGIN_STATE,false)){
            iv_signout.visable(true)
        }else{
            iv_signout.visable(false)
        }


        refreshLayout.setOnRefreshListener {
            mViewModel.getData()
            it.finishRefresh(2000)
        }

        onClick()
    }

    private fun onClick(){
        iv_avatar.setOnClickListener {
            RouteUtil.start2UserInfo(requireContext())
        }
        iv_signout.setOnClickListener {
            mViewModel.signOut()
        }
        iv_qcdetail.setOnClickListener {
            RouteUtil.start2Fund(requireContext(),1)
        }
        iv_lcdetail.setOnClickListener {
            RouteUtil.start2Fund(requireContext(),2)
        }
        iv_myanimals.setOnClickListener {
            RouteUtil.start2AnimalHistory(requireContext())
        }
        iv_fodderdetail.setOnClickListener {
            RouteUtil.start2FodderDetail(requireContext(),3)
        }
        iv_password.setOnClickListener {
            RouteUtil.start2ChangePwd(requireContext())
        }
        iv_bankcard.setOnClickListener {
            RouteUtil.start2BankCardList(requireContext())
        }
        iv_personal.setOnClickListener {
            RouteUtil.start2UserInfo(requireContext())
        }
        iv_invitefriends.setOnClickListener {
            RouteUtil.start2Invite(requireContext())
        }
        iv_myteam.setOnClickListener {
            RouteUtil.start2MyTeam(requireContext())
        }
        iv_teamfunds.setOnClickListener {
            RouteUtil.start2TeamFund(requireContext())
        }
        iv_agencyincome.setOnClickListener {
            RouteUtil.start2AgencyIncome(requireContext())
        }
        /*iv_setting.setOnClickListener {
            val intent = Intent(requireContext(), LanguageActivity::class.java)
            requireContext().startActivity(intent)
        }*/
        tv_setting.setOnClickListener {
            if (!this::languagePop.isInitialized){
                languagePop = LanguagePop(context as Activity)
            }
            val setWidth = tv_setting.width
            val xOff = setWidth - DeviceUtil.dip2px(requireContext(),100f)
            languagePop.showAsDropDown(tv_setting,xOff,0)
            DeviceUtil.setBackgroundAlpha(context as Activity,0.5f)
        }
    }

    /**
     * 更新数据 主要是各个金额
     */
    fun updateData(){
     //   mViewModel.getData()
    }

    override fun initData() {
        mViewModel.getData()
    }


    @SuppressLint("SetTextI18n")
    override fun startObserve() {
        mViewModel.run {
            /*userInfoLiveData.observe(viewLifecycleOwner,{list ->
                list.filter { it.userID == MMKV.defaultMMKV().decodeInt(KEY_USER_ID) }
                    .map {

                    }
            })*/
            memberLiveData.observe(viewLifecycleOwner,{list ->
                list.filter { it.userID == MMKV.defaultMMKV().decodeInt(KEY_USER_ID) }
                    .map {
                        tv_userName.text = it.userName
                        tv_userId.text = "ID:${it.userID}"
                        tv_balance.text = MathUtil.getTwoBigDecimal(it.balance)
                        tv_lCoin.text = MathUtil.getTwoBigDecimal(it.lCoin)
                        tv_fodder.text = MathUtil.getTwoBigDecimal(it.fodder)
                        GlideUtils.loadAvatar(iv_avatar,it.headImg,R.mipmap.ic_avatar_default)
                        if (kv.decodeBool(KEY_LOGIN_STATE,false)){
                            iv_signout.visable(true)
                        }else{
                            iv_signout.visable(false)
                        }
                    }
            })
            signOutLiveData.observe(viewLifecycleOwner,{signOutState->
                if (signOutState){
                    iv_signout.visable(false)
                    tv_userName.text = context?.getString(R.string.mine_unlogin)
                    tv_userId.text = ""
                    tv_balance.text = "0.00"
                    tv_lCoin.text = "0.00"
                    tv_fodder.text = "0.00"
                    iv_avatar.setImageResource(R.mipmap.ic_avatar_default)
                }
            })


        }
    }
}