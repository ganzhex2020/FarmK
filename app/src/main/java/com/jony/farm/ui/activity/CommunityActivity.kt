package com.jony.farm.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.combodia.basemodule.base.BaseVMActivity
import com.combodia.basemodule.ext.toast
import com.combodia.basemodule.utils.LogUtils
import com.combodia.httplib.config.Constant
import com.combodia.httplib.config.Constant.KEY_USER_ID
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.jony.farm.R
import com.jony.farm.config.Const
import com.jony.farm.model.entity.MsgBean
import com.jony.farm.model.entity.SocketMsg
import com.jony.farm.socket.WebSocketUtil
import com.jony.farm.socket.WebSocketUtil.mWebSocket
import com.jony.farm.ui.adapter.ChatAdapter
import com.jony.farm.util.GsonHelper
import com.jony.farm.util.bus.RxBus
import com.jony.farm.util.bus.receiveEvent
import com.jony.farm.util.gone
import com.jony.farm.viewmodel.CommunityViewModel
import com.tencent.mmkv.MMKV
import com.xiaojinzi.component.anno.RouterAnno
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_community.*
import kotlinx.android.synthetic.main.layout_common_header.*
import org.json.JSONObject
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *Author:ganzhe
 *时间:2021/5/18 16:59
 *描述:This is CommunityActivity
 */
@RouterAnno(host = Const.MODULE_HOST_APP, path = Const.MODULE_PATH_APP_COMMUNITYCHAT,interceptorNames = [Const.LOGININTERCEPTOR])
class CommunityActivity:BaseVMActivity<CommunityViewModel>() {
    companion object{
        val LEFT = 1 //非自己
        val RIGHT = 2 //自己
    }
    val userId = MMKV.defaultMMKV().decodeInt(KEY_USER_ID)
    val chatList = mutableListOf<SocketMsg>()
    val chatAdapter by lazy { ChatAdapter(chatList) }



    override fun initVM(): CommunityViewModel = getViewModel()



    override fun getLayoutResId(): Int = R.layout.activity_community

    override fun initView() {

        ll_auto.gone()

        immersionBar {
            statusBarColor(R.color.transparent)
        }
        ll_parent.setPadding(0, statusBarHeight, 0, 0)
        iv_back.setOnClickListener { onBackPressed() }
        iv_title.setImageResource(R.mipmap.ic_title_communitychat)

        initRecy()

       /* btn_open.setOnClickListener {
            val kv = MMKV.defaultMMKV()
            val sessionId = kv.decodeString(Constant.KEY_SESSION_ID,"")
            WebSocketUtil.longConnect(sessionId)
        }
        btn_send.setOnClickListener {
            if (mWebSocket !=null){
               // val isOk =  mWebSocket!!.send("我的android客户端 哈哈")
               // LogUtils.error("是否发送成功：$isOk")


                val isSend = WebSocketUtil.senChatMsg(24,"我的android客户端 哈哈")
                LogUtils.error("发送是否成功:$isSend")
            }
        }
        btn_close.setOnClickListener {
            mWebSocket?.close(1000,"客户端主动关闭连接")
        }*/

    }

    fun initRecy(){
        recy_chat.run {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(this@CommunityActivity)
        }
    }

    override fun initData() {
        val isSend = WebSocketUtil.sendMsg(2,"24_24_24")
        LogUtils.error("进入房间:$isSend")

        //接受事件
        receiveEvent<String> {msg ->
            //LogUtils.error(it)
            val socketMsg = GsonHelper.convertEntity(msg,SocketMsg::class.java)
            val msgBean = GsonHelper.convertEntity(socketMsg.msg,MsgBean::class.java)
            socketMsg.msgBean = msgBean
            if (socketMsg.extendData ==userId){
                socketMsg.itemType = RIGHT
            }else{
                socketMsg.itemType = LEFT
            }
            LogUtils.error(socketMsg)
            if (checkbox.isChecked){
                chatList.add(socketMsg)
                chatAdapter.notifyItemInserted(chatList.size-1)
                recy_chat.scrollToPosition(chatList.size-1)
            }else{
                if (socketMsg.extendData!=0){
                    chatList.add(socketMsg)
                    chatAdapter.notifyItemInserted(chatList.size-1)
                    recy_chat.scrollToPosition(chatList.size-1)
                }
            }

        }
//        val sub = RxBus.getInstance().toObservable().subscribe(Consumer {
//            LogUtils.error(it)
//        })

        //发送消息
        tv_send.setOnClickListener {
            val msg = et_send.text.toString()
            if (msg.isBlank()){
                toast("请输入消息")
                return@setOnClickListener
            }
            if (mWebSocket !=null){
                val isSend = WebSocketUtil.senChatMsg(24,msg)
                LogUtils.error("发送是否成功:$isSend")
                if (isSend){
                    et_send.setText("")
                }
            }
        }
    }

    override fun startObserve() {

    }

    override fun onDestroy() {
        super.onDestroy()
        WebSocketUtil.sendMsg(5,"")
    }
}