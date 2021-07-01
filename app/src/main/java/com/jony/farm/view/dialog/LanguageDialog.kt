package com.jony.farm.view.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.combodia.basemodule.ext.toast
import com.combodia.httplib.config.Constant
import com.combodia.httplib.config.LanguageType
import com.combodia.httplib.ext.LanguageUtil
import com.jony.farm.R
import com.jony.farm.app.MyApp
import com.jony.farm.ui.activity.MainActivity
import com.jony.farm.util.DeviceUtil
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.dialog_credit.*
import kotlinx.android.synthetic.main.dialog_language.*

/**
 *Author:ganzhe
 *时间:2021/4/27 19:58
 *描述:This is AuthorionDialog
 */
class LanguageDialog(context: Activity) : Dialog(
    context,
    R.style.dialog_center_full
) {

    init {

        setCanceledOnTouchOutside(true)
        setCanceledOnTouchOutside(true)
        val view = View.inflate(context, R.layout.dialog_language, null)
        val window = window
        window?.setContentView(view)
        window?.setGravity(Gravity.BOTTOM)
        window?.setLayout(
            DeviceUtil.getScreenW(context),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val mAdapter = Adapter()
        recy.adapter = mAdapter
        recy.layoutManager = LinearLayoutManager(context)
        val list = listOf(LEntity("简体中文",false),LEntity("English",false))
        val language = LanguageUtil.getDefaultLanguage()
        if (language == LanguageType.CHINESE.language){
            list[0].select = true
        }else{
            list[1].select = true
        }
        mAdapter.setList(list)
        mAdapter.setOnItemClickListener { _, _, position ->
            for (i in list.indices) {
                mAdapter.data[i].select = false
            }
            mAdapter.data[position].select = true
            mAdapter.notifyDataSetChanged()
        }

        tv_cancle.setOnClickListener {
            dismiss()
        }
        tv_confirm.setOnClickListener {
            var position = -1
            for (i in mAdapter.data.indices){
                if (mAdapter.data[i].select){
                    position = i
                }
            }
            when(position){
                0 ->{
                    changeLanguage(context,LanguageType.CHINESE.language)
                }
                1 ->{
                    changeLanguage(context,LanguageType.ENGLISH.language)
                }
            }
            dismiss()
        }

    }
    private fun changeLanguage(context: Context,language: String) {
        val kv = MMKV.defaultMMKV()
        kv.encode(Constant.KEY_LANGUAGE,language)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            LanguageUtil.changeAppLanguage(MyApp.CONTEXT, language)
        }

        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    class LEntity(
        val name:String,
        var select:Boolean
    )

    class Adapter:BaseQuickAdapter<LEntity,BaseViewHolder>(R.layout.item_dialoglanguage){

        override fun convert(holder: BaseViewHolder, item: LEntity) {
            val tv_name = holder.getView<TextView>(R.id.tv_name)
            tv_name.text = item.name
            tv_name.isSelected = item.select

        }
    }

}