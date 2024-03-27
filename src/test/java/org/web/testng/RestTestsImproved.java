package org.web.testng;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.collections.web.dto.ResultsDto;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RestTestsImproved {
    @Test
    public void testRestAssured() {
        RestAssured.baseURI = "https://randomuser.me/";
        ResultsDto dto = RestAssured.given()
                .basePath("/api")
                .queryParam("inc", "gender,name,nat,id")
                .queryParam("noinfo")
                .queryParam("results", 5)
                .get()
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .extract()
                .as(ResultsDto.class);

        System.out.println(dto.getResults());
        for (int i = 0; i < dto.getResults().size(); i++) {
            System.out.println(dto.getResults().get(i).getId());
        }

        // Variant 1
        List<String> gender = new ArrayList<>();
        for (int i = 0; i < dto.getResults().size(); i++) {
            gender.add(dto.getResults().get(i).getGender());
            }
        assertTrue(gender.contains("female"), "There are no females in the results");

        //Variant 2
        boolean femaleGenderPresent = false;
        for (int i = 0; i < dto.getResults().size(); i++) {
            if (dto.getResults().get(i).getGender().equals("female")) {
                femaleGenderPresent = true;
                break;
            }
        }
        assertTrue(femaleGenderPresent, "There are no females in the results");
    }
}
