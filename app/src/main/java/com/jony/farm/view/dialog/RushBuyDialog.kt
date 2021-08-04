package com.jony.farm.view.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.*
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.ext.visable
import com.jony.farm.R
import com.jony.farm.model.entity.AnimalKindType
import com.jony.farm.util.CommonUtil
import com.jony.farm.util.DeviceUtil
import com.jony.farm.viewmodel.MarketViewModel
import kotlinx.android.synthetic.main.dialog_rushbuy.*


/**
 *Author:ganzhe
 *时间:2020/12/25 11:09
 *描述:This is VersionUpdateDialog
 */
class RushBuyDialog(viewModel: MarketViewModel, context: Context,animalId:Int,name:String,leftCount:Int,haveCount:Int) :
    Dialog(
        context,
        R.style.dialog_center_full
    )/*, LifecycleObserver */ {

    //private var animalId: Int = 0
    //private var leftCount = 0



    init {
        //   lifecycleOwner.lifecycle.addObserver(this)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        val view = View.inflate(context, R.layout.dialog_rushbuy, null)
        val window = window
        window?.setContentView(view)
        window?.setGravity(Gravity.CENTER)
        window?.setLayout(
            DeviceUtil.getScreenW(context) - DeviceUtil.dip2px(context, 40f),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        tv_animalName.text = name
        tv_leftCount.text = leftCount.toString()

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
                toast("请输入购买数量")
                return@setOnClickListener
            }
            if (count.toInt()>(23-haveCount)){
                toast("总共能买${23-haveCount}只")
                return@setOnClickListener
            }
            viewModel.buyAnimal(animalId, count.toInt())
            //dialog 消失
            dismiss()
        }
        iv_delete.setOnClickListener {
            dismiss()
        }

     //   viewModel.run {
            /*animalLeftLiveData.observe(lifecycleOwner, Observer { map ->

                et_count.setText("1")

                animalId = map["animalId"]!!
                val mLeftCount = map["leftCount"]

                if (mLeftCount != null) {
                    if (mLeftCount > 0) {
                 //       ll_soldout.visable(false)
                        ll_buy.visable(true)
                        tv_animalName.text = context.getString(AnimalKindType.getAnimalLabel(animalId))//CommonUtil.getNameByAnimalId(animalId)
                        tv_leftCount.text = mLeftCount.toString()
                        leftCount = mLeftCount
                    } else {
                //        ll_soldout.visable(true)
                        ll_buy.visable(false)
                    }
                }
            })
            buyStateLiveData.observe(lifecycleOwner, Observer { buyState ->
                if (buyState) {
                    dismiss()
                }
            })*/
     //   }

    }


}