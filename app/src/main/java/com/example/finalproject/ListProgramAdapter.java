package com.example.finalproject;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListProgramAdapter extends BaseAdapter{
	 	private Context context;
		private ArrayList<HashMap<String, String>> listProgram;

	    public ListProgramAdapter(Context c,ArrayList<HashMap<String, String>> ls) 
	    {
	        context = c;
	        listProgram = ls;

	    }
	    
		@Override
		public int getCount() {

			return listProgram.size();
		}

		@Override
		public Object getItem(int position) {

			return position;
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		//@SuppressLint("ResourceAsColor")
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {

			
			ListProHolder holder = null;

			if (convertView == null) {
				
				LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.listprogram_adapter, null);
				//convertView.setMinimumHeight((int)(70*MainActivity.density));

				holder = new ListProHolder();
				holder.tvName = (TextView)convertView.findViewById(R.id.textView1);
				holder.tvMax = (TextView)convertView.findViewById(R.id.textView2);
				holder.tvMin = (TextView)convertView.findViewById(R.id.textView3);
				convertView.setTag(holder);
				
			} else {
				holder = (ListProHolder)convertView.getTag();
				
			}

			holder.tvName.setText(listProgram.get(position).get("NamePro").toString());
			holder.tvMax.setText(listProgram.get(position).get("MaxTemp").toString());
			holder.tvMin.setText(listProgram.get(position).get("MinTemp").toString());
	        return convertView;
				
		}
		
		public class ListProHolder {
			TextView tvName;
			TextView tvMax;
			TextView tvMin;
		}
}
