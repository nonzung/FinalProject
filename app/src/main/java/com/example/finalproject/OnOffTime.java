package com.example.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class OnOffTime extends ActionBarActivity {
    ListView listview;
    public static ListProgramDateTimeAdapter adapter;
    public static ArrayList<HashMap<String, String>> collection,collections;
    private Button btnAddTime,btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_off_time);
        btnBack = (Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OnOffTime.this,OnOffProgram.class);
                finish();
                startActivity(i);
            }
        });
        listview = (ListView) findViewById(R.id.listTime);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OnOffTime.this);

                // set title
                alertDialogBuilder.setTitle("·°È‰¢");

                // set dialog message
                alertDialogBuilder
                        //.setMessage("Click yes to exit!")
                        .setCancelable(true)
                        .setPositiveButton("·°È‰¢",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                Intent in = new Intent(OnOffTime.this,AddTime.class);
                                in.putExtra("posEdit",position);
                                in.putExtra("Start", collections.get(position).get("Start"));
                                in.putExtra("Stop",collections.get(position).get("Stop"));
                                in.putExtra("Switch",collections.get(position).get("Switch"));
                                in.putExtra("DayOfWeeks",collections.get(position).get("DayOfWeeks"));
                                //in.putExtra("Sex",collections.get(position).get("Sex"));
                                startActivityForResult(in,202);
                            }
                        })
                       .setNegativeButton("≈∫",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                //dialog.cancel();
                                collections.remove(position);
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("TestHashMap", 0);
                                SharedPreferences.Editor prefEditor = pref.edit();
                                JSONArray result= new JSONArray(collections);
                                prefEditor.putString("Time", result.toString());
                                prefEditor.commit();
                                adapter.notifyDataSetChanged();
                                Toast.makeText(OnOffTime.this, "Data has been Deleted.", Toast.LENGTH_SHORT).show();

                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });
        btnAddTime = (Button)findViewById(R.id.btnAddTime);
        btnAddTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OnOffTime.this,AddTime.class);
                startActivityForResult(i,200);
            }
        });

        SharedPreferences pref = getApplicationContext().getSharedPreferences("TestHashMap", 0);
        //SharedPreferences.Editor prefEditor = pref.edit();
        collections = new ArrayList<HashMap<String, String>>();
        try {
            String storedCollection = pref.getString("Time", null);
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
        adapter = new ListProgramDateTimeAdapter(OnOffTime.this,collections);
        listview.setAdapter(adapter);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(MainActivity.this, ""+resultCode, Toast.LENGTH_SHORT).show();
        if(resultCode == RESULT_OK){
            //int pos = data.getExtras().getInt("PosEdit");
            if(requestCode == 202){
                Toast.makeText(this, "Saving OK", Toast.LENGTH_SHORT).show();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("TestHashMap", 0);
                SharedPreferences.Editor prefEditor = pref.edit();
                JSONArray result= new JSONArray(collections);
                prefEditor.putString("Time", result.toString());
                prefEditor.commit();
                adapter.notifyDataSetChanged();
            }else if(requestCode == 200) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("TestHashMap", 0);
                //SharedPreferences.Editor prefEditor = pref.edit();
                collections = new ArrayList<HashMap<String, String>>();
                try {
                    String storedCollection = pref.getString("Time", null);
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
                adapter = new ListProgramDateTimeAdapter(OnOffTime.this,collections);
                listview.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this,OnOffProgram.class);
        finish();
        startActivity(i);
    }
}
