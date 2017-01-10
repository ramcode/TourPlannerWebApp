package com.ridetour.backend.services;

import com.ridetour.backend.domains.TourOperator;
import com.ridetour.backend.models.TourInModel;
import com.ridetour.backend.models.TourOperatorModel;
import com.ridetour.backend.models.TourScheduleModel;
import com.ridetour.backend.response.ValidRestResponse;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by eyal on 5/26/2016.
 */
public interface BaseServices {

    ValidRestResponse createTour(TourInModel tourInModel);

    ValidRestResponse uploadImage(Long id, MultipartFile imagefile);

    ValidRestResponse tourImageList(Long id);

    ValidRestResponse deleteTourImage(Long tourId, Long imageId);

    ValidRestResponse deleteTour(Long tourId);

    ValidRestResponse uploadTourVideo(Long id, MultipartFile videoFile);


    ValidRestResponse getTourNames(Specification specification);

    ValidRestResponse tourVideoList(Long id);

    ValidRestResponse deleteTourVideo(Long tourId, Long videoId);

    TourOperator createTourOperator(TourOperatorModel tourModel);

    ValidRestResponse createTourSchedule(TourScheduleModel scheduleModel);

    ValidRestResponse viewTourDetails(Long tourId);

    ValidRestResponse getTourList(Specification specification);
}
