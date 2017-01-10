package com.ridetour.backend.models;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by eyal on 5/21/2016.
 */
public class TourInModel {
    @NotNull
    @Length(max = 50)
    private String name;
    @NotNull
    @Length(max = 50)
    private String city;
    @Length(max = 50)
    private String state;
    @NotNull
    @Length(max = 50)
    private String country;
    @NotNull
    private BigDecimal price;
    @NotNull
    @Length(max = 50)
    private String title;
    @NotNull
    @Length(max = 500)
    private String description;
    @NotNull
    @Length(max = 100)
    private String startPoint;
    @NotNull
    @Length(max = 100)
    private String endPoint;
    @NotNull
    private BigDecimal days;
    @NotNull
    private String lengthType;
    @NotNull
    private BigDecimal lengthValue;
    @Length(max = 1000)
    private String gpsTracks;
    @NotNull
    private String location;
    @NotNull
    private Integer minParticipates;
    @NotNull
    private Integer maxParticipates;
    @NotNull
    private String tourType;
    @Length(max = 100)
    private String mealPlan;
    @NotNull
    private BigDecimal minAge;
    @NotNull
    private String level;

    public TourInModel() {
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public TourInModel(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public BigDecimal getDays() {
        return days;
    }

    public void setDays(BigDecimal days) {
        this.days = days;
    }

    public String getLengthType() {
        return lengthType;
    }

    public void setLengthType(String lengthType) {
        this.lengthType = lengthType;
    }

    public BigDecimal getLengthValue() {
        return lengthValue;
    }

    public void setLengthValue(BigDecimal lengthValue) {
        this.lengthValue = lengthValue;
    }

    public String getGpsTracks() { return gpsTracks; }

    public void setGpsTracks(String gpsTracks) { this.gpsTracks = gpsTracks; }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getMinParticipates() {
        return minParticipates;
    }

    public void setMinParticipates(Integer minParticipates) {
        this.minParticipates = minParticipates;
    }

    public Integer getMaxParticipates() {
        return maxParticipates;
    }

    public void setMaxParticipates(Integer maxParticipates) {
        this.maxParticipates = maxParticipates;
    }

    public String getTourType() {
        return tourType;
    }

    public void setTourType(String tourType) {
        this.tourType = tourType;
    }

    public String getMealPlan() {
        return mealPlan;
    }

    public void setMealPlan(String mealPlan) {
        this.mealPlan = mealPlan;
    }

    public BigDecimal getMinAge() {
        return minAge;
    }

    public void setMinAge(BigDecimal minAge) {
        this.minAge = minAge;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
