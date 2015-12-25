package com.example.mertdesktop.schoolproject;


import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class VariableClass extends AppCompatActivity {

    public String addapterString;
    public String[] addapterArray=new String[200];
    public String[] addapterListFavor=new String[11];
    public static String[] settingArray ={"","","","","","","","","","","","","","","","","","","","","",""};
    public String[] emptyArray = {""};
    public static String[] accountsArray = {"","","","",""};
    public String[] adapterListViewMonday = {"00:00-00:30","00:30-01:00","01:00-01:30","01:30-02:00","02:00-02:30","02:30-03:00"
            ,"03:00-03:30","03:30-04:00","04:00-04:30","04:30-05:00","05:00-05:30","05:30-06:00","06:00-06:30","06:30-07:00","07:00-07:30"
            ,"07:30-08:00","08:00-08:30","08:30-09:00","09:00-09:30","09:30-10:00","10:00-10:30","10:30-11:00","11:00-11:30","11:30-12:00","12:00-12:30"
            ,"12:30-13:00","13:00-13:30","13:30-14:00","14:00-14:30","14:30-15:00","15:00-15:30","15:30-16:00","16:00-16:30","16:30-17:00","17:00-17:30"
            ,"17:30-18:00","18:00-18:30","18:30-19:00","19:00-19:30","19:30-20:00","20:00-20:30","20:30-21:00","21:00-21:30","21.30-22:00","22:00-22:30"
            ,"22:30-23:00","23:00-23:30","23:30-24:00"};
    public String[] adapterListViewTuesday = {"00:00-00:30","00:30-01:00","01:00-01:30","01:30-02:00","02:00-02:30","02:30-03:00"
            ,"03:00-03:30","03:30-04:00","04:00-04:30","04:30-05:00","05:00-05:30","05:30-06:00","06:00-06:30","06:30-07:00","07:00-07:30"
            ,"07:30-08:00","08:00-08:30","08:30-09:00","09:00-09:30","09:30-10:00","10:00-10:30","10:30-11:00","11:00-11:30","11:30-12:00","12:00-12:30"
            ,"12:30-13:00","13:00-13:30","13:30-14:00","14:00-14:30","14:30-15:00","15:00-15:30","15:30-16:00","16:00-16:30","16:30-17:00","17:00-17:30"
            ,"17:30-18:00","18:00-18:30","18:30-19:00","19:00-19:30","19:30-20:00","20:00-20:30","20:30-21:00","21:00-21:30","21.30-22:00","22:00-22:30"
            ,"22:30-23:00","23:00-23:30","23:30-24:00"};
    public String[] adapterListViewWednesday = {"00:00-00:30","00:30-01:00","01:00-01:30","01:30-02:00","02:00-02:30","02:30-03:00"
            ,"03:00-03:30","03:30-04:00","04:00-04:30","04:30-05:00","05:00-05:30","05:30-06:00","06:00-06:30","06:30-07:00","07:00-07:30"
            ,"07:30-08:00","08:00-08:30","08:30-09:00","09:00-09:30","09:30-10:00","10:00-10:30","10:30-11:00","11:00-11:30","11:30-12:00","12:00-12:30"
            ,"12:30-13:00","13:00-13:30","13:30-14:00","14:00-14:30","14:30-15:00","15:00-15:30","15:30-16:00","16:00-16:30","16:30-17:00","17:00-17:30"
            ,"17:30-18:00","18:00-18:30","18:30-19:00","19:00-19:30","19:30-20:00","20:00-20:30","20:30-21:00","21:00-21:30","21.30-22:00","22:00-22:30"
            ,"22:30-23:00","23:00-23:30","23:30-24:00"};
    public String[] adapterListViewThursday = {"00:00-00:30","00:30-01:00","01:00-01:30","01:30-02:00","02:00-02:30","02:30-03:00"
            ,"03:00-03:30","03:30-04:00","04:00-04:30","04:30-05:00","05:00-05:30","05:30-06:00","06:00-06:30","06:30-07:00","07:00-07:30"
            ,"07:30-08:00","08:00-08:30","08:30-09:00","09:00-09:30","09:30-10:00","10:00-10:30","10:30-11:00","11:00-11:30","11:30-12:00","12:00-12:30"
            ,"12:30-13:00","13:00-13:30","13:30-14:00","14:00-14:30","14:30-15:00","15:00-15:30","15:30-16:00","16:00-16:30","16:30-17:00","17:00-17:30"
            ,"17:30-18:00","18:00-18:30","18:30-19:00","19:00-19:30","19:30-20:00","20:00-20:30","20:30-21:00","21:00-21:30","21.30-22:00","22:00-22:30"
            ,"22:30-23:00","23:00-23:30","23:30-24:00"};
    public String[] adapterListViewFriday = {"00:00-00:30","00:30-01:00","01:00-01:30","01:30-02:00","02:00-02:30","02:30-03:00"
            ,"03:00-03:30","03:30-04:00","04:00-04:30","04:30-05:00","05:00-05:30","05:30-06:00","06:00-06:30","06:30-07:00","07:00-07:30"
            ,"07:30-08:00","08:00-08:30","08:30-09:00","09:00-09:30","09:30-10:00","10:00-10:30","10:30-11:00","11:00-11:30","11:30-12:00","12:00-12:30"
            ,"12:30-13:00","13:00-13:30","13:30-14:00","14:00-14:30","14:30-15:00","15:00-15:30","15:30-16:00","16:00-16:30","16:30-17:00","17:00-17:30"
            ,"17:30-18:00","18:00-18:30","18:30-19:00","19:00-19:30","19:30-20:00","20:00-20:30","20:30-21:00","21:00-21:30","21.30-22:00","22:00-22:30"
            ,"22:30-23:00","23:00-23:30","23:30-24:00"};
    public String[] adapterListViewSaturday = {"00:00-00:30","00:30-01:00","01:00-01:30","01:30-02:00","02:00-02:30","02:30-03:00"
            ,"03:00-03:30","03:30-04:00","04:00-04:30","04:30-05:00","05:00-05:30","05:30-06:00","06:00-06:30","06:30-07:00","07:00-07:30"
            ,"07:30-08:00","08:00-08:30","08:30-09:00","09:00-09:30","09:30-10:00","10:00-10:30","10:30-11:00","11:00-11:30","11:30-12:00","12:00-12:30"
            ,"12:30-13:00","13:00-13:30","13:30-14:00","14:00-14:30","14:30-15:00","15:00-15:30","15:30-16:00","16:00-16:30","16:30-17:00","17:00-17:30"
            ,"17:30-18:00","18:00-18:30","18:30-19:00","19:00-19:30","19:30-20:00","20:00-20:30","20:30-21:00","21:00-21:30","21.30-22:00","22:00-22:30"
            ,"22:30-23:00","23:00-23:30","23:30-24:00"};
    public String[] adapterListViewSunday = {"00:00-00:30","00:30-01:00","01:00-01:30","01:30-02:00","02:00-02:30","02:30-03:00"
            ,"03:00-03:30","03:30-04:00","04:00-04:30","04:30-05:00","05:00-05:30","05:30-06:00","06:00-06:30","06:30-07:00","07:00-07:30"
            ,"07:30-08:00","08:00-08:30","08:30-09:00","09:00-09:30","09:30-10:00","10:00-10:30","10:30-11:00","11:00-11:30","11:30-12:00","12:00-12:30"
            ,"12:30-13:00","13:00-13:30","13:30-14:00","14:00-14:30","14:30-15:00","15:00-15:30","15:30-16:00","16:00-16:30","16:30-17:00","17:00-17:30"
            ,"17:30-18:00","18:00-18:30","18:30-19:00","19:00-19:30","19:30-20:00","20:00-20:30","20:30-21:00","21:00-21:30","21.30-22:00","22:00-22:30"
            ,"22:30-23:00","23:00-23:30","23:30-24:00"};
    public String[] adapterListViewBackup = {"00:00-00:30","00:30-01:00","01:00-01:30","01:30-02:00","02:00-02:30","02:30-03:00"
            ,"03:00-03:30","03:30-04:00","04:00-04:30","04:30-05:00","05:00-05:30","05:30-06:00","06:00-06:30","06:30-07:00","07:00-07:30"
            ,"07:30-08:00","08:00-08:30","08:30-09:00","09:00-09:30","09:30-10:00","10:00-10:30","10:30-11:00","11:00-11:30","11:30-12:00","12:00-12:30"
            ,"12:30-13:00","13:00-13:30","13:30-14:00","14:00-14:30","14:30-15:00","15:00-15:30","15:30-16:00","16:00-16:30","16:30-17:00","17:00-17:30"
            ,"17:30-18:00","18:00-18:30","18:30-19:00","19:00-19:30","19:30-20:00","20:00-20:30","20:30-21:00","21.00-21:30","21:30-22:00",
            "22:00-22:30","22:30-23:00","23:00-23:30","23:30-24:00"};



    public String[] tabMondayText = {"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
            "","","","","","",};
    public String[] tabTuesdayText = {"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
            "","","","","","",};
    public String[] tabWednesdayText = {"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
            "","","","","","",};
    public String[] tabThursdayText  = {"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
            "","","","","","",};
    public String[] tabFridayText =   {"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
            "","","","","","",};
    public String[] tabSaturdayText = {"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
            "","","","","","",};
    public String[] tabSundayText  = {"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
            "","","","","","",};


    public Boolean[] tabMondayTags = new Boolean[48];
    public Boolean[] tabTuesdayTags = new Boolean[48];
    public Boolean[] tabWednesdayTags = new Boolean[48];
    public Boolean[] tabThursdayTags = new Boolean[48];
    public Boolean[] tabFridayTags = new Boolean[48];
    public Boolean[] tabSaturdayTags = new Boolean[48];
    public Boolean[] tabSundayTags = new Boolean[48];

    VariableClass()
    {
        addapterString="";
    }

    VariableClass(String tempString){
        addapterString = tempString;
    }
    public void ArrayAdapterReset(String a){
            for(int i = 0 ; i<48 ; i++){
                if(a == "Monday")
                    adapterListViewMonday[i]=adapterListViewBackup[i];
                else if(a == "Tuesday")
                    adapterListViewTuesday[i]=adapterListViewBackup[i];
                else if(a == "Wednesday")
                    adapterListViewWednesday[i]=adapterListViewBackup[i];
                else if(a == "Thursday")
                    adapterListViewThursday[i]=adapterListViewBackup[i];
                else if(a == "Friday")
                    adapterListViewFriday[i]=adapterListViewBackup[i];
                else if(a == "Saturday")
                    adapterListViewSaturday[i]=adapterListViewBackup[i];
                else if(a == "Sunday")
                    adapterListViewSunday[i]=adapterListViewBackup[i];
                else if(a=="all"){
                    adapterListViewMonday[i]=adapterListViewBackup[i];
                    adapterListViewTuesday[i]=adapterListViewBackup[i];
                    adapterListViewWednesday[i]=adapterListViewBackup[i];
                    adapterListViewThursday[i]=adapterListViewBackup[i];
                    adapterListViewFriday[i]=adapterListViewBackup[i];
                    adapterListViewSaturday[i]=adapterListViewBackup[i];
                    adapterListViewSunday[i]=adapterListViewBackup[i];
                }
                else
                    Toast.makeText(getApplicationContext(),"Mistake",Toast.LENGTH_LONG).show();
            }
    }
    public void tagsReset(String a){
        for(int i = 0 ; i<48 ; i++){
            if(a == "Monday")
            tabMondayTags[i]=false;
            else if(a == "Tuesday")
            tabTuesdayTags[i]=false;
            else if(a == "Wednesday")
            tabWednesdayTags[i]=false;
            else if(a == "Thursday")
            tabThursdayTags[i]=false;
            else if(a == "Friday")
            tabFridayTags[i]=false;
            else if(a == "Saturday")
            tabSaturdayTags[i]=false;
            else if(a == "Sunday")
            tabSundayTags[i]=false;
            else if(a=="all"){
                tabMondayTags[i]=false;
                tabTuesdayTags[i]=false;
                tabWednesdayTags[i]=false;
                tabThursdayTags[i]=false;
                tabFridayTags[i]=false;
                tabSaturdayTags[i]=false;
                tabSundayTags[i]=false;
            }
            else
                Toast.makeText(getApplicationContext(),"Mistake",Toast.LENGTH_LONG).show();
        }
    }
    public void tagsInitialize(){
        for(int i = 0 ; i<48 ; i++) {
            tabMondayTags[i] = false;
            tabTuesdayTags[i] = false;
            tabWednesdayTags[i] = false;
            tabThursdayTags[i] = false;
            tabFridayTags[i] = false;
            tabSaturdayTags[i] = false;
            tabSundayTags[i] = false;
        }
    }
    public void clearTabTexts(String a){
        for(int i = 0 ; i<48 ; i++){
            if(a == "Monday")
                tabMondayText[i]="";
            else if(a == "Tuesday")
                tabTuesdayText[i]="";
            else if(a == "Wednesday")
                tabWednesdayText[i]="";
            else if(a == "Thursday")
                tabThursdayText[i]="";
            else if(a == "Friday")
                tabFridayText[i]="";
            else if(a == "Saturday")
                tabSaturdayText[i]="";
            else if(a == "Sunday")
                tabSundayText[i]="";
            else if(a=="all"){
                tabMondayText[i]="";
                tabTuesdayText[i]="";
                tabWednesdayText[i]="";
                tabThursdayText[i]="";
                tabFridayText[i]="";
                tabSaturdayText[i]="";
                tabSundayText[i]="";
            }
            else
                Toast.makeText(getApplicationContext(),"Mistake",Toast.LENGTH_LONG).show();
        }
    }
    public void findTags(){
        for(int i = 0 ; i < 48 ; i++){
            if(!tabMondayText[i].isEmpty())
                tabMondayTags[i]=true;
        }
        for(int i = 0 ; i < 48 ; i++){
            if(!tabTuesdayText[i].isEmpty()){
                tabTuesdayTags[i]=true;
            }
        }
        for(int i = 0 ; i < 48 ; i++){
            if(!tabWednesdayText[i].isEmpty()){
                tabWednesdayTags[i]=true;
            }
        }
        for(int i = 0 ; i < 48 ; i++){
            if(!tabThursdayText[i].isEmpty()){
                tabThursdayTags[i]=true;
            }
        }
        for(int i = 0 ; i < 48 ; i++){
            if(!tabFridayText[i].isEmpty()){
                tabFridayTags[i]=true;
            }
        }
        for(int i = 0 ; i < 48 ; i++){
            if(!tabSaturdayText[i].isEmpty()){
                tabSaturdayTags[i]=true;
            }
        }
        for(int i = 0 ; i < 48 ; i++){
            if(!tabSundayText[i].isEmpty()){
                tabSundayTags[i]=true;
            }
        }

    }
    public void resetAddapterArray() {
        for (int i = 0; i < addapterArray.length; i++) {
            addapterArray[i] = "";
        }
    }
    static public void InitializeAccountArray() {
        for (int i = 0; i < accountsArray.length; i++) {
            accountsArray[i] = "";
        }
    }
    public void resetFavorList() {
        for (int i = 0; i < addapterListFavor.length; i++) {
            addapterListFavor[i] = "<Long press to add a new message>";
        }
    }
    }

