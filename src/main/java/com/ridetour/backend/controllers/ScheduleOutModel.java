package com.ridetour.backend.controllers;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.ridetour.backend.domains.TourSchedule;

import java.util.List;

/**
 * Created by eyal on 5/29/2016.
 */
@JsonRootName("tourSAP")
public class ScheduleOutModel {

    private Long tourId;

    private List<TourSchedule> sapList;

    public ScheduleOutModel(Long tourId, List<TourSchedule> sapList) {
        this.tourId = tourId;
        this.sapList = sapList;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public List<TourSchedule> getSapList() {
        return sapList;
    }

    public void setSapList(List<TourSchedule> sapList) {
        this.sapList = sapList;
    }
}
