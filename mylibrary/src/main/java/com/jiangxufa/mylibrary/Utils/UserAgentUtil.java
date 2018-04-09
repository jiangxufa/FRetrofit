package com.jiangxufa.mylibrary.Utils;

import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;

/**
 * 创建时间：2018/3/27
 * 编写人：lenovo
 * 功能描述：
 */

public class UserAgentUtil {

    /**
     * 在Api17之后可以通过WebSettings.getDefaultUserAgent(context)获取，但是经过测试极个别手机
     * 会出现找不到类的情况，因此try-catch一下  https://www.jianshu.com/p/ddbe8c637fc5
     *
     * @param context context
     * @return agent
     */
    public static String getUserAgent(Context context) {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(context);
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
