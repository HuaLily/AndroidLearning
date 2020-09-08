package com.huawenli.handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * 这个是测试HandlerThread的demo，
 * 实现的效果是在主页面点击按钮，在子线程中sleep，并生成随机数,然后在主UI更新
 */

public class MainActivity extends Activity {

    private final static String TAG = "MainActivity";

    final int MSD_GET = 1;
    final int MSD_RESULT = 2;

    private Button mbutton;
    private TextView mtextview;


    //子线程的Handler
    private HandlerThread mHandlerThread;
    private Handler mSubHandlerThread;


    //主UI的Handler
    private Handler mUIHandler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Log.i(TAG,"mUIHandler handleMessage thread:"+Thread.currentThread());
            switch (msg.what){
                case MSD_RESULT:
                    mtextview.setText((String)msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG,"onCreate thread:" + Thread.currentThread());

        mbutton = findViewById(R.id.click_btn);
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSubHandlerThread.sendEmptyMessage(MSD_GET);
            }
        });
        mtextview = findViewById(R.id.show_tv);
        initHandlerThread();
    }

    private void initHandlerThread() {
        //创建HandlerThread线程
        mHandlerThread = new HandlerThread("mHandlerThread");
        //运行线程
        mHandlerThread.start();
        //获取HandlerThread线程中的Looper实例
        Looper looper = mHandlerThread.getLooper();
        //通过Looper实例创建Handler实例，从而使mSubThreadHandler与该线程连接到一起。
        mSubHandlerThread = new Handler(looper){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i(TAG,"mSubThreadHandler handleMessage thread" +Thread.currentThread());
                super.handleMessage(msg);
                switch (msg.what){
                    case MSD_GET:
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Random random = new Random();
                        Message message = new Message();
                        message.obj = Integer.toString(random.nextInt());
                        message.what = MSD_RESULT;
                        //向UIThread发送消息
                        mUIHandler.sendMessage(message);
                        break;
                }

            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
        mHandlerThread.quit();
    }
}
