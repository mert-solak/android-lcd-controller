package com.example.mertdesktop.schoolproject;

import android.app.*;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class NotificationEmptyClass extends Service {
    public Intent intent;
    private StringBuilder stringBuilder;
    private String writingString;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
    public void readSettings() {
        try {
            FileInputStream fileInputStream = openFileInput("Setting.txt");
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
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        readSettings();
        if(VariableClass.settingArray[2].equals("checkBox is not checked")) {
            intent = new Intent();
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            Notification notificationForLower = new Notification.Builder(this)
                    .setTicker(null)
                    .setContentTitle("Your LCD Message")
                    .setContentText("<Empty>")
                    .setSmallIcon(R.drawable.event_availablexh)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.lcd))
                    .setContentIntent(pendingIntent)
                    .build();
            notificationForLower.flags = android.app.Notification.FLAG_AUTO_CANCEL;

            Notification notificationForUpper = new Notification.Builder(this)
                    .setTicker(null)
                    .setContentTitle("Your LCD Message")
                    .setContentText("<Empty>")
                    .setSmallIcon(R.drawable.event_availablexh)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.lcd))
                    .setColor(ContextCompat.getColor(getApplicationContext(), R.color.btn_bg))
                    .setContentIntent(pendingIntent)
                    .build();
            notificationForUpper.flags = android.app.Notification.FLAG_AUTO_CANCEL;
            VariableClass.settingArray[7] = "false";
            VariableClass.settingArray[9] = "false";
            writeFtpMessage("<empty>");
            writeForSettings();
            startService(new Intent(getApplicationContext(), FTPService.class));
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT < 21) {
                nm.notify(0, notificationForLower);
            } else {
                nm.notify(0, notificationForUpper);
            }
            stopSelf();
        /*

        Gönderme baglantı işlemi


         */
        }
        return START_STICKY;
    }
    public void writeFtpMessage(String str) {
        try {
            FileOutputStream fileOutputStream = openFileOutput("ftpMessage.txt", MODE_PRIVATE);
            fileOutputStream.write(str.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeForSettings() {
        stringBuilder = new StringBuilder();
        for (int i = 0; i < VariableClass.settingArray.length; i++) {
                stringBuilder.append(VariableClass.settingArray[i]);
                stringBuilder.append("\n");
        }
        writingString = stringBuilder.toString();
        try {
            FileOutputStream fileOutputStream = openFileOutput("Setting.txt", MODE_PRIVATE);
            fileOutputStream.write(writingString.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

