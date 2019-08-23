package com.example.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entity.ItemHomeCar;
import com.example.entity.ItemHomeSupplierCar;
import com.example.vcar.R;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.MyViewHolder> {
    private List<ItemHomeCar> listData;
    private LayoutInflater layoutInflater;
    private Context context;


    public CarAdapter(Context context, List<ItemHomeCar> listData) {
        this.listData = listData;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_controlSea;
        public ImageView img_Image;


        public MyViewHolder(View itemView) {
            super(itemView);
            txt_controlSea = itemView.findViewById(R.id.txt_home_car_controlSea);
            img_Image = itemView.findViewById(R.id.img_home_car_image);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = layoutInflater.inflate(R.layout.item_home_car,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ItemHomeCar item = listData.get(position);
        holder.txt_controlSea.setText(item.getControlSea());

    }


    @Override
    public int getItemCount() {
        return listData.size();
    }
}
