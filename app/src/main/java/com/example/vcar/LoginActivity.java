package com.example.vcar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entity.Customer;
import com.example.entity.Message;
import com.hanks.htextview.base.HTextView;

import org.json.JSONException;
import org.json.JSONObject;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class LoginActivity extends AppCompatActivity {
    FunctionSystem functionSystem = new FunctionSystem(this);
    HTextView htxt_title;
    EditText edit_username, edit_password;
    TextView txt_registration, txt_forgot_password;
    Button btn_login;

    int sumClickBack = 0;
    final Handler handler = new Handler();

    @Override
    public void onBackPressed() {//Vô hiệu hóa nút quay trở về màn hình trước
        sumClickBack++;

        if(sumClickBack == 1){
            Toast.makeText(this, getResources().getString(R.string.back_app), Toast.LENGTH_SHORT).show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    sumClickBack = 0;
                }
            }, 1000);
        }else if(sumClickBack == 2){
            sendResultIntentFinish();
        }

    }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addControls();
        addEvents();

        Intent intent = getIntent();
        if(intent.getBooleanExtra("checkData", false)) {
            edit_username.setText(intent.getStringExtra("userName"));
            edit_password.setText(intent.getStringExtra("password"));
            loginSystem();
        }
    }



    private void addControls() {
        htxt_title = findViewById(R.id.htxt_login_title);
        edit_username = findViewById(R.id.edit_registration_email);
        edit_password = findViewById(R.id.edit_registration_pass_word_repeat);
        txt_registration = findViewById(R.id.txt_login_registration);
        txt_forgot_password = findViewById(R.id.txt_forgotpassword_back_login);
        btn_login = findViewById(R.id.btn_forgotpassword_forgotpassword);
    }

    private void setValues() {
        htxt_title.animateText(getResources().getString(R.string.login_title));
    }

    private void addEvents() {
        txt_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            sendIntentRegistrationActivity();

            }
        });
        txt_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            sendIntentForgotPasswordActivity();
            }
        });
        
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(validateData()){
                loginSystem();
            }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //ActivityRegistration tra ve username da dang ky thanh cong
        if(requestCode == getResources().getInteger(R.integer.request_registration)
                && resultCode == getResources().getInteger(R.integer.result_registration)){
            edit_username.setText(data.getStringExtra("userName"));
            edit_password.setText("");
            edit_password.requestFocus();
            functionSystem.showDialogSuccess(getResources().getString(R.string.message_success));
        }else if(requestCode == getResources().getInteger(R.integer.request_forgotpassword)
                && resultCode == getResources().getInteger(R.integer.result_forgotpassword)){
            edit_username.setText(data.getStringExtra("userName"));
            edit_password.setText("");
            edit_password.requestFocus();
            functionSystem.showDialogSuccess(getResources().getString(R.string.forgotpassword_message_success));
        }
    }


    private void sendResultIntent(String data){
        Customer customer = new Customer(data);
        Intent intent = new Intent();
        intent.putExtra("id", customer.getId());
        intent.putExtra("userName", edit_username.getText().toString());
        intent.putExtra("password", edit_password.getText().toString());
        intent.putExtra("macAddress", customer.getMacAddress());
        setResult(getResources().getInteger(R.integer.result_login), intent);
        finish();
    }

    private void sendResultIntentFinish(){
        Intent intent = new Intent();
        intent.putExtra("finish", true);
        setResult(getResources().getInteger(R.integer.result_login_finish), intent);
        finish();
    }

    private void loginSystem() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userName", edit_username.getText().toString());
            jsonObject.put("password", edit_password.getText().toString());
            jsonObject.put("action", "login");
            new loginAPI().execute(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean validateData() {
        boolean result = false;
        if(edit_username.getText().toString().equals("") || edit_password.getText().toString().equals("")){
            Toast.makeText(this, getResources().getString(R.string.message_fill_info), Toast.LENGTH_SHORT).show();
        }else {
            result = true;
        }

        return  result;
    }

    private void sendIntentRegistrationActivity() {
        Intent intent = new Intent(this, RegistrationActivity.class);
//        intent.putExtra(EXTRA_MESSAGE, 1);
        startActivityForResult(intent, getResources().getInteger(R.integer.request_registration));
    }

    private void sendIntentForgotPasswordActivity() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
//        intent.putExtra(EXTRA_MESSAGE, 1);
        startActivityForResult(intent, getResources().getInteger(R.integer.request_forgotpassword));
    }

    private class loginAPI extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            functionSystem.showLoading();
        }

        protected String doInBackground(String... params) {
            return functionSystem.postMethod(getResources().getString(R.string.host).toString() + "customer", params[0]);
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
                edit_password.setText("");
            }
        }
    }


}




