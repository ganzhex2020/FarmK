package com.jony.farm.util;

import com.jony.farm.model.entity.AnimalEntity;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Author:ganzhe
 * 时间:2021/5/6 13:55
 * 描述:This is MapJUtil
 */
public class MapJUtil {

    public static RequestBody getRequestBody(String content, String type) {

        return RequestBody.create(content, MediaType.parse(type));


    }


    public static void listRemove(List<AnimalEntity> allList, List<AnimalEntity> removeList) {

        for (int i = 0; i < removeList.size(); i++) {
            for (int j = 0; j < allList.size(); j++) {
                if (removeList.get(i).getId() == allList.get(j).getId()){
                    allList.remove(j);
                    j--;
                }
            }
        }

    }

    public static void listRemoveJ(List<AnimalEntity> allList, List<AnimalEntity> removeList) {

        for (int i = 0; i < removeList.size(); i++) {
            for (int j = 0; j < allList.size(); j++) {
                if (removeList.get(i).getId() == allList.get(j).getId()){
                    allList.get(j).setAnimalID(0);
                    //  j--;
                }
            }
        }

    }
}
