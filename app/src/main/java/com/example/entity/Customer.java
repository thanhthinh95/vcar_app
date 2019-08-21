package com.example.entity;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Customer {
    private String id;
    private String name;
    private String password;
    private String numberPhone;
    private String email;
    private String macAddress;
    private Date created;
    private Date updated;
    private int status;

    public Customer() {
    }

//    public Customer(JSONObject jsonObject) {
//        this._id = jsonObject.getString("_id");
//        this.name = jsonObject.getString("name");
//        this.numberPhone = jsonObject.getString("numberPhone");
//        this.macAddress = jsonObject.getJSONArray("macAddress");
//        this.created = jsonObject.getS("_id");
//        this.updated = jsonObject.getString("_id");
//        this.status = jsonObject.getString("_id");
//    }


    public Customer(String id, String name, String password, String numberPhone, String email, String macAddress, Date created, Date updated, int status) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.numberPhone = numberPhone;
        this.email = email;
        this.macAddress = macAddress;
        this.created = created;
        this.updated = updated;
        this.status = status;
    }

    public Customer(String password, String numberPhone, String email, String macAddress){
        this.password = password;
        this.numberPhone = numberPhone;
        this.email = email;
        this.macAddress = macAddress;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    @Override
    public String toString() {
       JSONObject jsonObject = new JSONObject();
        try {
            if(getId() != null) jsonObject.put("_id", getId());
            if(getName() != null) jsonObject.put("name", getName());
            if(getPassword() != null) jsonObject.put("password", getPassword());
            if(getNumberPhone() != null) jsonObject.put("numberPhone", getNumberPhone());
            if(getEmail() != null) jsonObject.put("email", getEmail());
            if(getMacAddress() != null) jsonObject.put("macAddress", getMacAddress());


            return jsonObject.toString();
        } catch (JSONException e) {
            Log.w("error", "Failed convert Customer to String");
            return  null;
        }
    }
}