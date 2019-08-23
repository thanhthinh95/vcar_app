package com.example.vcar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.vcar.R;

public class fragmentHome extends Fragment implements View.OnContextClickListener{
    View view;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        this.view = inflater.inflate(R.layout.fragment_home , container,false);
        addControls();
        addEvents();
        return view;
    }

    private void addControls() {
    }

    private void addEvents() {
    }

    @Override
    public boolean onContextClick(View view) {
        return false;
    }
}
