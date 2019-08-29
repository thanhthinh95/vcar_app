package com.example.vcar.AlerDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.entity.Car;
import com.example.entity.Message;
import com.example.entity.Trip;
import com.example.vcar.FunctionSystem;
import com.example.vcar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PaymentDiaLog {
    private Context context;
    private Car car;
    String[] ticketIds;

    private FunctionSystem functionSystem;
    private EditText edit_code;
    private RecyclerView recy_ticket;


    private TextView txt_money, txt_discount, txt_total_money;
    private RadioGroup grad_payment;
    private RadioButton rad_case, rad_visa_card;
    private Button btn_back, btn_next;

    private  AlertDialog alertDialog;

    public PaymentDiaLog(Context context, Car car, String[] ticketIds) {
        this.context = context;
        this.car = car;
        this.ticketIds = ticketIds;
        functionSystem = new FunctionSystem(context);
    }

    public void show(){
        if(context != null){
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            final View view = inflater.inflate(R.layout.layout_home_payment, null);
            addControls(view);
            setValues();
            addEvents();
            builder.setView(view);

            alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            alertDialog.show();
        }else{
            functionSystem.showDialogError(context.getResources().getString(R.string.dialog_info_car_error_show));
        }
    }



    private void addControls(View view) {
        edit_code = view.findViewById(R.id.edit_payment_code);
        recy_ticket = view.findViewById(R.id.recy_payment_ticket);

        txt_money = view.findViewById(R.id.txt_payment_money);
        txt_discount = view.findViewById(R.id.txt_payment_discount);
        txt_total_money = view.findViewById(R.id.txt_payment_total_money);
        grad_payment = view.findViewById(R.id.grad_payment_payment);
        rad_case = view.findViewById(R.id.rad_payment_case);
        rad_visa_card = view.findViewById(R.id.rad_payment_visa_card);
        btn_back = view.findViewById(R.id.btn_payment_back);
        btn_next = view.findViewById(R.id.btn_payment_next);
    }

    private void setValues() {
        try {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < ticketIds.length; i++) {
                jsonArray.put(ticketIds[i]);
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("action", "getMany");
            jsonObject.put("ids", jsonArray.toString());

            new getTicketAPI().execute(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    private void addEvents() {

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                new BookTicketDiaLog(context, car, true).show();
            }
        });
    }


    private class getTicketAPI extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            functionSystem.showLoading();
        }

        protected String doInBackground(String... params) {
            return functionSystem.postMethod(context.getResources().getString(R.string.host_api).toString() + "ticket", params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            functionSystem.hideLoading();
            Message message = new Message(result);
            functionSystem.showDialogSuccess(result);
            if(message.getCode() == 200){
//                try {
//                    JSONArray jsonArray = new JSONArray(message.getData());
//                    trips = new ArrayList<Trip>();
//                    for (int i = 0; i < jsonArray.length(); i++){
//                        trips.add(new Trip(jsonArray.get(i).toString()));
//                    }
//                    showTrips();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }else{
                functionSystem.showDialogError(message.getMessage());
            }
        }
    }


}
