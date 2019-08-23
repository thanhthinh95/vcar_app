package com.example.entity;

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
