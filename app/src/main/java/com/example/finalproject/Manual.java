package com.example.finalproject;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Manual extends ActionBarActivity {
    public static ArrayList<HashMap<String, String>> collection, collections;
    ListManualAdapter adapter;
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
                    item.put(key, (String)ary.get(key));
                }
                collections.add(item);
            }

        } catch (Exception e) {
            Log.e("Restore", "while parsing", e);
        }
        adapter = new ListManualAdapter(Manual.this,collections);
        ListView lv = (ListView)findViewById(R.id.list);
        lv.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
