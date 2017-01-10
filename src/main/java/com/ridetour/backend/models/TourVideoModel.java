package com.ridetour.backend.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;
import java.util.Map;

/**
 * Created by eyal on 5/27/2016.
 */
@JsonAutoDetect
public class TourVideoModel {
    private Long tourId;

    private List<Map> videos;

    public TourVideoModel() {
    }

    private TourVideoModel(Builder builder) {
        tourId = builder.tourId;
        videos = builder.videos;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Long getTourId() {
        return tourId;
    }

    public List<Map> getVideos() {
        return videos;
    }

    public static final class Builder {
        private Long tourId;
        private List<Map> videos;

        private Builder() {
        }

        public Builder tourId(Long val) {
            tourId = val;
            return this;
        }

        public Builder videos(List<Map> val) {
            videos = val;
            return this;
        }

        public TourVideoModel build() {
            return new TourVideoModel(this);
        }
    }
}
