package com.huawenli.broadcast;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    ForceOfflineRecevier forceOfflineRecevier;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }


    //由于只需要栈顶的活动接受到intent，所以只需要在这个resume里面接受
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("huawenli",getClass().getSimpleName() +" onResume");
        forceOfflineRecevier = new ForceOfflineRecevier();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.huawenli.broadcast.FORCE_OFFLINE");
        registerReceiver(forceOfflineRecevier, intentFilter);
        Log.i("huawenli",getClass().getSimpleName() +" registerReceiver");

    }


    @Override
    protected void onPause() {
        super.onPause();

        if (forceOfflineRecevier !=null){
            unregisterReceiver(forceOfflineRecevier);
            forceOfflineRecevier = null;
        }

    }


    //接受强制下线的广播接收器,接收到强制下线的动作，给出弹窗
    class ForceOfflineRecevier extends BroadcastReceiver{

        @Override
        public void onReceive(final Context context, Intent intent) {
            Log.i("huawenli",getClass().getSimpleName() +" onReceive");
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Warning");
            builder.setCancelable(false);//不能通过back键返回
            builder.setMessage("强制下线，返回登录页");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                ActivityManager.finishAll();//销毁所有的活动
                    Intent intent1 = new Intent(context, LoginActivity.class);
                    startActivity(intent1);//转到登录页
                }
            });
            builder.show();
        }
    }

}
