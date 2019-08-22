package com.example.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {
    private int code;
    private String message;
    private String data;


    public Message() {
    }

    public Message(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            this.code = jsonObject.getInt("code");
            this.message = jsonObject.getString("message");
            this.data = jsonObject.getString("data");;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Message(int code, String message, String data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "Message{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
