package com.example.mertdesktop.schoolproject;

import android.app.*;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.HandlerThread;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class FTPService extends android.app.Service {
    Intent intent;
    StringBuilder stringBuilder;
    final class TheThread implements Runnable {
        int serviceId;
        TheThread(int serviceId) {
            this.serviceId = serviceId;
        }
        @Override
        public void run() {
                FTPClient ftpClient = new FTPClient();
                FileInputStream fis = null;
                try {
                    ftpClient.connect(VariableClass.settingArray[14]);
                    ftpClient.login(VariableClass.settingArray[15], VariableClass.settingArray[16]);
                    ftpClient.enterLocalPassiveMode();
                    String filename = VariableClass.settingArray[17]+VariableClass.settingArray[18]+".txt";
                    fis = new FileInputStream("/data/data/com.example.mertdesktop.schoolproject/files/ftpMessage.txt");
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
                    ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
                    ftpClient.storeFile(filename, fis);
                } catch (IOException e) {
                    e.printStackTrace();
                    notification();
                } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            stopSelf();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        readSettings();
        if(isNetworkAvailable()) {
            Thread thread = new Thread(new TheThread(startId));
            thread.start();
        }else{
            notification();
        }
        return START_NOT_STICKY;
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
    private void notification(){
        intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        Notification notificationForLower = new Notification.Builder(getApplicationContext())
                .setTicker(null)
                .setContentTitle("Connection Error!!")
                .setContentText("We can not access to the server, Make sure that you are connected to the internet")
                .setSmallIcon(R.drawable.ic_warning_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_network_check_black_24dp))
                .setContentIntent(pendingIntent)
                .build();
        notificationForLower.flags = android.app.Notification.FLAG_AUTO_CANCEL;
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(0, notificationForLower);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
