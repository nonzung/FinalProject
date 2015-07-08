package com.example.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;


public class AddTime extends Activity {
    private TextView tvDisplayTimeStart, tvDisplayTimeStop;
    private TimePicker timePicker1;
    private Button btnChangeTimeStart, btnChangeTimeStop;
    private RadioGroup radioGroup, radioGroup2;
    private int hour;
    private int minute;
    private int chkStat;
    static final int TIME_DIALOG_ID = 999;
    RadioButton radioDayButton, radioSwButton;
    int selectedId, selectedId2;
    TextView tvAdd;
    int idDay, idSw;
    public int posEdit;
    public static ArrayList<HashMap<String, String>> collection, collections;
    Bundle extras = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time);

        setCurrentTimeOnView();
        addListenerOnButton();
        extras = getIntent().getExtras();

        radioGroup = (RadioGroup) findViewById(R.id.radio);
        idDay = -1;
        idSw = -1;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                //    Toast.makeText(getApplicationContext(), "choice: "+checkedId,
                //            Toast.LENGTH_SHORT).show();
                selectedId = radioGroup.getCheckedRadioButtonId();
                radioDayButton = (RadioButton) findViewById(selectedId);
                idDay = group.indexOfChild(radioDayButton);
                Toast.makeText(AddTime.this,
                        "" + radioDayButton.getText(), Toast.LENGTH_SHORT).show();

            }

        });
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                //    Toast.makeText(getApplicationContext(), "choice: "+checkedId,
                //            Toast.LENGTH_SHORT).show();
                selectedId2 = radioGroup2.getCheckedRadioButtonId();
                radioSwButton = (RadioButton) findViewById(selectedId2);
                idSw = group.indexOfChild(radioSwButton);
                Toast.makeText(AddTime.this,
                        "" + radioSwButton.getText(), Toast.LENGTH_SHORT).show();

            }

        });

        tvAdd = (TextView) findViewById(R.id.tvAdd);
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (extras == null) {
                    if (idDay >= 0 && idSw >= 0) {

                        SharedPreferences pref = getApplicationContext().getSharedPreferences("TestHashMap", 0);
                        SharedPreferences.Editor prefEditor = pref.edit();
                        collection = new ArrayList<HashMap<String, String>>();
                        String storedCollection = pref.getString("Time", null);
                        try {
                            JSONArray array = new JSONArray(storedCollection);
                            HashMap<String, String> item = null;
                            for (int i = 0; i < array.length(); i++) {
                                String obj = array.get(i).toString();
                                JSONObject ary = new JSONObject(obj);
                                Iterator<String> it = ary.keys();
                                item = new HashMap<String, String>();
                                while (it.hasNext()) {
                                    String key = it.next();
                                    item.put(key, (String) ary.get(key).toString());
                                }
                                collection.add(item);
                            }
                        } catch (Exception e) {
                            Log.e("Restore", "while parsing", e);
                        }

                        HashMap<String, String> Listpro;
                        Listpro = new HashMap<String, String>();

                        Listpro.put("Start", tvDisplayTimeStart.getText().toString().replace(":","."));
                        Listpro.put("Stop", tvDisplayTimeStop.getText().toString().replace(":","."));
                        Listpro.put("Switch", String.valueOf(idSw));
                        Listpro.put("DayOfWeeks", String.valueOf(idDay));
                        Listpro.put("State", "0");
                        collection.add(Listpro);
                        JSONArray result = new JSONArray(collection);
                        prefEditor.putString("Time", result.toString());
                        prefEditor.commit();
                        //OnOffTime.adapter.notifyDataSetChanged();
                        Intent in = new Intent();
                        //in.putExtra("PosEdit", -1);
                        setResult(RESULT_OK, in);

                        finish();

                    } else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                AddTime.this);

                        // set title
                        alertDialogBuilder.setTitle("กรุณากรอกข้อมูลให้ครบ!!");
                        //alertDialogBuilder.setIcon(R.drawable.)
                        // set dialog message
                        alertDialogBuilder
                                .setMessage("ทำต่อกด Yes/ยกเลิก กด No")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        //AddTime.this.finish();
                                        dialog.cancel();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        finish();
                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }
                }else{
                    if (idDay >= 0 && idSw >= 0) {
                        OnOffTime.collections.get(posEdit).put("Start", tvDisplayTimeStart.getText().toString().toString().replace(":","."));
                        OnOffTime.collections.get(posEdit).put("Stop", tvDisplayTimeStop.getText().toString().toString().replace(":","."));
                        OnOffTime.collections.get(posEdit).put("Switch", String.valueOf(idSw));
                        OnOffTime.collections.get(posEdit).put("DayOfWeeks", String.valueOf(idDay));
                        Intent in = new Intent();
                        setResult(RESULT_OK, in);
                        finish();
                    }else{
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                AddTime.this);

                        // set title
                        alertDialogBuilder.setTitle("กรุณากรอกข้อมูลให้ครบ!!");
                        //alertDialogBuilder.setIcon(R.drawable.)
                        // set dialog message
                        alertDialogBuilder
                                .setMessage("ทำต่อกด Yes/ยกเลิก กด No")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        //AddTime.this.finish();
                                        dialog.cancel();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        finish();
                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }
                }

            }
        });
        if(extras != null) {
            posEdit = extras.getInt("posEdit");
            tvDisplayTimeStart.setText(extras.getString("Start").toString().replace(".",":"));
            tvDisplayTimeStop.setText(extras.getString("Stop").toString().replace(".",":"));
            radioGroup.getChildAt(Integer.valueOf(extras.getString("DayOfWeeks"))).setClickable(true);
            radioGroup2.getChildAt(Integer.valueOf(extras.getString("Switch"))).setClickable(true);
            //radioGroup.chil
            //radioGroup.
            //etName.setText(extras.getString("NamePro"));
            //etTempMax.setText(extras.getString("MaxTemp"));
            //etTempMin.setText(extras.getString("MinTemp"));
            //etMoisMax.setText(extras.getString("MaxMois"));
            //etMoisMin.setText(extras.getString("MinMois"));
            tvAdd.setText("Edit");
        }
    }


    public void setCurrentTimeOnView() {

        tvDisplayTimeStart = (TextView) findViewById(R.id.tvTime);
        tvDisplayTimeStop = (TextView) findViewById(R.id.tvTimeStop);
        //timePicker1 = (TimePicker) findViewById(R.id.timePicker1);

        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        // set current time into textview
        if(extras == null) {
            tvDisplayTimeStart.setText(
                    new StringBuilder().append(pad(hour))
                            .append(":").append(pad(minute)));
            tvDisplayTimeStop.setText(
                    new StringBuilder().append(pad(hour))
                            .append(":").append(pad(minute)));
        }
        // set current time into timepicker
        // timePicker1.setCurrentHour(hour);
        // timePicker1.setCurrentMinute(minute);

    }

    public void addListenerOnButton() {

        btnChangeTimeStart = (Button) findViewById(R.id.btnChangeTimeStart);

        btnChangeTimeStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(TIME_DIALOG_ID);
                chkStat = 1;

            }

        });
        btnChangeTimeStop = (Button) findViewById(R.id.btnChangeTimeStop);

        btnChangeTimeStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(TIME_DIALOG_ID);
                chkStat = 2;
            }

        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                // set time picker as current time
                return new TimePickerDialog(this,
                        timePickerListener, hour, minute, false);

        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;

                    // set current time into textview
                    if (chkStat == 1) {
                        tvDisplayTimeStart.setText(new StringBuilder().append(pad(hour))
                                .append(":").append(pad(minute)));
                    } else if (chkStat == 2) {
                        tvDisplayTimeStop.setText(new StringBuilder().append(pad(hour))
                                .append(":").append(pad(minute)));
                    }
                    // set current time into timepicker
                    //timePicker1.setCurrentHour(hour);
                    //timePicker1.setCurrentMinute(minute);

                }
            };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
}
