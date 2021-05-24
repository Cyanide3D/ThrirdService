package com.example.ThrirdService.simple;

import com.example.ThrirdService.model.Cargo;

public class SimpleShipUnloadingReport {

    private String name;
    private Cargo.CargoType cargoType;
    private String month;
    private int day;
    private int hour;
    private int minute;
    private int arrivalTime;
    private int delay;
    private int beginTime;
    private int unloadingTime;

    public SimpleShipUnloadingReport() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(int beginTime) {
        this.beginTime = beginTime;
    }

    public int getUnloadingTime() {
        return unloadingTime;
    }

    public Cargo.CargoType getCargoType() {
        return cargoType;
    }

    public void setCargoType(Cargo.CargoType cargoType) {
        this.cargoType = cargoType;
    }

    public void setUnloadingTime(int unloadingTime) {
        this.unloadingTime = unloadingTime;
    }
}
