package com.example.adminstrator.salesdiary;

/**
 * Created by Adminstrator on 6/14/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class splashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Thread timerThread=new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(3000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    Intent intent=new Intent(splashScreen.this,signUp.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }
    @Override
     protected void onPause()
        {
        super.onPause();
        finish();
        }
    }

