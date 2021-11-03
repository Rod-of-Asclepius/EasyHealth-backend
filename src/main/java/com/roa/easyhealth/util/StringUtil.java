package com.roa.easyhealth.util;

public class StringUtil {
    /**
     * 功能描述: 找一个不是null的字符串
     *
     * @param str 一串字符串
     */
    public static String getNotNullStr(String... str) {
        for (String s : str) {
            if (s != null && !"".equals(s)) {
                return s;
            }
        }
        return null;
    }
}
