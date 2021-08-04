package com.jony.farm.view.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.jony.farm.R
import com.jony.farm.ui.activity.LuckDrawActivity
import com.jony.farm.util.DeviceUtil
import kotlinx.android.synthetic.main.dialog_lucktip.*

class LuckTipDialog(context: Context,title:String,index:Int,backState:LuckDrawActivity.BackState): Dialog(
context,
R.style.dialog_center_full
) {

    init {
        setCanceledOnTouchOutside(true)
        setCanceledOnTouchOutside(true)
        val view = View.inflate(context, R.layout.dialog_lucktip, null)
        val window = window
        window?.setContentView(view)
        window?.setGravity(Gravity.CENTER)
        window?.setLayout(
            DeviceUtil.getScreenW(context) - DeviceUtil.dip2px(context, 40f),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        tv_title.text = title
      //  tv_content.text = content
        when(backState){
            LuckDrawActivity.BackState.WZJ ->{tv_content.text = ""}
            LuckDrawActivity.BackState.ZJ ->{
                tv_content.text = "抽中奖励"
                when(index){
                    1 ->{iv_content.setImageResource(R.drawable.ic_luckdraw_bg2)}
                    2 ->{iv_content.setImageResource(R.drawable.ic_luckdraw_bg3)}
                    3 ->{iv_content.setImageResource(R.drawable.ic_luckdraw_bg4)}
                    4 ->{iv_content.setImageResource(R.drawable.ic_luckdraw_bg5)}
                    5 ->{iv_content.setImageResource(R.drawable.ic_luckdraw_bg6)}
                    6 ->{iv_content.setImageResource(R.drawable.ic_luckdraw_bg7)}
                    7 ->{iv_content.setImageResource(R.drawable.ic_luckdraw_bg8)}
                }
            }
            LuckDrawActivity.BackState.OUT ->{tv_content.text = "明日再来噢"}
        }

        tv_confirm.setOnClickListener {
            dismiss()
        }

    }


}