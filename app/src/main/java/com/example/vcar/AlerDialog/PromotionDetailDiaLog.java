package com.example.vcar.AlerDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.entity.Car;
import com.example.entity.Message;
import com.example.entity.Promotion;
import com.example.vcar.FunctionSystem;
import com.example.vcar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PromotionDetailDiaLog {
    private Context context;
    private Promotion promotion;

    private FunctionSystem functionSystem;

    private TextView txt_name, txt_code, txt_nameCarSupplier, txt_route, txt_budget, txt_discount, txt_date, txt_status;
    private Button btn_back;

    private  AlertDialog alertDialog;

    public PromotionDetailDiaLog(Context context, Promotion promotion) {
        this.context = context;
        this.promotion = promotion;
        functionSystem = new FunctionSystem(context);
    }

    public void show(){
        if(context != null && promotion != null){
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            final View view = inflater.inflate(R.layout.layout_promotion_detail, null);
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
        txt_name = view.findViewById(R.id.txt_promotion_detail_name);
        txt_code = view.findViewById(R.id.txt_promotion_detail_code);
        txt_nameCarSupplier = view.findViewById(R.id.txt_promotion_detail_nameCarSupplier);
        txt_route = view.findViewById(R.id.txt_promotion_detail_route);
        txt_budget = view.findViewById(R.id.txt_promotion_detail_budget);
        txt_discount = view.findViewById(R.id.txt_promotion_detail_discount);
        txt_date = view.findViewById(R.id.txt_promotion_detail_date);
        txt_status = view.findViewById(R.id.txt_promotion_detail_status);
        btn_back = view.findViewById(R.id.btn_promotion_detail_back);
    }

    private void setValues() {
        if(promotion != null){
            txt_name.setText(promotion.getName());
            txt_code.setText(promotion.getCode());
            txt_nameCarSupplier.setText("Nhà xe: " + promotion.getCarSupplierName());
            txt_route.setText(promotion.getRoute());
            txt_budget.setText("Ngân sách: " + functionSystem.formatMoney.format(promotion.getBudget()) + " VNĐ");
            txt_discount.setText("Giảm giá: " + functionSystem.formatMoney.format(promotion.getDiscount()) + " VNĐ");
            txt_date.setText(functionSystem.dateTimeFormat.format(promotion.getDateStart()) + " - " + functionSystem.dateTimeFormat.format(promotion.getDateEnd()));
            txt_status.setText((promotion.getStatus() == 1 ? "Còn hiệu lực" : "Hết hiệu lực"));
        }
    }


    private void addEvents() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}
