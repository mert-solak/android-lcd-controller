package com.example.mertdesktop.schoolproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FTPPathFinder extends AppCompatActivity {
    String pth;
    FTPClient ftpClient = new FTPClient();

    String[] adapterArray;
    List<String> pathList = new ArrayList<String>();
    List<String> adapterList;
    Button buttonBack,buttonApply;
    ArrayAdapter adapter;
    FTPFile[] ftpDirectories;
    StringBuilder stringBuilder;
    int listPosition;
    ListView listView;
    NewsEntryAdapter newsEntryAdapter;
    boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_pop_up);
        openFTP();
        Initialization();
    }
    private void Initialization() {
        listView = (ListView)findViewById(R.id.listView2);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listPosition=position;
                pth =adapterList.get(position);
                if(!pathList.contains(pth))
                pathList.add(pth);
                OpenFolder();
            }
        });
        buttonBack = (Button)findViewById(R.id.button10);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pathList.size() > 0)
                    pathList.remove(pathList.size() - 1);
                CloseFolder();
            }
        });
        buttonApply = (Button)findViewById(R.id.button11);
        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringBuilder = new StringBuilder();
                for (int i = 0; i < pathList.size(); i++) {
                    stringBuilder.append(pathList.get(i));
                    stringBuilder.append("/");
                }
                VariableClass.settingArray[17] = stringBuilder.toString();
                writeSettings();
                finish();
            }
        });
    }
    private List<NewsEntry> getNewsEntries(FTPFile[] ftpFile) {

        // Let's setup some test data.
        // Normally this would come from some asynchronous fetch into a data source
        // such as a sqlite database, or an HTTP request

        final List<NewsEntry> entries = new ArrayList<NewsEntry>();

        for(int i = 0; i < ftpFile.length; i++) {
            entries.add(
                    new NewsEntry(
                            ftpFile[i].getName(),
                            Long.valueOf(ftpFile[i].getSize()).toString(),
                            ftpFile[i].getTimestamp().getTime(),
                            i % 2 == 0 ? R.drawable.folder_icon : R.drawable.folder_icon
                    )
            );
        }

        return entries;
    }
    private void OpenFolder(){
        adapterList = new ArrayList<String>();
        new Thread(){
            @Override
            public void run() {
                try {
                    ftpClient.changeWorkingDirectory(pth);
                    ftpDirectories = ftpClient.listDirectories();
                    adapterList = new ArrayList<String>();
                    for (int i = 0; i < ftpDirectories.length; i++) {
                        adapterList.add(ftpDirectories[i].getName());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FTPPathFinder.this.runOnUiThread(new Runnable() {
                    public void run() {
                        if(ftpDirectories != null) {
                            newsEntryAdapter = new NewsEntryAdapter(FTPPathFinder.this, R.layout.news_entry_list_item);
                            for(final NewsEntry entry : getNewsEntries(ftpDirectories)) {
                                newsEntryAdapter.add(entry);
                            }
                            listView.setAdapter(newsEntryAdapter);
                        }
                    }
                });
            }
        }.start();
    }
    public void writeSettings() {
        String writingString;
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
    private void CloseFolder(){
        adapterList = new ArrayList<String>();
        new Thread(){
            @Override
            public void run() {
                try {
                    ftpClient.changeToParentDirectory();
                    ftpDirectories = ftpClient.listDirectories();
                    adapterList = new ArrayList<String>();
                    for (int i = 0; i < ftpDirectories.length; i++) {
                        adapterList.add(ftpDirectories[i].getName());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FTPPathFinder.this.runOnUiThread(new Runnable() {
                    public void run() {
                        if(ftpDirectories != null) {
                            newsEntryAdapter = new NewsEntryAdapter(FTPPathFinder.this, R.layout.news_entry_list_item);
                            for (final NewsEntry entry : getNewsEntries(ftpDirectories)) {
                                newsEntryAdapter.add(entry);
                            }
                            listView.setAdapter(newsEntryAdapter);
                        }
                    }
                });
            }
        }.start();
    }

    private void openFTP(){
        adapterList = new ArrayList<String>();
        new Thread(){
            public void run(){
                try {
                    ftpClient.connect(VariableClass.settingArray[14]);
                    ftpClient.login(VariableClass.settingArray[15], VariableClass.settingArray[16]);
                    ftpClient.enterLocalPassiveMode();
                    ftpDirectories = ftpClient.listDirectories();
                    for (int i = 0; i < ftpDirectories.length; i++) {
                        adapterList.add(ftpDirectories[i].getName());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FTPPathFinder.this.runOnUiThread(new Runnable() {
                    public void run() {
                        if(ftpDirectories != null) {
                            newsEntryAdapter = new NewsEntryAdapter(FTPPathFinder.this, R.layout.news_entry_list_item);
                            for(final NewsEntry entry : getNewsEntries(ftpDirectories)) {
                                newsEntryAdapter.add(entry);
                            }
                            listView.setAdapter(newsEntryAdapter);
                        }
                    }
                });
            }
        }.start();
    }
    private void closeFtp(){
        new Thread(){
            @Override
            public void run() {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeFtp();
    }
    /*
    FTPFile[] ftpDirs = connection.listDirectories();
                    for (int i = 0; i < ftpDirs.length; i++) {
                        toppath = ftpDirs[1].getName();
                        System.out.println("CONNECT Directories in the ftp server are "
                                + ftpDirs[i].getName());
                    }
     */
}
