package com.ridetour.backend.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by eyal on 5/27/2016.
 */
@JsonAutoDetect
public class TourScheduleModel {

    @NotNull
    private Long tourId;

    @NotNull
    private List<ScheduleItem> data;

    public TourScheduleModel() {
    }

    public Long getTourId() {
        return tourId;
    }

    public List<ScheduleItem> getData() {
        return data;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public void setData(List<ScheduleItem> data) {
        this.data = data;
    }
}
