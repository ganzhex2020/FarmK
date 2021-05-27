package com.jony.farm.ui.activity

import androidx.fragment.app.Fragment
import com.combodia.basemodule.base.BaseVMActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.jony.farm.R
import com.jony.farm.ui.fragment.*
import com.jony.farm.util.RouteUtil
import com.jony.farm.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


/**
 *Author:ganzhe
 *时间:2021/3/7 19:59
 *描述:This is MainActivity
 */
class MainActivity:BaseVMActivity<MainViewModel>() {

    private val fragmentList = arrayListOf<Fragment>()
    private val homeFragment1 by lazy { HomeFragment() }
    private val homeFragment2 by lazy { MarketFragment() }
  //  private val homeFragment3 by lazy { LuckDrawFragment() }
    private val homeFragment4 by lazy { ShareFragment() }
    private val homeFragment5 by lazy { MineFragment() }

    private var lastPosition: Int = 0
    private var mPosition: Int = 0
    private var currentFragment: Fragment? = null//要显示的Fragment
    private var hideFragment: Fragment? = null//要隐藏的Fragment

 //   val authonManager by inject<AuthonManager>()
    init {
        fragmentList.add(homeFragment1)
        fragmentList.add(homeFragment2)
     //   fragmentList.add(homeFragment3)
        fragmentList.add(homeFragment4)
        fragmentList.add(homeFragment5)
    }

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun initVM(): MainViewModel = getViewModel()


    override fun initView() {

        //println(authonManager)

        immersionBar {
            statusBarColor(R.color.transparent)
        }
   //     ll_parent.setPadding(0,statusBarHeight,0,0)

        setSelectedFragment(1)
        ll1.setOnClickListener {
            setSelectedFragment(0)
        }
        ll2.setOnClickListener {
            setSelectedFragment(1)
          //  RouteUtil.start2Farm(this)
        }
        ll3.setOnClickListener {
           // setSelectedFragment(1)
              RouteUtil.start2Farm(this)
        }
        ll4.setOnClickListener {
            setSelectedFragment(2)
        }
        ll5.setOnClickListener {
            setSelectedFragment(3)
        }

        /*
        //BottomNavigationView 点击事件监听
        bottom_nav_view.setOnNavigationItemSelectedListener{menuItem ->
            when (menuItem.itemId){
                R.id.home ->  setSelectedFragment(0)
                R.id.muchang ->  setSelectedFragment(1)
                R.id.zhuanpan -> setSelectedFragment(2)
                R.id.siliao -> setSelectedFragment(3)
                R.id.mine -> setSelectedFragment(4)
            }

            true
        }*/

    }

    override fun initData() {

    }

    override fun startObserve() {

    }

    private fun setSelectedFragment(position: Int){

  //      bottom_nav_view.menu.getItem(position).isChecked = true
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        //要显示的fragment(解决了activity重建时新建实例的问题)
        currentFragment = fragmentManager.findFragmentByTag("fragment$position")
        hideFragment = fragmentManager.findFragmentByTag("fragment$lastPosition")

        if (position == lastPosition) {//如果位置相同
            if (currentFragment == null) {//如果fragment不存在(第一次启动应用的时候)
                currentFragment = fragmentList[position]
                currentFragment?.let { transaction.add(R.id.fl_container, it, "fragment$position") }
            }//如果位置相同，且fragment存在，则不作任何操作
        }
        if (position != lastPosition) {//如果位置不同
            //如果要隐藏的fragment存在，则隐藏
            hideFragment?.let { transaction.hide(it) }
            if (currentFragment == null) {//如果要显示的fragment不存在，则新加并提交事务
                currentFragment = fragmentList[position]
                currentFragment?.let { transaction.add(R.id.fl_container, it, "fragment$position") }
            } else {//如果要显示的存在则直接显示
                currentFragment?.let { transaction.show(it) }
                /**
                 * 存在的fragment切换根据业务 重新请求数据
                 */
                when(position){
                //    0 -> (currentFragment as HomeFragment).updateData()
                  //  3 -> (currentFragment as MineFragment).updateData()
                }
            }

        }

        transaction.commit()//提交事务
        lastPosition = position//更新要隐藏的fragment的位置
        mPosition = position
    }


}