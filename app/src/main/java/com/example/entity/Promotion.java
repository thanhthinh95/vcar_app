package com.example.entity;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Promotion {
    private String id;
    private String carSupplierId;
    private String carSupplierName;
    private String startPoint;
    private String endPoint;

    private String route;
    private String name;
    private String code;
    private Date dateStart;
    private Date dateEnd;
    private int amount = -9999;
    private long budget = -9999;
    private long discount = -9999;
    private int status = -9999;

    private String action;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private DateFormat dateFormatString = new SimpleDateFormat("dd/MM/yy");

    public Promotion() {
    }


    public Promotion(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            this.id = jsonObject.getString("_id");
            this.carSupplierId = jsonObject.getString("carSupplierId");
            this.carSupplierName = jsonObject.getString("carSupplierName");
            this.startPoint = jsonObject.getString("startPoint");
            this.endPoint = jsonObject.getString("endPoint");
            this.name = jsonObject.getString("name");
            this.code = jsonObject.getString("code");
            this.status = jsonObject.getInt("status");

            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            if(!jsonObject.isNull("dateStart")){
                this.dateStart = dateFormat.parse(jsonObject.getString("dateStart"));
            }

            if(!jsonObject.isNull("dateEnd")){
                this.dateEnd = dateFormat.parse(jsonObject.getString("dateEnd"));
            }

            if(!jsonObject.isNull("amount")){
                this.amount = jsonObject.getInt("amount");
            }

            if(!jsonObject.isNull("budget")){
                this.budget = jsonObject.getLong("budget");
            }

            if(!jsonObject.isNull("discount")){
                this.discount = jsonObject.getLong("discount");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarSupplierId() {
        return carSupplierId;
    }

    public void setCarSupplierId(String carSupplierId) {
        this.carSupplierId = carSupplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarSupplierName() {
        return carSupplierName;
    }

    public void setCarSupplierName(String carSupplierName) {
        this.carSupplierName = carSupplierName;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRoute() {
        return this.startPoint + " - " + this.endPoint;
    }


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDateStartAndDateEnd(){
        return dateFormatString.format(dateStart) + " - " +dateFormatString.format(dateEnd);
    }

}
