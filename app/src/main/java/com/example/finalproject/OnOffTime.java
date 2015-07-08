package com.example.finalproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.finalproject.Adapter.ListProgramDateTimeAdapter;
import com.example.finalproject.Arduino.SendArdu;
import com.example.finalproject.Arduino.SendToArdu;

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
                //Intent i = new Intent(OnOffTime.this,OnOffProgram.class);
                finish();
                //startActivity(i);
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
            //Toast.makeText(OnOffTime.this,array.toString(),
            //        Toast.LENGTH_LONG).show();
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
            Toast.makeText(OnOffTime.this, "Update Value Started....", Toast.LENGTH_SHORT).show();
            new UpDateValue().execute();
        }

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
                            item.put(key, (String)ary.get(key).toString());
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
        //Intent i = new Intent(this,OnOffProgram.class);
        finish();
        //startActivity(i);
    }
    class UpDateValue extends AsyncTask<Void,Void,Void>{
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(OnOffTime.this);
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
                        String start = collections.get(position).get("Start").toString().substring(0, 2) + collections.get(position).get("Start").toString().substring(3, collections.get(position).get("Start").toString().length());
                        String stop = collections.get(position).get("Stop").toString().substring(0, 2) + collections.get(position).get("Stop").toString().substring(3, collections.get(position).get("Stop").toString().length());
                        String msgSend = "11" + start + stop +
                                collections.get(position).get("Switch").toString() +
                                collections.get(position).get("DayOfWeeks").toString() + position;
                        SendToArdu.send(msgSend, "192.168.4.1", 8000, new SendToArdu.SendCallback() {
                            public void onSuccess(String tag) {
                                Toast.makeText(OnOffTime.this, "onSuccess", Toast.LENGTH_SHORT).show();

                            }

                            public void onFailed(String tag) {
                                Toast.makeText(OnOffTime.this, "onFailed", Toast.LENGTH_SHORT).show();

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
                adapter = new ListProgramDateTimeAdapter(OnOffTime.this, collections);
                listview.setAdapter(adapter);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
