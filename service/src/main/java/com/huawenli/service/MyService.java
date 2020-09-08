package com.huawenli.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {

    private Binder mBinder = new myBinder();
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
      //  throw new UnsupportedOperationException("Not yet implemented");
        return mBinder;
    }

    //第一次创建调用
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("MyService","onCreate");
        //前台服务
        Intent intent = new Intent(MyService.this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        //在android8.0后 需要给notification设置一个channelid
        String CHANNEL_ONE_ID = "com.huawenli.service";
        String CHANNEL_ONE_NAME = "Channel One";
        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
        }


        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("this is content title")
                .setContentText("this is content text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pi)
                .setChannelId(CHANNEL_ONE_ID)
                .build();
        startForeground(1, notification);

    }

    //每次启动服务都会调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("MyService","onStartCommand");
        return super.onStartCommand(intent, flags, startId);

    }

    //销毁的时候调用
    @Override
    public void onDestroy() {
        Log.i("MyService","onDestroy");
        super.onDestroy();
    }


    class myBinder extends Binder{
        public void startBinder(){
            Log.i("myBinder","startBinder");
        }

        public int getProgress(){
            Log.i("myBinder","getProgress");
            return 0;
        }
    }
}
