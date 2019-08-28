package com.example.entity;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Car {
    private String id;
    private String nameSupplier;
    private String startPoint;
    private String endPoint;
    private String controlSea;
    private String[] imageUrl;
    private String type;
    private int numberSeat;
    private JSONObject[] seatDiagram;
    private long fare;
    private String[] pointStop;


    public Car() {
    }

    public Car(String id, String nameSupplier, String controlSea, String startPoint, String endPoint, String[] imageUrl, String type, int numberSeat, JSONObject[] seatDiagram, long fare, String[] pointStop) {
        this.id = id;
        this.nameSupplier = nameSupplier;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.controlSea = controlSea;
        this.imageUrl = imageUrl;
        this.type = type;
        this.numberSeat = numberSeat;
        this.seatDiagram = seatDiagram;
        this.fare = fare;
        this.pointStop = pointStop;
    }

    public Car(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            this.id = jsonObject.getString("_id");
            this.nameSupplier = jsonObject.getString("nameSupplier");
            this.controlSea = jsonObject.getString("controlSea");
            this.type = jsonObject.getString("type");
            this.startPoint = jsonObject.getString("startPoint");
            this.endPoint = jsonObject.getString("endPoint");

            JSONArray jsonArray = jsonObject.getJSONArray("imageUrl");
            this.imageUrl = new String[jsonArray.length()];
            for(int i = 0; i < jsonArray.length(); i++){
                this.imageUrl[i] = (String) jsonArray.get(i);
            }


            jsonArray = jsonObject.getJSONArray("pointStop");
            pointStop = new String[jsonArray.length()];
            for(int i = 0; i < jsonArray.length(); i++){
                this.pointStop[i] = (String) jsonArray.get(i);
            }


            jsonArray = jsonObject.getJSONArray("seatDiagram");
            seatDiagram = new JSONObject[jsonArray.length()];
            for(int i = 0 ; i < jsonArray.length(); i++){
                this.seatDiagram[i] = jsonArray.getJSONObject(i);
            }

            this.fare = jsonObject.getLong("fare");
            this.numberSeat = jsonObject.getInt("numberSeat");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getNameSupplier() {
        return nameSupplier;
    }

    public void setNameSupplier(String nameSupplier) {
        this.nameSupplier = nameSupplier;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getControlSea() {
        return controlSea;
    }

    public void setControlSea(String controlSea) {
        this.controlSea = controlSea;
    }

    public String[] getImageUrl() {
        return imageUrl;
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

    public void setImageUrl(String[] imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getNumberSeat() {
        return numberSeat;
    }

    public JSONObject[] getSeatDiagram() {
        return seatDiagram;
    }

    public void setSeatDiagram(JSONObject[] seatDiagram) {
        this.seatDiagram = seatDiagram;
    }

    public void setNumberSeat(int numberSeat) {
        this.numberSeat = numberSeat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getFare() {
        return fare;
    }

    public void setFare(long fare) {
        this.fare = fare;
    }

    public String[] getPointStop() {
        return pointStop;
    }

    public void setPointStop(String[] pointStop) {
        this.pointStop = pointStop;
    }
}
