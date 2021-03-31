package com.example.asynctaskexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    private static final String TAG = "1234";
    public static final String NUM = "send";
    private ProgressBar progressBar;
    private TextView textView;
    private MyService myService;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if(intent.getExtras()!=null)
            {
                Log.d(TAG, "onReceive: "+intent.getStringExtra("title"));
                progressBar.setVisibility(View.GONE);
                textView.setText(intent.getStringExtra("title"));
            } // if closed
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBarMainActivity);
        textView = findViewById(R.id.texViewMainActivity);
    } // onCreate closed
    public void onClickStart(View view)
    {

        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo jobInfo = new JobInfo.Builder(1,new ComponentName(MainActivity.this,MyService.class))
                .setMinimumLatency(0)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(15*60*1000)
                .build();
            jobScheduler.schedule(jobInfo);
        progressBar.setVisibility(View.VISIBLE);
        Toast.makeText(MainActivity.this, "Job Scheduled", Toast.LENGTH_SHORT).show();
    } // onClickStart closed
    public void onClickCanceled(View view)
    {
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(1);
        progressBar.setVisibility(View.GONE);
        Toast.makeText(MainActivity.this, "Job Canceled", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        registerReceiver(broadcastReceiver,new IntentFilter("Response"));
    }
} // MainActivity closed