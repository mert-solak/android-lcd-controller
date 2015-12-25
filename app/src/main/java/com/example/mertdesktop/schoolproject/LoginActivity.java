package com.example.mertdesktop.schoolproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    StringBuilder stringBuilder;
    FileOutputStream fos;
    FileInputStream fis;
    Button buttonLogin, buttonSignin;
    EditText editTextUserName, editTextPassword;
    String username, password;
    String writingString;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        VariableClass.InitializeAccountArray();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}

/*
WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String macAddress = wInfo.getMacAddress();
    public void readMyAccount(){
        try {
            FileInputStream fileInputStream = openFileInput("myAccount.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lines;
            int i = 0;
            while ((lines = bufferedReader.readLine()) != null) {
                VariableClass.myAccount[i] = lines;
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void writeMyAccount(String strName,String strPass){
        stringBuilder = new StringBuilder();
        stringBuilder.append(strName);
        stringBuilder.append("\n");
        stringBuilder.append(strPass);
        writingString = stringBuilder.toString();
        try {
            FileOutputStream fileOutputStream = openFileOutput("myAccount.txt", MODE_PRIVATE);
            fileOutputStream.write(writingString.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void writeToFtpServer(){
        new Thread(){
            public void run(){
                try {
                    LoginActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            writeAccountsToLocal(username + password);
                        }
                    });
                    FTPConnectionService.ftpConnection.enterLocalPassiveMode();
                    String filename = "My_Book/found.000/Accounts.txt";
                    fis = new FileInputStream("/data/data/com.example.mertdesktop.schoolproject/files/localAccounts.txt");
                    FTPConnectionService.ftpConnection.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
                    FTPConnectionService.ftpConnection.setFileTransferMode(FTP.BINARY_FILE_TYPE);
                    FTPConnectionService.ftpConnection.storeFile(filename, fis);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (fis != null) {
                            fis.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    public void readAccountsFromLocal(){
            try {
                FileInputStream fileInputStream = openFileInput("localAccounts.txt");
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String lines;
                int i = 0;
                while ((lines = bufferedReader.readLine()) != null) {
                    VariableClass.accountsArray[i] = lines;
                    i++;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    public void writeAccountsToLocal(String str){
        stringBuilder = new StringBuilder();
        for (int i = 0; i < VariableClass.accountsArray.length; i++) {
            if(!VariableClass.accountsArray[i].isEmpty()) {
                stringBuilder.append(VariableClass.accountsArray[i]);
                stringBuilder.append("\n");
            }
        }
        stringBuilder.append(str);
        writingString = stringBuilder.toString();
        try {
            FileOutputStream fileOutputStream = openFileOutput("localAccounts.txt", MODE_PRIVATE);
            fileOutputStream.write(writingString.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void readAndCheckFromFtpServer(){
        new Thread()
        {
            public void run()
            {
                try {

                    FTPConnectionService.ftpConnection.enterLocalPassiveMode();
                    String filename = "My_Book/found.000/Accounts.txt";
                    fos = new FileOutputStream("/data/data/com.example.mertdesktop.schoolproject/files/localAccounts.txt");
                    FTPConnectionService.ftpConnection.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
                    FTPConnectionService.ftpConnection.setFileTransferMode(FTP.BINARY_FILE_TYPE);
                    FTPConnectionService.ftpConnection.retrieveFile(filename, fos);
                    System.out.println("log out");
                } catch (IOException e) {
                    e.printStackTrace();
                    createNewAccountsFileOnLocal();
                } finally {
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                LoginActivity.this.runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        readAccountsFromLocal();
                        if(str.equals("login")){
                            if (Arrays.asList(VariableClass.accountsArray).contains(username + password)) {
                                writeMyAccount(username, password);
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            } else {
                                Toast.makeText(LoginActivity.this, "Check your username and password again", Toast.LENGTH_SHORT).show();
                            }
                        }else if(str.equals("signin")){
                            if(Arrays.asList(VariableClass.accountsArray).contains(username + password)){
                                Toast.makeText(LoginActivity.this, "UserName and Password are used", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                writeMyAccount(username, password);
                                writeToFtpServer();
                            }
                        }
                    }
                });
            }
        }.start();
    }
    public void createNewAccountsFileOnLocal(){
        try {
            FileOutputStream fileOutputStream = openFileOutput("localAccounts.txt", MODE_PRIVATE);
            fileOutputStream.write("".getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/