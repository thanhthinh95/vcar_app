package com.example.entity;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Point {
    private String id;
    private String name;
    private int type = -9999;
    private Date created;
    private Date updated;
    private int status = -9999;

    private String action;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public Point() {
    }

    public Point(String id, String name, int type, int status) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
    }

    public Point(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            this.id = jsonObject.getString("_id");
            this.name = jsonObject.getString("name");
            this.type = jsonObject.getInt("type");
            this.status = jsonObject.getInt("status");

            this.created = dateFormat.parse(jsonObject.getString("created"));
            this.updated = dateFormat.parse(jsonObject.getString("updated"));
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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
            if(getType() != -9999) jsonObject.put("type", getType());

            if(getCreated() != null) jsonObject.put("created", dateFormat.format(getCreated()));
            if(getUpdated() != null) jsonObject.put("updated", dateFormat.format(getUpdated()));
            if(getStatus() != -9999) jsonObject.put("status", getStatus());
            if(getAction() != null) jsonObject.put("action", getAction());

            return jsonObject.toString();
        } catch (JSONException e) {
            Log.w("error", "Failed convert Point to String");
            return  null;
        }
    }
}
