package com.example.mapsapplication.model;

public class Restaurant {
    //Components
    private int restaurantID;
    private String restaurantName;
    private String restaurantLong;
    private String restaurantLat;

    //Constructor
    public Restaurant(int restaurantID, String restaurantName, String restaurantLat, String restaurantLong) {
        this.restaurantID = restaurantID;
        this.restaurantName = restaurantName;
        this.restaurantLat = restaurantLat;
        this.restaurantLong = restaurantLong;
    }

    public Restaurant(String restaurantName, String restaurantLat, String restaurantLong) {
        this.restaurantName = restaurantName;
        this.restaurantLat = restaurantLat;
        this.restaurantLong = restaurantLong;
    }

    public Restaurant ()
    {

    }

    //Get Functions
    public int getRestaurantID() {
        return restaurantID;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRestaurantLat() {
        return restaurantLat;
    }

    public String getRestaurantLong() {
        return restaurantLong;
    }


    //Set Function
    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setRestaurantLong(String restaurantLong) { this.restaurantLong = restaurantLong; }

    public void setRestaurantLat(String restaurantLat) {
        this.restaurantLat = restaurantLat;
    }



}
