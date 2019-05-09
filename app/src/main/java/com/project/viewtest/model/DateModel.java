package com.project.viewtest.model;

public class DateModel {

    private int year;
    private int month;
    private int day;

    public DateModel(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
