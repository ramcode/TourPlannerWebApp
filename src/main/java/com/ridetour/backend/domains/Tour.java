package com.ridetour.backend.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by eyal on 5/22/2016.
 */
@Entity
@Table(name = "tour", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Tour extends PersistableEntity<Long> {
    private static final long serialVersionUID = 1L;
    @NotNull
    @Size(max = 50)
    @Column(nullable = false, name = "name")
    private String name;
    @NotNull
    @Size(max = 50)
    @Column(nullable = false, name = "city")
    private String city;
    @Size(max = 50)
    @Column(name = "state")
    private String state;
    @NotNull
    @Size(max = 50)
    @Column(nullable = false, name = "country")
    private String country;
    @NotNull
    @Column(nullable = false, name = "price")
    private BigDecimal price;
    @NotNull
    @Size(max = 50)
    @Column(nullable = false, name = "title")
    private String title;
    @NotNull
    @Size(max = 500)
    @Column(name = "description")
    private String description;
    @NotNull
    @Size(max = 100)
    @Column(name = "start_point")
    private String startPoint;
    @NotNull
    @Size(max = 100)
    @Column(name = "end_point")
    private String endPoint;
    @NotNull
    @Column(name = "tour_type")
    @Enumerated(EnumType.STRING)
    private TourType tourType;
    @NotNull
    @Column(name = "days")
    private BigDecimal days;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "length_type")
    private LengthType lengthType;
    @NotNull
    @Column(nullable = false, name = "length_value")
    private BigDecimal lengthValue;
    @Lob
    @Column(name = "gps_tracks")
    private String gpsTracks;
    @NotNull
    @Column(nullable = false, name = "participants_min")
    private Integer participantsMin;
    @NotNull
    @Column(nullable = false, name = "participants_max")
    private Integer participantsMax;
    @Size(max = 100)
    @Column(name = "meal_plan")
    private String mealPlan;
    @NotNull
    @Column(nullable = false, name = "min_age")
    private BigDecimal minAge;
    @NotNull
    @Column(nullable = false, name = "difficulty_level")
    private String difficultyLevel;
    @NotNull
    @Column(nullable = false, name = "operator_id")
    private long operatorId;
    @NotNull
    @Column(nullable = false, name = "rating_sum")
    private BigDecimal ratingSum;
    @NotNull
    @Column(nullable = false, name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date created;
    @NotNull
    @Column(nullable = false, name = "user_id")
    private long userId;
    @NotNull
    @Column(nullable = false, name = "status")
    private String status;

    @OneToMany(mappedBy = "tour", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<TourImage> images;
    @JsonIgnore
    @OneToMany(mappedBy = "tour", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TourVideo> videos;

    public Tour() {
    }

    public Tour(Long id) {
        this.id = id;
    }

    public Tour(String name) {
        this.name = name;
    }

    private Tour(Builder builder) {
        name = builder.name;
        city = builder.city;
        state = builder.state;
        country = builder.country;
        price = builder.price;
        title = builder.title;
        description = builder.description;
        startPoint = builder.startPoint;
        endPoint = builder.endPoint;
        tourType = builder.tourType;
        days = builder.days;
        lengthType = builder.lengthType;
        lengthValue = builder.lengthValue;
        gpsTracks = builder.gpsTracks;
        participantsMin = builder.participantsMin;
        participantsMax = builder.participantsMax;
        mealPlan = builder.mealPlan;
        minAge = builder.minAge;
        difficultyLevel = builder.difficultyLevel;
        operatorId = builder.operatorId;
        ratingSum = builder.ratingSum;
        created = builder.created;
        userId = builder.userId;
        status = builder.status;
        setImages(builder.images);
        setVideos(builder.videos);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return false;
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

    public BigDecimal getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public TourType getTourType() {
        return tourType;
    }

    public BigDecimal getDays() {
        return days;
    }

    public LengthType getLengthType() {
        return lengthType;
    }

    public BigDecimal getLengthValue() {
        return lengthValue;
    }

    public String getGpsTracks() {
        return gpsTracks;
    }

    public Integer getParticipantsMin() {
        return participantsMin;
    }

    public Integer getParticipantsMax() {
        return participantsMax;
    }

    public String getMealPlan() {
        return mealPlan;
    }

    public BigDecimal getMinAge() {
        return minAge;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public long getOperatorId() {
        return operatorId;
    }

    public BigDecimal getRatingSum() {
        return ratingSum;
    }

    public Date getCreated() {
        return created;
    }

    public long getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public List<TourImage> getImages() {
        return images;
    }

    public List<TourVideo> getVideos() {
        return videos;
    }

    @Override
    public String toString() {
        return toStringHelper().add("name", name).toString();
    }

    private void setImages(List<TourImage> images) {
        this.images = images;
    }

    private void setVideos(List<TourVideo> videos) {
        this.videos = videos;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public static final class Builder {
        private String name;
        private String city;
        private String state;
        private String country;
        private BigDecimal price;
        private String title;
        private String description;
        private String startPoint;
        private String endPoint;
        private TourType tourType;
        private BigDecimal days;
        private LengthType lengthType;
        private BigDecimal lengthValue;
        private String gpsTracks;
        private Integer participantsMin;
        private Integer participantsMax;
        private String guidedTour;
        private String mealPlan;
        private BigDecimal minAge;
        private String difficultyLevel;
        private long operatorId;
        private BigDecimal ratingSum;
        private Date created;
        private long userId;
        private String status;
        private List<TourImage> images;
        private List<TourVideo> videos;

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

        public Builder price(BigDecimal val) {
            price = val;
            return this;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder startPoint(String val) {
            startPoint = val;
            return this;
        }

        public Builder endPoint(String val) {
            endPoint = val;
            return this;
        }

        public Builder tourType(TourType val) {
            tourType = val;
            return this;
        }

        public Builder days(BigDecimal val) {
            days = val;
            return this;
        }

        public Builder lengthType(LengthType val) {
            lengthType = val;
            return this;
        }

        public Builder lengthValue(BigDecimal val) {
            lengthValue = val;
            return this;
        }

        public Builder gpsTracks(String val) {
            gpsTracks = val;
            return this;
        }

        public Builder participantsMin(Integer val) {
            participantsMin = val;
            return this;
        }

        public Builder participantsMax(Integer val) {
            participantsMax = val;
            return this;
        }

        public Builder guidedTour(String val) {
            guidedTour = val;
            return this;
        }

        public Builder mealPlan(String val) {
            mealPlan = val;
            return this;
        }

        public Builder minAge(BigDecimal val) {
            minAge = val;
            return this;
        }

        public Builder difficultyLevel(String val) {
            difficultyLevel = val;
            return this;
        }

        public Builder operatorId(long val) {
            operatorId = val;
            return this;
        }

        public Builder ratingSum(BigDecimal val) {
            ratingSum = val;
            return this;
        }

        public Builder created(Date val) {
            created = val;
            return this;
        }

        public Builder userId(long val) {
            userId = val;
            return this;
        }

        public Builder status(String val) {
            status = com.ridetour.backend.services.TConstants.TOUR_STATE_ACTIVE;
            return this;
        }

        public Builder images(List<TourImage> val) {
            images = val;
            return this;
        }

        public Builder videos(List<TourVideo> val) {
            videos = val;
            return this;
        }

        public Tour build() {
            return new Tour(this);
        }
    }
}
