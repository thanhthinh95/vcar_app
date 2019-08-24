package com.example.vcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entity.Message;
import com.hanks.htextview.base.HTextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPasswordActivity extends AppCompatActivity {
    HTextView htxt_title;
    EditText edit_username;
    Button btn_forgotpassword;
    TextView txt_back_login;

    FunctionSystem functionSystem = new FunctionSystem(this);

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

        btn_forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateData()){
                    ForgotPasswordSystem();
                }
            }
        });
    }

    private void ForgotPasswordSystem() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userName", edit_username.getText().toString());
            jsonObject.put("action", "forgotPassword");
            new forgotPasswordAPI().execute(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean validateData() {
        boolean result = false;
        if(edit_username.getText().toString().equals("")){
            Toast.makeText(this, getResources().getString(R.string.message_fill_info), Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }

        return  result;
    }


    private class forgotPasswordAPI extends AsyncTask<String, String, String> {
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

    private void sendResultIntent(String data) {
        Intent intent = new Intent();
        intent.putExtra("userName", edit_username.getText().toString());
        setResult(getResources().getInteger(R.integer.result_forgotpassword), intent);
        finish();
    }

}
