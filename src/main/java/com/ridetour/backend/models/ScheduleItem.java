package com.ridetour.backend.models;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * com.ridetour.backend.models
 * Created by eyal on 6/3/2016.
 */
public class ScheduleItem {
    public ScheduleItem() {
    }

    public ScheduleItem(Date day, BigDecimal cost) {
        this.day = day;
        this.cost = cost;
    }

    @NotNull
    private Date day;
    @NotNull
    private BigDecimal cost;

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
