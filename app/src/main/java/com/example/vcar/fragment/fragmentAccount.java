package com.example.vcar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.vcar.AlerDialog.AccountChangePassDiaLog;
import com.example.vcar.AlerDialog.AccountInfoDiaLog;
import com.example.vcar.CheckActivity;
import com.example.vcar.MainActivity;
import com.example.vcar.R;

public class fragmentAccount extends Fragment implements View.OnContextClickListener{
    View view;
    TextView txt_accountInfo, txt_changePass, txt_logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        this.view = inflater.inflate(R.layout.fragment_account , container,false);
        addControls();
        addEvents();
        return view;
    }

    private void addControls() {
        txt_accountInfo = view.findViewById(R.id.txt_account_info);
        txt_changePass = view.findViewById(R.id.txt_account_change_pass);
        txt_logout = view.findViewById(R.id.txt_account_logout);
    }

    private void addEvents() {
        txt_accountInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AccountInfoDiaLog(view.getContext()).show();
            }
        });

        txt_changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AccountChangePassDiaLog(view.getContext()).show();

            }
        });

        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CheckActivity.class);
                intent.putExtra("logout", true);
                startActivityForResult(intent, getResources().getInteger(R.integer.request_logout));
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == getResources().getInteger(R.integer.request_logout) && resultCode == getResources().getInteger(R.integer.result_logout)){
            MainActivity.idCustomer = data.getStringExtra("id");
        }
    }

    @Override
    public boolean onContextClick(View view) {
        return false;
    }
}
