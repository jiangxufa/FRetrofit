package com.jiangxufa.mylibrary.intercepter;//package com.jiangxufa.fretrofit.intercepter;
//
//import com.jiangxufa.modulea.utils.Utils;
//
//import java.io.IOException;
//
//import app.ifeixiu.com.coremodel.http.util.StringUtil;
//import app.ifeixiu.com.coremodel.http.util.UserAgentUtil;
//import app.ifeixiu.com.remotevideo.AppConfig;
//import app.ifeixiu.com.remotevideo.manager.AccountManager;
//import okhttp3.FormBody;
//import okhttp3.HttpUrl;
//import okhttp3.Interceptor;
//import okhttp3.Request;
//import okhttp3.Response;
//
///**
// * 创建时间：2018/1/18
// * 编写人：lenovo
// * 功能描述：
// */
//
//public class ParamsInterceptor implements Interceptor {
//
//    private HttpUrl.Builder getParams;
//    private Request newRequest;
//
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        Request oldRequest = chain.request();
//        String method = oldRequest.method();
//        if (method.equals("POST")) {
//            newRequest = doPost(oldRequest);
//        } else {
//            newRequest = doGet(oldRequest);
//        }
//        return chain.proceed(newRequest);
//    }
//
//    private Request doPost(Request request) {
//        //创建一个新的FromBoby
//        FormBody.Builder bobyBuilder = new FormBody.Builder();
//        //获取原先的boby
//        FormBody body = (FormBody) request.body();
//        //遍历boby
//        for (int i = 0; i < body.size(); i++) {
//            //取出原先boby的数据  存入新的boby里
//            bobyBuilder.add(body.encodedName(i), body.encodedValue(i));
//        }
//        //添加制定的公共参数到新的boby里  把原先的boby给替换掉
//        body = bobyBuilder.add("terminalType", AppConfig.getTerminalType())
//                .add("versionCode", AppConfig.getVersionCode() + "")
//                .add("uniqid", AppConfig.getUniquePsuedoID())
//                .build();
//        //获取新的request  取代原先的request
//        Request newRequest;
//        if (StringUtil.isNotBlank(AccountManager.getInstance().getToken())) {
//            newRequest = request.newBuilder()
//                    .post(body)
////                    //.removeHeader("User-Agent")
//                    .addHeader("User-Agent", UserAgentUtil.getUserAgent(Utils.getApp()))
//                    .addHeader("token", AccountManager.getInstance().getToken())
//                    .build();
//        } else {
//            newRequest = request.newBuilder()
//                    //添加到请求里
//                    .post(body)
//                    //.removeHeader("User-Agent")
//                    .addHeader("User-Agent", UserAgentUtil.getUserAgent(Utils.getApp()))
//                    .build();
//        }
//        return newRequest;
//    }
//
//    private Request doGet(Request oldRequest) {
//        getParams = getGetParams(oldRequest);
//        Request newRequest;
//        if (StringUtil.isNotBlank(AccountManager.getInstance().getToken())) {
//            newRequest = oldRequest.newBuilder()
//                    .method(oldRequest.method(), oldRequest.body())
//                    //添加到请求里
//                    .url(getParams.build())
//                    //.removeHeader("User-Agent")
//                    .addHeader("User-Agent", UserAgentUtil.getUserAgent(Utils.getApp()))
//                    .addHeader("token", AccountManager.getInstance().getToken())
//                    .build();
//        } else {
//            newRequest = oldRequest.newBuilder()
//                    .method(oldRequest.method(), oldRequest.body())
//                    //添加到请求里
//                    .url(getParams.build())
//                    //.removeHeader("User-Agent")
//                    .addHeader("User-Agent", UserAgentUtil.getUserAgent(Utils.getApp()))
//                    .build();
//        }
//        return newRequest;
//    }
//
//    private HttpUrl.Builder getGetParams(Request request) {
//        if (StringUtil.isNotBlank(AccountManager.getInstance().getToken())) {
//            //获取到请求地址api
//            getParams = request.url().newBuilder()
//                    .addQueryParameter("terminalType", AppConfig.getTerminalType())
//                    .addQueryParameter("versionCode", AppConfig.getVersionCode() + "")
//                    .addQueryParameter("uniqid ", AppConfig.getUniquePsuedoID());
//        } else {
//            getParams = request.url().newBuilder()
//                    .addQueryParameter("versionCode", AppConfig.getVersionCode() + "")
//                    .addQueryParameter("terminalType", AppConfig.getTerminalType())
//                    .addQueryParameter("uniqid ", AppConfig.getUniquePsuedoID());
//        }
//        return getParams;
//    }
//}
