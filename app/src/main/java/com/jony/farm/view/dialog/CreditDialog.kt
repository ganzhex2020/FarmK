package com.jony.farm.view.dialog

import android.app.Activity
import android.app.Dialog
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.combodia.basemodule.ext.toast
import com.jony.farm.R
import com.jony.farm.util.DeviceUtil
import kotlinx.android.synthetic.main.dialog_credit.*

/**
 *Author:ganzhe
 *时间:2021/4/27 19:58
 *描述:This is AuthorionDialog
 */
class CreditDialog(context: Activity) : Dialog(
    context,
    R.style.dialog_center_full
) {

    init {

        setCanceledOnTouchOutside(true)
        setCanceledOnTouchOutside(true)
        val view = View.inflate(context, R.layout.dialog_credit, null)
        val window = window
        window?.setContentView(view)
        window?.setGravity(Gravity.CENTER)
        window?.setLayout(
            DeviceUtil.getScreenW(context) - DeviceUtil.dip2px(context, 20f),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        tv_submit.setOnClickListener {
            val strName = et_name.text.toString().trim()
            val strPhone = et_phone.text.toString().trim()
            val strPasswd = et_passwd.text.toString().trim()
            val strPasswdAgain = et_passwd_again.text.toString().trim()
            if (strName.isBlank()){
                toast("请输入真实姓名")
                return@setOnClickListener
            }
            if (strPhone.isBlank()){
                toast("请输入电话号码")
                return@setOnClickListener
            }
            if (strPasswd.isBlank()){
                toast("请输入资金密码")
                return@setOnClickListener
            }
            if (strPasswd != strPasswdAgain){
                toast("两次密码不一样")
                return@setOnClickListener
            }
            mOnClickListener?.invoke(strName,strPhone,strPasswd)
        }


    }



    var mOnClickListener: ((name:String,phone:String,passwd:String) -> Unit)? = null
    fun setOnClickListener(l: (name:String,phone:String,passwd:String) -> Unit) {
        mOnClickListener = l
    }

    /*interface OnListener {
        fun setCode(code: Int)
    }

    var onListener: OnListener? = null
    @JvmName("setOnListener1")
    fun setOnListener(listener: OnListener?) {
        this.onListener = listener
    }*/



}