package com.ridetour.backend.controllers;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;
import java.util.Map;

/**
 * Created by eyal on 5/27/2016.
 */
@JsonAutoDetect
public class TourImageModel {
    private Long tourId;

    private List<Map> images;

    public TourImageModel() {
    }

    private TourImageModel(Builder builder) {
        tourId = builder.tourId;
        images = builder.images;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Long getTourId() {
        return tourId;
    }

    public List<Map> getImages() {
        return images;
    }

    public static final class Builder {
        private Long tourId;
        private List<Map> images;

        private Builder() {
        }

        public Builder tourId(Long val) {
            tourId = val;
            return this;
        }

        public Builder images(List<Map> val) {
            images = val;
            return this;
        }

        public TourImageModel build() {
            return new TourImageModel(this);
        }
    }
}
