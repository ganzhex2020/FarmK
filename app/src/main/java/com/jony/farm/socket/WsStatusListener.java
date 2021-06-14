package com.jony.farm.socket;

import okhttp3.Response;
import okio.ByteString;

/**
 * Author:ganzhe
 * 时间:2020/12/30 19:55
 * 描述:This is WsStatusListener
 */
public abstract class WsStatusListener {

    public void onOpen(Response response) {
    }

    public void onMessage(String text) {
    }

    public void onMessage(ByteString bytes) {
    }

    public void onReconnect() {

    }

    public void onClosing(int code, String reason) {
    }


    public void onClosed(int code, String reason) {
    }

    public void onFailure(Throwable t, Response response) {
    }
}
