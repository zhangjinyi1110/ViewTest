package com.project.viewtest.utils;

import java.util.Calendar;

public class Test {

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, 2, 20);
        System.out.println(calendar.getTime().toString());
        Lunar lunar = new Lunar(calendar);
        System.out.println(lunar.animalsYear() + "/" + lunar.cyclical() + "/" + lunar.getLunarMonthString() + "/" + lunar.toString());
    }

}
