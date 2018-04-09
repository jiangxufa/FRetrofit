package com.jiangxufa.mylibrary.Utils;

import com.jiangxufa.mylibrary.base.RefreshView;
import com.jiangxufa.mylibrary.mode.ApiException;
import com.jiangxufa.mylibrary.mode.FResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 创建时间：2018/4/8
 * 编写人：lenovo
 * 功能描述：
 */

public class FRxUtils {

    /**
     * 统一线程处理
     * 参考 https://blog.csdn.net/jdsjlzx/article/details/59223182 博客
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper(final RefreshView view) {    //compose简化线程
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if (view != null) {
                                    view.refresh();
                                }
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 分发响应数据
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<FResponse<T>, T> handleResult() {   //compose判断结果
        return new ObservableTransformer<FResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<FResponse<T>> upstream) {
                return upstream.flatMap(new Function<FResponse<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(FResponse<T> tDoResponse) {
                        try {
                            if (tDoResponse.isSucc()) {
                                return createData(tDoResponse.getData());
                            } else {
                                ApiException apiException = new ApiException(tDoResponse.getCode(), tDoResponse.getTip());
                                return Observable.error(apiException);
                            }
                        } catch (Exception e) {
                            return Observable.error(e);
                        }
                    }
                });
            }
        };
    }

    /**
     * 生成Observable
     *
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T t) {
        return Observable.create(new ObservableOnSubscribe<T>() {

            @Override
            public void subscribe(ObservableEmitter<T> subscriber) throws Exception {
                try {
                    subscriber.onNext(t);
                    subscriber.onComplete();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

}
