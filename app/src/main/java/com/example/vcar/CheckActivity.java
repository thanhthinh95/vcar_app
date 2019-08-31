package com.example.vcar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class CheckActivity extends AppCompatActivity {
    final FunctionSystem functionSystem = new FunctionSystem(this);

    ProgressBar prb_load;
    boolean statusLogout = false;


    @Override
    public void onBackPressed() {//Vô hiệu hóa nút quay trở về màn hình trước
//        super.onBackPressed();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (functionSystem.isNetworkAvailable()) {
            changeAnimationProgressBar(10);

            if (functionSystem.getAddress() != null) {
                changeAnimationProgressBar(20);


                if (functionSystem.pingToServer()) {
                    changeAnimationProgressBar(30);

                    if (statusLogout) {//Fragment Account yeu cau dang xuat

                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("id", "");
                            jsonObject.put("userName", "");
                            jsonObject.put("password", "");
                            jsonObject.put("macAddress", "");
                            jsonObject.put("status", statusLogout);
                            writeFileInfo(jsonObject.toString());

                            sendIntentToLoginActivity("", "", true);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        checkDataLoginOld();
                    }
                } else {
                    functionSystem.showDialogError(getResources().getString(R.string.check_error_server) + " " + getResources().getString(R.string.ipServer));
                }
            } else {
                functionSystem.showDialogError(getResources().getString(R.string.check_error_mac));
            }



        } else {
            functionSystem.showDialogError(getResources().getString(R.string.check_error_network));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        addControls();
        setValues();

        Intent intent = getIntent();
        statusLogout = intent.getBooleanExtra("logout", false);

    }

    private void addControls() {
        prb_load = findViewById(R.id.prb_check_load);
    }

    private void setValues() {
        prb_load.setMax(100);
    }


    private void checkDataLoginOld(){
        String data = readFileInfo();
        changeAnimationProgressBar(60);

        String userName = null, password = null;
        boolean status = false;
        if(data != null){
            try {
                JSONObject jsonObject = new JSONObject(data);
                userName = jsonObject.getString("userName");
                password = jsonObject.getString("password");
                status = jsonObject.getBoolean("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        sendIntentToLoginActivity(userName, password, status);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //ActivityLogin tra ve username da dang nhap thanh cong
        if(requestCode == getResources().getInteger(R.integer.request_login)
                && resultCode == getResources().getInteger(R.integer.result_login)){
            changeAnimationProgressBar(80);

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", data.getStringExtra("id"));
                jsonObject.put("userName", data.getStringExtra("userName"));
                jsonObject.put("password", data.getStringExtra("password"));
                jsonObject.put("macAddress", data.getStringExtra("macAddress"));
                jsonObject.put("status", false);

                writeFileInfo(jsonObject.toString());
                sendResultIntent(data.getStringExtra("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(requestCode == getResources().getInteger(R.integer.request_login)
            && resultCode == getResources().getInteger(R.integer.result_login_finish)){
            sendResultIntent(null);
        }
    }

    private void sendResultIntent(String dataId){
        changeAnimationProgressBar(100);
        Intent intent = new Intent();

        if(dataId == null){//Activity gui yeu cau finish()
            setResult(getResources().getInteger(R.integer.result_login_finish), intent);
        }else{
            if(statusLogout){
                intent.putExtra("id", dataId);
                setResult(getResources().getInteger(R.integer.result_logout), intent);
            }else{
                intent.putExtra("id", dataId);
                setResult(getResources().getInteger(R.integer.result_check), intent);
            }

        }

        finish();
    }

    private void writeFileInfo(String data) {
        changeAnimationProgressBar(90);

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput("infoAccount.txt", this.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            functionSystem.showDialogError(getResources().getString(R.string.check_error_wrileFile));
        } catch (IOException e) {
            e.printStackTrace();
            functionSystem.showDialogError(getResources().getString(R.string.check_error_wrileFile));
        }
    }

    private String readFileInfo(){
        try {
            changeAnimationProgressBar(50);
            InputStream inputStream = this.openFileInput("infoAccount.txt");
            if(inputStream != null){//Da co file, tien hanh doc noi dung
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                bufferedReader.close();
                return stringBuilder.toString();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void sendIntentToLoginActivity(String userName, String password, Boolean status) {
        changeAnimationProgressBar(70);
        Intent intent = new Intent(this, LoginActivity.class);
        if(userName != null && password != null){
            intent.putExtra("checkData", true);
            intent.putExtra("userName", userName);
            intent.putExtra("password", password);
            intent.putExtra("status", status);
        }else {
            intent.putExtra("checkData", false);
        }
        startActivityForResult(intent, getResources().getInteger(R.integer.request_login));
    }

    private void changeAnimationProgressBar(int value){
        ObjectAnimator animation = ObjectAnimator.ofInt(prb_load, "progress", prb_load.getProgress(), value);
        animation.setDuration(value * 20);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }
}
