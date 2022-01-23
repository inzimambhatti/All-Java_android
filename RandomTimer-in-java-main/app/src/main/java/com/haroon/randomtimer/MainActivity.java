package com.haroon.randomtimer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    public static int min=900;
    public static int max=28800;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startAlarm();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        int i = sharedpreferences.getInt("id",0);
        if (i==0)
        {
            showHelpDialog();
            editor.putInt("id", 1);
            editor.apply();
        }


        ImageView btnHelp = findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            showHelpDialog();
            }
        });
        ImageView btnSettings = findViewById(R.id.btnSetting);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
            }
        });

        Button btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Alarm Play", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,RemainderBraoadcast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent,0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                long time = System.currentTimeMillis();
                long time1 = randBetween(min,max);
                Log.i("time", String.valueOf(time1));


                long seconds =  time1* 100;
                Log.i("seconds", String.valueOf(seconds));
                alarmManager.set(AlarmManager.RTC_WAKEUP,time+seconds,
                        pendingIntent);
            }
        });
    }
    private void showHelpDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog,null);
        builder.setView(view).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        builder.create();
        builder.show();

    }
    private void startAlarm()
    {        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

        CharSequence name = "Alarm Ringing";
        String description = "Your ALarm Now Ringing";
        int impo = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("alarm", name, impo);
        channel.setDescription(description);


        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
    }
    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}