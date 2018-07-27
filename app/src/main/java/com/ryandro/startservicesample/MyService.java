package com.ryandro.startservicesample;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyService extends Service {
    int Count = 0;
    private boolean isDestroyCalled = false;

  public class MySericeBinder extends Binder {

        public MyService getMyService() {
            return MyService.this;
        }
    }

    private IBinder iBinder = new MySericeBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("MyService : ", "OnBind Method is Called");
        return iBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("MyService : ", "OnUnBind Method is Called");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyService : ", "OnCreate Method is Called");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService : ", "OnStartCommand Method is Called");
        Log.d("Service Thread :", " " + Thread.currentThread().getId());
        startCount();
        return START_STICKY;
    }

    private void startCount() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("MyService : ", "OnStartCommand Method is Called");
                Log.d("Service Thread :", " " + Thread.currentThread().getId());
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (isDestroyCalled) {
                        break;
                    }
                    Log.d("MyService : ", "" + Count++);
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isDestroyCalled = true;
        Log.d("MyService : ", "OnDestroy Method is Called");

    }

    public int getTheCurrentData(){
         return Count;
    }
}
