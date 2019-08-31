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

import com.example.entity.Message;
import com.example.entity.Ticket;
import com.example.vcar.FunctionSystem;
import com.example.vcar.R;

import org.json.JSONException;
import org.json.JSONObject;

public class TicketVoteDiaLog {
    private Context context;
    private Ticket ticket;

    private FunctionSystem functionSystem;
    private ImageButton imgbtn_stars_1, imgbtn_stars_2, imgbtn_stars_3, imgbtn_stars_4, imgbtn_stars_5;
    EditText edit_complain;
    int vote = 0;

    private Button btn_ok, btn_back;
    private  AlertDialog alertDialog;

    public TicketVoteDiaLog(Context context, Ticket ticket) {
        this.context = context;
        this.ticket = ticket;
        functionSystem = new FunctionSystem(context);
    }

    public void show(){
        if(context != null && ticket != null){
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            final View view = inflater.inflate(R.layout.layout_ticket_vote, null);
            addControls(view);
            setValues();
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
        btn_back = view.findViewById(R.id.btn_ticketvote_back);
        imgbtn_stars_1 = view.findViewById(R.id.imgbtn_ticket_stars_1);
        imgbtn_stars_2 = view.findViewById(R.id.imgbtn_ticket_stars_2);
        imgbtn_stars_3 = view.findViewById(R.id.imgbtn_ticket_stars_3);
        imgbtn_stars_4 = view.findViewById(R.id.imgbtn_ticket_stars_4);
        imgbtn_stars_5 = view.findViewById(R.id.imgbtn_ticket_stars_5);
        edit_complain = view.findViewById(R.id.edit_ticket_vote_complain);
    }

    private void setValues(){
        if(ticket.getVote() != -9999){
            vote = ticket.getVote();
            showVote();
        }
    }

    private void addEvents() {
        imgbtn_stars_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vote = 1;
                showVote();
            }
        });

        imgbtn_stars_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vote = 2;
                showVote();
            }
        });

        imgbtn_stars_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vote = 3;
                showVote();
            }
        });

        imgbtn_stars_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vote = 4;
                showVote();
            }
        });

        imgbtn_stars_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vote = 5;
                showVote();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("action", "voteTicket");
                    jsonObject.put("_id", ticket.getId());
                    jsonObject.put("vote", vote);
                    jsonObject.put("complain", edit_complain.getText().toString());
                    new voteTicketAPI().execute(jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }


    private void showVote(){
        switch (vote){
            case 1:
                imgbtn_stars_1.setImageResource(R.drawable.ic_star_black_24dp);
                imgbtn_stars_2.setImageResource(R.drawable.ic_star_border_black_24dp);
                imgbtn_stars_3.setImageResource(R.drawable.ic_star_border_black_24dp);
                imgbtn_stars_4.setImageResource(R.drawable.ic_star_border_black_24dp);
                imgbtn_stars_5.setImageResource(R.drawable.ic_star_border_black_24dp);
                break;
            case 2:
                imgbtn_stars_1.setImageResource(R.drawable.ic_star_black_24dp);
                imgbtn_stars_2.setImageResource(R.drawable.ic_star_black_24dp);
                imgbtn_stars_3.setImageResource(R.drawable.ic_star_border_black_24dp);
                imgbtn_stars_4.setImageResource(R.drawable.ic_star_border_black_24dp);
                imgbtn_stars_5.setImageResource(R.drawable.ic_star_border_black_24dp);
                break;
            case 3:
                imgbtn_stars_1.setImageResource(R.drawable.ic_star_black_24dp);
                imgbtn_stars_2.setImageResource(R.drawable.ic_star_black_24dp);
                imgbtn_stars_3.setImageResource(R.drawable.ic_star_black_24dp);
                imgbtn_stars_4.setImageResource(R.drawable.ic_star_border_black_24dp);
                imgbtn_stars_5.setImageResource(R.drawable.ic_star_border_black_24dp);
                break;
            case 4:
                imgbtn_stars_1.setImageResource(R.drawable.ic_star_black_24dp);
                imgbtn_stars_2.setImageResource(R.drawable.ic_star_black_24dp);
                imgbtn_stars_3.setImageResource(R.drawable.ic_star_black_24dp);
                imgbtn_stars_4.setImageResource(R.drawable.ic_star_black_24dp);
                imgbtn_stars_5.setImageResource(R.drawable.ic_star_border_black_24dp);
                break;
            case 5:
                imgbtn_stars_1.setImageResource(R.drawable.ic_star_black_24dp);
                imgbtn_stars_2.setImageResource(R.drawable.ic_star_black_24dp);
                imgbtn_stars_3.setImageResource(R.drawable.ic_star_black_24dp);
                imgbtn_stars_4.setImageResource(R.drawable.ic_star_black_24dp);
                imgbtn_stars_5.setImageResource(R.drawable.ic_star_black_24dp);
                break;
        }
    }

    private class voteTicketAPI extends AsyncTask<String, String, String> {
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
