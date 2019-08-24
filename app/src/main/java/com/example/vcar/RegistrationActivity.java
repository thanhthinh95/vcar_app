package com.example.vcar;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entity.Customer;
import com.example.entity.Message;
import com.hanks.htextview.base.HTextView;

public class RegistrationActivity extends AppCompatActivity {
    @Override
    protected void onStart() {
        super.onStart();
        setValues();
    }

    @Override
    protected void onStop() {
        super.onStop();
        htxt_title.animateText(getResources().getString(R.string.text_demo));
    }

    FunctionSystem functionSystem = new FunctionSystem(this);
    HTextView htxt_title;
    EditText edit_numberphone, edit_email, edit_password, edit_password_repeat;
    Button btn_registration;
    TextView txt_back_login;


    Customer customer = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        addControls();
        addEvents();
    }

    private void addControls() {
        htxt_title = findViewById(R.id.txt_registration_title);
        edit_numberphone = findViewById(R.id.edit_registration_number_phone);
        edit_email = findViewById(R.id.edit_registration_email);
        edit_password = findViewById(R.id.edit_registration_pass_word);
        edit_password_repeat = findViewById(R.id.edit_registration_pass_word_repeat);
        btn_registration = findViewById(R.id.btn_forgotpassword_forgotpassword);
        txt_back_login = findViewById(R.id.txt_registration_back_login);
    }

    private void setValues() {
        htxt_title.animateText(getResources().getString(R.string.registration_title));
    }

    private void addEvents() {
        txt_back_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
        btn_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateData()){
                    registrationSystem();
                }
            }
        });
    }

    private boolean validateData(){
        boolean result = false;
        if(edit_numberphone.getText().toString().equals("") ||
                edit_email.getText().toString().equals("") ||
                edit_password.getText().toString().equals("") ||
                edit_password_repeat.getText().toString().equals("")
        ){
            Toast.makeText(this, getResources().getString(R.string.message_fill_info), Toast.LENGTH_SHORT).show();
        }else if(edit_password.getText().toString().length() < 6){
            Toast.makeText(this, getResources().getString(R.string.message_password_invalid), Toast.LENGTH_SHORT).show();
        }else if(!edit_password.getText().toString().equals(edit_password_repeat.getText().toString())){
            Toast.makeText(this, getResources().getString(R.string.message_password_repeat_invalid), Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }

        return  result;
    }

    private void registrationSystem() {
        //B1: Gui mã xác thực về hòm thư, server trả lại mã cho client,
        //B2: Nhập mã xác thực, nếu đúng như mã đã gửi về hòm thư, thực hiên đăng ký
        customer = new Customer(
                edit_password.getText().toString(),
                edit_numberphone.getText().toString(),
                edit_email.getText().toString(),
                functionSystem.getAddress());

        customer.setAction("verifyAccount");
        new verifyAccountAPI().execute(customer.toString());
    }

    private void verifyAccount(final String dataCodeFormAPI) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.layout_verify_account, null);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
//        alertDialog.setCancelable(false);
        alertDialog.show();

        final EditText edit_code = view.findViewById(R.id.edit_verify_code);
        final TextView txt_time = view.findViewById(R.id.txt_verify_time);
        final Button btn_ok = view.findViewById(R.id.btn_verify_ok);
        final Button btn_again = view.findViewById(R.id.btn_verify_again);

        final CountDownTimer downTimer = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long l) {
                txt_time.setText(getResources().getString(R.string.verify_message_time) + l/1000 + "s");
            }

            @Override
            public void onFinish() {
                txt_time.setText(getResources().getString(R.string.verify_message_timeout));
            }
        }.start();


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txt_time.getText().toString().equals(getResources().getString(R.string.verify_message_timeout))){
                    functionSystem.showDialogError(getResources().getString(R.string.verify_message_timeout));
                }else if(edit_code.getText().toString().equals(dataCodeFormAPI)){
                    //Neu ma xac thuc dung, thuc hien dang ky
                    downTimer.cancel();
                    alertDialog.dismiss();
                    customer.setAction("registration");
                    new registrationAPI().execute(customer.toString());
                }else{
                    functionSystem.showDialogError(getResources().getString(R.string.verify_message_invalid));
                }
            }
        });


        btn_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downTimer.cancel();
                alertDialog.dismiss();
                registrationSystem();
            }
        });

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                downTimer.cancel();
            }
        });
    }

    private void sendResultIntent(String data){
        customer = new Customer(data);
        Intent intent = new Intent();
        intent.putExtra("userName", customer.getNumberPhone());
        setResult(getResources().getInteger(R.integer.result_registration), intent);
        finish();
    }

    private class registrationAPI extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            functionSystem.showLoading();
        }

        protected String doInBackground(String... params) {
            return functionSystem.postMethod(getResources().getString(R.string.host_api).toString() + "customer", params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            functionSystem.hideLoading();
            Message message = new Message(result);
            if(message.getCode() == 200){
                sendResultIntent(message.getData());
            }else{
                functionSystem.showDialogError(message.getMessage());
            }
        }
    }


    private class verifyAccountAPI extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            functionSystem.showLoading();
        }

        protected String doInBackground(String... params) {
            return functionSystem.postMethod(getResources().getString(R.string.host_api).toString() + "customer", params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            functionSystem.hideLoading();
            Message message = new Message(result);
            if(message.getCode() == 200){
                verifyAccount(message.getData());
            }else{
                functionSystem.showDialogError(message.getMessage());
            }
        }
    }
}
