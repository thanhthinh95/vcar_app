package com.example.vcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hanks.htextview.base.HTextView;

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
            finish();
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
                sendMessageRegistrationActivity();

            }
        });
        txt_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessageForgotPasswordActivity();
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

    private void loginSystem() {
        new loginAPI().execute();
    }

    private boolean validateData() {
        boolean result = false;
        if(edit_username.getText().toString().equals("") || edit_password.getText().toString().equals("")
        ){
            Toast.makeText(this, getResources().getString(R.string.message_fill_info), Toast.LENGTH_SHORT).show();
        }else {
            result = true;
        }

        return  result;
    }

    private void sendMessageRegistrationActivity() {
        Intent intentRegistration = new Intent(this, RegistrationActivity.class);
        intentRegistration.putExtra(EXTRA_MESSAGE, 1);
        startActivity(intentRegistration);
    }

    private void sendMessageForgotPasswordActivity() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        intent.putExtra(EXTRA_MESSAGE, 1);
        startActivityForResult(intent, getResources().getInteger(R.integer.request_registration));
    }


    private class loginAPI extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            functionSystem.showLoading();
        }

        protected String doInBackground(String... params) {
            return functionSystem.getMethod(getResources().getString(R.string.host) + "customer");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            functionSystem.hideLoading();
            functionSystem.showDialogSuccess(result);
        }
    }
}




