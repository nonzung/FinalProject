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

        /*
		R1 = (RadioButton) findViewById(R.id.radioButton1);
		R1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (R1.isSelected() == true) {
					R1.setSelected(false);
					R1.setChecked(false);
					R1.setClickable(true);
					Toast.makeText(getApplicationContext(),
							R1.getText().toString() + "Unchecked",
							Toast.LENGTH_SHORT).show();
				} else {
					R1.setSelected(true);
					R1.setClickable(true);
					Toast.makeText(getApplicationContext(),
							R1.getText().toString() + "Checked",
							Toast.LENGTH_SHORT).show();
				}

				// Toast.makeText(getApplicationContext(), x.toString() ,
				// Toast.LENGTH_SHORT).show();
			}
		});
		R2 = (RadioButton) findViewById(R.id.radioButton2);
		R2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (R2.isSelected() == true) {
					R2.setSelected(false);
					R2.setChecked(false);
					R2.setClickable(true);
					Toast.makeText(getApplicationContext(),
							R2.getText().toString() + "Unchecked",
							Toast.LENGTH_SHORT).show();
				} else {
					R2.setSelected(true);
					R2.setClickable(true);
					Toast.makeText(getApplicationContext(),
							R2.getText().toString() + "Checked",
							Toast.LENGTH_SHORT).show();
				}

				// Toast.makeText(getApplicationContext(), x.toString() ,
				// Toast.LENGTH_SHORT).show();
			}
		});
		R3 = (RadioButton) findViewById(R.id.radioButton3);
		R3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (R3.isSelected() == true) {
					R3.setSelected(false);
					R3.setChecked(false);
					R3.setClickable(true);
					Toast.makeText(getApplicationContext(),
							R3.getText().toString() + "Unchecked",
							Toast.LENGTH_SHORT).show();
				} else {
					R3.setSelected(true);
					R3.setClickable(true);
					Toast.makeText(getApplicationContext(),
							R3.getText().toString() + "Checked",
							Toast.LENGTH_SHORT).show();
				}

				// Toast.makeText(getApplicationContext(), x.toString() ,
				// Toast.LENGTH_SHORT).show();
			}
		});
		R4 = (RadioButton) findViewById(R.id.radioButton4);
		R4.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (R4.isSelected() == true) {
					R4.setSelected(false);
					R4.setChecked(false);
					R4.setClickable(true);
					Toast.makeText(getApplicationContext(),
							R4.getText().toString() + "Unchecked",
							Toast.LENGTH_SHORT).show();
				} else {
					R4.setSelected(true);
					R4.setClickable(true);
					Toast.makeText(getApplicationContext(),
							R4.getText().toString() + "Checked",
							Toast.LENGTH_SHORT).show();
				}

				// Toast.makeText(getApplicationContext(), x.toString() ,
				// Toast.LENGTH_SHORT).show();
			}
		});
		R5 = (RadioButton) findViewById(R.id.radioButton5);
		R5.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (R5.isSelected() == true) {
					R5.setSelected(false);
					R5.setChecked(false);
					R5.setClickable(true);
					Toast.makeText(getApplicationContext(),
							R5.getText().toString() + "Unchecked",
							Toast.LENGTH_SHORT).show();
				} else {
					R5.setSelected(true);
					R5.setClickable(true);
					Toast.makeText(getApplicationContext(),
							R5.getText().toString() + "Checked",
							Toast.LENGTH_SHORT).show();
				}

				// Toast.makeText(getApplicationContext(), x.toString() ,
				// Toast.LENGTH_SHORT).show();
			}
		});
		R6 = (RadioButton) findViewById(R.id.radioButton6);
		R6.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (R6.isSelected() == true) {
					R6.setSelected(false);
					R6.setChecked(false);
					R6.setClickable(true);
					Toast.makeText(getApplicationContext(),
							R6.getText().toString() + "Unchecked",
							Toast.LENGTH_SHORT).show();
				} else {
					R6.setSelected(true);
					R6.setClickable(true);
					Toast.makeText(getApplicationContext(),
							R6.getText().toString() + "Checked",
							Toast.LENGTH_SHORT).show();
				}

				// Toast.makeText(getApplicationContext(), x.toString() ,
				// Toast.LENGTH_SHORT).show();
			}
		});
		*/
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
                    /*
                    String sw;
                    if(R1.isChecked() == true){
                        sw = "1";
                    }else{
                        sw ="0";
                    }
                    if(R2.isChecked() == true){
                        sw = sw+""+1;
                    }else{
                        sw = sw+""+0;
                    }
                    if(R3.isChecked() == true){
                        sw = sw+""+1;
                    }else{
                        sw = sw+""+0;
                    }
                    if(R4.isChecked() == true){
                        sw = sw+""+1;
                    }else{
                        sw = sw+""+0;
                    }
                    if(R5.isChecked() == true){
                        sw = sw+""+1;
                    }else{
                        sw = sw+""+0;
                    }
                    if(R6.isChecked() == true){
                        sw = sw+""+1;
                    }else{
                        sw = sw+""+0;
                    }
                    */
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
