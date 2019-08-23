package com.example.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entity.ItemHomeSupplierCar;
import com.example.vcar.R;

import java.util.List;

public class SupplierCarAdapter extends RecyclerView.Adapter<SupplierCarAdapter.MyViewHolder> {
    private List<ItemHomeSupplierCar> listData;
    private LayoutInflater layoutInflater;
    private Context context;


    public SupplierCarAdapter(Context context, List<ItemHomeSupplierCar> listData) {
        this.listData = listData;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_nameCarSupplier, txt_route, txt_timeStart;
        public ProgressBar pro_vote;
        public RecyclerView rec_listData;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_nameCarSupplier = itemView.findViewById(R.id.txt_home_supplierCar_name);
            txt_route = itemView.findViewById(R.id.txt_home_supplierCar_route);
            rec_listData = itemView.findViewById(R.id.recy_home_supplierCar_listCar);
            txt_timeStart = itemView.findViewById(R.id.txt_home_supplierCar_timeStartEarly);
            pro_vote = itemView.findViewById(R.id.pro_home_supplierCar_vote);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, txt_nameCarSupplier.getText(),Toast.LENGTH_SHORT).show();
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(context,"Long item clicked " + txt_nameCarSupplier.getText() , Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = layoutInflater.inflate(R.layout.item_home_suppliercar,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ItemHomeSupplierCar itemRecyclerHome = listData.get(position);
        holder.txt_nameCarSupplier.setText(itemRecyclerHome.getNameCarSupplier());
        holder.txt_timeStart.setText(itemRecyclerHome.getEarliestTimeStart());
        holder.txt_route.setText(itemRecyclerHome.getRoute());

//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.rec_listData.setLayoutManager(mLayoutManager);
        CarAdapter carAdapter = new CarAdapter(context, itemRecyclerHome.getListCar());
        holder.rec_listData.setItemAnimator(new DefaultItemAnimator());
        holder.rec_listData.setAdapter(carAdapter);
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }
}
