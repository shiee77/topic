package com.sang.topic.util;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
    @author : liujx
    @description :
    @date : Create in 下午1:45 2018/1/8

**/
public class TopicStringUtils {
    public static List<Integer> toIntegerList(String str) {
        List<Integer> list = new ArrayList<>();
        if(StringUtils.isEmpty(str)) return list;
        String[] arr = str.split(",");
        for (String s : arr) {
            Integer i = Integer.valueOf(s);
            list.add(i);
        }
        return list;
    }

    public static List<String> toStringList(String str) {
        List<String> list = new ArrayList<>();
        String[] arr = str.split(",");
        for (String s : arr) {
            list.add(s);
        }
        return list;
    }

    public static String integerToString(Integer... args) {
        StringBuilder str = new StringBuilder();
        for (Integer s : args) {
            str.append(",");
            str.append(s);
        }
        if(args.length > 0)
            return str.substring(1);
        return "";
    }
}
