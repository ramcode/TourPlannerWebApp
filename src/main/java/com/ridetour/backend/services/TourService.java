package com.ridetour.backend.services;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.ridetour.backend.controllers.ScheduleOutModel;
import com.ridetour.backend.controllers.TourImageModel;
import com.ridetour.backend.domains.*;
import com.ridetour.backend.models.*;
import com.ridetour.backend.repo.*;
import com.ridetour.backend.response.MessageResponse;
import com.ridetour.backend.response.ValidRestResponse;
import com.ridetour.backend.services.amazon.AmazonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by eyal on 5/22/2016.
 */
@Service
@Transactional
public class TourService extends SimpleService<Tour, Long, TourRepository> implements BaseServices, TConstants {
    @Autowired
    private AmazonServices amazonServices;
    @Autowired
    private TourImageRepository imageRepository;
    @Autowired
    private TourVideoRepository videoRepository;
    @Autowired
    private TourOperatorRepository operatorRepository;
    @Autowired
    private TourScheduleRepository scheduleRepository;

    @Override
    public ValidRestResponse createTour(TourInModel tourInModel) {
        Tour tour = Tour.newBuilder()
                .name(tourInModel.getName())
                .city(tourInModel.getCity())
                .country(tourInModel.getCountry())
                .created(new Date())
                .days(tourInModel.getDays())
                .description(tourInModel.getDescription())
                .difficultyLevel(tourInModel.getLevel())
                .endPoint(tourInModel.getEndPoint())
                .gpsTracks(tourInModel.getGpsTracks())
                .lengthType(LengthType.valueOf(tourInModel.getLengthType()))
                .lengthValue(tourInModel.getLengthValue())
                .mealPlan(tourInModel.getMealPlan())
                .minAge(tourInModel.getMinAge())
                .participantsMax(tourInModel.getMaxParticipates())
                .participantsMin(tourInModel.getMinParticipates())
                .price(tourInModel.getPrice())
                .ratingSum(new BigDecimal(180))
                .startPoint(tourInModel.getStartPoint())
                .state(tourInModel.getState())
                .status(TConstants.TOUR_STATE_ACTIVE)
                .title(tourInModel.getTitle())
                .tourType(TourType.valueOf(tourInModel.getTourType()))
                .build();
        return ValidRestResponse.of(save(tour));
    }

    @Override
    public ValidRestResponse uploadImage(Long id, MultipartFile imagefile) {
        Optional<Tour> tourOptional = findById(id);
        if (tourOptional.isPresent()) {
            try {
                PutObjectResult imageResult = amazonServices.upload(imagefile);
                if (imageResult != null) {
                    Tour tour = tourOptional.get();
                    TourImage tourImage = TourImage.newBuilder()
                            .image(imageResult.getMetadata().getUserMetaDataOf(FILE_META_DATA_KEY))
                            .tour(tour)
                            .original(imageResult.getMetadata().getUserMetaDataOf(FILE_META_DATA_ORIGINAL))
                            .build();
                    imageRepository.save(tourImage);
                    return ValidRestResponse.of(new MessageResponse("OK"));
                } else {
                    return ValidRestResponse.of(new MessageResponse("Failed upload image"));
                }
            } catch (Exception e) {
                return ValidRestResponse.of(e);
            }
        } else {
            return ValidRestResponse.of(new MessageResponse("Invalid tour"));
        }
    }

    @Override
    public ValidRestResponse tourImageList(Long id) {
        Optional<Tour> tourOptional = findById(id);
        if (tourOptional.isPresent()) {
            Tour tour = tourOptional.get();
            List<Map> imgs = FluentIterable.from(tour.getImages())
                    .transform(new Function<TourImage, Map>() {
                        @Nullable
                        @Override
                        public Map apply(@Nullable TourImage input) {
                            return Maps.newHashMap(
                                    ImmutableMap.builder().
                                            put("imageId", input != null ? input.getId() : null).
                                            put("imagePath", amazonServices.getUrl(input != null ? input.getImage() : null)).
                                            build());
                        }
                    }).toList();
            return ValidRestResponse.of(TourImageModel.newBuilder()
                    .tourId(tour.getId())
                    .images(imgs).build());
        } else {
            return ValidRestResponse.of(TourImageModel.newBuilder()
                    .build());
        }
    }

    @Override
    public ValidRestResponse deleteTourImage(Long tourId, Long imageId) {
        TourImage tourImage = imageRepository.findOne(imageId);
        Optional<Long> deletedCount = Optional.of(imageRepository.deleteByTour_IdAndId(tourId, imageId));
        if (deletedCount.isPresent()) {
            Long count = deletedCount.get();
            if (count > 0 && tourImage != null) {
                if (amazonServices.file_delete(tourImage.getImage())) {
                    return ValidRestResponse.of(new HashMap());
                }
            }
            return ValidRestResponse.of(new MessageResponse("Failed Tour image delete"));
        } else {
            return ValidRestResponse.of(new MessageResponse("No tour image"));
        }
    }

    @Override
    public ValidRestResponse deleteTour(Long tourId) {
        scheduleRepository.deleteByTour_Id(tourId);
        getRepository().delete(tourId);
        return ValidRestResponse.of(new MessageResponse("Deleted tour"));
    }

    @SuppressWarnings("unchecked")
    public ValidRestResponse getTourNames(Specification specification) {
        Map tourDetails;
        List<Map> tourList = FluentIterable.from(getRepository().findAll(specification))
                .transform(new Function<Tour, Map>() {
                    @Override
                    public Map apply(Tour tour) {
                        return Maps.newHashMap(
                                ImmutableMap.builder()
                                        .put("id", tour.getId())
                                        .put("name", tour.getName())
                                        .build());
                    }
                }).toList();
        tourDetails = Maps.newHashMap(
                ImmutableMap.builder()
                        .put("tours", tourList)
                        .build());
        return ValidRestResponse.of(tourDetails);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ValidRestResponse tourVideoList(Long id) {
        Optional<List> tourVideoOptional = Optional.of(videoRepository.findByTour_Id(id));
        if (tourVideoOptional.isPresent()) {
            List tourVideoList = tourVideoOptional.get();
            List<Map> videos = FluentIterable.from(tourVideoList)
                    .transform(new Function<TourVideo, Map>() {
                        @Nullable
                        @Override
                        public Map apply(@Nullable TourVideo input) {
                            return Maps.newHashMap(
                                    ImmutableMap.builder().
                                            put("videoId", input != null ? input.getId() : null).
                                            put("videoPath", amazonServices.getUrl(input != null ? input.getVideo() : null)).
                                            build());
                        }
                    }).toList();
            return ValidRestResponse.of(TourVideoModel.newBuilder()
                    .tourId(id)
                    .videos(videos).build());
        } else {
            return ValidRestResponse.of(TourImageModel.newBuilder()
                    .build());
        }
    }

    @Override
    public ValidRestResponse deleteTourVideo(Long tourId, Long videoId) {
        TourVideo tourVideo = videoRepository.findOne(videoId);
        Optional<Long> deleteCount = Optional.of(videoRepository.deleteByTour_IdAndId(tourId, videoId));
        if (deleteCount.isPresent()) {
            Long count = deleteCount.get();
            if (count > 0 && tourVideo != null) {
                if (amazonServices.file_delete(tourVideo.getVideo())) {
                    return ValidRestResponse.of(new HashMap());
                }
            }
            return ValidRestResponse.of(new MessageResponse("Failed Tour video delete"));
        } else {
            return ValidRestResponse.of(new MessageResponse("No tour video"));
        }
    }

    @Override
    public TourOperator createTourOperator(TourOperatorModel tourModel) {
        TourOperator tourOperator = TourOperator.newBuilder()
                .name(tourModel.getName())
                .city(tourModel.getCity())
                .country(tourModel.getCountry())
                .mobile(tourModel.getMobile())
                .website(tourModel.getWebsite())
                .phone(tourModel.getPhone())
                .poc(tourModel.getPoc())
                .state(tourModel.getState())
                .build();
        return operatorRepository.save(tourOperator);
    }

    @Override
    public ValidRestResponse createTourSchedule(TourScheduleModel scheduleModel) {
        Optional<Tour> tourOptional = findById(scheduleModel.getTourId());
        if (tourOptional.isPresent()) {
            Tour tour = tourOptional.get();
            List<TourSchedule> scheduleList = new ArrayList<>();
            List<ScheduleItem> itemList = scheduleModel.getData();
            for (ScheduleItem item : itemList) {
                TourSchedule tourSchedule = TourSchedule.newBuilder()
                        .cost(item.getCost())
                        .tour(tour)
                        .day(item.getDay())
                        .build();
                scheduleList.add(tourSchedule);
            }
            scheduleList = scheduleRepository.save(scheduleList);
            return ValidRestResponse.of(new ScheduleOutModel(tour.getId(), scheduleList));
        } else {
            return ValidRestResponse.of(new MessageResponse("Invalid tour"));
        }
    }

    @Override
    public ValidRestResponse viewTourDetails(Long tourId) {
        Optional<Tour> tourOptional = findById(tourId);
        if (tourOptional.isPresent()) {
            Tour tour = tourOptional.get();
            return ValidRestResponse.of(viewTourDetail(tour));
        } else {
            return ValidRestResponse.of(new MessageResponse("Invalid tour"));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public ValidRestResponse getTourList(Specification specification) {
        Optional<List<Tour>> tourOptional = Optional.fromNullable(getRepository().findAll(specification));
        if (tourOptional.isPresent()) {
            List<Tour> tourList = tourOptional.get();
            List<Map> tourData =new ArrayList<>();
            for(Tour tour:tourList) tourData.add(viewTourDetail(tour));
            return ValidRestResponse.of(Maps.newHashMap(
                    ImmutableMap.builder().put("tours", tourData).build()));
        } else {
            return ValidRestResponse.of(new MessageResponse("No Data"));
        }
    }

    @Override
    public ValidRestResponse uploadTourVideo(Long id, MultipartFile videoFile) {
        Optional<Tour> tourOptional = findById(id);
        if (tourOptional.isPresent()) {
            try {

                PutObjectResult videoResult = amazonServices.upload(videoFile);
                if (videoResult != null) {
                    Tour tour = tourOptional.get();
                    TourVideo tourVideo = TourVideo.newBuilder()
                            .video(videoResult.getMetadata().getUserMetaDataOf(FILE_META_DATA_KEY))
                            .tour(tour)
                            .original(videoResult.getMetadata().getUserMetaDataOf(FILE_META_DATA_ORIGINAL))
                            .build();
                    videoRepository.save(tourVideo);
                    return ValidRestResponse.of(new MessageResponse("OK"));
                } else {
                    return ValidRestResponse.of(new MessageResponse("Failed video upload"));
                }

            } catch (Exception e) {
                return ValidRestResponse.of(e);
            }
        } else {
            return ValidRestResponse.of(new MessageResponse("Invalid tour"));
        }
    }

    private Map viewTourDetail(Tour tour) {
        List<String> images = FluentIterable.from(tour.getImages())
                .transform(new Function<TourImage, String>() {
                    @Nullable
                    @Override
                    public String apply(@Nullable TourImage input) {
                        return amazonServices.getUrl(input != null ? input.getImage() : null);
                    }
                }).toList();
        List<TourVideo> videoList = videoRepository.findByTour_Id(tour.getId());
        List<String> videos = FluentIterable.from(videoList)
                .transform(new Function<TourVideo, String>() {
                    @Nullable
                    @Override
                    public String apply(@Nullable TourVideo input) {
                        return amazonServices.getUrl(input.getVideo());
                    }
                }).toList();
        List<TourSchedule> scheduleList = scheduleRepository.findByTour_Id(tour.getId());
        return Maps.newHashMap(
                ImmutableMap.builder()
                        .put("tour", tour)
                        .put("images", images)
                        .put("videos", videos)
                        .put("sap", scheduleList)
                        .build()
        );
    }
}