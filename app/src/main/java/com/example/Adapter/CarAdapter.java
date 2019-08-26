package com.example.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entity.Car;
import com.example.entity.ItemHomeCar;
import com.example.entity.ItemHomeSupplierCar;
import com.example.entity.Message;
import com.example.vcar.AlerDialog.InforCarDiaLog;
import com.example.vcar.FunctionSystem;
import com.example.vcar.R;
import com.example.vcar.RegistrationActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

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
        private View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
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
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("_id", listData.get(position).getId());
                jsonObject.put("action", "getId");
                new getCarAPI().execute(jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        private class getCarAPI extends AsyncTask<String, String, String> {
            protected void onPreExecute() {
                super.onPreExecute();
                functionSystem.showLoading();
            }

            protected String doInBackground(String... params) {
                return functionSystem.postMethod(context.getResources().getString(R.string.host_api).toString() + "car", params[0]);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                functionSystem.hideLoading();
                Message message = new Message(result);
                if(message.getCode() == 200){
                    new InforCarDiaLog(context, new Car(message.getData())).show();
                }else{
                    functionSystem.showDialogError(message.getMessage());
                }
            }
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
                    .placeholder(R.drawable.icon_download)
                    .error(R.drawable.ic_directions_bus_black_24dp)
                    .into(holder.img_Image);
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
