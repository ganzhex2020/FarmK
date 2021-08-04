package com.jony.farm.ui.fragment

import android.annotation.SuppressLint
import androidx.recyclerview.widget.GridLayoutManager
import com.combodia.basemodule.base.BaseVMFragment
import com.combodia.basemodule.utils.LogUtils
import com.jony.farm.R
import com.jony.farm.model.entity.BannerEntity
import com.jony.farm.ui.adapter.MarketAdapter
import com.jony.farm.util.CommonUtil
import com.jony.farm.util.DeviceUtil
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

    private var buyName:String = ""


    private val bannerList by lazy { mutableListOf<BannerEntity>() }
 //   private val bannerAdapter by lazy { HomeBannerAdapter(bannerList) }

    private val marketAdapter by lazy { MarketAdapter() }



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
            adapter = marketAdapter
        }
        val gridSpaceItemDecoration = GridSpaceItemDecoration(DeviceUtil.dip2px(requireContext(), 10f), DeviceUtil.dip2px(requireContext(), 0f))
        home_recy.addItemDecoration(gridSpaceItemDecoration)
        marketAdapter.addChildClickViewIds(R.id.tv_rushbuy)

        marketAdapter.setOnItemClickListener { _, _, position ->
            LogUtils.error(marketAdapter.data[position])
       //     RouteUtil.start2Farm(home_recy.context,position)
        //    ShareUtil.shareText()
         }
        marketAdapter.setOnItemChildClickListener { _, view, position ->
            if (view.id == R.id.tv_rushbuy){
                buyName = CommonUtil.getGameName(marketAdapter.getItem(position).animalName)
                mViewModel.getAnimalLeft(marketAdapter.getItem(position).animalID)
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
                marketAdapter.setList(it)
            })
            //点击领养动物后 返回的剩下数量大于0
            animalLeftLiveData.observe(viewLifecycleOwner,{
                it?.let { map ->
                    val leftCount =  map["leftCount"] as Int
                    val haveCount =  map["haveCount"] as Int
                    val animalId = map["animalId"] as Int
                    val rushBuyDialog = RushBuyDialog(mViewModel,requireContext(),animalId,buyName,leftCount,haveCount)
                    rushBuyDialog.show()
                }
            })
            hashRateLiveData.observe(viewLifecycleOwner,{
                tv_allHash.text = if (it.allHash.toString().length>13){it.allHash.toString().take(13)}else{it.allHash.toString()}
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