package com.example.mertdesktop.schoolproject;

import android.app.*;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import java.util.Calendar;


public class NotificationClass extends Service {
    public static int hour, min,hourSec,minSec;
    PendingIntent pendingIntent;
    Calendar constCal=Calendar.getInstance();
    public static String myString="";
    VariableClass varObj;
    public static boolean startFlag=false;
    StringBuilder stringBuilder;
    public String writingString;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void readString() {
        try {
            FileInputStream fileInputStream = openFileInput("MondayString.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lines;
            int i = 0;
            while ((lines = bufferedReader.readLine()) != null) {
                varObj.tabMondayText[i] = lines;
                i++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileInputStream fileInputStream = openFileInput("TuesdayString.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lines;
            int i = 0;
            while ((lines = bufferedReader.readLine()) != null) {
                varObj.tabTuesdayText[i] = lines;
                i++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileInputStream fileInputStream = openFileInput("WednesdayString.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lines;
            int i = 0;
            while ((lines = bufferedReader.readLine()) != null) {
                varObj.tabWednesdayText[i] = lines;
                i++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileInputStream fileInputStream = openFileInput("ThursdayString.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lines;
            int i = 0;
            while ((lines = bufferedReader.readLine()) != null) {
                varObj.tabThursdayText[i] = lines;
                i++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileInputStream fileInputStream = openFileInput("FridayString.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lines;
            int i = 0;
            while ((lines = bufferedReader.readLine()) != null) {
                varObj.tabFridayText[i] = lines;
                i++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileInputStream fileInputStream = openFileInput("SaturdayString.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lines;
            int i = 0;
            while ((lines = bufferedReader.readLine()) != null) {
                varObj.tabSaturdayText[i] = lines;
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileInputStream fileInputStream = openFileInput("SundayString.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String lines;
            int i = 0;
            while ((lines = bufferedReader.readLine()) != null) {
                varObj.tabSundayText[i] = lines;
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        readMessage();
        readSettings();
        if(VariableClass.settingArray[1].equals("checkBox is not checked")) {
            Calendar cur_cal = Calendar.getInstance();
            cur_cal.setTimeInMillis(System.currentTimeMillis());
            if (startFlag == false) {
                Intent intention = new Intent(this, MainActivity.class);
                pendingIntent = PendingIntent.getActivity(this, 0, intention, 0);
                Notification notificationForUpper = new Notification.Builder(this)
                        .setTicker(null)
                        .setContentTitle("Your LCD Message")
                        .setContentText(myString)
                        .setSmallIcon(R.drawable.event_availablexh)
                        .setColor(ContextCompat.getColor(getApplicationContext(), R.color.btn_bg))
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.lcd))
                        .setContentIntent(pendingIntent)
                        .build();
                notificationForUpper.flags = android.app.Notification.FLAG_AUTO_CANCEL;

                Notification notificationForLower = new Notification.Builder(this)
                        .setTicker(null)
                        .setContentTitle("Your LCD Message")
                        .setContentText(myString)
                        .setSmallIcon(R.drawable.event_availablexh)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.lcd))
                        .setContentIntent(pendingIntent)
                        .build();
                notificationForLower.flags = android.app.Notification.FLAG_AUTO_CANCEL;
                VariableClass.settingArray[7] = "true";
                writeFtpMessage(myString);
                writeForSettings();
                startService(new Intent(getApplicationContext(), FTPService.class));
                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT < 21) {
                    nm.notify(0, notificationForLower);
                } else {
                    nm.notify(0, notificationForUpper);
                }
                calculateFinish(cur_cal);
            /*

        Gönderme baglantı işlemi


             */
            }
            startFlag = false;
            AlarmSetter(cur_cal);
        }
        return START_NOT_STICKY;
    }
    public void readMessage() {
        try {
            FileInputStream fileInputStream = openFileInput("Message.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lines;
            while ((lines = bufferedReader.readLine()) != null) {
                myString = lines;
            }
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
    public void writeMessage() {
        try {
            FileOutputStream fileOutputStream = openFileOutput("Message.txt", MODE_PRIVATE);
            fileOutputStream.write(myString.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean checkEmptyness(){
        int counter=0;
        for(int k = 0; k<48 ;k++) {
            if (!varObj.tabMondayText[k].isEmpty()) {
                counter++;
            }
            if (!varObj.tabTuesdayText[k].isEmpty()) {
                counter++;
            }
            if (!varObj.tabWednesdayText[k].isEmpty()) {
                counter++;
            }
            if (!varObj.tabThursdayText[k].isEmpty()) {
                counter++;
            }
            if (!varObj.tabFridayText[k].isEmpty()) {
                counter++;
            }
            if (!varObj.tabSaturdayText[k].isEmpty()) {
                counter++;
            }
            if (!varObj.tabSundayText[k].isEmpty()) {
                counter++;
            }
        }
        if(counter>0)
            return true;
        else
            return false;
    }
    public void AlarmSetter(Calendar cal) {
        varObj = new VariableClass();
        readString();
        if(!checkEmptyness())
            return;
        if (cal.get(Calendar.DAY_OF_WEEK) == 2) {
                for (int i = 0; i < varObj.tabMondayText.length; i++) {
                    if (!varObj.tabMondayText[i].isEmpty()) {
                        calculateTime(i);
                        if (((hour * 60) + min) > (((cal.get(Calendar.HOUR_OF_DAY)) * 60) + (cal.get(Calendar.MINUTE)))) {
                            myString = varObj.tabMondayText[i];
                            AlarmManager(cal);
                            break;
                        }
                    }
                    if (i == 47 && varObj.tabMondayText[i].isEmpty() == true) {
                        cal.add(Calendar.DATE, 1);
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE,0);
                        constCal=cal;
                        AlarmSetter(cal);
                    }
                }
        }else if (cal.get(Calendar.DAY_OF_WEEK) == 3) {
                for (int i = 0; i < varObj.tabTuesdayText.length; i++) {
                    if (!varObj.tabTuesdayText[i].isEmpty()) {
                        calculateTime(i);
                        if (((hour * 60) + min) > (((cal.get(Calendar.HOUR_OF_DAY)) * 60) + (cal.get(Calendar.MINUTE)))) {
                            myString = varObj.tabTuesdayText[i];
                            AlarmManager(cal);
                            break;
                        }
                    }
                    if (i == 47 && varObj.tabTuesdayText[i].isEmpty() == true) {
                        cal.add(Calendar.DATE, 1);
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        constCal=cal;
                        AlarmSetter(cal);
                    }
                }
        } else if (cal.get(Calendar.DAY_OF_WEEK) == 4) {
                for (int i = 0; i < varObj.tabWednesdayText.length; i++) {
                    if (!varObj.tabWednesdayText[i].isEmpty()) {
                        calculateTime(i);
                        if (((hour * 60) + min) > (((cal.get(Calendar.HOUR_OF_DAY)) * 60) + (cal.get(Calendar.MINUTE)))) {
                            myString = varObj.tabWednesdayText[i];
                            AlarmManager(cal);
                            break;
                        }

                    }
                    if (i == 47 && varObj.tabWednesdayText[i].isEmpty() == true) {
                        cal.add(Calendar.DATE, 1);
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        constCal=cal;
                        AlarmSetter(cal);
                    }
                }
        } else if (cal.get(Calendar.DAY_OF_WEEK) == 5) {
                for (int i = 0; i < varObj.tabThursdayText.length; i++) {
                    if (!varObj.tabThursdayText[i].isEmpty()) {
                        calculateTime(i);
                        if (((hour * 60) + min) > (((cal.get(Calendar.HOUR_OF_DAY)) * 60) + (cal.get(Calendar.MINUTE)))) {
                            myString = varObj.tabThursdayText[i];
                            AlarmManager(cal);
                            break;
                        }
                    }
                    if (i == 47 && varObj.tabThursdayText[i].isEmpty() == true) {
                        cal.add(Calendar.DATE, 1);
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        constCal=cal;
                        AlarmSetter(cal);
                    }
                }
        } else if (cal.get(Calendar.DAY_OF_WEEK) == 6) {
                for (int i = 0; i < varObj.tabFridayText.length; i++) {
                    if (!varObj.tabFridayText[i].isEmpty()) {
                        calculateTime(i);
                        if (((hour * 60) + min) > (((cal.get(Calendar.HOUR_OF_DAY)) * 60) + (cal.get(Calendar.MINUTE)))) {
                            myString = varObj.tabFridayText[i];
                            AlarmManager(cal);
                            break;
                        }
                    }
                    if (i == 47 && varObj.tabFridayText[i].isEmpty() == true) {
                        cal.add(Calendar.DATE, 1);
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        constCal=cal;
                        AlarmSetter(cal);
                    }
                }
        } else if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
                for (int i = 0; i < varObj.tabSaturdayText.length; i++) {
                    if (!varObj.tabSaturdayText[i].isEmpty()) {
                        calculateTime(i);
                        if (((hour * 60) + min) > (((cal.get(Calendar.HOUR_OF_DAY)) * 60) + (cal.get(Calendar.MINUTE)))) {
                            myString = varObj.tabSaturdayText[i];
                            AlarmManager(cal);
                            break;
                        }
                    }
                    if (i == 47 && varObj.tabSaturdayText[i].isEmpty() == true) {
                        cal.add(Calendar.DATE, 1);
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        constCal=cal;
                        AlarmSetter(cal);
                    }
                }
        } else if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
                for (int i = 0; i < varObj.tabSundayText.length; i++) {
                    if (!varObj.tabSundayText[i].isEmpty()) {
                        calculateTime(i);
                        if (((hour * 60) + min) > (((cal.get(Calendar.HOUR_OF_DAY)) * 60) + (cal.get(Calendar.MINUTE)))) {
                            myString = varObj.tabSundayText[i];
                            AlarmManager(cal);
                            break;
                        }
                    }
                    if (i == 47 && varObj.tabSundayText[i].isEmpty() == true) {
                        cal.add(Calendar.DATE, 1);
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        constCal=cal;
                        AlarmSetter(cal);
                    }
                }
        }
    }
    public void AlarmManager(Calendar time) {

        Intent myIntent = new Intent(this , NotificationClass.class);
        AlarmManager alarmmanager = (AlarmManager) getSystemService(ALARM_SERVICE);
        pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);

        time.set(Calendar.SECOND, 0);
        time.set(Calendar.MINUTE, min);
        time.set(Calendar.HOUR_OF_DAY, hour);
        alarmmanager.set(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), pendingIntent);
        writeMessage();


    }
    public void AlarmManagerSec(Calendar time) {
        readSettings();
        if(VariableClass.settingArray[2].equals("checkBox is not checked")) {
            Intent myIntent = new Intent(this, NotificationEmptyClass.class);
            AlarmManager alarmmanager = (AlarmManager) getSystemService(ALARM_SERVICE);

            pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);
            time.set(Calendar.SECOND, 0);
            time.set(Calendar.MINUTE, minSec);
            time.set(Calendar.HOUR_OF_DAY, hourSec);
            alarmmanager.set(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), pendingIntent);
        }
    }
    public void calculateFinish(Calendar cal){
        cal.add(Calendar.MINUTE, 29);
        hourSec=cal.get(Calendar.HOUR_OF_DAY);
        minSec=cal.get(Calendar.MINUTE);
        AlarmManagerSec(cal);
    }
    public static void calculateTime(int i) {
        if (i == 0) {
            hour = 0;
            min = 1;
        } else if (i == 1) {
            hour = 0;
            min = 30;
        } else if (i == 2) {
            hour = 1;
            min = 0;
        } else if (i == 3) {
            hour = 1;
            min = 30;
        } else if (i == 4) {
            hour = 2;
            min = 0;
        } else if (i == 5) {
            hour = 2;
            min = 30;
        } else if (i == 6) {
            hour = 3;
            min = 0;
        } else if (i == 7) {
            hour = 3;
            min = 30;
        } else if (i == 8) {
            hour = 4;
            min = 0;
        } else if (i == 9) {
            hour = 4;
            min = 30;
        } else if (i == 10) {
            hour = 5;
            min = 0;
        } else if (i == 11) {
            hour = 5;
            min = 30;
        } else if (i == 12) {
            hour = 6;
            min = 0;
        } else if (i == 13) {
            hour = 6;
            min = 30;
        } else if (i == 14) {
            hour = 7;
            min = 0;
        } else if (i == 15) {
            hour = 7;
            min = 30;
        } else if (i == 16) {
            hour = 8;
            min = 0;
        } else if (i == 17) {
            hour = 8;
            min = 30;
        } else if (i == 18) {
            hour = 9;
            min = 0;
        } else if (i == 19) {
            hour = 9;
            min = 30;
        } else if (i == 20) {
            hour = 10;
            min = 0;
        } else if (i == 21) {
            hour = 10;
            min = 30;
        } else if (i == 22) {
            hour = 11;
            min = 0;
        } else if (i == 23) {
            hour = 11;
            min = 30;
        } else if (i == 24) {
            hour = 12;
            min = 0;
        } else if (i == 25) {
            hour = 12;
            min = 30;
        } else if (i == 26) {
            hour = 13;
            min = 0;
        } else if (i == 27) {
            hour = 13;
            min = 30;
        } else if (i == 28) {
            hour = 14;
            min = 0;
        } else if (i == 29) {
            hour = 14;
            min = 30;
        } else if (i == 30) {
            hour = 15;
            min = 0;
        } else if (i == 31) {
            hour = 15;
            min = 30;
        } else if (i == 32) {
            hour = 16;
            min = 0;
        } else if (i == 33) {
            hour = 16;
            min = 30;
        } else if (i == 34) {
            hour = 17;
            min = 0;
        } else if (i == 35) {
            hour = 17;
            min = 30;
        } else if (i == 36) {
            hour = 18;
            min = 0;
        } else if (i == 37) {
            hour = 18;
            min = 30;
        } else if (i == 38) {
            hour = 19;
            min = 0;
        } else if (i == 39) {
            hour = 19;
            min = 30;
        } else if (i == 40) {
            hour = 20;
            min = 0;
        } else if (i == 41) {
            hour = 20;
            min = 30;
        } else if (i == 42) {
            hour = 21;
            min = 0;
        } else if (i == 43) {
            hour = 21;
            min = 30;
        } else if (i == 44) {
            hour = 22;
            min = 0;
        } else if (i == 45) {
            hour = 22;
            min = 30;
        } else if (i == 46) {
            hour = 23;
            min = 0;
        } else if (i == 47) {
            hour = 23;
            min = 30;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
