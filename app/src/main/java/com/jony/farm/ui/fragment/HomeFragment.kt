package com.jony.farm.ui.fragment

import com.combodia.basemodule.base.BaseVMFragment
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.model.entity.BannerEntity
import com.jony.farm.model.entity.CompanyEntity
import com.jony.farm.ui.adapter.HomeBannerAdapter
import com.jony.farm.util.RouteUtil
import com.jony.farm.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/5/17 15:23
 *描述:This is HomeFragment
 */
class HomeFragment :BaseVMFragment<HomeViewModel>(){

    private val bannerList by lazy { mutableListOf<BannerEntity>() }
    private val bannerAdapter by lazy { HomeBannerAdapter(bannerList) }

    private var companyEntity:CompanyEntity? = null

    override fun initVM(): HomeViewModel = getViewModel()

    override fun getLayoutResId(): Int = R.layout.fragment_home

    override fun initView() {
        ll_parent.setPadding(0, statusBarHeight, 0, 0)

          home_banner.run {
            addBannerLifecycleObserver(viewLifecycleOwner)
        //    indicator = CircleIndicator(context)
            setBannerRound2(20f)
            adapter = bannerAdapter
        }
         bannerAdapter.setOnBannerListener { _, position ->
            // LogUtils.error("position:$position data:${bannerList[position].url} ")

         }
        refreshLayout.setOnRefreshListener {
            mViewModel.getData()
            it.finishRefresh(2000)
        }
        onClick()
    }

    private fun onClick(){

        //充值
        iv_recharge.setOnClickListener {
            RouteUtil.start2Recharge(requireContext())
        }
        iv_withdraw.setOnClickListener {
            RouteUtil.start2Withdraw(requireContext())
        }
        iv_invite.setOnClickListener {
            RouteUtil.start2Invite(requireContext())
        }
        iv_service.setOnClickListener {
            if (companyEntity!=null&&companyEntity!!.serviceUrl.isNotEmpty()){
                RouteUtil.go2WebView(requireContext(),companyEntity!!.serviceUrl,"Service")
            }
        }

        rl_blocknews.setOnClickListener {
            RouteUtil.start2BlockNews(requireContext())
        }
        rl_leaderbord.setOnClickListener {
            RouteUtil.start2Rank(requireContext())
        }
        rl_faq.setOnClickListener {
            RouteUtil.start2Faq(requireContext())
        }
        rl_luckdraw.setOnClickListener {
            RouteUtil.start2Luckdraw(requireContext())
        }
        rl_communitychat.setOnClickListener {
            RouteUtil.start2CommunityChat(requireContext())
        }


    }

    override fun initData() {
        mViewModel.getData()
    }

    override fun startObserve() {
        mViewModel.run {
            bannerLiveData.observe(viewLifecycleOwner,{
                bannerLiveData.observe(viewLifecycleOwner, {
                    bannerList.clear()
                    bannerList.addAll(it)
                    bannerAdapter.notifyDataSetChanged()
                })
            })
            companyLiveData.observe(viewLifecycleOwner,{
                companyEntity = it
            })
        }
    }
}