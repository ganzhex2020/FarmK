package com.jony.farm.ui.activity

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import com.combodia.basemodule.base.BaseActivity
import com.combodia.basemodule.utils.LogUtils
import com.jony.farm.R
import kotlinx.android.synthetic.main.activity_test2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.launch

/**
 *Author:ganzhe
 *时间:2021/4/2 18:39
 *描述:This is Test1Activity
 */
class Test2Activity:BaseActivity() {

   // private val test1Adapter by lazy { Test1Adapter() }
    private val mList by lazy { mutableListOf<String>() }


    override fun getLayoutResId(): Int = R.layout.activity_test2

    override fun initView() {
       /* for (i in 0..100){
            mList.add(i.toString())
        }*/
        //检查 recy的高度
        /*recy1.viewTreeObserver.addOnGlobalLayoutListener {
            LogUtils.error("recy高度:${recy1.height}")
        }*/
       /* recy1.layoutManager = LinearLayoutManager(this)
        recy1.adapter = test1Adapter
        recy1.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                *//*for (i in 0 until recyclerView.childCount) {
                    recyclerView.getChildAt(i).invalidate()
                }*//*
                for (i in 0 until recyclerView.childCount) {
                    val pad: Int = calculateTranslate(recyclerView.context, recyclerView.getChildAt(i).top, recyclerView.height)
                    recyclerView.getChildAt(i).setPadding(pad, 0, 0, 0)
                }

                Log.i("ccb", "onScrolled: dx$dx  ,dy$dy")
                val dyy = (dy * -1).toFloat()
                setRotate(rvDish, dyy / 4)
            }
        })

        test1Adapter.setList(mList)*/

      //  LogUtils.error("recy高度:${recy1.height}")

        btn_start1.setOnClickListener {
          //  myTask()

            val anim = AnimationUtils.loadAnimation(this,R.anim.alpha)
            rvDish.startAnimation(anim)
        }

        btn_start2.setOnClickListener {
            val anim = AnimationUtils.loadAnimation(this,R.anim.scale)
            rvDish.startAnimation(anim)
        }

        btn_start3.setOnClickListener {
            val anim = AnimationUtils.loadAnimation(this,R.anim.rotate)
            rvDish.startAnimation(anim)
        }


    }

    fun myTask(){
        lifecycleScope.launch(Dispatchers.Main){
            val skipChannel = ticker(delayMillis = 10, initialDelayMillis = 0)
            repeat(500) {i ->

                skipChannel.receive()
                LogUtils.error("$i ")
                val dy = (i * -1).toFloat()
                setRotate(rvDish,dy)
            }
        }

    }

    fun mytask2(){

    }

    /*fun calculateTranslate(context: Context, top: Int, h: Int): Int {
        var h = h
        var result = 0
        h -= DeviceUtil.dip2px(context, 60f) //减去当前控件的高度，（60是已知当前Item的高度）
        val hh = h / 2
        result = abs(hh - top)
        result = hh - result
        return result / 2
    }*/

    private var values = 0f
    private var newValues = 0f
    private var rotationAnimator: ObjectAnimator? = null

    fun setRotate(view: View?, dy: Float) {
        if (rotationAnimator == null) rotationAnimator = ObjectAnimator.ofFloat(view, "rotation", 0f, 10f)
        newValues = values + dy
        rotationAnimator?.duration = 10L
        rotationAnimator?.setFloatValues(values, newValues)
        rotationAnimator?.start()
        values = newValues
    }

    override fun initData() {

    }

   /* class Test1Adapter :BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_pading){

        fun calculateTranslate(context: Context, top: Int, h: Int): Int {
            var h = h
            var result = 0
            h -= DeviceUtil.dip2px(context, 60f) //减去当前控件的高度，（60是已知当前Item的高度）
            val hh = h / 2
            result = abs(hh - top)
            result = hh - result
            return result / 2
        }


        override fun convert(holder: BaseViewHolder, item: String) {
            holder.setText(R.id.tv, "Card$item")
            val mv = (holder.itemView as RelativeLayout)
            mv.setPadding(calculateTranslate(recyclerView.context, mv.top, recyclerView.height), 0, 0, 0)

        }


    }*/
}