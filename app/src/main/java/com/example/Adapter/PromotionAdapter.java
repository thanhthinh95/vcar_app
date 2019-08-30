package com.example.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entity.Promotion;
import com.example.vcar.AlerDialog.PromotionDetailDiaLog;
import com.example.vcar.FunctionSystem;
import com.example.vcar.R;

import java.util.List;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.MyViewHolder> {
    private List<Promotion> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    FunctionSystem functionSystem;

    public PromotionAdapter(Context context, List<Promotion> listData) {
        this.listData = listData;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        functionSystem = new FunctionSystem(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_name, txt_code, txt_discount, txt_date, txt_carSupplierName, txt_route;
        private View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            addControls();
            addEvents();
        }

        private void addControls() {
            txt_carSupplierName = itemView.findViewById(R.id.txt_promotion_carSupplierName);
            txt_route = itemView.findViewById(R.id.txt_promotion_route);
            txt_name = itemView.findViewById(R.id.txt_ticket_carSupplierName);
            txt_code = itemView.findViewById(R.id.txt_promotion_code);
            txt_discount = itemView.findViewById(R.id.txt_promotion_discount);
            txt_date = itemView.findViewById(R.id.txt_promotion_date);
        }

        private void addEvents() {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPromotionDetail(getAdapterPosition());
                }
            });
        }


    }

    private void showPromotionDetail(int position) {
        new PromotionDetailDiaLog(context, listData.get(position)).show();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = layoutInflater.inflate(R.layout.item_promotion_pro,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Promotion item = listData.get(position);
        holder.txt_carSupplierName.setText("Nhà xe: " + item.getCarSupplierName());
        holder.txt_route.setText("Tuyến đường: " + item.getRoute());
        holder.txt_name.setText(item.getName());
        holder.txt_code.setText(item.getCode());
        holder.txt_discount.setText("Giảm giá: " + functionSystem.formatMoney.format(item.getDiscount()) + " VNĐ");
        holder.txt_date.setText(item.getDateStartAndDateEnd());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
