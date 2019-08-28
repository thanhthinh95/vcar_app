package com.example.vcar.AlerDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.entity.Car;
import com.example.entity.Message;
import com.example.entity.Trip;
import com.example.vcar.FunctionSystem;
import com.example.vcar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookTicketDiaLog {
    private Car car;
    private Context context;

    private FunctionSystem functionSystem;
    private TextView txt_controlSea, txt_route, txt_type, txt_fare, txt_seat_diagram, txt_seat_empty, txt_seat_selected, txt_seat_selecting, txt_sum_money, txt_date_start;
    private ImageButton imgbtn_collapse_extend, imgbtn_trans, imgbtn_nextFloor, imgbtn_prevFloor, imtbtn_update_date_start;
    private ConstraintLayout conlay_info_ticket, conlay_info_car, conlay_seat_diagram;
    private Spinner sp_hours;
    private TableLayout tbl_diagram;
    private Button btn_back, btn_next;

    private int currendFloor = 0;
    private int sumSeatEmpty = 0;
    private int sumSeatSelected = 0;
    private int sumSeatSelecting = 0;
    private boolean direction = true; //Phuong huong di chuyen, true: xuoi; false : ngược
    private boolean statusCollapse = true;//Trang thai dong mo menu, true: dong, false: mo

    private JSONObject seatSelecting = new JSONObject();
    private AlertDialog alertDialog;
    private List<Trip> trips = null;
    private Date dateStart = new Date();


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

            builder.setView(view);
            alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            alertDialog.show();



            addControls(view);
            setValues();
            addEvents();
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
        imgbtn_trans = view.findViewById(R.id.imgbtn_book_ticket_trans);
        tbl_diagram = view.findViewById(R.id.tbl_book_ticket_diagram);
        imgbtn_nextFloor = view.findViewById(R.id.imgbtn_book_ticket_nextfloor);
        imgbtn_prevFloor = view.findViewById(R.id.imgbtn_book_ticket_prevfloor);
        txt_seat_diagram = view.findViewById(R.id.txt_book_ticket_seat_diagram);
        txt_seat_empty = view.findViewById(R.id.txt_book_ticket_seat_empty);
        txt_seat_selected = view.findViewById(R.id.txt_book_ticket_seat_selected);
        txt_seat_selecting = view.findViewById(R.id.txt_book_ticket_seat_selecting);
        txt_sum_money = view.findViewById(R.id.txt_book_ticket_sum_money);
        sp_hours = view.findViewById(R.id.sp_book_ticket_hours);
        txt_date_start = view.findViewById(R.id.txt_book_ticket_date_start);
        imtbtn_update_date_start = view.findViewById(R.id.imgbtn_book_ticket_update_date_start);
    }

    private void setValues() {
        if(car != null){
            txt_controlSea.setText(car.getNameSupplier() + " - " + car.getControlSea());
            txt_type.setText(context.getResources().getString(R.string.info_car_type) + ": " + car.getType() + " (" + car.getNumberSeat() + " chỗ)");
            txt_fare.setText(context.getResources().getString(R.string.info_car_fare) + ": "+ car.getFare());


            showDirection();
            showDateStart();

            sumSeatEmpty = car.getNumberSeat();
            showValueNumberSeat();

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("carId", car.getId());
                jsonObject.put("action", "getCarId");
                new getTripAPI().execute(jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }


            if(car.getSeatDiagram().length > 0){//Hien thi so do cho ngoi tang 1
                currendFloor = 0;
                showSeatDiagram(currendFloor);

                if(car.getSeatDiagram().length == 1){
                    imgbtn_nextFloor.setVisibility(View.INVISIBLE);
                    imgbtn_prevFloor.setVisibility(View.INVISIBLE);
                }
            }
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
                    ViewGroup.LayoutParams params = conlay_seat_diagram.getLayoutParams();
                    params.height = 1200;
                    conlay_seat_diagram.setLayoutParams(params);
                    imgbtn_collapse_extend.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                }
            }
        });

        imtbtn_update_date_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDateStart();
            }
        });

        txt_date_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDateStart();
            }
        });

        imgbtn_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                direction = !direction;
                showDirection();
                showTrips();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new InforCarDiaLog(context, car).show();
                alertDialog.dismiss();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Dang chuyen sang thanh toan", Toast.LENGTH_SHORT).show();
            }
        });


        imgbtn_nextFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currendFloor < car.getSeatDiagram().length - 1){
                    currendFloor++;
                }else{
                    currendFloor = 0;
                }

                showSeatDiagram(currendFloor);
            }
        });

        imgbtn_prevFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currendFloor == 0){
                    currendFloor = car.getSeatDiagram().length - 1;
                }else{
                    currendFloor --;
                }

                showSeatDiagram(currendFloor);
            }
        });
    }

    private void changeDateStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        int mYear =  calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                R.style.DialogDateTimePickerTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        try {
                            String dateOnlyValue = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            dateStart = functionSystem.dateOnlyFormat.parse(dateOnlyValue);
                            showDateStart();
                            showTrips();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void showDateStart() {
        if(dateStart != null){
            txt_date_start.setText(context.getResources().getString(R.string.book_ticket_date) + ": " + functionSystem.dateOnlyFormat.format(dateStart));
        }

    }

    private void showDirection() {
        if(direction){//Chieu xuoi
            txt_route.setText(car.getStartPoint() + " - " + car.getEndPoint());
        }else{//Chieu nguoc
            txt_route.setText(car.getEndPoint() + " - " + car.getStartPoint());
        }
    }


    private void showSeatDiagram(int numberFloor){
        try {
            if(car.getSeatDiagram().length == 1){
                txt_seat_diagram.setText(context.getResources().getString(R.string.book_ticket_seat_diagram));
            }else{
                txt_seat_diagram.setText(context.getResources().getString(R.string.book_ticket_seat_diagram) + " tầng " + (numberFloor + 1));
            }

            JSONObject map = car.getSeatDiagram()[numberFloor];
            int numRow = map.getInt("numRow");
            int numColumn = map.getInt("numColumn");
            JSONObject data = map.getJSONObject("data");

            String nameSeat;

            tbl_diagram.removeAllViews();


            for (int row = 0; row < numRow; row++) {
                TableRow tableRow= new TableRow(context);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                tableRow.setLayoutParams(lp);

                for(int col = 0; col < numColumn; col++){
                    final Button btn_seat = new Button(context);
                    int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, context.getResources().getDisplayMetrics());
                    int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, context.getResources().getDisplayMetrics());


                    btn_seat.setLayoutParams(new TableRow.LayoutParams(width,height));
                    btn_seat.setTextSize(10);

                    nameSeat = (numberFloor + 1) + mapNumber(row) + (col + 1);
                    if(data.getString(nameSeat).equals("0")){//Vi tri loi di
                        btn_seat.setBackgroundResource(R.drawable.background_none);

                    }else if(data.getString(nameSeat).equals("1")){//Vi tri ghe
                        btn_seat.setText(nameSeat);


                        btn_seat.setOnClickListener(new View.OnClickListener() {//Su kien khi click chon ghe
                            @Override
                            public void onClick(View view) {
                                try {
                                    if(!seatSelecting.isNull(btn_seat.getText().toString())){//Ghe da chon truoc do, huy bo cho
                                        seatSelecting.remove(btn_seat.getText().toString());
                                        btn_seat.setBackgroundResource(R.drawable.background_seat_emply);
                                        sumSeatSelecting--;
                                    }else{//Cho ngoi chua duoc chon, tien hanh chon cho
                                        seatSelecting.put(btn_seat.getText().toString(), "2");
                                        btn_seat.setBackgroundResource(R.drawable.background_seat_selecting);
                                        sumSeatSelecting++;
                                    }

                                    sumSeatEmpty = car.getNumberSeat() - sumSeatSelected - sumSeatSelecting;

                                    showValueNumberSeat();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });


                        if(!seatSelecting.isNull(btn_seat.getText().toString())){//Neu ghe da duoc chon truoc do, hien thi lai
                            btn_seat.setBackgroundResource(R.drawable.background_seat_selecting);

                        }else{
                            btn_seat.setBackgroundResource(R.drawable.background_seat_emply);
                        }
                    }


                    tableRow.addView(btn_seat);
                }

                tbl_diagram.addView(tableRow, row);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showValueNumberSeat() {
        txt_seat_empty.setText(context.getResources().getString(R.string.book_ticket_seat_emply) + ": " + sumSeatEmpty);
        txt_seat_selected.setText(context.getResources().getString(R.string.book_ticket_seat_selected) + ": " + sumSeatSelected);
        txt_seat_selecting.setText(context.getResources().getString(R.string.book_ticket_seat_selecting) + ": " + sumSeatSelecting);
        txt_sum_money.setText((car.getFare() * sumSeatSelecting) + " VNĐ");
    }

    private String mapNumber(int number) {
        switch (number) {
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            case 3:
                return "D";
            case 4:
                return "E";
            case 5:
                return "F";
            case 6:
                return "G";
            case 7:
                return "H";
            case 8:
                return "I";
            case 9:
                return "J";
            case 10:
                return "K";
            case 11:
                return "L";
            case 12:
                return "M";
            case 13:
                return "N";
            case 14:
                return "O";
            case 15:
                return "P";
            case 16:
                return "Q";
            case 17:
                return "R";
            case 18:
                return "S";
            case 19:
                return "T";
            case 20:
                return "U";
            case 21:
                return "V";
            case 22:
                return "W";
            case 23:
                return "X";
            case 24:
                return "Y";
            case 25:
                return "Z";
            default:
                return null;
        }
    }


    private class getTripAPI extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            functionSystem.showLoading();
        }

        protected String doInBackground(String... params) {
            return functionSystem.postMethod(context.getResources().getString(R.string.host_api).toString() + "trip", params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            functionSystem.hideLoading();
            Message message = new Message(result);
            if(message.getCode() == 200){
                try {
                    JSONArray jsonArray = new JSONArray(message.getData());
                    trips = new ArrayList<Trip>();
                    for (int i = 0; i < jsonArray.length(); i++){
                        trips.add(new Trip(jsonArray.get(i).toString()));
                    }
                    showTrips();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                functionSystem.showDialogError(message.getMessage());
            }
        }
    }

    private void showTrips() {
        if(trips != null && trips.size() > 0){
            List<String> listValue = new ArrayList<String>();
            int typeMach = 0;//Chieu nguoc
            if(direction){//chieu xuoi
                typeMach = 1;
            }

            sp_hours.setAdapter(null);
            Date dateNow = new Date();
            if(functionSystem.dateOnlyFormat.format(dateStart).equals(functionSystem.dateOnlyFormat.format(dateNow))){//Ngay khoi hanh la ngay hom nay
                String dateTimeValue;

                for(int i = 0; i < trips.size(); i++){
                    try {
                        if(trips.get(i).getType() == typeMach){
                            dateTimeValue = functionSystem.dateOnlyFormat.format(dateStart) + " " + trips.get(i).getTimeStartString();
                            if(dateNow.getTime() < functionSystem.dateTimeFormat.parse(dateTimeValue).getTime()) {//Hien thi nhung chuyen di trong tuong lai
                                listValue.add(trips.get(i).getTimeStartString());
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }else{//Ngay khoi hanh khong phai la hom nay
                for(int i = 0; i < trips.size(); i++){
                    if(trips.get(i).getType() == typeMach){//Hien thi tat ca cac chuyen di theo chieu xuoi / nguoc
                        listValue.add(trips.get(i).getTimeStartString());
                    }
                }
            }


            if(listValue.size() == 0){
                String mesage = txt_route.getText().toString() + " " + functionSystem.dateOnlyFormat.format(dateStart) + " đã hết chuyến xe phục vụ. Hãy chọn ngày khác!";
                functionSystem.showDialogError(mesage);

            }else{
                ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, listValue);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                sp_hours.setAdapter(adapter);
            }
        }else {
            functionSystem.showDialogError(context.getResources().getString(R.string.dialog_book_ticket_error_trip));
        }
    }
}
