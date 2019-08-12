package com.example.vcar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


public class FunctionSystem {
    private Context context;

    public FunctionSystem(){}
    public FunctionSystem(final Context context){
        this.context = context;
    }



    public boolean isNetworkAvailable() {
        if(this.context == null) return  false;
        final ConnectivityManager connectivityManager = ((ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public void showDialogSuccess(final String value){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        final LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
        final View view = inflater.inflate(R.layout.layout_dialog_error, null);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        ((TextView) view.findViewById(R.id.txt_dialog_error)).setText(value);
        view.findViewById(R.id.btn_dialog_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
            alertDialog.dismiss();
            }
        });

    }
}
