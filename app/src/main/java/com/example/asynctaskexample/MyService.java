package com.example.asynctaskexample;

import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static android.app.Activity.*;

public class MyService extends JobService
{



    public static final String TAG = "1234";
    public static final String URL = "https://jsonplaceholder.typicode.com/todos/1";
    RequestQueue requestQueue;

    @Override
    public void onCreate()
    {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(this);

    }

    private boolean jobCanceled = false;

    @Override
    public boolean onStartJob(JobParameters params)
    {
        Log.d(TAG, "onStartJob: ");
        doBackgroundWork(params);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params)
    {
        jobCanceled = true;
        return false;
    }

    private void doBackgroundWork(JobParameters parameters)
    {
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        if(response!=null)
                        {
                            Intent intent = new Intent("Response");
                            try
                            {
                                intent.putExtra("title",response.getString("title"));
                                sendBroadcast(intent);

                            } catch (JSONException e)
                            {
                                e.printStackTrace();
                            }

                        } // if closed

                    } // onResponse closed
                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.d(TAG, "onErrorResponse: "+error.getMessage());
                    }
                });
                requestQueue.add(jsonObjectRequest);
            } /// run closed
        }; // Runnable closed
        Thread thread = new Thread(runnable);
        thread.start();
    } // closed

} // MyService closed