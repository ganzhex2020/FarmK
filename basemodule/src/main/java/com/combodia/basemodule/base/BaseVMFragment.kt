package com.combodia.basemodule.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.combodia.basemodule.common.callback.EmptyPageCallBack
import com.combodia.basemodule.common.callback.ErrorPageCallBack
import com.combodia.basemodule.common.callback.LoadingCallBack
import com.combodia.httplib.model.LoadState
import com.combodia.httplib.model.LoadStateType
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir

/**
 *Author:ganzhe
 *时间:2020/10/24 14:06
 *描述:This is BaseVMFragment
 */
abstract class BaseVMFragment<VM : BaseViewModel>:BaseFragment() {

    lateinit var mViewModel: VM
    private lateinit var loadService: LoadService<*>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      //  mBinding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        val rootView = inflater.inflate(getLayoutResId(), container, false)
        loadService = LoadSir.getDefault().register(rootView) { reLoad() }
        return loadService.loadLayout

    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mBinding.lifecycleOwner = this
        mViewModel = initVM()
        startObserve()
        showSuccess()
        mViewModel.loadState.observe(viewLifecycleOwner, observer)
        mViewModel.dialogLoadingState.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) { // 显示dialogLoading
                    showDialogLoading()
                } else {// 隐藏dialogLoading
                    hideDialogLoading()
                }
            }
        })
        super.onViewCreated(view, savedInstanceState)
    }*/

    override fun initFragment() {

        mViewModel = initVM()
        startObserve()
        showSuccess()//TODO fragment 这里需要初始化页面显示success 否则页面会默认空白
        super.initFragment()
        mViewModel.loadState.observe(this, observer)
        mViewModel.dialogLoadingState.observe(this,  {
            it?.let {
                if (it) { // 显示dialogLoading
                    showDialogLoading()
                } else {// 隐藏dialogLoading
                    hideDialogLoading()
                }
            }
        })
    }


    abstract fun initVM(): VM

    abstract fun startObserve()

    /**
     * 重试加载页面
     * 一般重新请求页面初始化数据
     */
    open fun reLoad() {}

    /**
     * 分发应用状态
     */
    private val observer by lazy {
        Observer<LoadState> {
            it?.let {
                when (it.code) {
                    LoadStateType.SUCCESS -> showSuccess()
                    LoadStateType.LOADING -> showLoading()
                    LoadStateType.ERROR -> showError()
                    LoadStateType.EMPTY -> showEmpty()
                }
            }
        }
    }


    open fun showLoading() {
        loadService.showCallback(LoadingCallBack::class.java)
    }

    open fun showSuccess() {
        loadService.showCallback(SuccessCallback::class.java)
    }

    open fun showError() {
        loadService.showCallback(
            ErrorPageCallBack::class.java
        )
    }

    open fun showEmpty() {
        loadService.showCallback(EmptyPageCallBack::class.java)
    }



}