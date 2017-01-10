package com.ridetour.backend.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by eyal on 5/28/2016.
 */
public class TourOperatorModel {
    @NotNull
    @Size(max = 50)
    private String name;
    @NotNull
    @Size(max = 50)
    private String city;
    @Size(max = 50)
    private String state;
    @NotNull
    @Size(max = 50)
    private String country;
    @NotNull
    @Size(max = 50)
    private String poc;
    @NotNull
    @Size(max = 50)
    private String website;
    @NotNull
    @Size(max = 20)
    private String phone;
    @NotNull
    @Size(max = 20)
    private String mobile;

    public TourOperatorModel() {
    }

    public String getName() {
        return name;
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

    public String getPoc() {
        return poc;
    }

    public void setPoc(String poc) {
        this.poc = poc;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
