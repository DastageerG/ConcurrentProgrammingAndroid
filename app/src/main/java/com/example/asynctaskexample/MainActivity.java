package com.example.asynctaskexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.icu.util.BuddhistCalendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    private static final String TAG = "1234";
    public static final String NUM = "send";
    private ProgressBar progressBar;
    private TextView textView;
    private MyService myService;
    private ServiceConnection serviceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            MyService.ServiceBinder serviceBinder = (MyService.ServiceBinder) service;
            myService = serviceBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            if(myService!=null)
            {
                myService = null;
            }
        } // onService Disconnected
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBarMainActivity);
        textView = findViewById(R.id.texViewMainActivity);
    } // onCreate closed


    @Override
    protected void onStart()
    {
        super.onStart();
        Intent intent = new Intent(MainActivity.this,MyService.class);
        bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        unbindService(serviceConnection);
        Log.d(TAG, "onStop: Unbinded");
    }

    public void onClick(View view)
    {
        textView.setText(myService.helloWorld());
    }
} // MainActivity closed