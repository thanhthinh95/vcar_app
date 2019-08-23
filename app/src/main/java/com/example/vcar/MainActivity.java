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
import com.example.vcar.fragment.fragmentNotification;
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

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    TextView mTextMessage;
    FunctionSystem functionSystem = new FunctionSystem(this);

    private BottomNavigationView navView;
    private final String BACK_STACK_ROOT_ROOT = "root_fragment_root";
    private final String BACK_STACK_ROOT_HOME = "root_fragment_home";
    private final String BACK_STACK_ROOT_TICKET = "root_fragment_home";
    private final String BACK_STACK_ROOT_NOTIFICATION = "root_fragment_notification";
    private final String BACK_STACK_ROOT_ACCOUNT = "root_fragment_account";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendIntentToCheckActivity();
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
                FragmentManager fragmentManager = getSupportFragmentManager();

                switch (menuItem.getItemId()) {
                    case R.id.navi_home:
                        fragmentManager.popBackStack(BACK_STACK_ROOT_HOME, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        newTabFragment(new fragmentHome(), BACK_STACK_ROOT_ACCOUNT, false);
                        return true;
                    case R.id.navi_ticket:
                        fragmentManager.popBackStack(BACK_STACK_ROOT_TICKET, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        newTabFragment(new fragmentTicket(), BACK_STACK_ROOT_ACCOUNT, false);
                        return true;
                    case R.id.navi_notification:
                        fragmentManager.popBackStack(BACK_STACK_ROOT_NOTIFICATION, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        newTabFragment(new fragmentNotification(), BACK_STACK_ROOT_ACCOUNT, false);
                        return true;
                    case R.id.navi_account:
                        fragmentManager.popBackStack(BACK_STACK_ROOT_ACCOUNT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        newTabFragment(new fragmentAccount(), BACK_STACK_ROOT_ACCOUNT, false);
                        return true;
                }
                return false;
            }
        });
    }

    public void newTabFragment(Fragment fragment, String valueStack, boolean firstFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(valueStack);
        if(!firstFragment) fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        fragmentTransaction.replace(R.id.main_constraint_layout, fragment);
        fragmentTransaction.commit();
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
            newTabFragment(new fragmentHome(), null, true);

        }else if(requestCode == getResources().getInteger(R.integer.request_check) && resultCode == getResources().getInteger(R.integer.result_login_finish)){
            finish();
        }
    }

}
