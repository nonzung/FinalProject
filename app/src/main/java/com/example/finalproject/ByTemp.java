package com.example.finalproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ByTemp extends Activity {
	RadioButton R1, R2, R3, R4, R5, R6;
	TextView tvBack, tvAdd;
	EditText etTempMax,etTempMin,etMoisMax,etMoisMin;
    TextView etName;
    SharedPreferences pref;
    SharedPreferences.Editor prefEditor;
    int posEdit;
    public static ArrayList<HashMap<String, String>> collection,collections;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_by_temp);
        pref = getApplicationContext().getSharedPreferences("TestHashMap", 0);
        prefEditor = pref.edit();
        final Bundle extras = getIntent().getExtras();


		etName = (TextView)findViewById(R.id.etName);
        etTempMax = (EditText)findViewById(R.id.etMaxTemp);
		etTempMin = (EditText)findViewById(R.id.etMinTemp);
        etMoisMax = (EditText)findViewById(R.id.etMaxMois);
        etMoisMin = (EditText)findViewById(R.id.etMinMois);


		tvBack = (TextView) findViewById(R.id.tvBack);
		tvBack.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Do stuff here
				Intent i = new Intent(ByTemp.this, OnOffProgram.class);
				startActivity(i);
			}
		});
		tvAdd = (TextView) findViewById(R.id.tvAdd);
		
		//MainActivity.ListProgram = new ArrayList<HashMap<String,String>>();
		tvAdd.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Do stuff here
                if(extras == null) {
                    collection = new ArrayList<HashMap<String, String>>();
                    String storedCollection = pref.getString("Secret", null);
                    try {
                        JSONArray array = new JSONArray(storedCollection);
                        HashMap<String, String> item = null;
                        for (int i = 0; i < array.length(); i++) {
                            String obj = array.get(i).toString();
                            JSONObject ary = new JSONObject(obj);
                            Iterator<String> it = ary.keys();
                            item = new HashMap<String, String>();
                            while (it.hasNext()) {
                                String key = it.next();
                                item.put(key, (String) ary.get(key));
                            }
                            collection.add(item);
                        }
                    } catch (Exception e) {
                        Log.e("Restore", "while parsing", e);
                    }

                    HashMap<String, String> Listpro;
                    Listpro = new HashMap<String, String>();
                    //Listpro.put("ProType", "ByTemp");
                    Listpro.put("NamePro", etName.getText().toString());
                    Listpro.put("MaxTemp", etTempMax.getText().toString());
                    Listpro.put("MinTemp", etTempMin.getText().toString());
                    Listpro.put("MaxMois", etMoisMax.getText().toString());
                    Listpro.put("MinMois", etMoisMin.getText().toString());

                    //MainActivity.ListProgram.add(Listpro);
                    collection.add(Listpro);
                    JSONArray result = new JSONArray(collection);
                    prefEditor.putString("Secret", result.toString());
                    prefEditor.commit();
                    finish();
                }else{
                    /*
                    collection = new ArrayList<HashMap<String, String>>();
                    String storedCollection = pref.getString("Secret", null);
                    try {
                        JSONArray array = new JSONArray(storedCollection);
                        HashMap<String, String> item = null;
                        for (int i = 0; i < array.length(); i++) {
                            String obj = array.get(i).toString();
                            JSONObject ary = new JSONObject(obj);
                            Iterator<String> it = ary.keys();
                            item = new HashMap<String, String>();
                            while (it.hasNext()) {
                                String key = it.next();
                                item.put(key, (String) ary.get(key));
                            }
                            collection.add(item);
                        }
                    } catch (Exception e) {
                        Log.e("Restore", "while parsing", e);
                    }
                    */
                   // HashMap<String, String> Listpro;
                    //Listpro = new HashMap<String, String>();

                    OnOffProgram.collections.get(posEdit).put("NamePro", etName.getText().toString());
                    OnOffProgram.collections.get(posEdit).put("MaxTemp", etTempMax.getText().toString());
                    OnOffProgram.collections.get(posEdit).put("MinTemp", etTempMin.getText().toString());
                    OnOffProgram.collections.get(posEdit).put("MaxMois", etMoisMax.getText().toString());
                    OnOffProgram.collections.get(posEdit).put("MinMois", etMoisMin.getText().toString());


                    //MainActivity.ListProgram.add(Listpro);
                    //collection.add(Listpro);
                    //JSONArray result = new JSONArray(collection);
                    //prefEditor.putString("Secret", result.toString());
                    //prefEditor.commit();
                    Intent in = new Intent();
                    in.putExtra("PosEdit", -1);
                    setResult(RESULT_OK, in);
                    finish();
                }
			}
		});
        if(extras != null) {
            posEdit = extras.getInt("posEdit");
            etName.setText(extras.getString("NamePro"));
            etTempMax.setText(extras.getString("MaxTemp"));
            etTempMin.setText(extras.getString("MinTemp"));
            etMoisMax.setText(extras.getString("MaxMois"));
            etMoisMin.setText(extras.getString("MinMois"));
            tvAdd.setText("Edit");
        }
	}

}
