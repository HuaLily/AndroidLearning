package com.huawenli.aidl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    IMyAidInterface iMyAidInterface;
    private ServiceConnection mAIDILConnetion;
    private Button mLoginBtn;
    private EditText mAccountEdt;
    private EditText mPwdEdt;
    private TextView mShowtv;

    private String account;
    private String pwd;

    //连接Server端的Service和发送数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAccountEdt = findViewById(R.id.account);
        mPwdEdt = findViewById(R.id.password);
        mShowtv = findViewById(R.id.show);


        Intent intent = new Intent();
        intent.setAction("com.huawenli.aidlserver");
        intent.setPackage("com.huawenli.aidl");//setPackage设置的是Server APK的包名
        bindService(intent, new ConnectCallback(), Context.BIND_AUTO_CREATE);


        mLoginBtn = findViewById(R.id.login);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private ICallback iCallback = new ICallback.Stub(){
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void server2client(String data) throws RemoteException {
            mShowtv.setText(data);
        }
    };

    public void login() {
        if (iMyAidInterface != null){
            account = mAccountEdt.getText().toString();
            pwd = mPwdEdt.getText().toString();
            try {

                iMyAidInterface.login(account, pwd,iCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }


        }
    }

    class ConnectCallback implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iMyAidInterface = IMyAidInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            iMyAidInterface = null;

        }
    }
}
