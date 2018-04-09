package com.jiangxufa.mylibrary.mode;

import com.jiangxufa.mylibrary.Utils.ToastUtils;
import com.jiangxufa.mylibrary.base.RefreshView;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import retrofit2.adapter.rxjava2.HttpException;

/**
 * 创建时间：2018/4/8
 * 编写人：lenovo
 * 功能描述：
 */

public abstract class FAbstractObserver implements Observer {

    private RefreshView refreshView;

    public FAbstractObserver(RefreshView view) {
        this.refreshView = view;
    }

    @Override
    public void onError(Throwable t) {
        try {
            if (t instanceof ApiException) {
                ApiException apiService = (ApiException) t;
                onApiException(((ApiException) t));
            } else if (t instanceof UnknownHostException) {
                ToastUtils.showShort("网络连接异常，请开启网络");
            } else if (t instanceof SocketTimeoutException) {
                ToastUtils.showShort("网络连接异常，请开启网络");
            } else if (t instanceof HttpException) {
                ToastUtils.showShort("网络不好，请检查您的网络");
            } else {
                ToastUtils.showShort(t.toString());
            }
        } catch (Exception e) {
            ToastUtils.showShort(t.toString());
        } finally {
            dismissRefresh();
        }
    }


    @Override
    public void onComplete() {
        dismissRefresh();
    }

    private void dismissRefresh() {
        if (refreshView != null) {
            refreshView.dismiss();
        }
    }

    protected abstract void onApiException(ApiException e);


}
