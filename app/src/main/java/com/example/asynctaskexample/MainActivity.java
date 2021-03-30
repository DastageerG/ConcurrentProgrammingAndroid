package com.example.asynctaskexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.util.BuddhistCalendar;
import android.os.AsyncTask;
import android.os.Bundle;
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

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            Bundle bundle = intent.getExtras();
            if(bundle!=null)
            {
                int sum = bundle.getInt("SUM");
                int result = bundle.getInt("RESULT");

                if(result == RESULT_OK)
                {
                    textView.setText("Sum = "+sum);
                } // if closed
                else
                {
                    textView.setText("Process failed");
                }
            } // if closed
        } // onReceive closed
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBarMainActivity);
        textView = findViewById(R.id.texViewMainActivity);
    } // onCreate closed

    public void startTask(View view)
    {
        Intent intent = new Intent(getApplicationContext(),MyService.class);
        intent.putExtra(NUM,345);
        startService(intent);
    }

//    private class MyAsyncTask extends AsyncTask<Integer,Integer,String>
//    {
//        int count = 0;
//        @Override
//        protected void onPreExecute()
//        {
//            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
//
//        } // onPreExecute closed
//
//        @Override
//        protected void onProgressUpdate(Integer... values)
//        {
//            super.onProgressUpdate(values);
//            textView.setText("Running  ...  "+String.valueOf(values[0]));
//            progressBar.setProgress(values[0]);
//        }
//
//        @Override
//        protected String doInBackground(Integer... integers)
//        {
//            for(;count<=integers[0];count++)
//            {
//                try
//                {
//                    Thread.sleep(1000);
//                    publishProgress(count);
//                }catch (Exception e)
//                {
//                    Log.d(TAG, "doInBackground: "+e.getMessage());
//                }
//            }
//            return "Task Completed";
//        } // doInBackground closed
//
//        @Override
//        protected void onPostExecute(String s)
//        {
//            super.onPostExecute(s);
//            textView.setText(s);
//            progressBar.setVisibility(View.GONE);
//        }  // onPostExecute closed

//    } // MyAsyncTask closed


    @Override
    protected void onResume()
    {
        super.onResume();
        registerReceiver(broadcastReceiver,new IntentFilter(MyService.Notification));
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

    } // onStart closed
} // MainActivity closed