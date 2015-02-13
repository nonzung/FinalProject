package com.example.finalproject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
import android.widget.ListView;
//import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class OnOffProgram extends Activity {
	ListView listview;
	ListProgramAdapter adapter;
    public static ArrayList<HashMap<String, String>> collection,collections;
    SharedPreferences pref = getApplicationContext().getSharedPreferences("TestHashMap", 0);
    SharedPreferences.Editor prefEditor = pref.edit();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_on_off_program);
		listview = (ListView)findViewById(R.id.list);

		/*if(MainActivity.ListProgram.size()>0){
			adapter = new ListProgramAdapter(this,MainActivity.ListProgram);
			listview.setAdapter(adapter);
			
		}else{
			Toast.makeText(getApplicationContext(),
					"ไม่มีข้อมูล",
					Toast.LENGTH_SHORT).show();
		}
		*/
        String storedCollection = pref.getString("Secret", null);
        collections = new ArrayList<HashMap<String, String>>();
        try {
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

        } catch (JSONException e) {
            Log.e("Restore", "while parsing", e);
        }
        adapter = new ListProgramAdapter(OnOffProgram.this,collections);
        listview.setAdapter(adapter);

	}

}
