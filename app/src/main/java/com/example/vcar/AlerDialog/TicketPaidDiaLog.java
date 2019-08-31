package com.example.vcar.AlerDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.entity.Message;
import com.example.entity.Ticket;
import com.example.vcar.FunctionSystem;
import com.example.vcar.R;

import org.json.JSONException;
import org.json.JSONObject;

public class TicketPaidDiaLog {
    private Context context;
    private Ticket ticket;

    private FunctionSystem functionSystem;


    private Button btn_ok, btn_cancel;
    private  AlertDialog alertDialog;

    public TicketPaidDiaLog(Context context, Ticket ticket) {
        this.context = context;
        this.ticket = ticket;
        functionSystem = new FunctionSystem(context);
    }

    public void show(){
        if(context != null && ticket != null){
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            final View view = inflater.inflate(R.layout.layout_ticket_paid, null);
            addControls(view);
            addEvents();
            builder.setView(view);

            alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.setCancelable(true);
            alertDialog.show();
        }else{
            functionSystem.showDialogError(context.getResources().getString(R.string.dialog_info_car_error_show));
        }
    }



    private void addControls(View view) {
        btn_ok = view.findViewById(R.id.btn_ticketvote_ok);
        btn_cancel = view.findViewById(R.id.btn_ticketvote_back);
    }



    private void addEvents() {
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("action", "useTicket");
                    jsonObject.put("_id", ticket.getId());
                    new useTicketAPI().execute(jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("action", "cancelTicket");
                    jsonObject.put("_id", ticket.getId());
                    new cancelTicketAPI().execute(jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private class useTicketAPI extends AsyncTask<String, String, String> {
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
                functionSystem.showDialogSuccess("Thành Công");
            }else{
                functionSystem.showDialogError("Có lỗi xảy ra, thử lại sau");
            }


            alertDialog.dismiss();
        }
    }

    private class cancelTicketAPI extends AsyncTask<String, String, String> {
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
                functionSystem.showDialogSuccess("Đã thực hiện hủy vé thành công");
            }else{
                functionSystem.showDialogError("Có lỗi xảy ra, thử lại sau");
            }

            alertDialog.dismiss();
        }
    }
}
