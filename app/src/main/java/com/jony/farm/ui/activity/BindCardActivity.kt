package com.jony.farm.ui.activity

import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.combodia.basemodule.base.BaseVMActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.model.entity.BindCardType
import com.jony.farm.ui.fragment.MainLandFragment
import com.jony.farm.ui.fragment.PickPayFragment
import com.jony.farm.viewmodel.BindCardViewModel
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_bindcard.*
import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/4/27 15:03
 *描述:This is BindCardActivity
 */

@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_BINDCARD, interceptorNames = [Const.LOGININTERCEPTOR])
class BindCardActivity : BaseVMActivity<BindCardViewModel>() {

    val titles = mutableListOf<String>()
    private val fragments = mutableListOf<Fragment>()

    override fun initVM(): BindCardViewModel = getViewModel()


    override fun getLayoutResId(): Int = R.layout.activity_bindcard

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_bindcard)

        initTabVp()
    }

    private fun initTabVp() {

        titles.add(getString(BindCardType.MAINLAND.label))
        titles.add(getString(BindCardType.PICKPAY.label))
        titles.forEach { title ->
            if (title == getString(BindCardType.MAINLAND.label)) {
                val fragment = MainLandFragment()
                val bundle = Bundle()
                bundle.putString("title",title)
                fragment.arguments = bundle
                fragments.add(fragment)
            }

            if (title == getString(BindCardType.PICKPAY.label)) {
                val fragment = PickPayFragment()
                val bundle = Bundle()
                bundle.putString("title",title)
                fragment.arguments = bundle
                fragments.add(fragment)
            }
        }

        vp.adapter = object : FragmentStateAdapter(this) {

            override fun getItemCount(): Int = titles.size
            override fun createFragment(position: Int): Fragment {
                return fragments[position]
            }
        }
        //tablayout 与 ViewPager2 绑定
        TabLayoutMediator(tab, vp) { tab, position ->
            //  tab.text = titles[position]
            tab.setCustomView(R.layout.item_bind_tab)
            val tv = tab.customView!!.findViewById<TextView>(R.id.tv_tab_item)
            tv.text = titles[position]
            tv.setTextColor(ContextCompat.getColor(this, R.color.color_333))
            if (position == 0) {
                tv.setTextColor(ContextCompat.getColor(this, R.color.white))
                tv.background = ContextCompat.getDrawable(this, R.drawable.bg_gradient_pri20)
            }

        }.attach()

        tab.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val tv = tab.customView!!.findViewById<TextView>(R.id.tv_tab_item)
                tv.setTextColor(ContextCompat.getColor(this@BindCardActivity, R.color.white))
                tv.background = ContextCompat.getDrawable(this@BindCardActivity, R.drawable.bg_gradient_pri20)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val tv = tab.customView!!.findViewById<TextView>(R.id.tv_tab_item)
                tv.setTextColor(ContextCompat.getColor(this@BindCardActivity, R.color.color_333))
                tv.background = (ContextCompat.getDrawable(this@BindCardActivity, R.drawable.bg_retc_white20))
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


    }

    override fun initData() {

    }

    override fun startObserve() {

    }
}