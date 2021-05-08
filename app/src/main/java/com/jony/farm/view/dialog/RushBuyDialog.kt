package com.jony.farm.view.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.*
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.ext.visable
import com.combodia.basemodule.utils.LogUtils
import com.jony.farm.R
import com.jony.farm.util.CommonUtil
import com.jony.farm.util.DeviceUtil
import com.jony.farm.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.dialog_rushbuy.*


/**
 *Author:ganzhe
 *时间:2020/12/25 11:09
 *描述:This is VersionUpdateDialog
 */
class RushBuyDialog(viewModel: HomeViewModel, lifecycleOwner: LifecycleOwner, context: Context) :
    Dialog(
        context,
        R.style.dialog_center_full
    )/*, LifecycleObserver */ {

    private var animalId: Int = 0
    private var leftCount = 0



    init {
        //   lifecycleOwner.lifecycle.addObserver(this)
        setCanceledOnTouchOutside(true)
        setCanceledOnTouchOutside(true)
        val view = View.inflate(context, R.layout.dialog_rushbuy, null)
        val window = window
        window?.setContentView(view)
        window?.setGravity(Gravity.CENTER)
        window?.setLayout(
            DeviceUtil.getScreenW(context) - DeviceUtil.dip2px(context, 80f),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        iv_sub.setOnClickListener {
            var count = et_count.text.toString().trim()
            count = if (count.isBlank()||count.toInt()<=1){
                "1"
            }else{
                (count.toInt()-1).toString()
            }

            et_count.setText(count)
            et_count.setSelection(count.length)
        }
        iv_add.setOnClickListener {
            var count = et_count.text.toString().trim()
            count = when {
                count.isBlank() -> {
                    "1"
                }
                count.toInt()>=leftCount -> {
                    leftCount.toString()
                }
                else -> {
                    (count.toInt()+1).toString()
                }
            }
            et_count.setText(count)
            et_count.setSelection(count.length)
        }



        iv_rush.setOnClickListener {
            val count = et_count.text.toString().trim()
            if (count.isBlank()) {
                toast("please enter buy count")
                return@setOnClickListener
            }
            viewModel.buyAnimal(animalId, count.toInt())

        }


        viewModel.run {
            animalLeftLiveData.observe(lifecycleOwner, Observer { map ->

                et_count.setText("1")

                animalId = map["animalId"]!!
                val mLeftCount = map["leftCount"]

                if (mLeftCount != null) {
                    if (mLeftCount > 0) {
                        ll_soldout.visable(false)
                        ll_buy.visable(true)
                        tv_animalName.text = CommonUtil.getNameByAnimalId(animalId)
                        tv_leftCount.text = mLeftCount.toString()
                        leftCount = mLeftCount
                    } else {
                        ll_soldout.visable(true)
                        ll_buy.visable(false)
                    }

                }
            })
            buyStateLiveData.observe(lifecycleOwner, Observer { buyState ->
                if (buyState) {
                    dismiss()
                }
            })
        }

    }


}