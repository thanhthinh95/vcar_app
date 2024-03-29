package com.example.entity;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

public class ItemHomeSupplierCar {
    private String id;
    private String name;
    private String startPoint;
    private String endPoint;
    private long maxFare;
    private long minFare;
    private String numberPhone;
    private List<ItemHomeCar> listCar = new ArrayList<>();


    public ItemHomeSupplierCar() {
    }


    public ItemHomeSupplierCar(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            this.id = jsonObject.getString("_id");
            this.name = jsonObject.getString("name");
            this.startPoint = jsonObject.getString("startPoint");
            this.endPoint = jsonObject.getString("endPoint");
            this.startPoint = jsonObject.getString("startPoint");
            this.maxFare = jsonObject.getLong("maxFare");
            this.minFare = jsonObject.getLong("minFare");
            this.numberPhone = jsonObject.getString("numberPhone");

            JSONArray jsonArrayCar = new JSONArray(jsonObject.getString("cars"));
            this.listCar = new ArrayList<ItemHomeCar>();
            for(int i = 0; i < jsonArrayCar.length(); i++){
                this.listCar.add(new ItemHomeCar(jsonArrayCar.get(i).toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getRoute(){
        return this.startPoint + " - " + this.endPoint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public long getMaxFare() {
        return maxFare;
    }

    public void setMaxFare(long maxFare) {
        this.maxFare = maxFare;
    }

    public long getMinFare() {
        return minFare;
    }

    public void setMinFare(long minFare) {
        this.minFare = minFare;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public List<ItemHomeCar> getListCar() {
        return listCar;
    }

    public void setListCar(List<ItemHomeCar> listCar) {
        this.listCar = listCar;
    }
}
