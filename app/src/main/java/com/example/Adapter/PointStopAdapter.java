package com.example.Adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entity.Car;
import com.example.entity.ItemHomeCar;
import com.example.entity.Message;
import com.example.vcar.AlerDialog.InforCarDiaLog;
import com.example.vcar.FunctionSystem;
import com.example.vcar.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PointStopAdapter extends RecyclerView.Adapter<PointStopAdapter.MyViewHolder> {
    private String[] listData;
    private LayoutInflater layoutInflater;
    private Context context;

    FunctionSystem functionSystem;

    public PointStopAdapter(Context context, String[] listData) {
        this.listData = listData;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        functionSystem = new FunctionSystem(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_name;
        public ImageView img_icon;
        private View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            addControls();
        }

        private void addControls() {
            txt_name = itemView.findViewById(R.id.txt_info_car_namePointStop);
            img_icon = itemView.findViewById(R.id.img_info_car_iconPointStop);
        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = layoutInflater.inflate(R.layout.item_info_car_pointstop, parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_name.setText(listData[position]);

        if(position == 0){
            holder.img_icon.setImageResource(R.drawable.start_point);
        }else if(position == listData.length - 1){
            holder.img_icon.setImageResource(R.drawable.end_point);
        }else{
            holder.img_icon.setImageResource(R.drawable.point_stop);

        }
    }

    @Override
    public int getItemCount() {
        return listData.length;
    }
}
