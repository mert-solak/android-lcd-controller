package com.example.mertdesktop.schoolproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ListChangePopUp extends Activity {
    public String writingString;
    private Button myButton;
    private TextView textView;
    private StringBuilder stringBuilder;
    private AlertDialog.Builder alertBuilder;
    private AlertDialog alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_layout);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .7), (int) (height * 0.2));
        Listeners();
    }
    public void Listeners(){
        textView = (TextView)findViewById(R.id.editText);
        myButton = (Button)findViewById(R.id.button2);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getStringExtra("ButtonName").equals("message")) {
                    VariableClass.settingArray[5] = textView.getText().toString();
                    writeForSettings();
                    finish();
                }
                else if(getIntent().getStringExtra("ButtonName").equals("tolerance")){
                    try{
                        int num = Integer.parseInt(textView.getText().toString());
                        if(num >= 100) {
                            VariableClass.settingArray[8] = textView.getText().toString();
                            writeForSettings();
                            finish();
                        }
                        else{
                            alertBuilder = new AlertDialog.Builder(ListChangePopUp.this);
                            alertBuilder.setMessage("The value which is smaller than 100m can cause inconsistency!!")
                                    .setCancelable(false)
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            textView.setText("");
                                            dialog.cancel();
                                        }
                                    }).setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    VariableClass.settingArray[8] = textView.getText().toString();
                                    writeForSettings();
                                    finish();
                                }
                            });
                            alert = alertBuilder.create();
                            alert.setTitle("Warning!!");
                            alert.show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(ListChangePopUp.this, "Has to be an integer", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(getIntent().getStringExtra("ButtonName").equals("domain")){
                    VariableClass.settingArray[14] = textView.getText().toString();
                    SettingsClass.listChangeFlag = true;
                    writeForSettings();
                    finish();
                }
                else if(getIntent().getStringExtra("ButtonName").equals("username")){
                    VariableClass.settingArray[15] = textView.getText().toString();
                    SettingsClass.listChangeFlag = true;
                    writeForSettings();
                    finish();
                }
                else if(getIntent().getStringExtra("ButtonName").equals("password")){
                    VariableClass.settingArray[16] = textView.getText().toString();
                    SettingsClass.listChangeFlag = true;
                    writeForSettings();
                    finish();
                }
                else if(getIntent().getStringExtra("ButtonName").equals("filename")) {
                    VariableClass.settingArray[18] = textView.getText().toString();
                    SettingsClass.listChangeFlag = true;
                    writeForSettings();
                    finish();
                }
            }
        });
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
