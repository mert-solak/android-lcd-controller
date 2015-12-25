package com.example.mertdesktop.schoolproject;


import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class LocationClass extends android.app.Service {
    private LocationManager locationManager;
    private LocationListener locationListener;
    Criteria criteria;
    int gpsTimer;
    String locationProvider;
    float distanceInMeters;
    Location loc1,loc2;
    Intent intent;
    StringBuilder stringBuilder;
    String writingString;
    @Override
    public void onCreate() {
        super.onCreate();
        VariableClass.InitializeAccountArray();
        InitializeLocation();
    }
    public void InitializeLocation(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                readSettings();
                if(VariableClass.settingArray[6].equals("checked")) {
                    loc1 = new Location("");
                    loc1 = location;
                    distanceInMeters = loc1.distanceTo(loc2);
                    Toast.makeText(LocationClass.this, Double.valueOf(distanceInMeters).toString() , Toast.LENGTH_SHORT).show();
                    Toast.makeText(LocationClass.this, VariableClass.settingArray[7] , Toast.LENGTH_SHORT).show();
                    if(VariableClass.settingArray[7].equals("false") || VariableClass.settingArray[7].isEmpty()) {
                        if (distanceInMeters > Integer.parseInt(VariableClass.settingArray[8]) && (VariableClass.settingArray[9].equals("false") || VariableClass.settingArray[9].isEmpty())) {
                            writeFtpMessage(VariableClass.settingArray[5]);
                            intent = new Intent(getApplicationContext(), SettingsClass.class);
                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                            Notification notificationForLower = new Notification.Builder(getApplicationContext())
                                    .setTicker(null)
                                    .setContentTitle("Your LCD Message")
                                    .setContentText(VariableClass.settingArray[5])
                                    .setSmallIcon(R.drawable.event_availablexh)
                                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.lcd))
                                    .setContentIntent(pendingIntent)
                                    .build();
                            notificationForLower.flags = android.app.Notification.FLAG_AUTO_CANCEL;
                            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            VariableClass.settingArray[9]="true";
                            writeForSettings();
                            startService(new Intent(getApplicationContext(), FTPService.class));
                            nm.notify(0, notificationForLower);

                        } else if(distanceInMeters <= Integer.parseInt(VariableClass.settingArray[8]) && VariableClass.settingArray[9].equals("true")){
                            writeFtpMessage("<empty>");
                            intent = new Intent(getApplicationContext(), SettingsClass.class);
                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                            Notification notificationForLower = new Notification.Builder(getApplicationContext())
                                    .setTicker(null)
                                    .setContentTitle("Your LCD Message")
                                    .setContentText("<empty>")
                                    .setSmallIcon(R.drawable.event_availablexh)
                                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.lcd))
                                    .setContentIntent(pendingIntent)
                                    .build();
                            notificationForLower.flags = android.app.Notification.FLAG_AUTO_CANCEL;
                            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            VariableClass.settingArray[9]="false";
                            writeForSettings();
                            startService(new Intent(getApplicationContext(), FTPService.class));
                            nm.notify(0, notificationForLower);
                        }
                    }
                }
                if(VariableClass.settingArray[6].equals("unchecked")){
                    locationManager.removeUpdates(locationListener);
                    VariableClass.settingArray[20] = "false";
                    writeForSettings();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }
    public void setGPsTimer(String str){
        int number;
        if(!str.equals("100"))
            number = Integer.parseInt(str);
        else
            number = 99;
        number = 100 - number;
        gpsTimer = (number * 3000)+5000;
        Toast.makeText(LocationClass.this, Integer.valueOf(gpsTimer/1000).toString(), Toast.LENGTH_SHORT).show();
    }
    public void criteria() {
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        locationProvider = locationManager.getBestProvider(criteria, false);
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
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        readSettings();
        if(VariableClass.settingArray[6].equals("checked")) {
            locationManager.removeUpdates(locationListener);
            setGPsTimer(VariableClass.settingArray[11]);
            criteria();
            loc2 = new Location("");
            loc2.setLatitude(Double.parseDouble(VariableClass.settingArray[4]));
            loc2.setLongitude(Double.parseDouble(VariableClass.settingArray[3]));
            locationManager.requestLocationUpdates(locationProvider, gpsTimer, 0, locationListener);
            VariableClass.settingArray[20] = "true";
            writeForSettings();
        }
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
