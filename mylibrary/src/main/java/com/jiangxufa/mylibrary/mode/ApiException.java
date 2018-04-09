package com.jiangxufa.mylibrary.mode;


/**
 * 创建时间：2018/1/17
 * 编写人：lenovo
 * 功能描述：用来统一封装服务端异常
 */
public class ApiException extends Exception {

    private int code;
    private String tip;

    public ApiException(int code, String tip) {
        this.code = code;
        this.tip = tip;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
