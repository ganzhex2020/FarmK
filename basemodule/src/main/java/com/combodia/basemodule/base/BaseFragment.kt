package com.combodia.basemodule.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.combodia.basemodule.view.dialog.LoadingDialog
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir

/**
 *Author:ganzhe
 *时间:2020/10/24 13:36
 *描述:This is BaseFragment
 */
abstract class BaseFragment:Fragment() {

    private var mDialogLoading: LoadingDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initFragment()
        super.onViewCreated(view, savedInstanceState)
    }

    protected open fun initFragment(){
        initView()
        initData()
    }

    abstract fun getLayoutResId(): Int

    abstract fun initView()

    abstract fun initData()

    open fun showDialogLoading() {
        if (mDialogLoading == null){
            mDialogLoading = context?.let { LoadingDialog(it) }
        }
        if (mDialogLoading!=null&&!mDialogLoading!!.isShowing) {
            mDialogLoading!!.show()
        }
    }

    open fun hideDialogLoading() {
        if (mDialogLoading != null && mDialogLoading!!.isShowing) {
            mDialogLoading!!.dismiss()
        }
    }

}