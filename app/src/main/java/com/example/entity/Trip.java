package com.example.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Trip {
    private String id;
    private String carId;
    private int type;
    private Date timeStart;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    DateFormat dateFormatOutput = new SimpleDateFormat("HH:mm");


    public Trip() {
    }

    public Trip(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            this.id = jsonObject.getString("_id");
            this.carId = jsonObject.getString("carId");
            this.type = jsonObject.getInt("type");
            this.timeStart = dateFormat.parse(jsonObject.getString("timeStart"));
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

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeStartString() {
        return dateFormatOutput.format(timeStart);
    }

}
