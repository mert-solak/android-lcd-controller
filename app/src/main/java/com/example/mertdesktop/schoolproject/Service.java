package com.example.mertdesktop.schoolproject;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Random;


public class Service extends android.app.Service {
    private int serviceCopyId;
    FTPClient client = new FTPClient();
    FTPClient client2 = new FTPClient();
    FileInputStream fis = null;
    FileOutputStream fos = null;
    PendingIntent pendingIntent;
    public final IBinder iBinder = new LocalBinder();
   public class LocalBinder extends Binder{
       Service getService(){
           return Service.this;
       }
   }
    final class TheThread implements Runnable{
        int serviceId;
        TheThread(int serviceId){
            this.serviceId = serviceId;
        }

        @Override
        public void run() {
            try {
                readSettings();
                client.connect("ftpmodemdisk.asuscomm.com");
                boolean x = client.login("se302", "guestforse302");
                System.out.println(x);
                client.enterLocalPassiveMode();
                String filename = "My_Book/found.000/0.txt";
                fos = new FileOutputStream("/data/data/com.example.mertdesktop.schoolproject/files/MessageForLCD.txt");
                client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
                client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
                client.retrieveFile(filename, fos);
                client.logout();
                System.out.println("log out");
            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                    client.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            stopSelf();
        }
    }
    public void createInitialTxt(){
        try {
            client.connect("ftpmodemdisk.asuscomm.com");
            boolean x = client.login("se302", "guestforse302");
            System.out.println(x);
            client.enterLocalPassiveMode();
            String filename = "My_Book/found.000/ftpMessage.txt";
            fis = new FileInputStream("/data/data/com.example.mertdesktop.schoolproject/files/ftpMessage.txt");
            client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
            client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            client.storeFile(filename, fis);
            client.logout();
            System.out.println("log out");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        stopSelf();
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.serviceCopyId = startId;
        return START_STICKY;
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
    public void sendOnTime() {
        Notification notification = new Notification.Builder(this)
                .setTicker("TickerTitle")
                .setContentTitle("Title")
                .setContentText("ContentText")
                .setSmallIcon(R.drawable.assigment_m)
                .setContentIntent(pendingIntent)
                .build();

        notification.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(0,notification);
        }
    public void thread (){
        Thread thread = new Thread(new TheThread(serviceCopyId));
        thread.start();
    }
    @Override
    public void onDestroy() {

        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }
}
