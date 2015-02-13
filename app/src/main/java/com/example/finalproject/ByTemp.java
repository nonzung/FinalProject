package com.example.finalproject;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class ByTemp extends Activity {
	RadioButton R1, R2, R3, R4, R5, R6;
	TextView tvBack, tvAdd;
	EditText etName,etMax,etMin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_by_temp);

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
		etName = (EditText)findViewById(R.id.etName);
		etMax = (EditText)findViewById(R.id.etMaxTemp);
		etMin = (EditText)findViewById(R.id.etMinTemp);
		
		tvBack = (TextView) findViewById(R.id.tvBack);
		tvBack.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Do stuff here
				Intent i = new Intent(ByTemp.this, ControlSensor.class);
				startActivity(i);
			}
		});
		tvAdd = (TextView) findViewById(R.id.tvAdd);
		
		MainActivity.ListProgram = new ArrayList<HashMap<String,String>>();
		tvAdd.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Do stuff here
				//Intent i = new Intent(ByTemp.this, MainActivity.class);
				//startActivity(i);
				//MainActivity.ListProgram = new ArrayList<HashMap<String,String>>();
				HashMap<String,String> Listpro;
				Listpro = new HashMap<String,String>();
				
				Listpro.put("NamePro", etName.getText().toString());
				Listpro.put("MaxTemp", etMax.getText().toString());
				Listpro.put("MinTemp", etMin.getText().toString());
				
				MainActivity.ListProgram.add(Listpro);
				
			}
		});

	}

}
