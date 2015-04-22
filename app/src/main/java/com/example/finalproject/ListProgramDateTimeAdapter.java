package com.example.finalproject;

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

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Non on 3/16/2015.
 */
public class ListProgramDateTimeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<HashMap<String, String>> listProgram;
    String[] Day = {"อาทิตย์","จันทร์","อังคาร","พุทธ","พฤหัสบดี","ศุกร์","เสาร์"};

    public ListProgramDateTimeAdapter(Context c,ArrayList<HashMap<String, String>> ls)
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
            holder.tvStart = (TextView)convertView.findViewById(R.id.textView2);
            holder.tvStop = (TextView)convertView.findViewById(R.id.textView3);
            holder.tvSwitch = (TextView)convertView.findViewById(R.id.textView4);
            holder.tvDayOfWeeks = (TextView)convertView.findViewById(R.id.textView5);
            holder.tvType = (TextView)convertView.findViewById(R.id.tv_type);
            holder.sw = (Switch)convertView.findViewById(R.id.mySwitch);
            convertView.setTag(holder);

        } else {
            holder = (ListProHolder)convertView.getTag();

        }

        holder.tvName.setText("" + (position + 1));
        holder.tvStart.setText("Start :"+listProgram.get(position).get("Start").toString());
        holder.tvStop.setText("Stop :"+listProgram.get(position).get("Stop").toString());
        holder.tvSwitch.setText("Switch :"+(Integer.valueOf(listProgram.get(position).get("Switch").toString())+1));
        holder.tvDayOfWeeks.setText("DayOfWeeks :"+Day[Integer.valueOf(listProgram.get(position).get("DayOfWeeks").toString())]);

        if(listProgram.get(position).get("State").toString().equals("1")){
            holder.sw.setChecked(true);
        }
        //holder.tvType.setText("Switch"+(position+1));

        holder.sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    String start = listProgram.get(position).get("Start").toString().substring(0,2)+listProgram.get(position).get("Start").toString().substring(3, listProgram.get(position).get("Start").toString().length());
                    String stop = listProgram.get(position).get("Stop").toString().substring(0,2)+listProgram.get(position).get("Stop").toString().substring(3,listProgram.get(position).get("Stop").toString().length());
                    String msgSend = "11"+start+stop+
                            listProgram.get(position).get("Switch").toString()+
                            listProgram.get(position).get("DayOfWeeks").toString()+position;
                    SendArdu sendArdu = new SendArdu(context,msgSend);
                    sendArdu.execute(msgSend);

                    Toast.makeText(context.getApplicationContext(),
                            "ON " + position,
                            Toast.LENGTH_SHORT).show();
                    SharedPreferences pref = context.getApplicationContext().getSharedPreferences("TestHashMap", 0);
                    SharedPreferences.Editor prefEditor = pref.edit();
                    listProgram.get(position).put("State","1");
                    JSONArray result = new JSONArray(listProgram);
                    prefEditor.putString("Time", result.toString());
                    prefEditor.commit();

                }else{
                    String msgSend = "100000000000"+position;
                    SendArdu sendArdu = new SendArdu(context,msgSend);
                    sendArdu.execute(msgSend);
                    Toast.makeText(context.getApplicationContext(),
                            "OFF "+position,
                            Toast.LENGTH_SHORT).show();
                    SharedPreferences pref = context.getApplicationContext().getSharedPreferences("TestHashMap", 0);
                    SharedPreferences.Editor prefEditor = pref.edit();
                    listProgram.get(position).put("State","0");
                    JSONArray result = new JSONArray(listProgram);
                    prefEditor.putString("Time", result.toString());
                    prefEditor.commit();
                }

            }
        });
        return convertView;

    }

    public class ListProHolder {
        TextView tvStart;
        TextView tvStop;
        TextView tvSwitch;
        TextView tvDayOfWeeks;
        TextView tvName;
        TextView tvType;
        Switch sw;
    }
}
