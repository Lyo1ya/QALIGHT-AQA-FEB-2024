package org.example;

public class Phone {
    public String model;

    public int batteryLevel;

    public void charge() {
       batteryLevel = batteryLevel + 1;
    }
}
