package com.jony.farm.ui.fragment

import com.combodia.basemodule.base.BaseVMFragment
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.model.entity.BannerEntity
import com.jony.farm.model.entity.CompanyEntity
import com.jony.farm.ui.adapter.HomeBannerAdapter
import com.jony.farm.util.DeviceUtil
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


//        tv_marquee.setText("11111111111111122222222222222222223333333333333333 444444444444 5555555")
//        tv_marquee.addMarqueeLifecycleObserver(viewLifecycleOwner)
//        tv_marquee.startScroll()
        val params = rl_luckdraw.layoutParams
        params.width = (DeviceUtil.getScreenW(requireContext()) - DeviceUtil.dip2px(requireContext(),54f))/2
        params.height = (DeviceUtil.getScreenW(requireContext()) - DeviceUtil.dip2px(requireContext(),54f))/2
        rl_luckdraw.layoutParams = params

        val params1 = rl_leaderbord.layoutParams
        params1.width = (DeviceUtil.getScreenW(requireContext()) - DeviceUtil.dip2px(requireContext(),54f))/2
        params1.height = ((DeviceUtil.getScreenW(requireContext()) - DeviceUtil.dip2px(requireContext(),54f))/2 - DeviceUtil.dip2px(requireContext(),10f))/2
        rl_leaderbord.layoutParams = params1

        val params2 = rl_faq.layoutParams
        params2.width = (DeviceUtil.getScreenW(requireContext()) - DeviceUtil.dip2px(requireContext(),54f))/2
        params2.height = ((DeviceUtil.getScreenW(requireContext()) - DeviceUtil.dip2px(requireContext(),54f))/2 - DeviceUtil.dip2px(requireContext(),10f))/2
        rl_faq.layoutParams = params2

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
                RouteUtil.go2WebView(requireContext(),companyEntity!!.serviceUrl,context?.getString(R.string.kf))
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
        iv_isagent.setOnClickListener {
            mViewModel.isAgent()
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
            announceLiveData.observe(viewLifecycleOwner,{ list ->
                val stringBuffer = StringBuffer()
                list.map {
                    if (it.announceType == 1){
                        stringBuffer.append(it.announceContent+" ")
                    }
                }
                tv_auto.text = stringBuffer.toString()
                tv_auto.speed = 2f //滚动速度
                tv_auto.init(350f) // width通常就是屏幕宽！
                tv_auto.startScroll()
            })
            isAgentLiveData.observe(viewLifecycleOwner,{
                if (it){
                    RouteUtil.start2TeamPromote(requireContext())
                }else{
                    RouteUtil.start2AgentArch(requireContext())
                }
            })

        }
    }

}