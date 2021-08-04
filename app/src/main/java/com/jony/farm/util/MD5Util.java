package com.jony.farm.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    private static char sHexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getMD5(String source) {
        try {
            byte[] bytes = source.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            md.update(bytes);
            // 获得密文
            byte[] mdBytes = md.digest();
            // 把密文转换成十六进制的字符串形式
            int length = mdBytes.length;
            char[] chars = new char[length * 2];
            int k = 0;
            for (int i = 0; i < length; i++) {
                byte byte0 = mdBytes[i];
                chars[k++] = sHexDigits[byte0 >>> 4 & 0xf];
                chars[k++] = sHexDigits[byte0 & 0xf];
            }
            return new String(chars);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getMD5Str(String str) {
        byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest  = md5.digest(str.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //16是表示转换为16进制数
        String md5Str = new BigInteger(1, digest).toString(16);
        return md5Str;
    }
}
