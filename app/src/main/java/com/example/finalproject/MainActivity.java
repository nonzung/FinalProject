package com.example.finalproject;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button btnProgram,btnOnOff,btnManual,btnSensor;
	public static ArrayList<HashMap<String, String>> ListProgram;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //SharedPreferences pref = getApplicationContext().getSharedPreferences("TestHashMap", 0);
        //SharedPreferences.Editor prefEditor = pref.edit();
        //prefEditor.putString("Secret", null);
        //prefEditor.commit();
        //MainActivity.ListProgram = new ArrayList<HashMap<String,String>>();
        btnProgram = (Button)findViewById(R.id.btnprogram);
        btnProgram.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Do stuff here
            Intent i = new Intent(MainActivity.this, ProgramActivity.class);
            startActivity(i);
        }
    });
        btnOnOff = (Button)findViewById(R.id.btnOnOff);
        btnOnOff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Do stuff here
            Intent i = new Intent(MainActivity.this, OnOffProgram.class);
            startActivity(i);
        }
    });
    }
}
