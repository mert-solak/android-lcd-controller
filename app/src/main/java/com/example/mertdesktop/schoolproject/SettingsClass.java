package com.example.mertdesktop.schoolproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SettingsClass extends AppCompatActivity {
    boolean flagDomain,flagLogin;
    static boolean listChangeFlag,flagStartService;
    CheckBox rememberPast,stopSch,stopDel;
    TextView textViewPast,textViewSchedule,textViewLocation ,textViewDefault,textViewDefaultResult,textViewTolerance,Tolerance,textViewFtpConnectionTitle,
                    textViewDomainTitle,textViewDomain,textViewUsernameTitle,textViewUsername,textViewPasswordTitle,textViewPassword,textViewFileNameTitle,
                    textViewFileName,textViewPathTitle,textViewPath;
    Button buttonChange,buttonChangeSecond,buttonDomain,buttonUsername,buttonPassword,buttonFileName,buttonPath;
    ProgressBar progressBarLocation,progressBarDomain,progressBarUsername,progressBarPassword;
    ImageView imageViewDomain,imageViewUsername,imageViewLocation,imageViewPassword;
    StringBuilder stringBuilder;
    String writingString;
    SeekBar seekBar;
    Intent intent;
    String locationProvider;
    Switch locationSwitch;
    int seekResult;

    private LocationManager locationManager;
    Criteria criteria;
    private LocationListener locationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ActionBar ab = getSupportActionBar();
        ab.setLogo(R.drawable.letter55);
        ab.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO);
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME);
        ab.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        ab.setDisplayUseLogoEnabled(true);
        Preperation();
        readSettings();
        InitializeLocation();
        permission();
        checkBoxPrep();
    }
    public void Preperation(){
        textViewPast=(TextView)findViewById(R.id.textView);
        textViewPast.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        textViewSchedule=(TextView)findViewById(R.id.textView2);
        textViewSchedule.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        textViewLocation=(TextView)findViewById(R.id.textView3);
        textViewLocation.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        textViewDefault = (TextView)findViewById(R.id.textView4);
        textViewDefaultResult = (TextView)findViewById(R.id.textView5);
        textViewTolerance = (TextView)findViewById(R.id.textView6);
        Tolerance = (TextView)findViewById(R.id.textView7);
        buttonChangeSecond = (Button)findViewById(R.id.button4);
        progressBarLocation = (ProgressBar)findViewById(R.id.progressBar);
        buttonChange = (Button)findViewById(R.id.button);
        locationSwitch = (Switch)findViewById(R.id.switch1);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        textViewFtpConnectionTitle = (TextView)findViewById(R.id.textView11);
        textViewFtpConnectionTitle.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        imageViewDomain = (ImageView)findViewById(R.id.imageView);
        progressBarDomain = (ProgressBar)findViewById(R.id.progressBar3);
        buttonDomain = (Button)findViewById(R.id.button5);
        imageViewUsername = (ImageView)findViewById(R.id.imageView4);
        buttonUsername = (Button)findViewById(R.id.button6);
        progressBarUsername = (ProgressBar)findViewById(R.id.progressBar4);
        imageViewPassword = (ImageView)findViewById(R.id.imageView5);
        buttonPassword = (Button)findViewById(R.id.button7);
        progressBarPassword = (ProgressBar)findViewById(R.id.progressBar5);
        imageViewLocation = (ImageView)findViewById(R.id.imageView3);
        textViewDomainTitle = (TextView)findViewById(R.id.textView12);
        textViewDomain = (TextView)findViewById(R.id.textView13);
        textViewUsernameTitle = (TextView)findViewById(R.id.textView14);
        textViewUsername = (TextView)findViewById(R.id.textView15);
        textViewPasswordTitle = (TextView)findViewById(R.id.textView16);
        textViewPassword = (TextView)findViewById(R.id.textView17);
        textViewFileNameTitle = (TextView)findViewById(R.id.textView18);
        textViewFileName = (TextView)findViewById(R.id.textView20);
        textViewPathTitle = (TextView)findViewById(R.id.textView19);
        textViewPath = (TextView)findViewById(R.id.textView21);
        buttonFileName = (Button)findViewById(R.id.button8);
        buttonPath = (Button)findViewById(R.id.button9);
    }
    public void InitializeLocation(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        locationProvider = locationManager.getBestProvider(criteria, true);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                progressBarLocation.setVisibility(View.INVISIBLE);
                imageViewLocation.setImageResource(R.drawable.symbol_check_icon);
                imageViewLocation.setVisibility(View.VISIBLE);
                VariableClass.settingArray[12] = "true";
                VariableClass.settingArray[3] = Double.valueOf(location.getLongitude()).toString();
                VariableClass.settingArray[4] = Double.valueOf(location.getLatitude()).toString();
                writeForSettings();
                locationManager.removeUpdates(locationListener);
                intent = new Intent(getApplicationContext(),LocationClass.class);
                startService(intent);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                if(locationSwitch.isChecked()) {
                    imageViewLocation.setVisibility(View.INVISIBLE);
                    progressBarLocation.setVisibility(View.VISIBLE);
                    locationManager.requestLocationUpdates(locationProvider, 1000, 0, locationListener);
                }if(!locationSwitch.isChecked()) {
                    locationManager.removeUpdates(locationListener);
                    progressBarLocation.setVisibility(View.INVISIBLE);
                    imageViewLocation.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(SettingsClass.this, "Turn On Your GPS service", Toast.LENGTH_SHORT).show();
                locationManager.removeUpdates(locationListener);
                locationSwitch.setChecked(false);
                progressBarLocation.setVisibility(View.INVISIBLE);
                imageViewLocation.setImageResource(R.drawable.close_icon);
                imageViewLocation.setVisibility(View.VISIBLE);
            }
        };
    }
    public void InitializeWidgets(){
        if(rememberPast.isChecked()) {
            VariableClass.settingArray[0] = "checkBox is checked";
        }
        else {
            VariableClass.settingArray[0] = "checkBox is not checked";
        }
        if(stopSch.isChecked()) {
            VariableClass.settingArray[1] = "checkBox is checked";
        }
        else {
            VariableClass.settingArray[1] = "checkBox is not checked";
            intent = new Intent(getApplicationContext(),NotificationClass.class);
            startService(intent);
            NotificationClass.startFlag=true;
        }
        if(stopDel.isChecked()) {
            VariableClass.settingArray[2] = "checkBox is checked";
        }
        else {
            VariableClass.settingArray[2] = "checkBox is not checked";
        }
        writeForSettings();
    }
    public void checkBoxPrep(){
        readSettings();
        if(VariableClass.settingArray[11].isEmpty()) {
            VariableClass.settingArray[11] = "0";
            writeForSettings();
        }
        seekResult = Integer.valueOf(VariableClass.settingArray[11]);
        seekBar.setProgress(seekResult);
        if (!VariableClass.settingArray[14].isEmpty() && !VariableClass.settingArray[14].equals(textViewDomain.getText().toString())) {
            StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < VariableClass.settingArray[14].length(); i++) {
                    stringBuilder.append("*");
                }
                textViewDomain.setText(stringBuilder.toString());
            }
        if (!VariableClass.settingArray[15].isEmpty() && !VariableClass.settingArray[15].equals(textViewUsername.getText().toString()))
            textViewUsername.setText(VariableClass.settingArray[15]);
        if (!VariableClass.settingArray[16].isEmpty() && !VariableClass.settingArray[16].equals(textViewPassword.getText().toString()))
            textViewPassword.setText(VariableClass.settingArray[16]);
        if (VariableClass.settingArray[0].equals("checkBox is checked"))
            rememberPast.setChecked(true);
        if(VariableClass.settingArray[0].isEmpty())
            rememberPast.setChecked(true);
        if (VariableClass.settingArray[0].equals("checkBox is not checked"))
            rememberPast.setChecked(false);
        if (VariableClass.settingArray[1].equals("checkBox is checked"))
            stopSch.setChecked(true);
        if (VariableClass.settingArray[1].equals("checkBox is not checked"))
            stopSch.setChecked(false);
        if (VariableClass.settingArray[2].equals("checkBox is checked"))
            stopDel.setChecked(true);
        if (VariableClass.settingArray[2].equals("checkBox is not checked"))
            stopDel.setChecked(false);
        if(VariableClass.settingArray[6].equals("checked"))
            locationSwitch.setChecked(true);
        if(VariableClass.settingArray[6].equals("unchecked"))
            locationSwitch.setChecked(false);
        if(!VariableClass.settingArray[5].isEmpty())
            textViewDefaultResult.setText(VariableClass.settingArray[5]);
        else
            VariableClass.settingArray[5] = "Empty";
        if(!VariableClass.settingArray[8].isEmpty())
            Tolerance.setText(VariableClass.settingArray[8]);
        else
            VariableClass.settingArray[8] = "5000";
        if(VariableClass.settingArray[3].isEmpty())
            VariableClass.settingArray[3] = "0";
        if(VariableClass.settingArray[4].isEmpty())
            VariableClass.settingArray[4] = "0";
        if(VariableClass.settingArray[20].equals("true") && VariableClass.settingArray[6].equals("checked"))
            imageViewLocation.setImageResource(R.drawable.symbol_check_icon);
        else if(VariableClass.settingArray[20].equals("false") && VariableClass.settingArray[6].equals("checked"))
            imageViewLocation.setImageResource(R.drawable.symbol_check_icon);

        DomainChecking();
        writeForSettings();
    }

    private void DomainChecking() {
        progressBarDomain.setVisibility(View.VISIBLE);
        progressBarUsername.setVisibility(View.VISIBLE);
        progressBarPassword.setVisibility(View.VISIBLE);
        imageViewDomain.setVisibility(View.INVISIBLE);
        imageViewUsername.setVisibility(View.INVISIBLE);
        imageViewPassword.setVisibility(View.INVISIBLE);
        new Thread() {
            FTPClient connection = new FTPClient();
            @Override
            public void run() {
                try {
                    connection.connect(VariableClass.settingArray[14]);
                    if(FTPReply.isPositiveCompletion(connection.getReplyCode())){
                        flagDomain = true;
                        flagLogin= connection.login(VariableClass.settingArray[15], VariableClass.settingArray[16]);
                        System.out.println(flagLogin);
                        connection.enterLocalPassiveMode();
                        boolean logout = connection.logout();
                        System.out.println(logout);
                    }
                } catch (IOException e) {
                    flagDomain = false;
                    e.printStackTrace();
                }finally {
                    try {
                        connection.disconnect();
                        System.out.println("disconnected");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                SettingsClass.this.runOnUiThread(new Runnable() {
                    public void run() {
                        if (flagDomain == true) {
                            progressBarDomain.setVisibility(View.INVISIBLE);
                            imageViewDomain.setImageResource(R.drawable.symbol_check_icon);
                            imageViewDomain.setVisibility(View.VISIBLE);
                        }
                        if (flagDomain == false) {
                            progressBarDomain.setVisibility(View.INVISIBLE);
                            imageViewDomain.setImageResource(R.drawable.close_icon);
                            imageViewDomain.setVisibility(View.VISIBLE);
                        }
                        if (flagLogin == true) {
                            progressBarUsername.setVisibility(View.INVISIBLE);
                            progressBarPassword.setVisibility(View.INVISIBLE);
                            imageViewUsername.setImageResource(R.drawable.symbol_check_icon);
                            imageViewPassword.setImageResource(R.drawable.symbol_check_icon);
                            imageViewUsername.setVisibility(View.VISIBLE);
                            imageViewPassword.setVisibility(View.VISIBLE);
                        }
                        if (flagLogin == false) {
                            progressBarUsername.setVisibility(View.INVISIBLE);
                            progressBarPassword.setVisibility(View.INVISIBLE);
                            imageViewUsername.setImageResource(R.drawable.close_icon);
                            imageViewPassword.setImageResource(R.drawable.close_icon);
                            imageViewUsername.setVisibility(View.VISIBLE);
                            imageViewPassword.setVisibility(View.VISIBLE);
                        }
                        if(flagLogin == true && flagDomain == true)
                            VariableClass.settingArray[19] = "true";
                        else
                            VariableClass.settingArray[19] = "false";
                        writeForSettings();
                    }
                });
            }
        }.start();
    }

    public void onClicklistener() {
        rememberPast = (CheckBox) findViewById(R.id.checkBox);
        stopSch=(CheckBox)findViewById(R.id.checkBox2);
        stopDel=(CheckBox)findViewById(R.id.checkBox3);
        int id = Resources.getSystem().getIdentifier("btn_check_holo_light", "drawable", "android");
        stopDel.setButtonDrawable(id);
        stopSch.setButtonDrawable(id);
        rememberPast.setButtonDrawable(id);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekResult = seekBar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                readSettings();
                VariableClass.settingArray[11] = Integer.valueOf(seekResult).toString();
                writeForSettings();
                if (VariableClass.settingArray[12].equals("true")) {
                    intent = new Intent(getApplicationContext(), LocationClass.class);
                    startService(intent);
                }
            }
        });
        buttonFileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), ListChangePopUp.class);
                intent.putExtra("ButtonName", "filename");
                startActivity(intent);
            }
        });
        stopSch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitializeWidgets();
            }
        });
        rememberPast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitializeWidgets();
            }
        });
        stopDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitializeWidgets();
            }
        });
        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext() , ListChangePopUp.class);
                intent.putExtra("ButtonName", "message");
                startActivity(intent);
            }
        });
        buttonPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext() , FTPPathFinder.class);
                intent.putExtra("url", VariableClass.settingArray[14]);
                startActivity(intent);
            }
        });
       locationSwitch.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (locationSwitch.isChecked()) {
                   if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                       progressBarLocation.setVisibility(View.VISIBLE);
                       imageViewLocation.setVisibility(View.INVISIBLE);
                       VariableClass.settingArray[6] = "checked";
                       writeForSettings();
                       locationManager.requestLocationUpdates(locationProvider, 1000, 0, locationListener);
                       System.out.println("locationstarted");
                   } else {
                       locationSwitch.setChecked(false);
                       intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                       startActivity(intent);
                       Toast.makeText(SettingsClass.this, "GPS should be open", Toast.LENGTH_LONG).show();
                   }
               }
               if (!locationSwitch.isChecked()) {
                   locationManager.removeUpdates(locationListener);
                   VariableClass.settingArray[12] = "false";
                   VariableClass.settingArray[6] = "unchecked";
                   VariableClass.settingArray[9] = "false";
                   writeForSettings();
                   progressBarLocation.setVisibility(View.INVISIBLE);
                   imageViewLocation.setVisibility(View.INVISIBLE);
               }
           }
       });
        buttonChangeSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), ListChangePopUp.class);
                intent.putExtra("ButtonName", "tolerance");
                startActivity(intent);
            }
        });
        textViewDomain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (listChangeFlag) {
                    progressBarDomain.setVisibility(View.VISIBLE);
                    progressBarUsername.setVisibility(View.VISIBLE);
                    progressBarPassword.setVisibility(View.VISIBLE);
                    imageViewDomain.setVisibility(View.INVISIBLE);
                    imageViewUsername.setVisibility(View.INVISIBLE);
                    imageViewPassword.setVisibility(View.INVISIBLE);
                    new Thread() {
                        FTPClient connection = new FTPClient();
                        @Override
                        public void run() {
                            try {
                                connection.connect(VariableClass.settingArray[14]);
                                if(FTPReply.isPositiveCompletion(connection.getReplyCode())){
                                    flagDomain = true;
                                    flagLogin= connection.login(VariableClass.settingArray[15], VariableClass.settingArray[16]);
                                    System.out.println(flagLogin);
                                    connection.enterLocalPassiveMode();
                                    boolean logout = connection.logout();
                                    System.out.println(logout);
                                }
                            } catch (IOException e) {
                                flagDomain = false;
                                e.printStackTrace();
                            }finally {
                                try {
                                    connection.disconnect();
                                    System.out.println("disconnected");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            SettingsClass.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    if (flagDomain == true) {
                                        progressBarDomain.setVisibility(View.INVISIBLE);
                                        imageViewDomain.setImageResource(R.drawable.symbol_check_icon);
                                        imageViewDomain.setVisibility(View.VISIBLE);
                                    }
                                    if (flagDomain == false) {
                                        progressBarDomain.setVisibility(View.INVISIBLE);
                                        imageViewDomain.setImageResource(R.drawable.close_icon);
                                        imageViewDomain.setVisibility(View.VISIBLE);
                                    }
                                    if (flagLogin == true) {
                                        progressBarUsername.setVisibility(View.INVISIBLE);
                                        progressBarPassword.setVisibility(View.INVISIBLE);
                                        imageViewUsername.setImageResource(R.drawable.symbol_check_icon);
                                        imageViewPassword.setImageResource(R.drawable.symbol_check_icon);
                                        imageViewUsername.setVisibility(View.VISIBLE);
                                        imageViewPassword.setVisibility(View.VISIBLE);
                                    }
                                    if (flagLogin == false) {
                                        progressBarUsername.setVisibility(View.INVISIBLE);
                                        progressBarPassword.setVisibility(View.INVISIBLE);
                                        imageViewUsername.setImageResource(R.drawable.close_icon);
                                        imageViewPassword.setImageResource(R.drawable.close_icon);
                                        imageViewUsername.setVisibility(View.VISIBLE);
                                        imageViewPassword.setVisibility(View.VISIBLE);
                                    }
                                    if(flagLogin == true && flagDomain == true)
                                        VariableClass.settingArray[19] = "true";
                                    else
                                        VariableClass.settingArray[19] = "false";
                                    writeForSettings();
                                }
                            });
                        }
                    }.start();
                }
            }
        });
        textViewUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (listChangeFlag) {
                    imageViewUsername.setVisibility(View.INVISIBLE);
                    imageViewPassword.setVisibility(View.INVISIBLE);
                    progressBarUsername.setVisibility(View.VISIBLE);
                    progressBarPassword.setVisibility(View.VISIBLE);
                    new Thread() {
                        FTPClient connection = new FTPClient();
                        @Override
                        public void run() {
                            try {
                                connection.connect(VariableClass.settingArray[14]);
                                if(FTPReply.isPositiveCompletion(connection.getReplyCode())){
                                    flagDomain = true;
                                    flagLogin= connection.login(VariableClass.settingArray[15], VariableClass.settingArray[16]);
                                    System.out.println(flagLogin);
                                    connection.enterLocalPassiveMode();
                                    boolean logout = connection.logout();
                                    System.out.println(logout);
                                }
                            } catch (IOException e) {
                                flagDomain = false;
                                e.printStackTrace();
                            }finally {
                                try {
                                    connection.disconnect();
                                    System.out.println("disconnected");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            SettingsClass.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    if (flagDomain == true) {
                                        imageViewDomain.setImageResource(R.drawable.symbol_check_icon);
                                    }
                                    if (flagDomain == false) {
                                        imageViewDomain.setImageResource(R.drawable.close_icon);
                                    }
                                    if (flagLogin == true) {
                                        progressBarUsername.setVisibility(View.INVISIBLE);
                                        progressBarPassword.setVisibility(View.INVISIBLE);
                                        imageViewUsername.setImageResource(R.drawable.symbol_check_icon);
                                        imageViewPassword.setImageResource(R.drawable.symbol_check_icon);
                                        imageViewUsername.setVisibility(View.VISIBLE);
                                        imageViewPassword.setVisibility(View.VISIBLE);
                                    }
                                    if (flagLogin == false) {
                                        progressBarUsername.setVisibility(View.INVISIBLE);
                                        progressBarPassword.setVisibility(View.INVISIBLE);
                                        imageViewUsername.setImageResource(R.drawable.close_icon);
                                        imageViewPassword.setImageResource(R.drawable.close_icon);
                                        imageViewUsername.setVisibility(View.VISIBLE);
                                        imageViewPassword.setVisibility(View.VISIBLE);
                                    }
                                    if(flagLogin == true && flagDomain == true)
                                        VariableClass.settingArray[19] = "true";
                                    else
                                        VariableClass.settingArray[19] = "false";
                                    writeForSettings();
                                }
                            });
                        }
                    }.start();
                }
            }
        });
        textViewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (listChangeFlag) {
                    imageViewUsername.setVisibility(View.INVISIBLE);
                    imageViewPassword.setVisibility(View.INVISIBLE);
                    progressBarUsername.setVisibility(View.VISIBLE);
                    progressBarPassword.setVisibility(View.VISIBLE);
                    new Thread() {
                        FTPClient connection = new FTPClient();
                        @Override
                        public void run() {
                            try {
                                connection.connect(VariableClass.settingArray[14]);
                                if(FTPReply.isPositiveCompletion(connection.getReplyCode())){
                                    flagDomain = true;
                                    flagLogin= connection.login(VariableClass.settingArray[15], VariableClass.settingArray[16]);
                                    System.out.println(flagLogin);
                                    connection.enterLocalPassiveMode();
                                    boolean logout = connection.logout();
                                    System.out.println(logout);
                                }
                            } catch (IOException e) {
                                flagDomain = false;
                                e.printStackTrace();
                            }finally {
                                try {
                                    connection.disconnect();
                                    System.out.println("disconnected");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            SettingsClass.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    if (flagDomain == true) {
                                        imageViewDomain.setImageResource(R.drawable.symbol_check_icon);
                                    }
                                    if (flagDomain == false) {
                                        imageViewDomain.setImageResource(R.drawable.close_icon);
                                    }
                                    if (flagLogin == true) {
                                        progressBarUsername.setVisibility(View.INVISIBLE);
                                        progressBarPassword.setVisibility(View.INVISIBLE);
                                        imageViewUsername.setImageResource(R.drawable.symbol_check_icon);
                                        imageViewPassword.setImageResource(R.drawable.symbol_check_icon);
                                        imageViewUsername.setVisibility(View.VISIBLE);
                                        imageViewPassword.setVisibility(View.VISIBLE);
                                    }
                                    if (flagLogin == false) {
                                        progressBarUsername.setVisibility(View.INVISIBLE);
                                        progressBarPassword.setVisibility(View.INVISIBLE);
                                        imageViewUsername.setImageResource(R.drawable.close_icon);
                                        imageViewPassword.setImageResource(R.drawable.close_icon);
                                        imageViewUsername.setVisibility(View.VISIBLE);
                                        imageViewPassword.setVisibility(View.VISIBLE);
                                    }
                                    if(flagLogin == true && flagDomain == true)
                                        VariableClass.settingArray[19] = "true";
                                    else
                                        VariableClass.settingArray[19] = "false";
                                    writeForSettings();
                                }
                            });
                        }
                    }.start();
                }
            }
        });
        buttonDomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), ListChangePopUp.class);
                intent.putExtra("ButtonName", "domain");
                startActivity(intent);
            }
        });
        buttonUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), ListChangePopUp.class);
                intent.putExtra("ButtonName", "username");
                startActivity(intent);
            }
        });
        buttonPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), ListChangePopUp.class);
                intent.putExtra("ButtonName", "password");
                startActivity(intent);
            }
        });
    }
    public void permission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET
                },10);
                return;
            }
            else
            {
                onClicklistener();
            }
        }
        else{
            onClicklistener();
        }
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    onClicklistener();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        onResumeChanges();
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
    private void onResumeChanges(){
        readSettings();
        if(!VariableClass.settingArray[5].isEmpty())
            textViewDefaultResult.setText(VariableClass.settingArray[5]);
        if (!VariableClass.settingArray[8].isEmpty())
            Tolerance.setText(VariableClass.settingArray[8]);
        if (!VariableClass.settingArray[14].isEmpty() && !VariableClass.settingArray[14].equals(textViewDomain.getText().toString())) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < VariableClass.settingArray[14].length(); i++) {
                stringBuilder.append("*");
            }
            textViewDomain.setText(stringBuilder.toString());
        }
        if (!VariableClass.settingArray[15].isEmpty() && !VariableClass.settingArray[15].equals(textViewUsername.getText().toString()))
            textViewUsername.setText(VariableClass.settingArray[15]);
        if (!VariableClass.settingArray[16].isEmpty() && !VariableClass.settingArray[16].equals(textViewPassword.getText().toString()))
            textViewPassword.setText(VariableClass.settingArray[16]);
        if (!VariableClass.settingArray[18].isEmpty() && !VariableClass.settingArray[18].equals(textViewFileName.getText().toString()))
            textViewFileName.setText(VariableClass.settingArray[18]);
        if (!VariableClass.settingArray[17].isEmpty())
            textViewPath.setText(VariableClass.settingArray[17]);
        listChangeFlag = false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    /*
    SETTİNG ARRAY
    [0] = rememberpast
    [1] = stop sch
    [2] = stop deletion
    [3] = longitute
    [4] = Latitute
    [5] = Location Message
    [6] = Location Swtich
    [7] = Öncelik Flagı
    [8] = Tolerance
    [9] = control Flag for Location tekrarı
    [10] = HashNumber,
    [11] = seekbar number;
    [12] = service start flag
    [13] = Hash flag;
    [14] = DomainName
    [15] = UserName
    [16] = Password
    [17] = path
    [18] = filename
    [19] = startConnectionFlag
    [20] = location control flag
     */
}
