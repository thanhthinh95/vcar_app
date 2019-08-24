package com.example.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entity.ItemHomeCar;
import com.example.entity.ItemHomeSupplierCar;
import com.example.vcar.FunctionSystem;
import com.example.vcar.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.MyViewHolder> {
    private List<ItemHomeCar> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    FunctionSystem functionSystem;

    public CarAdapter(Context context, List<ItemHomeCar> listData) {
        this.listData = listData;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        functionSystem = new FunctionSystem(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_controlSea;
        public ImageView img_Image;

        public MyViewHolder(View itemView) {
            super(itemView);

            addControls();
            addEvents();
        }

        private void addControls() {
            txt_controlSea = itemView.findViewById(R.id.txt_home_car_controlSea);
            img_Image = itemView.findViewById(R.id.img_home_car_image);
        }

        private void addEvents() {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showInfoCar(getAdapterPosition());
                }
            });
        }

        private void showInfoCar(int position) {
            functionSystem.showDialogSuccess("Day la thong tin xe: " + listData.get(position).getControlSea());
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
        if(item.getImage().length() > 0){
            Picasso.get()
                    .load(context.getResources().getString(R.string.host) + item.getImage())
                    .placeholder(R.drawable.ic_cloud_download_black_24dp)
                    .error(R.drawable.ic_directions_bus_black_24dp)
                    .into(holder.img_Image);

        }
    }



    @Override
    public int getItemCount() {
        return listData.size();
    }
}
