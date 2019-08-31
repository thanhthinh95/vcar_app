package com.example.vcar.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.Adapter.TicketAdapter;

import com.example.entity.Message;
import com.example.entity.Ticket;
import com.example.vcar.FunctionSystem;
import com.example.vcar.MainActivity;
import com.example.vcar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class fragmentTicket extends Fragment implements View.OnContextClickListener{
    View view;

    FunctionSystem functionSystem;
    SwipeRefreshLayout srl_data;
    RecyclerView recy_data;
    ImageButton img_refresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        this.view = inflater.inflate(R.layout.fragment_ticket , container,false);
        functionSystem = new FunctionSystem(view.getContext());
        addControls();
        setValues();
        addEvents();
        return view;
    }

    private void addControls() {
        srl_data = view.findViewById(R.id.srl_ticket_data);
        recy_data = view.findViewById(R.id.recy_ticket_data);
        img_refresh = view.findViewById(R.id.imgbtn_ticket_refresh);
    }

    private void setValues(){
        searchTicket();
    }

    private void searchTicket(){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("action", "getManyForCusomer");
            jsonObject.put("customerId", MainActivity.idCustomer);
            new getticketAPI().execute(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void addEvents() {
        srl_data.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchTicket();
            }
        });

        img_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchTicket();
            }
        });
    }


    @Override
    public boolean onContextClick(View view) {
        return false;
    }

    private class getticketAPI extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            functionSystem.showLoading();
        }

        protected String doInBackground(String... params) {
            return functionSystem.postMethod(getResources().getString(R.string.host_api).toString() + "ticket", params[0]);
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
                    List<Ticket> tickets = new ArrayList<Ticket>();

                    for (int i = 0; i < jsonArray.length(); i++){
                        tickets.add(new Ticket(jsonArray.get(i).toString()));
                    }
                    bindValuesTicket(tickets);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                functionSystem.showDialogError(message.getMessage());
            }
        }
    }

    private void bindValuesTicket(List<Ticket> tickets) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recy_data.setLayoutManager(mLayoutManager);
        TicketAdapter ticketAdapter = new TicketAdapter(view.getContext(), tickets);
        recy_data.setItemAnimator(new DefaultItemAnimator());
        recy_data.setAdapter(ticketAdapter);
    }
}
