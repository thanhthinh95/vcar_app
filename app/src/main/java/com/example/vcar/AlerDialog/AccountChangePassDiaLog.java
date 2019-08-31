package com.example.vcar.AlerDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.entity.Customer;
import com.example.entity.Message;
import com.example.vcar.FunctionSystem;
import com.example.vcar.MainActivity;
import com.example.vcar.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AccountChangePassDiaLog {
    private Context context;
    Customer customer;
    private FunctionSystem functionSystem;
    EditText edit_old_pass, edit_new_pass, edit_repeat_pass;



    private Button btn_ok, btn_back;
    private  AlertDialog alertDialog;

    public AccountChangePassDiaLog(Context context) {
        this.context = context;
        functionSystem = new FunctionSystem(context);
    }

    public void show(){
        if(context != null){
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            final View view = inflater.inflate(R.layout.layout_acount_changepass, null);
            addControls(view);
            addEvents();
            builder.setView(view);

            alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(true);
            alertDialog.show();
        }else{
            functionSystem.showDialogError(context.getResources().getString(R.string.dialog_info_car_error_show));
        }
    }



    private void addControls(View view) {
        btn_ok = view.findViewById(R.id.btn_account_done);
        btn_back = view.findViewById(R.id.btn_account_back);
        edit_old_pass = view.findViewById(R.id.edit_account_old_pass);
        edit_new_pass = view.findViewById(R.id.edit_account_new_pass);
        edit_repeat_pass = view.findViewById(R.id.edit_account_repeat_pass);
    }


    private void addEvents() {
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("action", "updatePass");
                        jsonObject.put("_id", MainActivity.idCustomer);
                        jsonObject.put("oldPass", edit_old_pass.getText().toString());
                        jsonObject.put("newPass", edit_new_pass.getText().toString());
                        new updateCustomerPasswordAPI().execute(jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    private boolean validate() {
        if(edit_new_pass.getText().length() < 6 ||
                edit_repeat_pass.getText().length() < 6 ||
                edit_old_pass.getText().length() < 6){
            functionSystem.showDialogError(context.getResources().getString(R.string.message_password_invalid));
            return false;
        }else if(!edit_new_pass.getText().toString().equals(edit_repeat_pass.getText().toString())){
            functionSystem.showDialogError(context.getResources().getString(R.string.message_password_repeat_invalid));
            return false;
        }
        return true;
    }



    private class updateCustomerPasswordAPI extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            functionSystem.showLoading();
        }

        protected String doInBackground(String... params) {
            return functionSystem.postMethod(context.getResources().getString(R.string.host_api).toString() + "customer", params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            functionSystem.hideLoading();
            Message message = new Message(result);
            if(message.getCode() == 200){
                alertDialog.dismiss();
                functionSystem.showDialogSuccess("Thành công");
            }else{
                functionSystem.showDialogError(message.getMessage());
            }
        }
    }
}
