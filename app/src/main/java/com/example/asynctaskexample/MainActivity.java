package com.example.asynctaskexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Data;



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
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

public class MainActivity extends AppCompatActivity
{

    public static final String TAG = "1234";
    public static final String NUM = "send";
    private ProgressBar progressBar;
    private TextView textView;
    private Context context = MainActivity.this;
    private UUID uuid;

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
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        Data data = new Data.Builder()
                .putString("hello","Hello Worker")
                .build();
        WorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
                .setConstraints(constraints  )
                .setInputData(data)
                .build();

        uuid = workRequest.getId();
        WorkManager.getInstance(context).enqueue(workRequest);

        WorkManager.getInstance(context).getWorkInfoByIdLiveData(workRequest.getId()).observe(MainActivity.this, new Observer<WorkInfo>()
        {
            @Override
            public void onChanged(WorkInfo workInfo)
            {
                if(workInfo.getState() == WorkInfo.State.SUCCEEDED)
                {
                    textView.setText(workInfo.getOutputData().getString("time"));
                }
            }
        });

    } // onClickStart closed
    public void onClickCanceled(View view)
    {
        WorkManager workManager = WorkManager.getInstance(context);
        workManager.cancelWorkById(uuid);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }
} // MainActivity closed