package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ControlSensor extends Activity {
	Button ByTemp, ByMoisture;
	TextView tvBack, tvHome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control_sensor);
		
		ByTemp = (Button) findViewById(R.id.btnByTemp);
		ByTemp.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Do stuff here
				Intent i = new Intent(ControlSensor.this, ByTemp.class);
				startActivity(i);
			}
		});
		
		ByMoisture = (Button) findViewById(R.id.btnByMoisture);
		ByMoisture.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Do stuff here
				Intent i = new Intent(ControlSensor.this, ByMoisture.class);
				startActivity(i);
			}
		});
		
		tvBack = (TextView) findViewById(R.id.tvBack);
		tvBack.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Do stuff here
				Intent i = new Intent(ControlSensor.this, AddProgramActivity.class);
				startActivity(i);
			}
		});
		tvHome = (TextView) findViewById(R.id.tvHome);
		tvHome.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Do stuff here
				Intent i = new Intent(ControlSensor.this, MainActivity.class);
				startActivity(i);
			}
		});

	}

}
