package com.ridetour.backend.controllers;

import com.ridetour.backend.domains.Tour;
import com.ridetour.backend.models.TourInModel;
import com.ridetour.backend.models.TourOperatorModel;
import com.ridetour.backend.models.TourScheduleModel;
import com.ridetour.backend.response.ValidRestResponse;
import com.ridetour.backend.services.BaseServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * Created by eyal on 5/21/2016.
 */
@Controller
@RequestMapping(value = "/tour")
@Api(tags = "Tour")
class TourController {

    @Autowired
    private BaseServices services;
    @Autowired
    private Environment env;

    @ApiOperation(value = "Create New Tour")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ValidRestResponse createTour(@Valid @RequestBody TourInModel tourInModel) throws Exception {
        return services.createTour(tourInModel);
    }

    @ApiOperation(value = "Upload Tour Image")
    @RequestMapping(value = "/{id}/image/upload", method = RequestMethod.POST)
    @ResponseBody
    public ValidRestResponse uploadImage(@PathVariable("id") Long id,
                                         @RequestParam("image") MultipartFile imagefile) throws Exception {
        return services.uploadImage(id, imagefile);
    }

    @ApiOperation(value = "Get Tour Image List")
    @RequestMapping(value = "/{id}/images", method = RequestMethod.GET)
    @ResponseBody
    public ValidRestResponse tourImageList(@PathVariable("id") Long id) throws Exception {
        return services.tourImageList(id);
    }

    @ApiOperation(value = "Delete Tour Image")
    @RequestMapping(value = "/{id}/delete/{imageId}", method = RequestMethod.POST)
    @ResponseBody
    public ValidRestResponse deleteTourImage(@PathVariable("id") Long tourId,
                                             @PathVariable("imageId") Long imageId) throws Exception {
        return services.deleteTourImage(tourId, imageId);
    }

    @ApiOperation(value = "Delete Tour")
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    @ResponseBody
    public ValidRestResponse deleteTour(@PathVariable("id") Long tourId) throws Exception {
        return services.deleteTour(tourId);
    }

    @ApiOperation(value = "Get Tour Names")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "city", dataType = "string", value = "Tour city name"),
            @ApiImplicitParam(name = "country", dataType = "string"),
            @ApiImplicitParam(name = "state", dataType = "string")
    })
    @RequestMapping(value = "/names", method = RequestMethod.GET)
    @ResponseBody
    public ValidRestResponse getTourNames(
            @And({@Spec(path = "country", spec = Like.class),
                    @Spec(path = "state", spec = Like.class),
                    @Spec(path = "city", spec = Like.class)
            }) @ApiIgnore Specification<Tour> spec) {
        return services.getTourNames(spec);
    }

    @ApiOperation(value = "Upload Tour Video")
    @RequestMapping(value = "/{id}/video/create", method = RequestMethod.POST)
    @ResponseBody
    public ValidRestResponse uploadTourVideo(@PathVariable("id") Long id,
                                             @RequestParam("video") MultipartFile videofile) throws Exception {
        return services.uploadTourVideo(id, videofile);
    }

    @ApiOperation(value = "Delete Tour Video")
    @RequestMapping(value = "/{id}/video/delete/{videoId}", method = RequestMethod.POST)
    @ResponseBody
    public ValidRestResponse deleteTourVideo(@PathVariable("id") Long tourId,
                                             @PathVariable("videoId") Long videoId) throws Exception {
        return services.deleteTourVideo(tourId, videoId);
    }

    @ApiOperation(value = "Get Tour Video List")
    @RequestMapping(value = "/{id}/videos", method = RequestMethod.GET)
    @ResponseBody
    public ValidRestResponse tourVideoList(@PathVariable("id") Long tourId) throws Exception {
        return services.tourVideoList(tourId);
    }

    @ApiOperation(value = "Create Tour Operator")
    @RequestMapping(value = "/operator/create", method = RequestMethod.POST)
    @ResponseBody
    public ValidRestResponse createTourOperator(@Valid @RequestBody TourOperatorModel tourModel) throws Exception {
        return ValidRestResponse.of(services.createTourOperator(tourModel));
    }

    @ApiOperation(value = "Create Tour SAP")
    @RequestMapping(value = "/sas/create", method = RequestMethod.POST)
    @ResponseBody
    public ValidRestResponse createTourSchedule(@Valid @RequestBody TourScheduleModel scheduleModel) throws Exception {
        return services.createTourSchedule(scheduleModel);
    }

    @ApiOperation(value = "Get tour details")
    @RequestMapping(value = "/{id}/details", method = RequestMethod.GET)
    @ResponseBody
    public ValidRestResponse viewTourDetails(@PathVariable("id") Long id) throws Exception {
        return services.viewTourDetails(id);
    }

    @ApiOperation(value = "Get Tour List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "city", dataType = "string", value = "Tour city name"),
            @ApiImplicitParam(name = "country", dataType = "string"),
            @ApiImplicitParam(name = "state", dataType = "string")
    })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ValidRestResponse getTourList(
            @And({@Spec(path = "country", spec = Like.class),
                    @Spec(path = "state", spec = Like.class),
                    @Spec(path = "city", spec = Like.class)
            }) @ApiIgnore Specification<Tour> spec) {
        return services.getTourList(spec);
    }
}
