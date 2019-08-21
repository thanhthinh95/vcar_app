package com.example.vcar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hanks.htextview.base.HTextView;

public class ForgotPasswordActivity extends AppCompatActivity {
    HTextView htxt_title;
    EditText edit_username;
    Button btn_forgotpassword;
    TextView txt_back_login;


    @Override
    protected void onStart() {
        super.onStart();
        setValues();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        
        addControls();
        addEvents();
    }

    private void addControls() {
        htxt_title = findViewById(R.id.htxt_forgotpassword_title);
        edit_username = findViewById(R.id.edit_registration_email);
        btn_forgotpassword = findViewById(R.id.btn_forgotpassword_forgotpassword);
        txt_back_login = findViewById(R.id.txt_forgotpassword_back_login);
    }

    private void setValues() {
        htxt_title.animateText(getResources().getString(R.string.forgotpassword_title));
    }

    private void addEvents() {
        txt_back_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
