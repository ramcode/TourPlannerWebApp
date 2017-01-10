package com.ridetour.backend.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by Eyal on 03-Jun-16.
 */
@JsonAutoDetect
public class TourOutModel {
    @JsonProperty
    private String name;
    @JsonProperty
    private String city;
    @Length(max = 50)
    private String state;
    @NotNull
    @JsonProperty
    private String country;
    @NotNull
    @JsonProperty
    private BigDecimal price;
    @NotNull
    @JsonProperty
    private String title;
    @NotNull
    @JsonProperty
    private String description;
    @JsonProperty
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
}
