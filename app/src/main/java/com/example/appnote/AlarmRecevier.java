package com.example.appnote;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import java.util.Date;


public class AlarmRecevier extends BroadcastReceiver {
    final String CHANNEL_ID="201";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("MyAction")){
            String tieude = intent.getStringExtra("tieude");
            String ghichu = intent.getStringExtra("ghichu");
            intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            int flag;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                flag = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE;
            }else{
                flag = PendingIntent.FLAG_UPDATE_CURRENT;
            }
            int code = 0;
            PendingIntent pIntent = PendingIntent.getActivity(context,code,intent,flag);
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
                NotificationChannel channel=new NotificationChannel(CHANNEL_ID,
                        "Channel 1",
                        NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(channel);
            }
            NotificationCompat.Builder builder = new NotificationCompat
                    .Builder(context,CHANNEL_ID)
                    .setContentTitle(tieude)
                    .setContentText(ghichu)
                    .setContentIntent(pIntent)
                    .setSmallIcon(R.drawable.ic_clock)
                    .setColor(Color.BLUE)
                    .setAutoCancel(true)
                    .setCategory(NotificationCompat.CATEGORY_ALARM);
            notificationManager.notify(getNotificationid(),builder.build());
        }
    }
    private int getNotificationid(){
        int time =(int)new Date().getTime();
        return time;
    }

}
