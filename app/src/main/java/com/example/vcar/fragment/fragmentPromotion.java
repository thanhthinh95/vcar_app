package com.example.vcar.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.Adapter.PromotionAdapter;
import com.example.Adapter.SupplierCarAdapter;
import com.example.entity.Message;
import com.example.entity.Point;
import com.example.entity.Promotion;
import com.example.vcar.FunctionSystem;
import com.example.vcar.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class fragmentPromotion extends Fragment implements View.OnContextClickListener{
    View view;
    FunctionSystem functionSystem;
    SwipeRefreshLayout srl_data;
    RecyclerView recy_data;

    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        this.view = inflater.inflate(R.layout.fragment_promotion , container,false);
        functionSystem = new FunctionSystem(view.getContext());
        addControls();
        setValues();
        addEvents();
        return view;
    }

    private void addControls() {
        srl_data = view.findViewById(R.id.srl_promotion_data);
        recy_data = view.findViewById(R.id.recy_promotion_data);
    }

    private void setValues(){
        searchPromotion();
    }

    private void searchPromotion(){
        new getPromotionAPI().execute();

    }

    private void addEvents() {
        srl_data.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchPromotion();
            }
        });
    }

    @Override
    public boolean onContextClick(View view) {
        return false;
    }


    private class getPromotionAPI extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            functionSystem.showLoading();
        }

        protected String doInBackground(String... params) {
            return functionSystem.getMethod(getResources().getString(R.string.host_api).toString() + "promotion");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            functionSystem.hideLoading();
            srl_data.setRefreshing(false);
            Message message = new Message(result);
            if(message.getCode() == 200){
                try {
                    JSONArray jsonArray = new JSONArray(message.getData());
                    List<Promotion> promotions = new ArrayList<Promotion>();

                    for (int i = 0; i < jsonArray.length(); i++){
                        promotions.add(new Promotion(jsonArray.get(i).toString()));
                    }
                    bindValuesPromotion(promotions);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                functionSystem.showDialogError(message.getMessage());
            }
        }
    }

    private void bindValuesPromotion(List<Promotion> promotions) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recy_data.setLayoutManager(mLayoutManager);
        PromotionAdapter promotionAdapter = new PromotionAdapter(view.getContext(), promotions);
        recy_data.setItemAnimator(new DefaultItemAnimator());
        recy_data.setAdapter(promotionAdapter);
    }
}
