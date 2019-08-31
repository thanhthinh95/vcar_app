package com.example.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Ticket {
    private String id;
    private String carSupplierId;
    private String carSupplierName;
    private String carSupplierPhone;
    private String carId;
    private String controlSea;
    private String startPoint;
    private String endPoint;
    private Date timeTrip;
    private Date dateTrip;
    private int vote = -9999;
    private int typeTrip;
    private String route;
    private String position;
    private long fare = -9999;
    private int status = -9999;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");


    public Ticket() {
    }

    public Ticket(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            this.id = jsonObject.getString("_id");
            this.carSupplierId = jsonObject.getString("carSupplierId");
            this.carSupplierName = jsonObject.getString("carSupplierName");
            this.carSupplierPhone = jsonObject.getString("carSupplierPhone");
            this.carId = jsonObject.getString("carId");
            this.controlSea = jsonObject.getString("controlSea");
            this.startPoint = jsonObject.getString("startPoint");
            this.endPoint = jsonObject.getString("endPoint");
            this.position = jsonObject.getString("position");
            this.typeTrip = jsonObject.getInt("typeTrip");

            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            if(!jsonObject.isNull("timeTrip")){
                this.timeTrip = dateFormat.parse(jsonObject.getString("timeTrip"));
            }

            if(!jsonObject.isNull("dateTrip")){
                this.dateTrip = dateFormat.parse(jsonObject.getString("dateTrip"));
            }

            if(!jsonObject.isNull("vote")){
                this.vote = jsonObject.getInt("vote");
            }

            this.fare = jsonObject.getInt("fare");
            this.status = jsonObject.getInt("status");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getRoute(){
        if(typeTrip == 1){//Chieu xuoi
            return this.startPoint + " - " + this.endPoint;
        }else if(typeTrip == 0){//Chieu nguoc
            return this.endPoint + " - " + this.startPoint;
        }

        return "";
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

    public String getCarSupplierName() {
        return carSupplierName;
    }

    public void setCarSupplierName(String carSupplierName) {
        this.carSupplierName = carSupplierName;
    }

    public String getCarSupplierPhone() {
        return carSupplierPhone;
    }

    public void setCarSupplierPhone(String carSupplierPhone) {
        this.carSupplierPhone = carSupplierPhone;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getControlSea() {
        return controlSea;
    }

    public void setControlSea(String controlSea) {
        this.controlSea = controlSea;
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

    public Date getTimeTrip() {
        return timeTrip;
    }

    public void setTimeTrip(Date timeTrip) {
        this.timeTrip = timeTrip;
    }

    public Date getDateTrip() {
        return dateTrip;
    }

    public void setDateTrip(Date dateTrip) {
        this.dateTrip = dateTrip;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public long getFare() {
        return fare;
    }

    public void setFare(long fare) {
        this.fare = fare;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

}
