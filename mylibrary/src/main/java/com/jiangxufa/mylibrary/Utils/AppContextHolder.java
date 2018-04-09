package com.jiangxufa.mylibrary.Utils;

import android.content.Context;

/**
 * 创建时间：2018/4/8
 * 编写人：lenovo
 * 功能描述：
 */

public class AppContextHolder {

    private static Context mContext;

    public static Context getContext() {
        if (mContext == null) {
            throw new RuntimeException("请先初始化的context");
        }
        return mContext;
    }

    public static void initContext(Context mContext) {
        AppContextHolder.mContext = mContext;
    }
}
