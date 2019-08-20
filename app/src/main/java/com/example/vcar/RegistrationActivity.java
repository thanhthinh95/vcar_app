package com.example.vcar;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    HTextView htxt_title;
    EditText edit_numberphone, edit_email, edit_password, edit_password_repeat;
    Button btn_registration;
    TextView txt_back_login;

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
        edit_email = findViewById(R.id.edit_forgotpassword_username);
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
    }
}
