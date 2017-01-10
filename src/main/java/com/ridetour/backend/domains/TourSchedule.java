package com.ridetour.backend.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by eyal on 5/29/2016.
 */
@Entity
@Table(name = "tour_schedule")
public class TourSchedule extends PersistableEntity<Long> {
    @ManyToOne
    @NotNull
    @JoinColumn(name = "tour_id", referencedColumnName = "id")
    @JsonIgnore
    private Tour tour;

    @Temporal(TemporalType.DATE)
    @Column(name = "day", nullable = false)
    @JsonProperty("time")
    private Date day;
    @Column(name = "cost", nullable = false)
    @JsonProperty("price")
    private BigDecimal cost;

    private TourSchedule() {

    }

    private TourSchedule(Builder builder) {
        tour = builder.tour;
        day = builder.day;
        cost = builder.cost;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Tour getTour() {
        return tour;
    }

    public Date getDay() {
        return day;
    }

    public BigDecimal getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return toStringHelper().add("id", id).toString();
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return false;
    }


    public static final class Builder {
        private Tour tour;
        private Date day;
        private BigDecimal cost;

        private Builder() {
        }

        public Builder tour(Tour val) {
            tour = val;
            return this;
        }

        public Builder day(Date val) {
            day = val;
            return this;
        }

        public Builder cost(BigDecimal val) {
            cost = val;
            return this;
        }

        public TourSchedule build() {
            return new TourSchedule(this);
        }
    }
}
