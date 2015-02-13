package com.example.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class OnOffProgram extends Activity {
	ListView listview;
	ListProgramAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_on_off_program);
		listview = (ListView)findViewById(R.id.list);
		if(MainActivity.ListProgram.size()>0){
			adapter = new ListProgramAdapter(this,MainActivity.ListProgram);
			listview.setAdapter(adapter);
			
		}else{
			Toast.makeText(getApplicationContext(),
					"ไม่มีข้อมูล",
					Toast.LENGTH_SHORT).show();
		}
		
	}

}
