package com.huawenli.broadcast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.ConnectException;

public class MainActivity extends BaseActivity {

//    private IntentFilter intentFilter;
//    private NetworkChangeReceiver networkChangeReceiver;

    private Button mForceOfflineBtn;

    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注册检测网络变化时，弹出toast提示的接收器
//        intentFilter = new IntentFilter();
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        networkChangeReceiver = new NetworkChangeReceiver();
//        registerReceiver(networkChangeReceiver,intentFilter);

        //本地广播
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter  = new IntentFilter("com.huawenli.broadcast.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);

        //点击按钮发送强制下线的intent动作
        mForceOfflineBtn = findViewById(R.id.force_offline);
        mForceOfflineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("huawenli","mForceOfflineBtn ");
//                Intent intent = new Intent("com.huawenli.broadcast.FORCE_OFFLINE");
////                sendBroadcast(intent); //发送广播

                //发送本地广播
                Intent intent = new Intent("com.huawenli.broadcast.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent);

                Log.i("huawenli","sendBroadcast ");
            }
        });


    }

    //测网络变化时，弹出toast提示的接收器
//    class NetworkChangeReceiver extends BroadcastReceiver{
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo activeNetworkInfo = connectionManager.getActiveNetworkInfo();
//            if(activeNetworkInfo != null && activeNetworkInfo.isAvailable()){
//                Toast.makeText(context,"network is available",Toast.LENGTH_SHORT).show();
//            }else{
//                Toast.makeText(context,"network is unavailable",Toast.LENGTH_SHORT).show();
//            }
//
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //活动销毁的时候取消注册，不然会一直弹出
       // unregisterReceiver(networkChangeReceiver);

        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    //本地广播
    class LocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "LocalReceiver" , Toast.LENGTH_SHORT).show();
        }
    }
}
