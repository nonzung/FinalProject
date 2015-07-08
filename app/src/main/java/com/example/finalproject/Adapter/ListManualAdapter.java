package com.example.finalproject.Adapter;

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

import com.example.finalproject.Arduino.SendToArdu;
import com.example.finalproject.R;
import com.example.finalproject.Arduino.SendArdu;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Non on 4/17/2015.
 */
public class ListManualAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<HashMap<String, String>> listProgram;

    public ListManualAdapter(Context c,ArrayList<HashMap<String, String>> ls)
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_manual, null);
            //convertView.setMinimumHeight((int)(70*MainActivity.density));

            holder = new ViewHolder();
            holder.tvName = (TextView)convertView.findViewById(R.id.tv_type);
            holder.sw = (Switch)convertView.findViewById(R.id.mySwitch);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder)convertView.getTag();

        }
        holder.tvName.setText("Switch"+(position+1));

        if(listProgram.get(position).get("State").toString().equals("1")){
            holder.sw.setChecked(true);
            //String msgSend = "0"+position+"1";
            //SendArdu sendArdu = new SendArdu(context,msgSend);
            //sendArdu.execute(msgSend);
        }

        holder.sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(context.getApplicationContext(),
                            "ON " + position,
                            Toast.LENGTH_SHORT).show();

                    String msgSend = "0"+position+"1";
                    /*
                    SendArdu sendArdu = new SendArdu(context,msgSend);
                    sendArdu.execute(msgSend);
                    */
                    SendToArdu.send(msgSend, "192.168.4.1", 8000, new SendToArdu.SendCallback() {
                        public void onSuccess(String tag) {
                            Toast.makeText(context, "onSuccess", Toast.LENGTH_SHORT).show();

                        }
                        public void onFailed(String tag) {
                            Toast.makeText(context, "onFailed", Toast.LENGTH_SHORT).show();

                        }
                    }, "TAG");


                    SharedPreferences pref = context.getApplicationContext().getSharedPreferences("TestHashMap", 0);
                    SharedPreferences.Editor prefEditor = pref.edit();
                    listProgram.get(position).put("State","1");
                    JSONArray result = new JSONArray(listProgram);
                    prefEditor.putString("Manual", result.toString());
                    prefEditor.commit();
                    //new SendArdu(context,msgSend);

                }else{
                    Toast.makeText(context.getApplicationContext(),
                            "OFF "+position,
                            Toast.LENGTH_SHORT).show();

                    String msgSend = "0"+position+"0";
                    /*
                    SendArdu sendArdu = new SendArdu(context,msgSend);
                    sendArdu.execute(msgSend);
                    */
                    SendToArdu.send(msgSend, "192.168.4.1", 8000, new SendToArdu.SendCallback() {
                        public void onSuccess(String tag) {
                            Toast.makeText(context, "onSuccess", Toast.LENGTH_SHORT).show();

                        }
                        public void onFailed(String tag) {
                            Toast.makeText(context, "onFailed", Toast.LENGTH_SHORT).show();

                        }
                    }, "TAG");

                    SharedPreferences pref = context.getApplicationContext().getSharedPreferences("TestHashMap", 0);
                    SharedPreferences.Editor prefEditor = pref.edit();
                    listProgram.get(position).put("State","0");
                    JSONArray result = new JSONArray(listProgram);
                    prefEditor.putString("Manual", result.toString());
                    prefEditor.commit();
                }

            }
        });

        return convertView;
    }
    public class ViewHolder {
        TextView tvName;
        Switch sw;
    }
}
