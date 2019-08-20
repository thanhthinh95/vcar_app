package com.example.vcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        htxt_title = findViewById(R.id.htxt_forgotpassword_title);
        edit_username = findViewById(R.id.edit_forgotpassword_username);
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

    }

    private void sendMessageRegistrationActivity() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.putExtra(EXTRA_MESSAGE, 1);
        startActivity(intent);
    }

    private void sendMessageForgotPasswordActivity() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        intent.putExtra(EXTRA_MESSAGE, 1);
        startActivity(intent);
    }
}




