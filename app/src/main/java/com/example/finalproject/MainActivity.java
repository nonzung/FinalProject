package com.example.finalproject;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;

public class MainActivity extends Activity {
    private Button btnProgram, btnOnOff, btnManual, btnSensor,btnDate;
    public static ArrayList<HashMap<String, String>> ListProgram;
    public static ArrayList<HashMap<String, String>> collection, collections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("TestHashMap", 0);
        SharedPreferences.Editor prefEditor = pref.edit();
        try {
            String storedCollection = pref.getString("Secret", null);
            //prefEditor.putString("Time", null);
            prefEditor.commit();
            if(storedCollection == null) {
                // Create Switch ListView Stucture
                prefEditor.putString("Secret", null);
                //prefEditor.putString("Time", null);
                //prefEditor.commit();
                HashMap<String, String> Listpro;
                collection = new ArrayList<HashMap<String, String>>();


                for (int i = 0; i < 6; i++) {
                    Listpro = new HashMap<String, String>();
                    Listpro.put("NamePro", "Switch" + (i + 1));
                    Listpro.put("MaxTemp", "0");
                    Listpro.put("MinTemp", "0");
                    Listpro.put("MaxMois", "0");
                    Listpro.put("MinMois", "0");
                    Listpro.put("State", "0");
                    collection.add(Listpro);
                }
                JSONArray result = new JSONArray(collection);
                prefEditor.putString("Secret", result.toString());
                prefEditor.commit();
            }
        } catch (Exception e) {
            Log.e("Restore", "while parsing", e);
        }
        /*
        try {
            String storedCollection = pref.getString("Time", null);
            if(storedCollection == null) {
                prefEditor.putString("Time", null);
                prefEditor.commit();
                HashMap<String, String> Listpro;
                collection = new ArrayList<HashMap<String, String>>();


                for (int i = 0; i < 6; i++) {
                    Listpro = new HashMap<String, String>();
                    Listpro.put("NamePro", "Switch" + (i + 1));
                    Listpro.put("MaxTemp", "0");
                    Listpro.put("MinTemp", "0");
                    Listpro.put("MaxMois", "0");
                    Listpro.put("MinMois", "0");
                    collection.add(Listpro);
                }
                JSONArray result = new JSONArray(collection);
                prefEditor.putString("Time", null);
                prefEditor.commit();
            }
        } catch (Exception e) {
            Log.e("Restore", "while parsing", e);
        }
        */
        //MainActivity.ListProgram = new ArrayList<HashMap<String,String>>();
        /*
        btnProgram = (Button)findViewById(R.id.btnprogram);
        btnProgram.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Do stuff here
            Intent i = new Intent(MainActivity.this, ProgramActivity.class);
            startActivity(i);
            }
        });
        */
        btnOnOff = (Button) findViewById(R.id.btnOnOff);
        btnOnOff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Do stuff here
                if(checkWifi() == true){
                Intent i = new Intent(MainActivity.this, OnOffProgram.class);
                startActivity(i);
                }else{
                    showAlert();
                }
            }
        });
        btnManual = (Button) findViewById(R.id.btnManual);
        btnManual.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Do stuff here
                if(checkWifi() == true){
                Intent i = new Intent(MainActivity.this, Manual.class);
                startActivity(i);
                }else{
                    showAlert();
                }
            }
        });
        btnSensor = (Button) findViewById(R.id.btnSensor);
        btnSensor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Do stuff here
                if(checkWifi() == true){
                String msgSend = "3";
                SendArdu sendArdu = new SendArdu(MainActivity.this,msgSend);
                sendArdu.execute(msgSend);
                }else{
                    showAlert();
                }
            }
        });
        btnDate = (Button) findViewById(R.id.btnTimeList);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, OnOffTime.class);
                startActivity(i);
            }
        });

    }
    public void showAlert(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_layout);
        dialog.setTitle("เชื่อมต่อเครือข่ายไม่ถูกต้อง!!");
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("คุณเชื่อมต่อเครือข่ายไม่ถูกต้อง กด OK เพื่อเลือกเครือข่าย");
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                dialog.dismiss();

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        //dialog.
        dialog.show();
    }
    public boolean checkWifi(){
        String WifiName = getWifiName(this);
        if(!WifiName.equals("My_AP_EPS8266")){
            return false;
        }else{
            return true;
        }

    }
    public String getWifiName(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (manager.isWifiEnabled()) {
            WifiInfo wifiInfo = manager.getConnectionInfo();
            if (wifiInfo != null) {
                NetworkInfo.DetailedState state = WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState());
                if (state == NetworkInfo.DetailedState.CONNECTED || state == NetworkInfo.DetailedState.OBTAINING_IPADDR) {
                    return wifiInfo.getSSID();
                }
            }
        }
        return null;
    }
}
