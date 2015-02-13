package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ProgramActivity extends Activity {
	Button btnAdd,btnEdit,btnDel,btnBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_program);
		
		btnAdd = (Button)findViewById(R.id.btnAddProgram);
		btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Do stuff here
            Intent i = new Intent(ProgramActivity.this, AddProgramActivity.class);
            startActivity(i);
        }
    });
		btnBack = (Button)findViewById(R.id.btnBackHome);
		btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Do stuff here
            Intent i = new Intent(ProgramActivity.this, MainActivity.class);
            startActivity(i);
        }
    });
	}
}
