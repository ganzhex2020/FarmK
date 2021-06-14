package com.jony.farm.ui.activity

import com.combodia.basemodule.base.BaseVMActivity
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.viewmodel.ChatViewModel
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_chat.*
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/6/9 21:17
 *描述:This is ChatActivity
 */

@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_CHAT, interceptorNames = [Const.LOGININTERCEPTOR])
class ChatActivity:BaseVMActivity<ChatViewModel>() {

    override fun initVM(): ChatViewModel = getViewModel()



    override fun getLayoutResId(): Int = R.layout.activity_chat

    override fun initView() {

        btn_open.setOnClickListener {

        }
        btn_close.setOnClickListener {

        }

    }

    override fun initData() {

    }

    override fun startObserve() {

    }
}