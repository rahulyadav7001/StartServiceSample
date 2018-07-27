package com.ryandro.startservicesample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Intent serviceIntend;
    boolean isStart = true;
    private Button btn_Startservice, btn_stopService,btn_getData,btn_bindService,btn_unBindService;
    private TextView tv_fatchData;

    private MyService myService;
    private ServiceConnection serviceConnection;
    private boolean isServiceConnected = false;
    private final int SERVICE_FLAG = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_Startservice = findViewById(R.id.btn_Startservice);
        btn_stopService = findViewById(R.id.btn_stopService);
        tv_fatchData = findViewById(R.id.tv_fatchData);
        btn_getData = findViewById(R.id.btn_getData);
        btn_bindService = findViewById(R.id.btn_bindService);
        btn_unBindService = findViewById(R.id.btn_unBindService);

        serviceIntend = new Intent(MainActivity.this, MyService.class);

        Log.d(" Current Thread : ", "" + Thread.currentThread().getId());

        btn_Startservice.setOnClickListener(this);
        btn_stopService.setOnClickListener(this);
        btn_bindService.setOnClickListener(this);
        btn_unBindService.setOnClickListener(this);
        btn_getData.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Startservice:
                startService();
                break;
            case R.id.btn_stopService:
                StopService();
                break;
            case R.id.btn_bindService:
                startBinding();
                break;
            case R.id.btn_unBindService:
                unBindService();
                break;
            case R.id.btn_getData:
                setData();
                break;

        }

    }

    private void setData() {
        if (isServiceConnected) {
            tv_fatchData.setText(""+ myService.getTheCurrentData());
        } else {
            tv_fatchData.setText("Service Is not Binded");
        }
}

    private void unBindService() {
        unbindService(serviceConnection);
        isServiceConnected =false;
    }

    private void startBinding() {
        if (serviceConnection == null) {
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder iBinder) {
                    MyService.MySericeBinder mySericeBinder = (MyService.MySericeBinder) iBinder;
                    myService = mySericeBinder.getMyService();
                    isServiceConnected = true;
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    isServiceConnected = false;
                }
            };
        }
        bindService(serviceIntend, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void StopService() {
        stopService(serviceIntend);
    }

    private void startService() {
        startService(serviceIntend);
    }
}
