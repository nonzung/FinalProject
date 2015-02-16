package com.example.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
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
    //SharedPreferences pref;
    //SharedPreferences.Editor prefEditor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_on_off_program);
		listview = (ListView)findViewById(R.id.list);
        //pref = getApplicationContext().getSharedPreferences("TestHashMap", 0);
        //prefEditor = pref.edit();
		/*if(MainActivity.ListProgram.size()>0){
			adapter = new ListProgramAdapter(this,MainActivity.ListProgram);
			listview.setAdapter(adapter);
			
		}else{
			Toast.makeText(getApplicationContext(),
					"ไม่มีข้อมูล",
					Toast.LENGTH_SHORT).show();
		}
		*/
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OnOffProgram.this);

                // set title
                alertDialogBuilder.setTitle("แก้ไขหรือลบ");

                // set dialog message
                alertDialogBuilder
                        //.setMessage("Click yes to exit!")
                        .setCancelable(true)
                        .setPositiveButton("แก้ไข",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                Intent in = new Intent(OnOffProgram.this,ByTemp.class);
                                in.putExtra("posEdit",position);
                                in.putExtra("NamePro", collections.get(position).get("NamePro"));
                                in.putExtra("MaxTemp",collections.get(position).get("MaxTemp"));
                                in.putExtra("MinTemp",collections.get(position).get("MinTemp"));
                                //in.putExtra("Sex",collections.get(position).get("Sex"));
                                startActivityForResult(in,202);
                            }
                        })
                        .setNegativeButton("ลบ",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                //dialog.cancel();
                                collections.remove(position);
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("TestHashMap", 0);
                                SharedPreferences.Editor prefEditor = pref.edit();
                                JSONArray result= new JSONArray(collections);
                                prefEditor.putString("Secret", result.toString());
                                prefEditor.commit();
                                adapter.notifyDataSetChanged();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });
        SharedPreferences pref = getApplicationContext().getSharedPreferences("TestHashMap", 0);
        //SharedPreferences.Editor prefEditor = pref.edit();
        collections = new ArrayList<HashMap<String, String>>();
        try {
            String storedCollection = pref.getString("Secret", null);
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
        adapter = new ListProgramAdapter(OnOffProgram.this,collections);
        listview.setAdapter(adapter);

	}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(MainActivity.this, ""+resultCode, Toast.LENGTH_SHORT).show();
        if(resultCode == RESULT_OK){
            int pos = data.getExtras().getInt("PosEdit");
            if(pos >= 0){
                collections.remove(pos);
                SharedPreferences pref = getApplicationContext().getSharedPreferences("TestHashMap", 0);
                SharedPreferences.Editor prefEditor = pref.edit();
                JSONArray result= new JSONArray(collections);
                prefEditor.putString("Secret", result.toString());
                prefEditor.commit();
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Data has been Deleted.", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Saving OK", Toast.LENGTH_SHORT).show();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("TestHashMap", 0);
                SharedPreferences.Editor prefEditor = pref.edit();
                JSONArray result= new JSONArray(collections);
                prefEditor.putString("Secret", result.toString());
                prefEditor.commit();
                adapter.notifyDataSetChanged();
            }
        }
    }

}
