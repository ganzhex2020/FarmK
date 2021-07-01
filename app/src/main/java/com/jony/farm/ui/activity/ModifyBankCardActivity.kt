package com.jony.farm.ui.activity

import android.os.Bundle
import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.utils.LogUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.model.entity.BankCardEntity
import com.jony.farm.ui.fragment.MainLandFragment
import com.jony.farm.viewmodel.BindCardViewModel
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_modifybankcard.*
import kotlinx.android.synthetic.main.layout_common_header.*
import org.koin.android.viewmodel.ext.android.getViewModel

@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_MODIFYBANKCARD, interceptorNames = [Const.LOGININTERCEPTOR])
class ModifyBankCardActivity:BaseVMActivity<BindCardViewModel>(){

    var bankCard :BankCardEntity? = null

    override fun initVM(): BindCardViewModel = getViewModel()

    override fun getLayoutResId(): Int = R.layout.activity_modifybankcard

    override fun initView() {

        bankCard = intent.getParcelableExtra("bankCard")
        LogUtils.error(bankCard)

        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_bindcard)


        val fragment = MainLandFragment()
        val bundle = Bundle()
        bundle.putParcelable("bankCard",bankCard)
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .add(R.id.modify_container, fragment)
            .commit()

    }

    override fun initData() {

    }

    override fun startObserve() {

    }
}