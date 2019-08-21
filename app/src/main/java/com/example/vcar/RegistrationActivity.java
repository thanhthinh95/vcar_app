package com.example.vcar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entity.Customer;
import com.hanks.htextview.base.HTextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
        Customer customer = new Customer(
                edit_password.getText().toString(),
                edit_numberphone.getText().toString(),
                edit_email.getText().toString(),
                functionSystem.getAddress());



        new test().execute(getResources().getString(R.string.host).toString() + "/api");

    }


    private class test extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            functionSystem.showLoading();
        }

        protected String doInBackground(String... params) {
            return functionSystem.getMethod(params[0]);

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            functionSystem.hideLoading();

            functionSystem.showDialogSuccess(result);

        }
    }
}
