package com.example.finalproject.Arduino;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalproject.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Non on 5/25/2015.
 */
public class SendArduUpdateValue extends AsyncTask<String, Void, Void> {
    private Socket socket;
    Context context;
    private ArrayList<HashMap<String, String>> massage;
    private static final int SERVERPORT = 8000;
    private static final String SERVER_IP = "192.168.4.1";

    public SendArduUpdateValue(Context c, ArrayList<HashMap<String, String>> listMsg) {
        context = c;
        massage = listMsg;
        //new MyClientTask().execute(massage);
    }

    ProgressDialog pd;
    String msg;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(context);
        pd.setTitle("Sending..");
        pd.setMessage("Updating  Values...");
        pd.setCancelable(true);
        pd.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pd.dismiss();
                cancel(true);
            }
        });
        pd.show();

        pd.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //socket.close();
                if (socket != null) {
                    try {
                        socket.close();
                        cancel(true);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    protected Void doInBackground(final String... arg0) {

        try {
            socket = null;
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

            socket = new Socket(serverAddr, SERVERPORT);
            try {

                for(int i =0;i<massage.size();i++) {
                    //out.println(str.substring(34,str.length()));
                    //out.println(str);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    char[] buffer = new char[2048];
                    int charsRead = 0;
                    if (massage.get(i).get("State").equals("1")) {

                                String str = "0"+i+"1";
                                PrintWriter out = null;

                                    out = new PrintWriter(new BufferedWriter(
                                            new OutputStreamWriter(socket.getOutputStream())),
                                            true);

                                out.println(str);


                    }
                }
                /*
                msg = null;
                while ((charsRead = in.read(buffer)) != -1) {
                    if (isCancelled())
                        break;
                    else {
                        // do your work here

                        String message = new String(buffer).substring(0, charsRead);
                        msg = message;
                        if (msg != null) {
                            return null;
                        }
                        Log.e("In While", "msg :" + message);
                    }

                }
                */

            } catch (UnknownHostException e) {
                e.printStackTrace();
                msg += "Send Error " + e.toString();
                Log.e("In While", "msg :" + msg);
                //socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                msg += "Send Error " + e.toString();
                Log.e("In While", "msg :" + msg);
            } catch (Exception e) {
                e.printStackTrace();
                msg += "Send Error " + e.toString();
                Log.e("In While", "msg :" + msg);
            }
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
            msg += "Connect Error " + e1.toString();
            Log.e("In While", "msg :" + msg);
        } catch (IOException e1) {
            e1.printStackTrace();
            msg += "Connect Error " + e1.toString();
            Log.e("In While", "msg :" + msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg += "Connect Error " + e.toString();
            Log.e("In While", "msg :" + msg);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        pd.dismiss();
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_layout);
        dialog.setTitle("Alert!!");
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("Update Done..");
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //dialog.
        dialog.show();


    }


}