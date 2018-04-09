package com.jiangxufa.mylibrary.Utils;

import java.text.NumberFormat;

/*
 * 创建时间：2018/1/6
 * 编写人：lenovo
 * 功能描述：基本的字符串判断工具
 */
public class StringUtil {

    public static boolean isBlank(String src) {
        return null == src || "".equals(src.trim());
    }

    public static boolean isNotBlank(String src) {
        return !isBlank(src);
    }

    public static String getPercentString(float a, float b) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);
        return numberFormat.format((a / b * 100)) + "%";
    }
}
