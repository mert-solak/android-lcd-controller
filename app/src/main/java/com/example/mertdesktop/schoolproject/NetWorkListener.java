package com.example.mertdesktop.schoolproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class NetWorkListener extends BroadcastReceiver {
    Intent intent;
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            FileInputStream fileInputStream = context.openFileInput("Setting.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lines;
            int i = 0;
            while ((lines = bufferedReader.readLine()) != null) {
                VariableClass.settingArray[i] = lines;
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (activeNetInfo != null && activeNetInfo.isConnected() && VariableClass.settingArray[19].equals("true")) {

        } else {
            intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
            PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, intent, 0);
            Notification notificationForLower = new Notification.Builder(context.getApplicationContext())
                    .setTicker(null)
                    .setContentTitle("Connection Error!!")
                    .setContentText("We can not access to the server, Make sure that you are connected to the internet")
                    .setSmallIcon(R.drawable.ic_warning_black_24dp)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_network_check_black_24dp))
                    .setContentIntent(pendingIntent)
                    .build();
            notificationForLower.flags = android.app.Notification.FLAG_AUTO_CANCEL;
            NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            nm.notify(0, notificationForLower);
        }
    }
}

