package com.example.travelapp.model;

public class FavoritesData extends DirectionsData {
    int favoriteId;
    public FavoritesData(int id,String placeName, String countryName, String price, double rating, Integer imageUrl) {
        super(placeName, countryName, price, rating, imageUrl);
        this.favoriteId=id;
    }

    public int getFavoriteId() {
        return favoriteId;
    }
}
