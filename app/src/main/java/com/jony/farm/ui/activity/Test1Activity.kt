package com.jony.farm.ui.activity

import android.widget.Toast
import com.combodia.basemodule.base.BaseActivity
import com.combodia.basemodule.utils.LogUtils
import com.jony.farm.R
import com.jony.farm.view.LuckPanAnimEndCallBack
import kotlinx.android.synthetic.main.activity_test1.*


/**
 *Author:ganzhe
 *时间:2021/4/2 18:39
 *描述:This is Test1Activity
 */
class Test1Activity : BaseActivity() {

    private val mItemStrs = arrayOf("123", "撒大声道1", "撒大声道2", "撒旦说", "撒大声道3", "哥哥哥", "对应效果", "对应代码")

    //  private val test1Adapter by lazy { Test1Adapter() }
    private val mList by lazy { mutableListOf<String>() }


    override fun getLayoutResId(): Int = R.layout.activity_test1

    override fun initView() {
        /*for (i in 0..100){
            mList.add(i.toString())
        }*/
        //检查 recy的高度
        /*recy1.viewTreeObserver.addOnGlobalLayoutListener {
            LogUtils.error("recy高度:${recy1.height}")
        }*/
        /*recy1.layoutManager = LinearLayoutManager(this)
        recy1.adapter = test1Adapter
        recy1.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                for (i in 0 until recyclerView.childCount) {
                    recyclerView.getChildAt(i).invalidate()
                }
            }
        })

        test1Adapter.setList(mList)*/

        //  LogUtils.error("recy高度:${recy1.height}")

        luckPan.setItems(mItemStrs)
        luckPan.setLuckNumber(3)
        luckPan.luckPanAnimEndCallBack = LuckPanAnimEndCallBack { str -> LogUtils.error("结束：$str") }
        luckdrawstart.setOnClickListener {
            luckPan.startAnim()
        }
    }

    override fun initData() {

    }

    /* class Test1Adapter :BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_arc){

         override fun convert(holder: BaseViewHolder, item: String) {
             holder.setText(R.id.tv, "Card$item")
             (holder.itemView as MatrixTranslateLayout).setParentHeight(recyclerView.height)

         }

         private fun calculateTranslate(top: Int, h: Int): Int {
             var h = h
             var result = 0
             h -= DeviceUtil.dip2px(recyclerView.context, 60f) //减去当前控件的高度，（60是已知当前Item的高度）
             val hh = h / 2
             result = abs(hh - top)
             result = hh - result
             return result / 2
         }
     }*/
}