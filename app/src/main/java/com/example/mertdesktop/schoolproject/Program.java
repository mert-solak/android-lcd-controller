package com.example.mertdesktop.schoolproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import org.apache.commons.net.ftp.FTPFile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Program extends AppCompatActivity {
    TabHost tabHost;
    String writingString;
    StringBuilder stringBuilder;
    AlertDialog.Builder a_builder;
    TabHost.TabSpec tabMonday,tabSunday,tabTuesday , tabWednesday , tabFriday,tabThursday,tabSaturday;
    ListView listMonday,listSunday,listTuesday , listWednesday , listFriday,listThursday,listSaturday;
    VariableClass varObj = new VariableClass();
    Intent intent;
    public Button tabMondayUpdate,tabMondayDelete,tabTuesdayUpdate,tabTuesdayDelete,tabWednesdayUpdate
            ,tabWednesdayDelete,tabThursdayUpdate,tabThursdayDelete,tabFridayUpdate,tabFridayDelete
            ,tabSaturdayUpdate,tabSaturdayDelete,tabSundayUpdate,tabSundayDelete;
    ArrayAdapter adapterMonday,adapterTuesday,adapterWednesday,adapterThursday,adapterFriday,adapterSaturday,adapterSunday;
    public EditText tabMondayText,tabTuesdayText,tabWednesdayText,tabThursdayText,tabFridayText,tabSaturdayText,
    tabSundayText;
    NewsEntryAdapterForProgram newsEntryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);
        ActionBar ab = getSupportActionBar();
        ab.setLogo(R.drawable.letter55);
        ab.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO);
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME);
        ab.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        ab.setDisplayUseLogoEnabled(true);
        setupTabHost();
        setupListViews();
        ButtonListeners();
        varObj.tagsInitialize();

        setupTextViews();
    }
    public void setupTextViews(){
        tabMondayText = (EditText)findViewById(R.id.tab_monday_edittext);
        tabTuesdayText=(EditText)findViewById(R.id.tab_tuesday_edittext);
        tabWednesdayText=(EditText)findViewById(R.id.tab_wednesday_edittext);
        tabThursdayText=(EditText)findViewById(R.id.tab_thursday_edittext);
        tabFridayText=(EditText)findViewById(R.id.tab_friday_edittext);
        tabSaturdayText=(EditText)findViewById(R.id.tab_saturday_edittext);
        tabSundayText=(EditText)findViewById(R.id.tab_sunday_edittext);
    }

    private void setupListViews(){
        prepareListView();
        listMonday = (ListView) findViewById(R.id.tab_monday_listview);
        listMonday.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabMondayText.length; i++) {
                    if (i == position) {
                        varObj.tabMondayText[i] = "";
                        writeStrings();
                    }
                }
                newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                for (final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup, varObj.tabMondayText)) {
                    newsEntryAdapter.add(entry);
                }
                listMonday.setAdapter(newsEntryAdapter);

                return true;
            }
        });
        listMonday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabMondayTags.length; i++) {
                    if (i == position) {
                        varObj.tabMondayTags[i] = true;
                    }
                }
            }
        });
        newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
        for(final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup,varObj.tabMondayText)) {
            newsEntryAdapter.add(entry);
        }
        listMonday.setAdapter(newsEntryAdapter);

        listTuesday = (ListView) findViewById(R.id.tab_tuesday_listview);
        listTuesday.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabTuesdayText.length; i++) {
                    if (i == position) {
                        varObj.tabTuesdayText[i] = "";
                        writeStrings();
                    }
                }

                newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                for (final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup, varObj.tabTuesdayText)) {
                    newsEntryAdapter.add(entry);
                }
                listTuesday.setAdapter(newsEntryAdapter);
                return true;
            }
        });
        listTuesday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabTuesdayTags.length; i++) {
                    if (i == position)
                        varObj.tabTuesdayTags[i] = true;
                }
            }
        });

        newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
        for(final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup,varObj.tabTuesdayText)) {
            newsEntryAdapter.add(entry);
        }
        listTuesday.setAdapter(newsEntryAdapter);


        listWednesday = (ListView) findViewById(R.id.tab_wednesday_listview);
        listWednesday.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabWednesdayText.length; i++) {
                    if (i == position) {
                        varObj.tabWednesdayText[i] = "";
                        writeStrings();
                    }
                }
                newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                for (final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup, varObj.tabWednesdayText)) {
                    newsEntryAdapter.add(entry);
                }
                listWednesday.setAdapter(newsEntryAdapter);
                return true;
            }
        });
        listWednesday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabWednesdayTags.length; i++) {
                    if (i == position)
                        varObj.tabWednesdayTags[i] = true;
                }
            }
        });

        newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
        for(final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup,varObj.tabWednesdayText)) {
            newsEntryAdapter.add(entry);
        }
        listWednesday.setAdapter(newsEntryAdapter);


        listThursday = (ListView) findViewById(R.id.tab_thursday_listview);
        listThursday.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabThursdayText.length; i++) {
                    if (i == position) {
                        varObj.tabThursdayText[i] = "";
                        writeStrings();
                    }
                }
                newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                for (final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup, varObj.tabThursdayText)) {
                    newsEntryAdapter.add(entry);
                }
                listThursday.setAdapter(newsEntryAdapter);
                return true;
            }
        });
        listThursday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabThursdayTags.length; i++) {
                    if (i == position)
                        varObj.tabThursdayTags[i] = true;
                }
            }
        });

        newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
        for(final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup,varObj.tabThursdayText)) {
            newsEntryAdapter.add(entry);
        }
        listThursday.setAdapter(newsEntryAdapter);


        listFriday = (ListView) findViewById(R.id.tab_friday_listview);
        listFriday.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabFridayText.length; i++) {
                    if (i == position) {
                        varObj.tabFridayText[i] = "";
                        writeStrings();
                    }
                }
                newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                for (final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup, varObj.tabFridayText)) {
                    newsEntryAdapter.add(entry);
                }
                listFriday.setAdapter(newsEntryAdapter);
                return true;
            }
        });
        listFriday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabFridayTags.length; i++) {
                    if (i == position)
                        varObj.tabFridayTags[i] = true;
                }
            }
        });

        newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
        for(final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup,varObj.tabFridayText)) {
            newsEntryAdapter.add(entry);
        }
        listFriday.setAdapter(newsEntryAdapter);


        listSaturday = (ListView) findViewById(R.id.tab_saturday_listview);
        listSaturday.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabSaturdayText.length; i++) {
                    if (i == position) {
                        varObj.tabSaturdayText[i] = "";
                        writeStrings();
                    }
                }
                newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                for (final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup, varObj.tabSaturdayText)) {
                    newsEntryAdapter.add(entry);
                }
                listSaturday.setAdapter(newsEntryAdapter);
                return true;
            }
        });
        listSaturday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabSaturdayTags.length; i++) {
                    if (i == position)
                        varObj.tabSaturdayTags[i] = true;
                }
            }
        });
        newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
        for(final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup,varObj.tabSaturdayText)) {
            newsEntryAdapter.add(entry);
        }
        listSaturday.setAdapter(newsEntryAdapter);

        listSunday = (ListView) findViewById(R.id.tab_sunday_listview);
        listSunday.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabSundayText.length; i++) {
                    if (i == position) {
                        varObj.adapterListViewSunday[i] = varObj.adapterListViewBackup[i];
                        varObj.tabSundayText[i] = "";
                        writeStrings();
                    }
                }
                newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                for (final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup, varObj.tabSundayText)) {
                    newsEntryAdapter.add(entry);
                }
                listSunday.setAdapter(newsEntryAdapter);
                return true;
            }
        });
        listSunday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabSundayTags.length; i++) {
                    if (i == position)
                        varObj.tabSundayTags[i] = true;
                }
            }
        });
        newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
        for(final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup,varObj.tabSundayText)) {
            newsEntryAdapter.add(entry);
        }
        listSunday.setAdapter(newsEntryAdapter);
    }

    public void ButtonListeners() {
        tabMondayUpdate = (Button) findViewById(R.id.tab_monday_button2);
        tabMondayUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < varObj.tabMondayTags.length; i++) {
                    if (varObj.tabMondayTags[i] == true) {
                        varObj.tabMondayText[i] = tabMondayText.getText().toString();
                        varObj.tabMondayTags[i] = false;
                    }
                }
                newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                for(final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup,varObj.tabMondayText)) {
                    newsEntryAdapter.add(entry);
                }
                listMonday.setAdapter(newsEntryAdapter);
            }
        });
        tabMondayDelete = (Button)findViewById(R.id.tab_monday_button1);
        tabMondayDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varObj.tagsReset("Monday");
                varObj.clearTabTexts("Monday");
                newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                for(final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup,varObj.tabMondayText)) {
                    newsEntryAdapter.add(entry);
                }
                listMonday.setAdapter(newsEntryAdapter);

            }
        });

        tabTuesdayUpdate = (Button)findViewById(R.id.tab_tuesday_button2);
        tabTuesdayUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < varObj.tabTuesdayTags.length; i++) {
                    if (varObj.tabTuesdayTags[i] == true) {
                        varObj.tabTuesdayText[i] = tabTuesdayText.getText().toString();
                        varObj.tabTuesdayTags[i] = false;
                    }
                }
                newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                for(final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup,varObj.tabTuesdayText)) {
                    newsEntryAdapter.add(entry);
                }
                listTuesday.setAdapter(newsEntryAdapter);
            }
        });
        tabTuesdayDelete = (Button)findViewById(R.id.tab_tuesday_button1);
        tabTuesdayDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varObj.tagsReset("Tuesday");
                varObj.clearTabTexts("Tuesday");
                newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                for(final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup,varObj.tabTuesdayText)) {
                    newsEntryAdapter.add(entry);
                }
                listTuesday.setAdapter(newsEntryAdapter);
            }
        });

        tabWednesdayUpdate = (Button)findViewById(R.id.tab_wednesday_button2);
        tabWednesdayUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < varObj.tabWednesdayTags.length; i++) {
                    if (varObj.tabWednesdayTags[i] == true) {
                        varObj.tabWednesdayText[i] = tabWednesdayText.getText().toString();
                        varObj.tabWednesdayTags[i] = false;
                    }
                }
                newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                for(final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup,varObj.tabWednesdayText)) {
                    newsEntryAdapter.add(entry);
                }
                listWednesday.setAdapter(newsEntryAdapter);
            }
        });
        tabWednesdayDelete = (Button)findViewById(R.id.tab_wednesday_button1);
        tabWednesdayDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varObj.tagsReset("Wednesday");
                varObj.clearTabTexts("Wednesday");
                newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                for(final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup,varObj.tabWednesdayText)) {
                    newsEntryAdapter.add(entry);
                }
                listWednesday.setAdapter(newsEntryAdapter);
            }
        });

        tabThursdayUpdate = (Button)findViewById(R.id.tab_thursday_button2);
        tabThursdayUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < varObj.tabThursdayTags.length; i++) {
                    if (varObj.tabThursdayTags[i] == true) {
                        varObj.tabThursdayText[i] = tabThursdayText.getText().toString();
                        varObj.tabThursdayTags[i] = false;
                    }
                }
                newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                for(final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup,varObj.tabThursdayText)) {
                    newsEntryAdapter.add(entry);
                }
                listThursday.setAdapter(newsEntryAdapter);
            }
        });
        tabThursdayDelete = (Button)findViewById(R.id.tab_thursday_button1);
        tabThursdayDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varObj.tagsReset("Thursday");
                varObj.clearTabTexts("Thursday");
                newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                for(final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup,varObj.tabThursdayText)) {
                    newsEntryAdapter.add(entry);
                }
                listThursday.setAdapter(newsEntryAdapter);
            }
        });

        tabFridayUpdate = (Button)findViewById(R.id.tab_friday_button2);
        tabFridayUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < varObj.tabFridayTags.length; i++) {
                    if (varObj.tabFridayTags[i] == true) {
                        varObj.tabFridayText[i] = tabFridayText.getText().toString();
                        varObj.tabFridayTags[i] = false;
                    }
                }
                newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                for(final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup,varObj.tabFridayText)) {
                    newsEntryAdapter.add(entry);
                }
                listFriday.setAdapter(newsEntryAdapter);
            }
        });
        tabFridayDelete = (Button)findViewById(R.id.tab_friday_button1);
        tabFridayDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varObj.tagsReset("Friday");
                varObj.clearTabTexts("Friday");
                newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                for(final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup,varObj.tabFridayText)) {
                    newsEntryAdapter.add(entry);
                }
                listFriday.setAdapter(newsEntryAdapter);
            }
        });

        tabSaturdayUpdate = (Button)findViewById(R.id.tab_saturday_button2);
        tabSaturdayUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < varObj.tabSaturdayTags.length; i++) {
                    if (varObj.tabSaturdayTags[i] == true) {
                        varObj.tabSaturdayText[i] = tabSaturdayText.getText().toString();
                        varObj.tabSaturdayTags[i] = false;
                    }
                }
                newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                for(final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup,varObj.tabSaturdayText)) {
                    newsEntryAdapter.add(entry);
                }
                listSaturday.setAdapter(newsEntryAdapter);
            }
        });
        tabSaturdayDelete = (Button)findViewById(R.id.tab_saturday_button1);
        tabSaturdayDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varObj.tagsReset("Saturday");
                varObj.clearTabTexts("Saturday");
                newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                for(final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup,varObj.tabSaturdayText)) {
                    newsEntryAdapter.add(entry);
                }
                listSaturday.setAdapter(newsEntryAdapter);
            }
        });

        tabSundayUpdate = (Button)findViewById(R.id.tab_sunday_button2);
        tabSundayUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < varObj.tabSundayTags.length; i++) {
                    if (varObj.tabSundayTags[i] == true) {
                        varObj.tabSundayText[i] = tabSundayText.getText().toString();
                        varObj.tabSundayTags[i] = false;
                    }
                }
                newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                for(final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup,varObj.tabSundayText)) {
                    newsEntryAdapter.add(entry);
                }
                listSunday.setAdapter(newsEntryAdapter);
            }
        });
        tabSundayDelete = (Button)findViewById(R.id.tab_sunday_button1);
        tabSundayDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varObj.tagsReset("Sunday");
                varObj.clearTabTexts("Sunday");
                newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                for(final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup,varObj.tabSundayText)) {
                    newsEntryAdapter.add(entry);
                }
                listSunday.setAdapter(newsEntryAdapter);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_program, menu);

        return true;
    }
    private void setupTabHost(){


        tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        tabMonday = tabHost.newTabSpec("Monday");
        tabMonday.setContent(R.id.tab_monday);
        tabMonday.setIndicator("Monday");
        tabHost.addTab(tabMonday);

        tabTuesday = tabHost.newTabSpec("Tuesday");
        tabTuesday.setContent(R.id.tab_tuesday);
        tabTuesday.setIndicator("Tuesday");
        tabHost.addTab(tabTuesday);

        tabWednesday = tabHost.newTabSpec("Wednesday");
        tabWednesday.setContent(R.id.tab_wednesday);
        tabWednesday.setIndicator("Wednesday");
        tabHost.addTab(tabWednesday);

        tabThursday = tabHost.newTabSpec("Thursday");
        tabThursday.setContent(R.id.tab_thursday);
        tabThursday.setIndicator("Thursday");
        tabHost.addTab(tabThursday);

        tabFriday = tabHost.newTabSpec("Friday");
        tabFriday.setContent(R.id.tab_friday);
        tabFriday.setIndicator("Friday");
        tabHost.addTab(tabFriday);

        tabSaturday = tabHost.newTabSpec("Saturday");
        tabSaturday.setContent(R.id.tab_saturday);
        tabSaturday.setIndicator("Saturday");
        tabHost.addTab(tabSaturday);

        tabSunday = tabHost.newTabSpec("Sunday");
        tabSunday.setContent(R.id.tab_sunday);
        tabSunday.setIndicator("Sunday");
        tabHost.addTab(tabSunday);

        for(int i = 0 ; i<tabHost.getTabWidget().getChildCount(); i++){
                TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                tv.setTextColor(Color.parseColor("#d20c0c"));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                intent = new Intent(this,SettingsClass.class);
                startActivity(intent);
                break;
            case R.id.action_remove:
                a_builder = new AlertDialog.Builder(Program.this);
                a_builder.setMessage("Do you want to clean your all history?")
                        .setCancelable(false)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        varObj.clearTabTexts("all");
                        varObj.tagsReset("all");
                        varObj.ArrayAdapterReset("all");
                        VariableClass.settingArray[7] = "false";
                        VariableClass.settingArray[9] = "false";
                        writeForSettings();
                        writeStrings();

                        newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                        for (final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup, varObj.tabMondayText)) {
                            newsEntryAdapter.add(entry);
                        }
                        listMonday.setAdapter(newsEntryAdapter);

                        newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                        for (final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup, varObj.tabTuesdayText)) {
                            newsEntryAdapter.add(entry);
                        }
                        listTuesday.setAdapter(newsEntryAdapter);

                        newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                        for (final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup, varObj.tabWednesdayText)) {
                            newsEntryAdapter.add(entry);
                        }
                        listWednesday.setAdapter(newsEntryAdapter);

                        newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                        for (final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup, varObj.tabThursdayText)) {
                            newsEntryAdapter.add(entry);
                        }
                        listThursday.setAdapter(newsEntryAdapter);

                        newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                        for (final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup, varObj.tabFridayText)) {
                            newsEntryAdapter.add(entry);
                        }
                        listFriday.setAdapter(newsEntryAdapter);

                        newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                        for (final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup, varObj.tabSaturdayText)) {
                            newsEntryAdapter.add(entry);
                        }
                        listSaturday.setAdapter(newsEntryAdapter);

                        newsEntryAdapter = new NewsEntryAdapterForProgram(Program.this, R.layout.news_entry_list_item);
                        for (final NewsEntry entry : getNewsEntries(varObj.adapterListViewBackup, varObj.tabSundayText)) {
                            newsEntryAdapter.add(entry);
                        }
                        listSunday.setAdapter(newsEntryAdapter);

                        tabMondayText.setText("");
                        tabTuesdayText.setText("");
                        tabWednesdayText.setText("");
                        tabThursdayText.setText("");
                        tabFridayText.setText("");
                        tabSaturdayText.setText("");
                        tabSundayText.setText("");

                    }
                });
                AlertDialog alert = a_builder.create();
                alert.setTitle("Warning!");
                alert.show();

                break;
            case R.id.action_confirm:
                a_builder = new AlertDialog.Builder(Program.this);
                a_builder.setMessage("Do you want to save your program permanently?")
                        .setCancelable(false)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        VariableClass.settingArray[7] = "false";
                        VariableClass.settingArray[9] = "false";
                        writeForSettings();
                        writeStrings();
                        startService();

                    }
                });
                alert = a_builder.create();
                alert.setTitle("Warning!");
                alert.show();

                break;
            default:

        }
        return super.onOptionsItemSelected(item);
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
    public void startService(){
        intent = new Intent(getApplicationContext(),NotificationClass.class);
        startService(intent);
        NotificationClass.startFlag=true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void readString(){
        try {
            FileInputStream fileInputStream = openFileInput("MondayString.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lines;
            int i = 0;
            while ((lines = bufferedReader.readLine()) != null) {
                varObj.tabMondayText[i]=lines;
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
            int i=0;
            while ((lines = bufferedReader.readLine()) != null) {
                varObj.tabTuesdayText[i]=lines;
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
                varObj.tabWednesdayText[i]=lines;
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
                varObj.tabThursdayText[i]=lines;
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
                varObj.tabFridayText[i]=lines;
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
                varObj.tabSaturdayText[i]=lines;
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
                varObj.tabSundayText[i]=lines;
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeStrings(){

        stringBuilder = new StringBuilder();
        for(int i = 0 ; i < 48 ; i++) {
            stringBuilder.append(varObj.tabMondayText[i]);
            stringBuilder.append("\n");
        }
        writingString = stringBuilder.toString();

        try {
            FileOutputStream fileOutputStream = openFileOutput("MondayString.txt", MODE_PRIVATE);
            fileOutputStream.write(writingString.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stringBuilder = new StringBuilder();
        for(int i = 0 ; i < 48 ; i++) {
            stringBuilder.append(varObj.tabTuesdayText[i]);
            stringBuilder.append("\n");
        }
        writingString = stringBuilder.toString();

        try {
            FileOutputStream fileOutputStream = openFileOutput("TuesdayString.txt", MODE_PRIVATE);
            fileOutputStream.write(writingString.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        stringBuilder = new StringBuilder();
        for(int i = 0 ; i < 48 ; i++) {
            stringBuilder.append(varObj.tabWednesdayText[i]);
            stringBuilder.append("\n");
        }
        writingString = stringBuilder.toString();

        try {
            FileOutputStream fileOutputStream = openFileOutput("WednesdayString.txt", MODE_PRIVATE);
            fileOutputStream.write(writingString.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        stringBuilder = new StringBuilder();
        for(int i = 0 ; i < 48 ; i++) {
            stringBuilder.append(varObj.tabThursdayText[i]);
            stringBuilder.append("\n");
        }
        writingString = stringBuilder.toString();

        try {
            FileOutputStream fileOutputStream = openFileOutput("ThursdayString.txt", MODE_PRIVATE);
            fileOutputStream.write(writingString.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stringBuilder = new StringBuilder();
        for(int i = 0 ; i < 48 ; i++) {
            stringBuilder.append(varObj.tabFridayText[i]);
            stringBuilder.append("\n");
        }
        writingString = stringBuilder.toString();

        try {
            FileOutputStream fileOutputStream = openFileOutput("FridayString.txt", MODE_PRIVATE);
            fileOutputStream.write(writingString.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        stringBuilder = new StringBuilder();
        for(int i = 0 ; i < 48 ; i++) {
            stringBuilder.append(varObj.tabSaturdayText[i]);
            stringBuilder.append("\n");
        }
        writingString = stringBuilder.toString();

        try {
            FileOutputStream fileOutputStream = openFileOutput("SaturdayString.txt", MODE_PRIVATE);
            fileOutputStream.write(writingString.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        stringBuilder = new StringBuilder();
        for(int i = 0 ; i < 48 ; i++) {
            stringBuilder.append(varObj.tabSundayText[i]);
            stringBuilder.append("\n");
        }
       writingString = stringBuilder.toString();

        try {
            FileOutputStream fileOutputStream = openFileOutput("SundayString.txt", MODE_PRIVATE);
            fileOutputStream.write(writingString.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private List<NewsEntry> getNewsEntries(String[] backUp,String[] messages) {

        // Let's setup some test data.
        // Normally this would come from some asynchronous fetch into a data source
        // such as a sqlite database, or an HTTP request

        final List<NewsEntry> entries = new ArrayList<NewsEntry>();
        SimpleDateFormat month_date = new SimpleDateFormat("dd/MM/yyyy");
        for(int i = 0; i < backUp.length; i++) {
            if(!messages[i].isEmpty())
            entries.add(
                    new NewsEntry(
                            backUp[i],
                            "["+messages[i]+"]"+" Created On "+month_date.format(Calendar.getInstance().getTime()),
                            Calendar.getInstance().getTime(),
                            i % 2 == 0 ? R.drawable.clock_icon : R.drawable.clock_icon
                    )
            );
            else
                entries.add(
                        new NewsEntry(
                                backUp[i],
                                "Empty",
                                Calendar.getInstance().getTime(),
                                i % 2 == 0 ? R.drawable.clock_icon : R.drawable.clock_icon
                        )
                );
        }

        return entries;
    }
    public void prepareListView(){
        varObj.clearTabTexts("all");
        readString();
        varObj.tagsInitialize();
        varObj.findTags();

        for (int i = 0; i < varObj.tabMondayTags.length; i++) {
            if (varObj.tabMondayTags[i] == true) {
                varObj.adapterListViewMonday[i] = varObj.adapterListViewBackup[i] + "    (" + varObj.tabMondayText[i] + ")";
                varObj.tabMondayTags[i] = false;
            }
        }

        /*newsEntryAdapter = new NewsEntryAdapter(FTPPathFinder.this, R.layout.news_entry_list_item);
        for(final NewsEntry entry : getNewsEntries(ftpDirectories)) {
            newsEntryAdapter.add(entry);
        }
        listView.setAdapter(newsEntryAdapter);
        */
        for (int i = 0; i < varObj.tabTuesdayTags.length; i++) {
            if (varObj.tabTuesdayTags[i] == true) {
                varObj.adapterListViewTuesday[i] = varObj.adapterListViewBackup[i] + "    (" + varObj.tabTuesdayText[i] + ")";
                varObj.tabTuesdayTags[i] = false;
            }
        }
        for (int i = 0; i < varObj.tabWednesdayTags.length; i++) {
            if (varObj.tabWednesdayTags[i] == true) {
                varObj.adapterListViewWednesday[i] = varObj.adapterListViewBackup[i] + "    (" + varObj.tabWednesdayText[i] + ")";
                varObj.tabWednesdayTags[i] = false;
            }
        }
        for (int i = 0; i < varObj.tabThursdayTags.length; i++) {
            if (varObj.tabThursdayTags[i] == true) {
                varObj.adapterListViewThursday[i] = varObj.adapterListViewBackup[i] + "    (" + varObj.tabThursdayText[i] + ")";
                varObj.tabThursdayTags[i] = false;
            }
        }
        for (int i = 0; i < varObj.tabFridayTags.length; i++) {
            if (varObj.tabFridayTags[i] == true) {
                varObj.adapterListViewFriday[i] = varObj.adapterListViewBackup[i] + "    (" + varObj.tabFridayText[i] + ")";
                varObj.tabFridayTags[i] = false;
            }
        }
        for (int i = 0; i < varObj.tabSaturdayTags.length; i++) {
            if (varObj.tabSaturdayTags[i] == true) {
                varObj.adapterListViewSaturday[i] = varObj.adapterListViewBackup[i] + "    (" + varObj.tabSaturdayText[i] + ")";
                varObj.tabSaturdayTags[i] = false;
            }
        }
        for (int i = 0; i < varObj.tabSundayTags.length; i++) {
            if (varObj.tabSundayTags[i] == true) {
                varObj.adapterListViewSunday[i] = varObj.adapterListViewBackup[i] + "    (" + varObj.tabSundayText[i] + ")";
                varObj.tabSundayTags[i] = false;
            }
        }
    }
}
