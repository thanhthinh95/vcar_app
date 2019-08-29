package com.example.vcar.AlerDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Adapter.PointStopAdapter;
import com.example.entity.Car;
import com.example.vcar.FunctionSystem;
import com.example.vcar.R;
import com.squareup.picasso.Picasso;

public class InforCarDiaLog {
    private Car car;
    private Context context;

    FunctionSystem functionSystem;
    TextView txt_controlSea, txt_route, txt_type, txt_fare, txt_pointStop;
    RecyclerView recy_pointStop;
    ImageView img_image;
    ImageButton imgbtn_prev, imgbtn_next, imgbtn_trans;
    Button btn_back, btn_next;

    int currentIndex = 0;
    boolean direction = true; //Phuong huong di chuyen, true: xuoi; false : ngược

    private AlertDialog alertDialog;

    public InforCarDiaLog(Context context, Car car) {
        this.context = context;
        this.car = car;
        functionSystem = new FunctionSystem(context);
    }

    public void show(){
        if(context != null && car != null){
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            final View view = inflater.inflate(R.layout.layout_home_info_car, null);
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
        recy_pointStop = view.findViewById(R.id.recy_payment_ticket);
        txt_controlSea = view.findViewById(R.id.txt_info_car_controSea);
        txt_type = view.findViewById(R.id.txt_book_ticket_type);
        txt_fare = view.findViewById(R.id.txt_book_ticket_fare);
        txt_pointStop = view.findViewById(R.id.txt_info_car_pointStop);
        txt_route = view.findViewById(R.id.txt_book_ticket_route);
        img_image = view.findViewById(R.id.img_info_car_image);
        imgbtn_prev = view.findViewById(R.id.imgbtn_info_car_prev);
        imgbtn_next = view.findViewById(R.id.imgbtn_info_car_next);
        btn_back = view.findViewById(R.id.btn_payment_back);
        btn_next = view.findViewById(R.id.btn_payment_next);
        imgbtn_trans = view.findViewById(R.id.imgbtn_book_ticket_trans);
    }

    private void setValues() {
        if(car != null){
            showImage();
            txt_controlSea.setText(car.getNameSupplier() + " - " + car.getControlSea());
            txt_pointStop.setText(context.getResources().getString(R.string.info_car_pointStop) + " (" + (car.getPointStop().length + 2 )+ ")");
            txt_type.setText(context.getResources().getString(R.string.info_car_type) + ": " + car.getType() + " (" + car.getNumberSeat() + " chỗ)");
            txt_fare.setText(context.getResources().getString(R.string.info_car_fare) + ": "+ car.getFare());
            showPointStop();
        }
    }

    private void addEvents() {
        imgbtn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentIndex > 0){
                    currentIndex--;
                }else{
                    currentIndex = car.getImageUrl().length - 1;
                }

                showImage();
            }
        });

        imgbtn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentIndex < car.getImageUrl().length - 1){
                    currentIndex++;
                }else{
                    currentIndex = 0;
                }

                showImage();
            }
        });

        imgbtn_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                direction = !direction;
                showPointStop();
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
                alertDialog.dismiss();
                new BookTicketDiaLog(context, car, direction).show();
            }
        });
    }

    private void showPointStop() {
        String[] pointStop = new String[car.getPointStop().length + 2];
        if(direction){//Chieu xuoi
            txt_route.setText(car.getStartPoint() + " - " + car.getEndPoint());

            pointStop[0] = car.getStartPoint();
            for(int i = 0; i < car.getPointStop().length; i++){
                pointStop[i + 1] = car.getPointStop()[i];
            }
            pointStop[car.getPointStop().length + 1] = car.getEndPoint();
        }else{//Chieu nguoc
            txt_route.setText(car.getEndPoint() + " - " + car.getStartPoint());

            pointStop[0] = car.getEndPoint();
            for(int j = 1, i = car.getPointStop().length - 1; i >= 0; i--, j++){
                pointStop[j] = car.getPointStop()[i];
            }
            pointStop[car.getPointStop().length + 1] = car.getStartPoint();
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recy_pointStop.setLayoutManager(mLayoutManager);
        PointStopAdapter pointStopAdapter = new PointStopAdapter(context, pointStop);
        recy_pointStop.setItemAnimator(new DefaultItemAnimator());
        recy_pointStop.setAdapter(pointStopAdapter);
    }

    private void showImage()  {
        if(car.getImageUrl()[currentIndex] != null){
            Picasso.get()
                    .load(context.getResources().getString(R.string.host) + car.getImageUrl()[currentIndex])
                    .placeholder(R.drawable.icon_download)
                    .error(R.drawable.ic_directions_bus_black_24dp)
                    .into(img_image);
        }

    }
}
