package com.ridetour.backend.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by eyal on 5/28/2016.
 */
@Entity
@Table(name = "tour_operator")
public class TourOperator extends PersistableEntity<Long> {

    private static final long serialVersionUID = 1L;
    @NotNull
    @Size(max = 50)
    @Column(nullable = false, name = "name")
    private String name;
    @NotNull
    @Size(max = 50)
    @Column(nullable = false, name = "city")
    private String city;
    @NotNull
    @Size(max = 50)
    @Column(nullable = false, name = "state")
    private String state;
    @NotNull
    @Size(max = 50)
    @Column(nullable = false, name = "country")
    private String country;
    @NotNull
    @Size(max = 50)
    @Column(nullable = false, name = "poc")
    private String poc;
    @NotNull
    @Size(max = 50)
    @Column(nullable = false, name = "website")
    private String website;
    @NotNull
    @Size(max = 20)
    @Column(nullable = false, name = "phone")
    private String phone;
    @NotNull
    @Size(max = 20)
    @Column(nullable = false, name = "mobile")
    private String mobile;

    public TourOperator() {

    }

    private TourOperator(Builder builder) {
        name = builder.name;
        city = builder.city;
        state = builder.state;
        country = builder.country;
        poc = builder.poc;
        website = builder.website;
        phone = builder.phone;
        mobile = builder.mobile;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getPoc() {
        return poc;
    }

    public String getWebsite() {
        return website;
    }

    public String getPhone() {
        return phone;
    }

    public String getMobile() {
        return mobile;
    }

    @Override
    public String toString() {
        return toStringHelper().add("name", name).toString();
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return false;
    }


    public static final class Builder {
        private String name;
        private String city;
        private String state;
        private String country;
        private String poc;
        private String website;
        private String phone;
        private String mobile;

        private Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder city(String val) {
            city = val;
            return this;
        }

        public Builder state(String val) {
            state = val;
            return this;
        }

        public Builder country(String val) {
            country = val;
            return this;
        }

        public Builder poc(String val) {
            poc = val;
            return this;
        }

        public Builder website(String val) {
            website = val;
            return this;
        }

        public Builder phone(String val) {
            phone = val;
            return this;
        }

        public Builder mobile(String val) {
            mobile = val;
            return this;
        }

        public TourOperator build() {
            return new TourOperator(this);
        }
    }
}
