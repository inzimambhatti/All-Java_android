package com.haroon.randomtimer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class RemainderBraoadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"alarm")
                .setSmallIcon(R.drawable.ic_baseline_alarm_on_24)
                .setContentTitle("Alarm ringing ringing..")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat  notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(200,builder.build());
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();
        Log.i("alarm ring","alarm rinf");
    }
}
