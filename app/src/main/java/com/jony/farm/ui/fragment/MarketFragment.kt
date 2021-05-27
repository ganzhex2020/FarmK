package com.jony.farm.ui.fragment

import android.annotation.SuppressLint
import androidx.recyclerview.widget.GridLayoutManager
import com.combodia.basemodule.base.BaseVMFragment
import com.combodia.basemodule.utils.LogUtils
import com.jony.farm.R
import com.jony.farm.model.entity.BannerEntity
import com.jony.farm.ui.adapter.HomeAdapter
import com.jony.farm.ui.adapter.HomeBannerAdapter
import com.jony.farm.util.DeviceUtil
import com.jony.farm.util.RouteUtil
import com.jony.farm.view.GridSpaceItemDecoration
import com.jony.farm.view.dialog.RushBuyDialog
import com.jony.farm.viewmodel.MarketViewModel
import kotlinx.android.synthetic.main.fragment_market.*
import org.koin.android.viewmodel.ext.android.getViewModel


/**
 *Author:ganzhe
 *时间:2021/3/7 20:28
 *描述:This is HomeFragment
 */
class MarketFragment :BaseVMFragment<MarketViewModel>() {

    private val bannerList by lazy { mutableListOf<BannerEntity>() }
 //   private val bannerAdapter by lazy { HomeBannerAdapter(bannerList) }

    private val homeAdapter by lazy { HomeAdapter() }
    private var rushBuyDialog :RushBuyDialog? = null


    override fun initVM(): MarketViewModel = getViewModel()

    override fun getLayoutResId(): Int = R.layout.fragment_market


    override fun initView() {
      /*  home_banner.run {
            addBannerLifecycleObserver(viewLifecycleOwner)
        //    indicator = CircleIndicator(context)
            setBannerRound2(10f)
            adapter = bannerAdapter
        }*/
       /* bannerAdapter.setOnBannerListener { _, position ->
           // LogUtils.error("position:$position data:${bannerList[position].url} ")

        }*/

        home_recy.run {
            layoutManager = GridLayoutManager(context, 2)
            adapter = homeAdapter
        }
        val gridSpaceItemDecoration = GridSpaceItemDecoration(DeviceUtil.dip2px(requireContext(), 10f), DeviceUtil.dip2px(requireContext(), 0f))
        home_recy.addItemDecoration(gridSpaceItemDecoration)
        homeAdapter.addChildClickViewIds(R.id.tv_rushbuy)

        homeAdapter.setOnItemClickListener { _, _, position ->
            LogUtils.error(homeAdapter.data[position])
       //     RouteUtil.start2Farm(home_recy.context,position)
        //    ShareUtil.shareText()
         }
        homeAdapter.setOnItemChildClickListener { _, view, position ->
            if (view.id == R.id.tv_rushbuy){
                mViewModel.getAnimalLeft(homeAdapter.getItem(position).animalID)
            }
        }

        refreshLayout.setOnRefreshListener {
            mViewModel.getData(isInit = false)
            it.finishRefresh(2000)
        }


    }

    /*fun updateData(){
        mViewModel.getAnimalList()
    }*/

    override fun initData() {
        mViewModel.getData(isInit = true)
    }





    @SuppressLint("SetTextI18n")
    override fun startObserve() {
        mViewModel.apply {
            /*bannerLiveData.observe(viewLifecycleOwner, {
                 bannerList.clear()
                 bannerList.addAll(it)
                 bannerAdapter.notifyDataSetChanged()
            })*/
            animalLiveData.observe(viewLifecycleOwner, {
                homeAdapter.setList(it)
            })
            animalLeftLiveData.observe(viewLifecycleOwner,{
                it?.let {
                    if(rushBuyDialog ==null){
                        rushBuyDialog = RushBuyDialog(mViewModel,viewLifecycleOwner,requireContext())
                    }
                    rushBuyDialog?.show()
                }
            })
            hashRateLiveData.observe(viewLifecycleOwner,{
                tv_allHash.text = it.allHash.toString()
                tv_blockHight.text = it.blockHigh.toString()
                tv_hashProfit.text = it.hashProfit.toString()+" FIL/TIB"
                tv_allAccount.text = it.allAccount.toString()
                tv_absenteeism.text = it.kuanggong.toString()
                tv_timeVal.text = it.timeVal.toString()+" s"
                tv_foundVal.text = it.foundVal.toString()+" FIL"
            })
        }
    }

}