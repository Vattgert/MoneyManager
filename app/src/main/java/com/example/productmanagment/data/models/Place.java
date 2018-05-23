package com.example.productmanagment.data.models;

public class Place {
    private String title;
    private String coordinates;

    public Place(String title, String coordinates) {
        this.title = title;
        this.coordinates = coordinates;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
