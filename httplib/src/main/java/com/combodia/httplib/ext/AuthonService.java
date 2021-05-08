package com.combodia.httplib.ext;

/**
 * Author:ganzhe
 * 时间:2021/4/15 19:21
 * 描述:This is ExceptionManager
 */
public class AuthonService {

    public static AuthonService authonService;

    public static AuthonService getInstance(){
        if (authonService ==null){
            synchronized (AuthonService.class){
                if(authonService ==null){
                    authonService = new AuthonService();
                }
            }
        }
        return authonService;
    }

    public interface OnListener{
        void setCode(int code);
    }
    public  OnListener onListener;
    public void setOnListener(OnListener listener) {
        this.onListener = listener;
    }

}
