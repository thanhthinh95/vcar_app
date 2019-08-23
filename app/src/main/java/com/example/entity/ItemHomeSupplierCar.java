package com.example.entity;

import java.util.ArrayList;
import java.util.List;

public class ItemHomeSupplierCar {
    private String nameCarSupplier;
    private String route;
    private List<ItemHomeCar> listCar = new ArrayList<>();
    private String earliestTimeStart;
    private int vote;

    public ItemHomeSupplierCar() {
    }

    public ItemHomeSupplierCar(String nameCarSupplier, String route, List<ItemHomeCar> listCar, String earliestTimeStart, int vote) {
        this.nameCarSupplier = nameCarSupplier;
        this.route = route;
        this.listCar = listCar;
        this.earliestTimeStart = earliestTimeStart;
        this.vote = vote;
    }

    public String getNameCarSupplier() {
        return nameCarSupplier;
    }

    public void setNameCarSupplier(String nameCarSupplier) {
        this.nameCarSupplier = nameCarSupplier;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public List<ItemHomeCar> getListCar() {
        return listCar;
    }

    public void setListCar(List<ItemHomeCar> listCar) {
        this.listCar = listCar;
    }

    public String getEarliestTimeStart() {
        return earliestTimeStart;
    }

    public void setEarliestTimeStart(String earliestTimeStart) {
        this.earliestTimeStart = earliestTimeStart;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }
}
