package com.jony.farm.util;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Author:ganzhe
 * 时间:2021/5/6 13:55
 * 描述:This is MapJUtil
 */
public class MapJUtil {

    public static RequestBody getRequestBody(String content, String type){

        return RequestBody.create(content, MediaType.parse(type));


    }
}
