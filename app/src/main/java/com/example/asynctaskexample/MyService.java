package com.example.asynctaskexample;

import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import static android.app.Activity.*;

public class MyService extends Service
{
    public static final String TAG = "1234";
    public static final String Notification = "com.example.asynctaskexample";

    public Binder binder = new ServiceBinder();

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.d(TAG, "onBind: ");
        return binder;
    }


    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public boolean onUnbind(Intent intent)
    {

        Log.d(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    public class ServiceBinder extends Binder
    {
        MyService getService()
        {
            return MyService.this;
        }
    }
            public String helloWorld()
            {
                return "Hellow World Again ";
            }

} // MyService closed