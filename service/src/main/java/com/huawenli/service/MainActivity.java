package com.huawenli.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {



    private Button mStartBtn;
    private Button mStopBtn;
    private Button mStartBindBtn;
    private Button mStopBindBtn;

    private MyService.myBinder myBinder;
    private Button mStart_Intent_Service;


    private ServiceConnection serviceConnection = new ServiceConnection() {

        //绑定时
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myBinder = (MyService.myBinder) iBinder;
            myBinder.startBinder();
            myBinder.getProgress();
        }

        //解绑时
        //onServiceDisconnected只会在Service丢失时才会调用， 通常会在Service所在进程被迫终止时才会调用，
        // 当Service重新运行时会再次调用onServiceConnected方法
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i("huawenli", "onServiceDisconnected");

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartBtn = findViewById(R.id.start_service);
        mStopBtn =findViewById(R.id.stop_service);
        mStartBindBtn = findViewById(R.id.start_bind);
        mStopBindBtn = findViewById(R.id.stop_bind);
        mStart_Intent_Service = findViewById(R.id.start_intent_service);




        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent start_service = new Intent(MainActivity.this,MyService.class);
              startService(start_service);

            }
        });

        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent stop = new Intent(MainActivity.this,MyService.class);
                stopService(stop);

            }
        });

        mStartBindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startbind = new Intent(MainActivity.this,MyService.class);
                bindService(startbind, serviceConnection, BIND_AUTO_CREATE);
            }
        });

        mStopBindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unbindService(serviceConnection);
            }
        });

        mStart_Intent_Service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MainActivity", "Thread id is"+Thread.currentThread().getId());
               Intent intentService =  new Intent(MainActivity.this,MyIntentService.class);
               startService(intentService);
            }
        });







    }
}


