package com.example.vcar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.entity.Message;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class FunctionSystem {
    private Context context;
    private AlertDialog alertDialogLoading = null;

    public FunctionSystem(final Context context){
        this.context = context;
    }

    public String getAddress(){
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return null;
    }

    public boolean isNetworkAvailable() {
        if(this.context == null) return  false;
        final ConnectivityManager connectivityManager = ((ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public void showDialogMessage(String dataJsonFromService){
         Message message = new Message(dataJsonFromService);
         if(message.getCode() == 200){
             showDialogSuccess(message.getMessage());
         }else if(message.getCode() == 500){
             showDialogError(message.getMessage());
         }
    }

    public void showDialogSuccess(final String value){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        final LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
        final View view = inflater.inflate(R.layout.layout_dialog_success, null);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();

        ((TextView) view.findViewById(R.id.txt_dialog_success)).setText(value);
        view.findViewById(R.id.btn_dialog_success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
            alertDialog.dismiss();
            }
        });
    }

    public void showDialogError(final String value){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        final LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
        final View view = inflater.inflate(R.layout.layout_dialog_error, null);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);

        alertDialog.show();

        ((TextView) view.findViewById(R.id.txt_dialog_error)).setText(value);
        view.findViewById(R.id.btn_dialog_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                alertDialog.dismiss();
            }
        });
    }

    public void showLoading(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_loading, null);

        alertDialogLoading = builder.create();
        alertDialogLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialogLoading.setView(view, 0,0,0,0);

        alertDialogLoading.setCanceledOnTouchOutside(false);
        alertDialogLoading.setCancelable(false);
        alertDialogLoading.show();
    }

    public void hideLoading(){
        if(alertDialogLoading != null){
            alertDialogLoading.dismiss();
            alertDialogLoading = null;
        }
    }


    public String getMethod(String urlString){
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL (urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(R.integer.timeout_server);
            urlConnection.setRequestMethod("GET");

            urlConnection.connect();
            InputStream stream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
            }

            urlConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }


    public String postMethod(String urlString, String data){
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL (urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(R.integer.timeout_server);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type","application/json");

            DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
            outputStream.writeBytes(data);
            outputStream.flush();
            outputStream.close();

            urlConnection.connect();

            InputStream stream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
            }

            urlConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }



}
