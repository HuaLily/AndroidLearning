package com.huawenli.handler;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * 测试使用handler，点击按钮，开启一个子线程，生成一个随机数，在主线程显示
 */

public class HandlerActivity extends Activity {

    private Button mbutton;
    private TextView mtextView;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1 : mtextView.setText(msg.obj.toString());
                break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        mbutton = findViewById(R.id.click_btn);
        mtextView = findViewById(R.id.show_tv);
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       Message message = new Message();
                       message.what = 1;
                       try {
                           Log.i("huawenli", "before sleep");
                           sleep(1000);
                           Log.i("huawenli", "after sleep");
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }

                       Random random = new Random();
                       message.obj = random.nextInt();
                       handler.sendMessage(message);

                   }
               }).start();
            }
        });


    }


}
