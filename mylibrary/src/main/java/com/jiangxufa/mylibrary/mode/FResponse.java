package com.jiangxufa.mylibrary.mode;


import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * 创建时间：2018/4/8
 * 编写人：lenovo
 * 功能描述：用来统一封装服务端返回数据
 */

public class FResponse<T> implements Serializable {

    public final static int CODE_SUCCESS = 0;

    private int code;
    private T data;
    private String tip;

    public boolean isSucc() {
        if (code == 0)
            return true;
        else
            return false;
    }

    public boolean isFail() {
        return !isSucc();
    }


    public void setCode(int code) {
        this.code = code;
    }

    public void setData(T data) {
        this.data = data;
    }

    @NonNull
    public T getData() {
        return data;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getCode() {
        return code;
    }

    public String getTip() {
        return tip;
    }


}
