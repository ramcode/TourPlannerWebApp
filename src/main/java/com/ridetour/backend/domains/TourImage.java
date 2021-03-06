package com.ridetour.backend.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by eyal on 5/24/2016.
 */
@Entity
@Table(name = "image")
public class TourImage extends PersistableEntity<Long> {
    private static final long serialVersionUID = 1L;
    @ManyToOne
    @NotNull
    @JoinColumn(name = "tour_id", referencedColumnName = "id")
    private Tour tour;
    @Size(max = 500)
    @Column(name = "image")
    private String image;
    @Size(max = 255)
    @Column(name = "original")
    private String original;

    public TourImage() {
    }

    private TourImage(Builder builder) {
        tour = builder.tour;
        image = builder.image;
        original = builder.original;
    }

    public static Builder newBuilder() {
        return new Builder();
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

    public Tour getTour() {
        return tour;
    }

    public String getImage() {
        return image;
    }

    public String getOriginal() {
        return original;
    }


    public static final class Builder {
        private Tour tour;
        private String image;
        private String original;

        private Builder() {
        }

        public Builder tour(Tour val) {
            tour = val;
            return this;
        }

        public Builder image(String val) {
            image = val;
            return this;
        }

        public Builder original(String val) {
            original = val;
            return this;
        }

        public TourImage build() {
            return new TourImage(this);
        }
    }
}
