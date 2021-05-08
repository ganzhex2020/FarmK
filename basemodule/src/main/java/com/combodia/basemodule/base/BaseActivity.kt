package com.combodia.basemodule.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.combodia.basemodule.view.dialog.LoadingDialog

/**
 *Author:ganZhe
 *时间:2020/10/21 7:30 PM
 *描述:This is BaseActivity
 */
abstract class BaseActivity:AppCompatActivity() {

    private var mDialogLoading: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        initActivity()
    }

    protected open fun initActivity() {
        initView()
        initData()
    }

    abstract fun getLayoutResId(): Int
    abstract fun initView()
    abstract fun initData()

    open fun showDialogLoading() {
        if (mDialogLoading == null){
            mDialogLoading = LoadingDialog(this)
        }
        if (!mDialogLoading!!.isShowing) {
            mDialogLoading!!.show()
        }
    }

    open fun hideDialogLoading() {
        if (mDialogLoading != null && mDialogLoading!!.isShowing) {
            mDialogLoading!!.dismiss()
        }
    }

}