package com.huawenli.aidl;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.TextView;
import android.widget.Toast;

//Client输入用户名和密码，并通过AIDL接口发送给Server
public class MainActivity extends AppCompatActivity {

    private TextView mtextView;

    ServiceConnection mAIDILConnetion = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            AIDLService.MyBinder binder = (AIDLService.MyBinder) iBinder;
            AIDLService aidlService = binder.getService();
            aidlService.setOnLoginListener(new AIDLService.onLoginListener() {
                @Override
                public void login(String username, String password) {
                    mtextView.setText("Message from client: userName:" + username +"passWord:"+ password);
                    Toast.makeText(MainActivity.this,"login successful",Toast.LENGTH_SHORT).show();
                }
            });


        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };



    //连接服务和实现AIDL接口
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtextView = findViewById(R.id.text);
        Intent intent = new Intent(this, AIDLService.class);
        bindService(intent, mAIDILConnetion, Context.BIND_AUTO_CREATE);

    }

    private Handler mHandler = new Handler();


    //而AIDL接口的实现需要注意，虽然这里实现在了MainActivity中，但是真正调用这段代码的场景未必在主线程中，
    // 因此，如果代码中存在UI操作的话，需要用一个主线程的handler来实现相关UI操作
//    @Override
//    public void login(final String username, final String password){
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                mtextView.setText("Message from client: userName:" + username +"\npassWord:"+ password);
//                Toast.makeText(MainActivity.this,"login successful",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


}
