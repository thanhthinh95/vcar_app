package com.example.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entity.ItemHomeSupplierCar;
import com.example.vcar.FunctionSystem;
import com.example.vcar.R;

import java.util.List;

public class SupplierCarAdapter extends RecyclerView.Adapter<SupplierCarAdapter.MyViewHolder> {
    private List<ItemHomeSupplierCar> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    FunctionSystem functionSystem;

    public SupplierCarAdapter(Context context, List<ItemHomeSupplierCar> listData) {
        this.listData = listData;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);

        functionSystem = new FunctionSystem(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_name, txt_route, txt_fare, txt_numberPhone;
        public RecyclerView rec_listData;

        public MyViewHolder(View itemView) {
            super(itemView);
            addControls();
            addEvents();
        }

        private void addControls() {
            txt_name = itemView.findViewById(R.id.txt_ticket_carSupplierName);
            txt_route = itemView.findViewById(R.id.txt_promotion_code);
            rec_listData = itemView.findViewById(R.id.recy_home_supplierCar_listCar);
            txt_fare = itemView.findViewById(R.id.txt_ticket_number);
            txt_numberPhone = itemView.findViewById(R.id.txt_home_supplierCar_numberPhone);
        }

        private void addEvents() {
            txt_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showInfoCarSupplier(getAdapterPosition());
                }
            });

            txt_route.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showInfoCarSupplier(getAdapterPosition());
                }
            });


        }

        private void showInfoCarSupplier(int position){
//            functionSystem.showDialogSuccess("Thong tin nha xe: " + listData.get(position).getName());
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
        holder.txt_name.setText(itemRecyclerHome.getName());
        holder.txt_fare.setText(itemRecyclerHome.getFare());
        holder.txt_route.setText(itemRecyclerHome.getRoute());
        holder.txt_numberPhone.setText(itemRecyclerHome.getNumberPhone());

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
