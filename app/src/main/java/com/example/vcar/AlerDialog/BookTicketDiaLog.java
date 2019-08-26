package com.example.vcar.AlerDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.entity.Car;
import com.example.vcar.FunctionSystem;
import com.example.vcar.R;

import org.json.JSONException;
import org.json.JSONObject;

public class BookTicketDiaLog {
    private Car car;
    private Context context;

    FunctionSystem functionSystem;
    TextView txt_controlSea, txt_route, txt_type, txt_fare;
    ImageButton imgbtn_collapse_extend, imgbtn_trans;
    ConstraintLayout conlay_info_ticket, conlay_info_car, conlay_seat_diagram;
    TableLayout tbl_diagram;

    Button btn_back, btn_next;

    int currentIndex = 0;
    boolean direction = true; //Phuong huong di chuyen, true: xuoi; false : ngược
    boolean statusCollapse = true;//Trang thai dong mo menu, true: dong, false: mo


    AlertDialog alertDialog;

    public BookTicketDiaLog(Context context, Car car, boolean direction) {
        this.context = context;
        this.car = car;
        this.direction = direction;
        functionSystem = new FunctionSystem(context);
    }

    public void show(){
        if(context != null && car != null){
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            final View view = inflater.inflate(R.layout.layout_home_book_ticket, null);
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
        conlay_info_car = view.findViewById(R.id.conlay_book_ticket_info_car);
        conlay_info_ticket = view.findViewById(R.id.conlay_book_ticket_info_ticket);
        conlay_seat_diagram = view.findViewById(R.id.conlay_book_ticket_seatdiagram);
        txt_controlSea = view.findViewById(R.id.txt_bookt_ticket_controlsea);
        txt_type = view.findViewById(R.id.txt_book_ticket_type);
        txt_fare = view.findViewById(R.id.txt_book_ticket_fare);
        imgbtn_collapse_extend = view.findViewById(R.id.imgbtn_book_ticket_collapse_extend);
        txt_route = view.findViewById(R.id.txt_book_ticket_route);
        btn_back = view.findViewById(R.id.btn_book_ticket_back);
        btn_next = view.findViewById(R.id.btn_book_ticket_next);
        imgbtn_trans = view.findViewById(R.id.imgbtn_info_car_trans);
        tbl_diagram = view.findViewById(R.id.tbl_book_ticket_diagram);
    }

    private void setValues() {
        if(car != null){
            txt_controlSea.setText(car.getNameSupplier() + " - " + car.getControlSea());
            txt_type.setText(context.getResources().getString(R.string.info_car_type) + ": " + car.getType() + " (" + car.getNumberSeat() + " chỗ)");
            txt_fare.setText(context.getResources().getString(R.string.info_car_fare) + ": "+ car.getFare());
            showDirection();
            showSeatDiagram();




        }
    }

    private void addEvents() {

        imgbtn_collapse_extend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusCollapse = !statusCollapse;

                if(statusCollapse){//Dang o trang thai dong menu
                    conlay_info_car.setVisibility(View.VISIBLE);
                    conlay_info_ticket.setVisibility(View.VISIBLE);
                    imgbtn_collapse_extend.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                }else{//Dang o trang thai mo menu
                    conlay_info_car.setVisibility(View.GONE);
                    conlay_info_ticket.setVisibility(View.GONE);
//                    ViewGroup.LayoutParams params = conlay_seat_diagram.getLayoutParams();
//                    params.height = 300;
//                    conlay_seat_diagram.setLayoutParams(params);
                    imgbtn_collapse_extend.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                }
            }
        });

        imgbtn_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                direction = !direction;
                showDirection();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Dang chuyen sang thanh toan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDirection() {
        if(direction){//Chieu xuoi
            txt_route.setText(car.getStartPoint() + " - " + car.getEndPoint());
        }else{//Chieu nguoc
            txt_route.setText(car.getEndPoint() + " - " + car.getStartPoint());
        }
    }

    private void showSeatDiagram(){


        for(int i = 0; i < car.getSeatDiagram().length; i++){
            try {
                JSONObject map = car.getSeatDiagram()[i];
                int numRow = map.getInt("numRow");
                int numColumn = map.getInt("numColumn");

                functionSystem.showDialogSuccess(numRow + " - " + numColumn);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


//        for (int i = 0; i <2; i++) {
//            TableRow row= new TableRow(context);
//            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
//            row.setLayoutParams(lp);
//
//
//            ImageButton addBtn = new ImageButton(context);
//            addBtn.setImageResource(R.drawable.end_point);
//
//            row.addView(addBtn);
//            tbl_diagram.addView(row,i);
//        }

    }

}
