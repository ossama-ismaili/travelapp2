package com.example.travelapp.model;

public class DirectionsData {
    int directionId;
    String placeName;
    String countryName;
    String price;
    double rating;
    Integer imageUrl;

    public DirectionsData(int directionId, String placeName, String countryName, String price, double rating, Integer imageUrl) {
        this.directionId=directionId;
        this.placeName = placeName;
        this.countryName = countryName;
        this.price = price;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }

    public DirectionsData(String placeName, String countryName, String price, double rating, Integer imageUrl) {
        this.placeName = placeName;
        this.countryName = countryName;
        this.price = price;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getDirectionId(){
        return directionId;
    }
}
