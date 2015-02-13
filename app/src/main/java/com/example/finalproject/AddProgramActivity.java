package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddProgramActivity extends Activity {
	Button btnConSensor, btnConTime;
	TextView btnBack, btnHome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_program);

		btnConSensor = (Button) findViewById(R.id.btnControlSensor);
		btnConSensor.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Do stuff here
				Intent i = new Intent(AddProgramActivity.this,
						ControlSensor.class);
				startActivity(i);
			}
		});

		btnBack = (TextView) findViewById(R.id.tvBack);
		btnBack.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Do stuff here
				Intent i = new Intent(AddProgramActivity.this,
						ProgramActivity.class);
				startActivity(i);
			}
		});
		btnHome = (TextView) findViewById(R.id.tvHome);
		btnHome.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Do stuff here
				Intent i = new Intent(AddProgramActivity.this,
						MainActivity.class);
				startActivity(i);
			}
		});
	}

}
