package com.example.finalproject.Adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.Arduino.SendArdu;

import org.json.JSONArray;

public class ListProgramAdapter extends BaseAdapter{
	 	private Context context;
		private ArrayList<HashMap<String, String>> listProgram;
        SendArdu sendMassage;
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
                holder.tvMaxMois = (TextView)convertView.findViewById(R.id.textView4);
                holder.tvMinMois = (TextView)convertView.findViewById(R.id.textView5);
                holder.tvType = (TextView)convertView.findViewById(R.id.tv_type);
                holder.sw = (Switch)convertView.findViewById(R.id.mySwitch);
				convertView.setTag(holder);

			} else {
				holder = (ListProHolder)convertView.getTag();
				
			}

			holder.tvName.setText(listProgram.get(position).get("NamePro").toString());
			holder.tvMax.setText("อุณหภูมิเปิด :"+listProgram.get(position).get("MaxTemp").toString());
			holder.tvMin.setText("อุณหภูมิปิด :"+listProgram.get(position).get("MinTemp").toString());
            holder.tvMaxMois.setText("ความชื้นเปิด :"+listProgram.get(position).get("MaxMois").toString());
            holder.tvMinMois.setText("ความชื้นปิด :"+listProgram.get(position).get("MinMois").toString());
            if(listProgram.get(position).get("State").toString().equals("1")){
                holder.sw.setChecked(true);


            }

            holder.tvType.setText("Switch"+(position+1));

            holder.sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        Toast.makeText(context.getApplicationContext(),
                                "ON "+position,
                                Toast.LENGTH_SHORT).show();

                                String msgSend = "2"+position+"1"+
                                        listProgram.get(position).get("MaxTemp").toString()+
                                        listProgram.get(position).get("MinTemp").toString()+
                                        listProgram.get(position).get("MaxMois").toString()+
                                        listProgram.get(position).get("MinMois").toString();
                                SendArdu sendArdu = new SendArdu(context,msgSend);
                                sendArdu.execute(msgSend);
                        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("TestHashMap", 0);
                        SharedPreferences.Editor prefEditor = pref.edit();
                        listProgram.get(position).put("State","1");
                        JSONArray result = new JSONArray(listProgram);
                        prefEditor.putString("Secret", result.toString());
                        prefEditor.commit();
                                //new SendArdu(context,msgSend);

                    }else{
                        Toast.makeText(context.getApplicationContext(),
                                "OFF "+position,
                                Toast.LENGTH_SHORT).show();
                        String msgSend = "2"+position+"0";
                        SendArdu sendArdu = new SendArdu(context,msgSend);
                        sendArdu.execute(msgSend);
                        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("TestHashMap", 0);
                        SharedPreferences.Editor prefEditor = pref.edit();
                        listProgram.get(position).put("State","0");
                        JSONArray result = new JSONArray(listProgram);
                        prefEditor.putString("Secret", result.toString());
                        prefEditor.commit();
                    }

                }
            });
	        return convertView;
				
		}
		
		public class ListProHolder {
			TextView tvName;
			TextView tvMax;
			TextView tvMin;
            TextView tvMaxMois;
            TextView tvMinMois;
            TextView tvType;
            Switch sw;
		}
}
