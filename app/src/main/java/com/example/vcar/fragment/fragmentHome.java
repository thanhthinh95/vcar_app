package com.example.vcar.fragment;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Adapter.SupplierCarAdapter;
import com.example.entity.ItemHomeCar;
import com.example.entity.ItemHomeSupplierCar;
import com.example.entity.Message;
import com.example.entity.Point;
import com.example.vcar.FunctionSystem;
import com.example.vcar.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class fragmentHome extends Fragment implements View.OnContextClickListener{
    View view;
    RecyclerView recyclerView;
    AutoCompleteTextView auto_start;
    AutoCompleteTextView auto_stop;

    FunctionSystem functionSystem;

    @Override
    public boolean onContextClick(View view) {
        return false;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        this.view = inflater.inflate(R.layout.fragment_home , container,false);
        functionSystem = new FunctionSystem(view.getContext());
        addControls();
        addEvents();
        setValues();
        return view;
    }

    private void setValues() {

        List<ItemHomeSupplierCar> arrayList = new ArrayList<>();
        List<ItemHomeCar> itemHomeCars = new ArrayList<>();

        itemHomeCars.add(new ItemHomeCar("e", "","BG-GFG213"));
        itemHomeCars.add(new ItemHomeCar("e", "","BG-GFG213"));
        itemHomeCars.add(new ItemHomeCar("e", "","BG-GFG213"));
        itemHomeCars.add(new ItemHomeCar("e", "","BG-GFG213"));
        itemHomeCars.add(new ItemHomeCar("e", "","BG-GFG213"));
        itemHomeCars.add(new ItemHomeCar("e", "","BG-GFG213"));
        itemHomeCars.add(new ItemHomeCar("e", "","BG-GFG213"));
        itemHomeCars.add(new ItemHomeCar("e", "","BG-GFG213"));
        itemHomeCars.add(new ItemHomeCar("e", "","BG-GFG213"));


        arrayList.add(new ItemHomeSupplierCar("Thanh Ha", "Hà Nội - Tuyên Quan", itemHomeCars, "04:45", 3));
        arrayList.add(new ItemHomeSupplierCar("Thanh Ha", "Hà Nội - Tuyên Quan", itemHomeCars, "04:45", 3));
        arrayList.add(new ItemHomeSupplierCar("Thanh Ha", "Hà Nội - Tuyên Quan", itemHomeCars, "04:45", 3));
        arrayList.add(new ItemHomeSupplierCar("Thanh Ha", "Hà Nội - Tuyên Quan", itemHomeCars, "04:45", 3));
        arrayList.add(new ItemHomeSupplierCar("Thanh Ha", "Hà Nội - Tuyên Quan", itemHomeCars, "04:45", 3));
        arrayList.add(new ItemHomeSupplierCar("Thanh Ha", "Hà Nội - Tuyên Quan", itemHomeCars, "04:45", 3));


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        SupplierCarAdapter homeAdapter = new SupplierCarAdapter(view.getContext(), arrayList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(homeAdapter);

        new getPointAPI().execute();

    }

    private void addControls() {
        recyclerView = view.findViewById(R.id.recy_home_data);
        auto_start = view.findViewById(R.id.auto_home_start);
        auto_stop = view.findViewById(R.id.auto_home_stop);
    }

    private void addEvents() {
        auto_stop.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_GO){

                    Log.w("TAG", "Ban vua click vao nut go");
                    return true;
                }
                return false;
            }
        });
    }

    private void bindValuesPointStartAndStop(List<Point> points){
        String [] values = new String[points.size()];
        for(int i = 0; i < points.size(); i++){
            values[i] = points.get(i).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_dropdown_item_1line, values);

        auto_start.setThreshold(1);//will start working from first character
        auto_start.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView

        auto_stop.setThreshold(1);//will start working from first character
        auto_stop.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
    }


    private class getPointAPI extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            functionSystem.showLoading();
        }

        protected String doInBackground(String... params) {
            return functionSystem.getMethod(getResources().getString(R.string.host).toString() + "point");
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                functionSystem.showDialogError(message.getMessage());
            }
        }
    }
}
