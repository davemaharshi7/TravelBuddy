package com.travelbuddy.travelguideapp.Models;

public class Place {

    private String placeImage,placeName;

    public Place(){

    }

    public Place(String placeImage, String placeName) {
        this.placeImage = placeImage;
        this.placeName = placeName;
    }

    public void setPlaceImage(String placeImage) {
        this.placeImage = placeImage;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceImage() {
        return placeImage;
    }

    public String getPlaceName() {
        return placeName;
    }
}
