package com.jony.farm.config;

import android.content.Intent;

import com.combodia.basemodule.utils.LogUtils;
import com.combodia.httplib.ext.AuthonService;
import com.jony.farm.ui.activity.LoginActivity;
import com.tencent.mmkv.MMKV;

import zlc.season.claritypotion.ClarityPotion;

import static com.combodia.httplib.config.Constant.KEY_LOGIN_STATE;

/**
 * Author:ganzhe
 * 时间:2021/4/15 20:16
 * 描述:This is AuthonManager
 */
public class AuthonManager {

    public static void authon(){
        AuthonService.getInstance().setOnListener(new AuthonService.OnListener() {
            @Override
            public void setCode(int code) {
                //修改登录状态
                MMKV.defaultMMKV().encode(KEY_LOGIN_STATE,false);

                Intent intent = new Intent();
                intent.setClass(ClarityPotion.clarityPotion, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ClarityPotion.clarityPotion.startActivity(intent);
            }
        });
    }


}
