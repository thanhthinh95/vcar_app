package com.example.entity;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ItemHomeCar {
    private String id;
    private String image;
    private String controlSea;

    public ItemHomeCar() {
    }

    public ItemHomeCar(String id, String image, String controlSea) {
        this.id = id;
        this.image = image;
        this.controlSea = controlSea;
    }

    public ItemHomeCar(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            this.id = jsonObject.getString("_id");
            this.controlSea = jsonObject.getString("controlSea");
            JSONArray jsonArray = jsonObject.getJSONArray("imageUrl");
            if (jsonArray.length() > 0)
                this.image = (String) jsonArray.get(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getControlSea() {
        return controlSea;
    }

    public void setControlSea(String controlSea) {
        this.controlSea = controlSea;
    }
}
