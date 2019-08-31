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

public class AccountInfoDiaLog {
    private Context context;
    Customer customer;
    private FunctionSystem functionSystem;
    EditText edit_name, edit_number, edit_email;
    Spinner sp_gender;

    int gender = 0;

    private Button btn_ok, btn_back;
    private  AlertDialog alertDialog;

    public AccountInfoDiaLog(Context context) {
        this.context = context;
        functionSystem = new FunctionSystem(context);
    }

    public void show(){
        if(context != null){
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            final View view = inflater.inflate(R.layout.layout_acount_info, null);
            addControls(view);
            setValue();
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

    private void setValue() {
        List<String> list = new ArrayList<>();
        list.add("Nữ");
        list.add("Nam");

        ArrayAdapter<String> adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        sp_gender.setAdapter(adapter);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("action", "getInfo");
            jsonObject.put("_id", MainActivity.idCustomer);
            new getCustomerInfoAPI().execute(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void addControls(View view) {
        btn_ok = view.findViewById(R.id.btn_account_done);
        btn_back = view.findViewById(R.id.btn_account_back);
        edit_name = view.findViewById(R.id.edit_account_old_pass);
        edit_email = view.findViewById(R.id.edit_account_repeat_pass);
        edit_number = view.findViewById(R.id.edit_account_new_pass);
        sp_gender = view.findViewById(R.id.sp_account_gender);
    }


    private void addEvents() {
        sp_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("action", "updateInfo");
                    jsonObject.put("_id", MainActivity.idCustomer);
                    jsonObject.put("name", edit_name.getText().toString());
                    jsonObject.put("gender", gender);
                    jsonObject.put("numberPhone", edit_number.getText().toString());
                    jsonObject.put("email", edit_email.getText().toString());
                    new updateCustomerInfoAPI().execute(jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
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


    private class getCustomerInfoAPI extends AsyncTask<String, String, String> {
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
                customer = new Customer(message.getData());
                showData();
            }else{
                functionSystem.showDialogError("Có lỗi xảy ra, thử lại sau");
            }
        }
    }


    private class updateCustomerInfoAPI extends AsyncTask<String, String, String> {
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
                functionSystem.showDialogSuccess("Thành công");
            }else{
                functionSystem.showDialogError("Có lỗi xảy ra, thử lại sau");
            }
            alertDialog.dismiss();
        }
    }

    private void showData() {
        if(customer != null){
            edit_name.setText(customer.getName());
            edit_number.setText(customer.getNumberPhone());
            edit_email.setText(customer.getEmail());

            if(customer.getGender() != -9999){
                sp_gender.setSelection(customer.getGender());
            }

        }

    }
}
