package com.huawenli.http;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtils {
    public static String sendGetHttpRequset( String address ) {
        StringBuilder builder = new StringBuilder();
        address.split(" ");


        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        Call call = okHttpClient.newCall(request);

        Response response = null;

        try { //同步调用，返回Response,会抛出IO异常
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //异步调用，设置OkHttp自带的回调函数
        //enqueue()f方法内部已经开好了子线程
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //在子线程,我们不能在子线程更新UI,需要借助于runOnUiThread()方法或者Handler来处理
                String res = response.body().string();


            }
        });


         return  response.body().toString();


    }
}
