package com.ridetour.backend.domains;

/**
 * com.ridetour.backend.domains
 * Created by eyal on 6/2/2016.
 */
public enum TourType {
    CROSS_COUNTRY("CROSS_COUNTRY"),ALL_MOUNTAIN("ALL_MOUNTAIN"),DOWNHILL("DOWNHILL");

    private final String tourType;

    TourType(final String tourType) {
        this.tourType = tourType;
    }

    @Override
    public String toString() {
        return tourType;
    }
}
