package com.example.finalproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.finalproject.Adapter.ListManualAdapter;
import com.example.finalproject.Adapter.ListProgramDateTimeAdapter;
import com.example.finalproject.Arduino.SendArdu;
import com.example.finalproject.Arduino.SendArduUpdateValue;
import com.example.finalproject.Arduino.SendToArdu;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Manual extends ActionBarActivity {
    public static ArrayList<HashMap<String, String>> collection, collections;
    ListManualAdapter adapter;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("TestHashMap", 0);
        SharedPreferences.Editor prefEditor = pref.edit();
        try {
            String storedCollection = pref.getString("Manual", null);
            //prefEditor.putString("Time", null);
            prefEditor.commit();
            if(storedCollection == null) {
                prefEditor.putString("Manual", null);
                //prefEditor.putString("Time", null);
                //prefEditor.commit();
                HashMap<String, String> Listpro;
                collection = new ArrayList<HashMap<String, String>>();


                for (int i = 0; i < 6; i++) {
                    Listpro = new HashMap<String, String>();
                    Listpro.put("NameSwitch", "Switch" + (i + 1));
                    Listpro.put("State", "0");
                    collection.add(Listpro);
                }
                JSONArray result = new JSONArray(collection);
                prefEditor.putString("Manual", result.toString());
                prefEditor.commit();
            }
        } catch (Exception e) {
            Log.e("Restore", "while parsing", e);
        }
        collections = new ArrayList<HashMap<String, String>>();
        try {
            String storedCollection = pref.getString("Manual", null);
            JSONArray array = new JSONArray(storedCollection);
            HashMap<String, String> item = null;
            for(int i =0; i<array.length(); i++){
                String obj = array.get(i).toString();
                JSONObject ary = new JSONObject(obj);
                Iterator<String> it = ary.keys();
                item = new HashMap<String, String>();
                while(it.hasNext()){
                    String key = it.next();
                    item.put(key, (String)ary.get(key).toString());
                }
                collections.add(item);
            }

        } catch (Exception e) {
            Log.e("Restore", "while parsing", e);
        }
        if(collections.size() > 0 && MainActivity.CheckEnDis == false) {
            Toast.makeText(Manual.this, "Update Value Started....", Toast.LENGTH_SHORT).show();
            new UpDateValue().execute();
        }
        //SendArduUpdateValue updateValue = new SendArduUpdateValue(Manual.this,collections);
        //updateValue.execute("");
        /*
        for (int i = 0; i < collections.size(); i++) {
            if (collections.get(i).get("State").equals("1")) {
                final int position = i;
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        String msgSend = "0"+position+"1";
                        SendArdu sendArdu = new SendArdu(Manual.this,msgSend);
                        sendArdu.execute(msgSend);
                    }
                }, 6000);
            }
        }
        */

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    class UpDateValue extends AsyncTask<Void,Void,Void> {
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Manual.this);
            pd.setTitle("Updating..");
            pd.setCancelable(true);
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 0; i < collections.size(); i++) {
                if (collections.get(i).get("State").equals("1")) {
                    final int position = i;
                    try {
                        Thread.sleep(2000);
                        String msgSend = "0"+position+"1";
                    /*
                    SendArdu sendArdu = new SendArdu(context,msgSend);
                    sendArdu.execute(msgSend);
                    */
                        SendToArdu.send(msgSend, "192.168.4.1", 8000, new SendToArdu.SendCallback() {
                            public void onSuccess(String tag) {
                                Toast.makeText(Manual.this, "onSuccess", Toast.LENGTH_SHORT).show();

                            }
                            public void onFailed(String tag) {
                                Toast.makeText(Manual.this, "onFailed", Toast.LENGTH_SHORT).show();

                            }
                        }, "TAG");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
            try {
                adapter = new ListManualAdapter(Manual.this,collections);
                ListView lv = (ListView)findViewById(R.id.list);
                lv.setAdapter(adapter);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
