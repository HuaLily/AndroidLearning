package com.huawenli.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

public class AIDLService extends Service {

    private onLoginListener onLoginListener;

    public void setOnLoginListener(AIDLService.onLoginListener onLoginListener) {
        this.onLoginListener = onLoginListener;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }


    public interface onLoginListener {
        void login(String username,String password);
    }

    //主要是通过实现Stub抽象类来扩展其中的login方法,同时与Service的IBinder进行绑定，用于下一步的服务链接
    class MyBinder extends IMyAidInterface.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void login(String username, String password,ICallback iCallback) throws RemoteException {
            if (onLoginListener != null) {
                onLoginListener.login(username, password);
                iCallback.server2client("Message from Server:\nuserName:"+username+"\npassWord:"+password);
            }
        }

        public AIDLService getService(){
            return AIDLService.this;
        }



    }
}
