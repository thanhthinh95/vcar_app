package com.example.vcar.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ValueAnimator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.Adapter.SupplierCarAdapter;
import com.example.entity.ItemHomeSupplierCar;
import com.example.entity.Message;
import com.example.entity.Point;
import com.example.vcar.FunctionSystem;
import com.example.vcar.R;
import com.hanks.htextview.base.DefaultAnimatorListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class fragmentHome extends Fragment implements View.OnContextClickListener{
    View view;
    ConstraintLayout conLay_find;
    Button btn_removeStart, btn_removeEnd;
    ImageView img_start, img_end, img_move;
    TextView txt_where;
    RecyclerView recyclerView;
    AutoCompleteTextView auto_start;
    AutoCompleteTextView auto_end;
    SwipeRefreshLayout srl_data;

    FunctionSystem functionSystem;

    boolean statusShowConLayoutFind = true;
    static int y;

    @Override
    public boolean onContextClick(View view) {
        return false;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        this.view = inflater.inflate(R.layout.fragment_home , container,false);
        addControls();
        setValues();
        addEvents();
        return view;
    }

    private void setValues() {
        functionSystem = new FunctionSystem(view.getContext());
        conLay_find.setLayoutTransition(new LayoutTransition());
        new getPointAPI().execute();
    }

    private void addControls() {
        conLay_find = view.findViewById(R.id.conlay_home_find);
        txt_where = view.findViewById(R.id.txt_home_where);
        recyclerView = view.findViewById(R.id.recy_home_data);
        auto_start = view.findViewById(R.id.auto_home_start);
        auto_end = view.findViewById(R.id.auto_home_end);
        btn_removeStart = view.findViewById(R.id.btn_home_removeStart);
        btn_removeEnd = view.findViewById(R.id.btn_home_removeEnd);
        srl_data = view.findViewById(R.id.srl_home_data);
        img_start = view.findViewById(R.id.img_home_start);
        img_move = view.findViewById(R.id.img_home_move);
        img_end = view.findViewById(R.id.img_home_end);
    }

    private void addEvents() {
        txt_where.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(statusShowConLayoutFind){
                    auto_start.setFocusable(true);
                    auto_start.requestFocus();
                    functionSystem.showKeyboardFrom(auto_start);
                }else{
                    slideDown();
                    statusShowConLayoutFind = true;
                }
            }
        });

        auto_end.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_GO){
                    functionSystem.hideKeyboardFrom(view);
                    functionSystem.hideKeyboardFrom(view);
                    searchCarSupplier();
                    return true;
                }
                return false;
            }
        });

        img_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movePoint();
            }
        });

        img_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movePoint();
            }
        });

        img_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movePoint();
            }
        });

        srl_data.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchCarSupplier();
            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    if(y<=0){
                        if(!statusShowConLayoutFind){
                            slideDown();
                            statusShowConLayoutFind = true;
                        }
                    }
                    else{
//                        y = 0; Khi keo den cuoi lan thu 2 thi bat o tim kiem len
                        if(statusShowConLayoutFind){
                            slideUp();
                            statusShowConLayoutFind = false;
                        }
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                y=dy;
            }
        });



        auto_start.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if(auto_start.getText().toString().equals("")){
                    btn_removeStart.setVisibility(View.INVISIBLE);
                    functionSystem.hideKeyboardFrom(view);
                    searchCarSupplier();
                }else{
                    btn_removeStart.setVisibility(View.VISIBLE);
                }
            }
        });


        auto_end.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if(auto_end.getText().toString().equals("")){
                    btn_removeEnd.setVisibility(View.INVISIBLE);
                    functionSystem.hideKeyboardFrom(view);
                    searchCarSupplier();
                }else{
                    btn_removeEnd.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_removeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auto_start.setText("");
            }
        });
        btn_removeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auto_end.setText("");
            }
        });
    }

    public void slideUp(){
        conLay_find.animate()
            .setDuration(0).alpha(0f)
            .setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    conLay_find.setVisibility(View.GONE);
                }
            });
    }

    public void slideDown(){
        conLay_find.animate()
            .setDuration(500).alpha(1f)
            .setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    conLay_find.setVisibility(View.VISIBLE);
                }
            });
    }


    private void movePoint(){
        String start = auto_start.getText().toString();
        String end = auto_end.getText().toString();

        auto_start.setText(end);
        auto_end.setText(start);

        searchCarSupplier();
    }

    private void searchCarSupplier() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("startPoint", auto_start.getText().toString());
            jsonObject.put("endPoint", auto_end.getText().toString());
            jsonObject.put("action", "searchCarSupplier");
            auto_start.clearFocus();
            auto_end.clearFocus();
            new searchCarSupplierAPI().execute(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void bindValuesPointStartAndStop(List<Point> points){
        String [] values = new String[points.size()];
        for(int i = 0; i < points.size(); i++){
            values[i] = points.get(i).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_dropdown_item_1line, values);

        auto_start.setThreshold(1);//will start working from first character
        auto_start.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView

        auto_end.setThreshold(1);//will start working from first character
        auto_end.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
    }

    private class searchCarSupplierAPI extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            srl_data.animate();
            srl_data.setRefreshing(true);
        }

        protected String doInBackground(String... params) {
            return functionSystem.postMethod(getResources().getString(R.string.host_api).toString() + "car_supplier", params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            srl_data.setRefreshing(false);
            Message message = new Message(result);
            if(message.getCode() == 200){
                try {
                    JSONArray jsonArray = new JSONArray(message.getData());
                    List<ItemHomeSupplierCar> homeSupplierCars = new ArrayList<ItemHomeSupplierCar>();

                    for (int i = 0; i < jsonArray.length(); i++){
                        homeSupplierCars.add(new ItemHomeSupplierCar(jsonArray.get(i).toString()));
                    }

                    bindValuesCarSupplier(homeSupplierCars);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                functionSystem.showDialogError(message.getMessage());
            }
        }
    }



    private void bindValuesCarSupplier(List<ItemHomeSupplierCar> homeSupplierCars) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        SupplierCarAdapter homeAdapter = new SupplierCarAdapter(view.getContext(), homeSupplierCars);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(homeAdapter);
    }


    private class getPointAPI extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            functionSystem.showLoading();
        }

        protected String doInBackground(String... params) {
            return functionSystem.getMethod(getResources().getString(R.string.host_api).toString() + "point");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            functionSystem.hideLoading();
            Message message = new Message(result);
            if(message.getCode() == 200){
                try {
                    JSONArray jsonArray = new JSONArray(message.getData());
                    List<Point> points = new ArrayList<Point>();

                    for (int i = 0; i < jsonArray.length(); i++){
                        points.add(new Point(jsonArray.get(i).toString()));
                    }
                    bindValuesPointStartAndStop(points);
                    searchCarSupplier();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                functionSystem.showDialogError(message.getMessage());
            }
        }
    }
}
