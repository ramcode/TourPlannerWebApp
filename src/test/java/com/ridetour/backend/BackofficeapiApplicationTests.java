package com.ridetour.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.http.ContentType;
import com.ridetour.backend.configuration.WebMain;
import com.ridetour.backend.domains.LengthType;
import com.ridetour.backend.domains.TourType;
import com.ridetour.backend.models.ScheduleItem;
import com.ridetour.backend.models.TourInModel;
import com.ridetour.backend.models.TourOperatorModel;
import com.ridetour.backend.models.TourScheduleModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebMain.class)
@WebAppConfiguration
@IntegrationTest("server.port:8080")
public class BackofficeapiApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void createTour() throws Exception {
        TourInModel tourInModel=new TourInModel();
        tourInModel.setName("Wang Ping");
        tourInModel.setCity("Shenyang");
        tourInModel.setCountry("China");
        tourInModel.setDays(new BigDecimal(20));
        tourInModel.setDescription("Test");
        tourInModel.setEndPoint("Shengyang");
        tourInModel.setStartPoint("Beijing");
        tourInModel.setGpsTracks("--");
        tourInModel.setLengthType(LengthType.KM.toString());
        tourInModel.setLengthValue(new BigDecimal(100));
        tourInModel.setLevel("EASY");
        tourInModel.setLocation("Shenyang");
        tourInModel.setMaxParticipates(10);
        tourInModel.setMinParticipates(5);
        tourInModel.setTitle("Test");
        tourInModel.setPrice(new BigDecimal(100));
        tourInModel.setMinAge(new BigDecimal(20));
        tourInModel.setState("Liaoning");
        tourInModel.setTourType(TourType.ALL_MOUNTAIN.toString());
        ObjectMapper om=new ObjectMapper();
        String body=om.writeValueAsString(tourInModel);
        String result=given()
                .body(body).
                        contentType(ContentType.JSON).
        post("/ridetour/tour/create").asString();
        System.out.println(result);
    }

    @Test
    public void tourListTest() throws Exception {
        String result=given().param("country","china").
                contentType(ContentType.JSON).
                get("/ridetour/tour/list").asString();
        System.out.println(result);
    }

    @Test
    public void tourNamesTest() throws Exception {
        String result=given().param("state","liao").
                contentType(ContentType.JSON).
                get("/ridetour/tour/names").asString();
        System.out.println(result);
    }

    @Test
    public void createTourOperatorTest() throws Exception {
        TourOperatorModel tourOperatorModel=new TourOperatorModel();
        tourOperatorModel.setState("Liaoning");
        tourOperatorModel.setCity("Shenyang");
        tourOperatorModel.setCountry("China");
        tourOperatorModel.setMobile("+86122222");
        tourOperatorModel.setName("Wang Ping");
        tourOperatorModel.setPhone("4009110999");
        tourOperatorModel.setPoc("Shenyang");
        tourOperatorModel.setWebsite("http://www.worldward.net");
        ObjectMapper om=new ObjectMapper();
        String body=om.writeValueAsString(tourOperatorModel);
        String result=given().body(body).
                contentType(ContentType.JSON).
                post("/ridetour/tour/tour/operator/create").asString();
        System.out.println(result);
    }@Test
    public void createTourSas() throws Exception {
        TourScheduleModel tourScheduleModel=new TourScheduleModel();
        tourScheduleModel.setTourId(2L);
        List<ScheduleItem> itemList=new ArrayList<>();
        itemList.add(new ScheduleItem(new Date(),new BigDecimal(100)));
        itemList.add(new ScheduleItem(new Date(),new BigDecimal(150)));
        tourScheduleModel.setData(itemList);
        ObjectMapper om=new ObjectMapper();
        String body=om.writeValueAsString(tourScheduleModel);
        String result=given().body(body).
                contentType(ContentType.JSON).
                post("/ridetour/tour/sas/create").asString();
        System.out.println(result);
    }
    @Test
    public void deleteTourTest() throws Exception {
        String result=given().pathParam("id",2).
                contentType(ContentType.JSON).
                post("/ridetour/tour/{id}/delete").asString();
        System.out.println(result);
    }
    @Test
    public void deleteTourImageTest() throws Exception {
        String result=given()
                .pathParam("id",3)
                .pathParam("imageId",1)
                .contentType(ContentType.JSON)
                .post("/ridetour/tour/{id}/delete/{imageId}").asString();
        System.out.println(result);
    }
    @Test
    public void getTourImagesTest() throws Exception {
        String result=given()
                .pathParam("id",2)
                .contentType(ContentType.JSON)
                .get("/ridetour/tour/{id}/images").asString();
        System.out.println(result);
    }
    @Test
    public void getTourVideosTest() throws Exception {
        String result=given()
                .pathParam("id",2)
                .contentType(ContentType.JSON)
                .get("/ridetour/tour/{id}/videos").asString();
        System.out.println(result);
    }
    @Test
    public void getTourDetailsTest() throws Exception {
        String result=given()
                .pathParam("id",2)
                .contentType(ContentType.JSON)
                .get("/ridetour/tour/{id}/details").asString();
        System.out.println(result);
    }

    @Test
    public void uploadTourImagesTest() throws Exception {
        String result=given()
                .pathParam("id",3)
                .multiPart("image",new File("d:/test.jpg"))
                .post("/ridetour/tour/{id}/image/upload").asString();
        System.out.println(result);
    }

    @Test
    public void createTourVideoTest() throws Exception {
        String result=given()
                .pathParam("id",3)
                .multiPart("video",new File("d:/test.jpg"))
                .post("/ridetour/tour/{id}/video/create").asString();
        System.out.println(result);
    }

    @Test
    public void deleteTourVideoTest() throws Exception {
        String result=given()
                .pathParam("id",3)
                .pathParam("videoId",1)
                .contentType(ContentType.JSON)
                .post("/ridetour/tour/{id}/video/delete/{videoId}").asString();
        System.out.println(result);
    }


}
