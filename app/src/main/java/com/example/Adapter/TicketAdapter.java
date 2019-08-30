package com.example.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entity.Ticket;
import com.example.vcar.FunctionSystem;
import com.example.vcar.R;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder> {
    private List<Ticket> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    FunctionSystem functionSystem;

    public TicketAdapter(Context context, List<Ticket>  listData) {
        this.listData = listData;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        functionSystem = new FunctionSystem(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_name, txt_route, txt_date, txt_position, txt_number, txt_fare;

        private View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            addControls();
        }

        private void addControls() {
            txt_name = itemView.findViewById(R.id.txt_ticket_carSupplierName);
            txt_route = itemView.findViewById(R.id.txt_ticket_route);
            txt_date = itemView.findViewById(R.id.txt_ticket_date);
            txt_position = itemView.findViewById(R.id.txt_ticket_position);
            txt_number = itemView.findViewById(R.id.txt_ticket_number);
            txt_fare = itemView.findViewById(R.id.txt_ticket_fare);
        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = layoutInflater.inflate(R.layout.item_ticket, parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Ticket ticket = listData.get(position);
        holder.txt_name.setText(ticket.getCarSupplierName() + " " + ticket.getControlSea());
        holder.txt_route.setText(ticket.getRoute());
        holder.txt_date.setText("Khởi hành: " + functionSystem.dateOnlyFormat.format(ticket.getDateTrip()) +" " + functionSystem.timeOnLyFormat.format(ticket.getTimeTrip()));
        holder.txt_position.setText("Vị trí: " + ticket.getPosition());
        holder.txt_number.setText(ticket.getCarSupplierPhone());
        holder.txt_fare.setText(functionSystem.formatMoney.format(ticket.getFare()) + " VNĐ");
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
