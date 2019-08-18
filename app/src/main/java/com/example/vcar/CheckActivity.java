package com.example.vcar;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class CheckActivity extends AppCompatActivity {
    final FunctionSystem functionSystem = new FunctionSystem(this);

    AlertDialog alertDialog;
    ProgressBar prb_load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        Log.d("EVENT_ACTIVITY", "onCreate Check");

        setValue();
        setValueDefault();
    }

    private void setValueDefault() {
        prb_load.setMax(100);
    }

    private void setValue() {
        prb_load = findViewById(R.id.prb_check_load);
    }

    @Override
    protected void onStart() {
        Log.d("EVENT_ACTIVITY", "onStart Check");
        super.onStart();

        if(functionSystem.isNetworkAvailable()){
            changeAnimationProgressBar(40);
        }

        if(true){
            changeAnimationProgressBar(60);
        }



        changeAnimationProgressBar(80);
        changeAnimationProgressBar(85);
        changeAnimationProgressBar(90);
        changeAnimationProgressBar(100);

        sendMessage();
    }


    public void sendMessage() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(EXTRA_MESSAGE, 1);
        startActivity(intent);
    }

    private void changeAnimationProgressBar(int value){
        ObjectAnimator animation = ObjectAnimator.ofInt(prb_load, "progress", prb_load.getProgress(), value);
        animation.setDuration(value * 20);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    private void showDialogError(final String value){
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(this);
        final LayoutInflater inflaterDialog = getLayoutInflater();
        View view = inflaterDialog.inflate(R.layout.layout_dialog_error, null);
        builderDialog.setView(view);
        alertDialog = builderDialog.create();
        alertDialog.show();

        ((TextView) view.findViewById(R.id.txt_dialog_error)).setText(value);
        view.findViewById(R.id.btn_dialog_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
            alertDialog.dismiss();
            }
        });
    }




    @Override
    protected void onResume() {
        Log.d("EVENT_ACTIVITY", "onResume Check");
        super.onResume();
    }




    @Override
    protected void onPause() {
        Log.d("EVENT_ACTIVITY", "onPause Check");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("EVENT_ACTIVITY", "onStop Check");
        super.onStop();
    }


    @Override
    protected void onRestart() {
        Log.d("EVENT_ACTIVITY", "onRestart Check");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.d("EVENT_ACTIVITY", "onDestroy Check");
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {//Vô hiệu hóa nút quay trở về màn hình trước
//        super.onBackPressed();
    }
}
