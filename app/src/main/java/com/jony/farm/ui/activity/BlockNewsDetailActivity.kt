package com.jony.farm.ui.activity

import android.os.Build
import android.os.Build.VERSION.SDK
import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import androidx.annotation.RequiresApi
import com.combodia.basemodule.base.BaseActivity
import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.utils.LogUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.viewmodel.BlockNewsViewModel
import com.xiaojinzi.component.Component
import com.xiaojinzi.component.anno.AttrValueAutowiredAnno
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_blocknewsdetail.*

import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/5/17 20:11
 *描述:This is BlockNewsDetailActivity
 */
@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_BLOCKNEWSDETAIL)
class BlockNewsDetailActivity:BaseVMActivity<BlockNewsViewModel>() {


    @JvmField
    @AttrValueAutowiredAnno("Id")
    var Id: Int = 0

    override fun initVM(): BlockNewsViewModel = getViewModel()

    override fun getLayoutResId(): Int = R.layout.activity_blocknewsdetail

    override fun onCreate(savedInstanceState: Bundle?) {
        Component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_blocknews)

    }

    override fun initData() {
        mViewModel.getNewsDetail(Id)
    }



    override fun startObserve() {
        mViewModel.newDetailLiveData.observe(this,{
            tv_title.text = it.title
            tv_time.text = it.addTime
            if (Build.VERSION.SDK_INT>24){
                tv_content.text = Html.fromHtml(it.content,FROM_HTML_MODE_LEGACY)//it.content
            }else{
                tv_content.text = Html.fromHtml(it.content)//it.content
            }
        })
    }
}