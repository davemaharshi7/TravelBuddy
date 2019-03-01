package com.travelbuddy.travelguideapp.Models;

public class PlaceDetails {
    private String placeName,placeOpenTiming,reasonToVisit,placeImage,placeDescription,placeCloseDays;
    public PlaceDetails(){}

    public PlaceDetails(String placeName, String placeOpenTiming, String reasonToVisit, String placeImage, String placeDescription, String placeCloseDays) {
        this.placeName = placeName;
        this.placeOpenTiming = placeOpenTiming;
        this.reasonToVisit = reasonToVisit;
        this.placeImage = placeImage;
        this.placeDescription = placeDescription;
        this.placeCloseDays = placeCloseDays;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceOpenTiming() {
        return placeOpenTiming;
    }

    public void setPlaceOpenTiming(String placeOpenTiming) {
        this.placeOpenTiming = placeOpenTiming;
    }

    public String getReasonToVisit() {
        return reasonToVisit;
    }

    public void setReasonToVisit(String reasonToVisit) {
        this.reasonToVisit = reasonToVisit;
    }

    public String getPlaceImage() {
        return placeImage;
    }

    public void setPlaceImage(String placeImage) {
        this.placeImage = placeImage;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
    }

    public String getPlaceCloseDays() {
        return placeCloseDays;
    }

    public void setPlaceCloseDays(String placeCloseDays) {
        this.placeCloseDays = placeCloseDays;
    }
}
