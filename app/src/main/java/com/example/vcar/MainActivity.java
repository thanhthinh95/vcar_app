package com.example.vcar;

import android.content.Intent;
import android.graphics.BlendMode;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;


import com.example.vcar.fragment.fragmentAccount;
import com.example.vcar.fragment.fragmentHome;
import com.example.vcar.fragment.fragmentPromotion;
import com.example.vcar.fragment.fragmentTicket;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Display;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    TextView mTextMessage;
    FunctionSystem functionSystem = new FunctionSystem(this);
    private BottomNavigationView navView;

    public static String idCustomer = "";
    private final String BACK_STACK_ROOT_HOME = "root_fragment_home";
    private final String BACK_STACK_ROOT_TICKET = "root_fragment_home";
    private final String BACK_STACK_ROOT_promotion = "root_fragment_promotion";
    private final String BACK_STACK_ROOT_ACCOUNT = "root_fragment_account";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendIntentToCheckActivity();
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
        }else{
            super.onBackPressed();
        }
    }

    protected void buildStart() {
        super.onResume();
        addControls();
        addEvents();
    }

    private void addEvents() {
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navi_home:
                        changeFragment(new fragmentHome());
                        return true;
                    case R.id.navi_ticket:
                        changeFragment(new fragmentTicket());
                        return true;
                    case R.id.navi_promotion:
                        changeFragment(new fragmentPromotion());
                        return true;
                    case R.id.navi_account:
                        changeFragment(new fragmentAccount());
                        return true;
                }
                return false;
            }
        });
    }

    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.main_constraint_layout, fragment)
            .commit();
    }

    private void addControls() {
        navView = findViewById(R.id.nav_view);
    }


    public void sendIntentToCheckActivity() {
        Intent intent = new Intent(this, CheckActivity.class);
        startActivityForResult(intent, getResources().getInteger(R.integer.request_check));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == getResources().getInteger(R.integer.request_check) && resultCode == getResources().getInteger(R.integer.result_check)){
            buildStart();
            idCustomer = data.getStringExtra("id");
            changeFragment(new fragmentHome());
        }else if(requestCode == getResources().getInteger(R.integer.request_check) && resultCode == getResources().getInteger(R.integer.result_login_finish)){
            finish();
        }
    }

}
