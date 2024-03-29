package com.example.vcar.AlerDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Adapter.TicketAdapter;
import com.example.entity.Car;
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

public class PaymentDiaLog {
    private Context context;
    private Car car;
    String[] ticketIds;

    private FunctionSystem functionSystem;
    private EditText edit_code;
    private RecyclerView recy_ticket;

    private TextView txt_money, txt_discount, txt_total_payment;
    private RadioGroup grad_payment;
    private RadioButton rad_case, rad_visa_card;
    private Button btn_back, btn_done;

    private  AlertDialog alertDialog;
    private List<Ticket> tickets = new ArrayList<>();
    private long money = 0, discount = 0, totalPayment = 0;
    private String promotionId = null;

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
            addEvents(view);
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
        txt_total_payment = view.findViewById(R.id.txt_payment_total_payment);
        grad_payment = view.findViewById(R.id.grad_payment_payment);
        rad_case = view.findViewById(R.id.rad_payment_case);
        rad_visa_card = view.findViewById(R.id.rad_payment_visa_card);
        btn_back = view.findViewById(R.id.btn_account_back);
        btn_done = view.findViewById(R.id.btn_account_done);
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



    private void addEvents(final View view) {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            try {
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < ticketIds.length; i++) {
                    jsonArray.put(ticketIds[i]);
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("action", "deleteMany");
                jsonObject.put("ids", jsonArray.toString());
                new deleteTicketAPI().execute(jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            }
        });

        edit_code.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH){
                    functionSystem.hideKeyboardFrom(view);
                    searchPromotion(edit_code.getText().toString());
                    return true;
                }
                return false;
            }
        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONArray jsonArray = new JSONArray();
                for(int i = 0; i < ticketIds.length; i++){
                    jsonArray.put(ticketIds[i]);
                }

                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("action", "createNew");
                    jsonObject.put("ticketIds", jsonArray.toString());
                    jsonObject.put("customerId", MainActivity.idCustomer);
                    jsonObject.put("promotionId", promotionId);
                    jsonObject.put("type", 1);//Chi tien mat
                    jsonObject.put("money", money);
                    jsonObject.put("discount", discount);
                    jsonObject.put("totalPayment", totalPayment);


                    new createPaymentAPI().execute(jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


    }

    private void searchPromotion(String code) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("action", "getForCode");
            jsonObject.put("code", code);
            new getPromotionAPI().execute(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class createPaymentAPI extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            functionSystem.showLoading();
        }

        protected String doInBackground(String... params) {
            return functionSystem.postMethod(context.getResources().getString(R.string.host_api).toString() + "payment", params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            functionSystem.hideLoading();
            Message message = new Message(result);
            if(message.getCode() == 200){
                alertDialog.dismiss();
                functionSystem.showDialogSuccess("Thanh toán thành công. Theo dõi vé trong vé của tôi");
            }else{
                functionSystem.showDialogError("Thanh toán thất bại, thử lại sau");
            }
        }
    }

    private class deleteTicketAPI extends AsyncTask<String, String, String> {//Thuc hien xoa các ticket chua thanh toan khi bam nut tro lai
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

            alertDialog.dismiss();
            new BookTicketDiaLog(context, car, true).show();
        }
    }

    private class getPromotionAPI extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            functionSystem.showLoading();
        }

        protected String doInBackground(String... params) {
            return functionSystem.postMethod(context.getResources().getString(R.string.host_api).toString() + "promotion", params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            functionSystem.hideLoading();
            Message message = new Message(result);
            if(message.getCode() == 200){
                try {
                    JSONObject jsonObject = new JSONObject(message.getData());
                    discount = jsonObject.getLong("discount");
                    promotionId = jsonObject.getString("_id");
                } catch (JSONException e) {
                    e.printStackTrace();
                    promotionId = null;
                    discount = 0;
                }
            }else{
                functionSystem.showDialogError("Mã khuyến mại không hợp lệ");
                edit_code.setText("");
                discount = 0;
                showValueMoneyPayment();
            }

            totalPayment = money - discount;
            showValueMoneyPayment();
        }
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
            if(message.getCode() == 200){
                try {
                    JSONArray jsonArray = new JSONArray(message.getData());
                    tickets = new ArrayList<Ticket>();
                    for (int i = 0; i < jsonArray.length(); i++){
                        tickets.add(new Ticket(jsonArray.get(i).toString()));
                    }
                    showTickets();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                functionSystem.showDialogError(message.getMessage());
            }
        }
    }

    private void showTickets() {
        if(tickets != null && tickets.size() > 0){
            List<String> listValue = new ArrayList<String>();

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            recy_ticket.setLayoutManager(mLayoutManager);
            TicketAdapter ticketAdapter = new TicketAdapter(context, tickets);
            recy_ticket.setItemAnimator(new DefaultItemAnimator());
            recy_ticket.setAdapter(ticketAdapter);


            for(int i = 0; i < tickets.size(); i++){
                money += tickets.get(i).getFare();
            }

            totalPayment = money;
            showValueMoneyPayment();
        }else {
            btn_done.setVisibility(View.INVISIBLE);
            functionSystem.showDialogError(context.getResources().getString(R.string.payment_message_error_ticket));
        }
    }

    private void showValueMoneyPayment() {
        txt_money.setText(functionSystem.formatMoney.format(money) + " VNĐ");
        txt_discount.setText(functionSystem.formatMoney.format(discount) + " VNĐ");
        txt_total_payment.setText(functionSystem.formatMoney.format(totalPayment) + " VNĐ");
    }


}
