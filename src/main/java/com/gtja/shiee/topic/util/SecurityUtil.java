package com.gtja.shiee.topic.util;

import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
    @author : liujx
    @description : 加密
    @date : Create in 下午5:22 2018/1/10

**/
public class SecurityUtil {
    public static String encryptPassword(String str) {
        return MD5(str);
    }

    public static String MD5(String md5) {
        if(StringUtils.isEmpty(md5))
            return "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toHexString((digest[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
