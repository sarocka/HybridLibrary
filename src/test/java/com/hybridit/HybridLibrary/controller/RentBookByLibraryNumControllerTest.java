package com.hybridit.HybridLibrary.controller;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RentBookByLibraryNumControllerTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void rentByLibraryNumCopyCustomerExisting() {

        given().auth()
                .basic("sara", "petruska").when().get("/api/rentByLibraryNum?libraryNum=4567-0987-ab&membershipNo=123456")
                .then().body("id", Matchers.equalTo(1)).
                body("libraryNum", Matchers.equalTo("4567-0987-ab")).
                body("dateOfBorrowing", Matchers.equalTo(LocalDate.now().toString())).
                body("bookId", Matchers.equalTo(1)).
                body("bookTitle", Matchers.equalTo("Flauberts Parrot"));
    }

    @Test
    public void rentByLibraryNumCustomerNotExisting() {

        given().auth()
                .basic("sara", "petruska").
                when().get("/api/rentByLibraryNum?libraryNum=4567-0987-cm&membershipNo=123056")
                .then().statusCode(404);
    }

    @Test
    public void rentByNonExistingLibraryNumCustomerExisting() {

        given().auth()
                .basic("sara", "petruska").
                when().get("/api/rentByLibraryNum?libraryNum=4567-0987-cp&membershipNo=123456")
                .then().statusCode(404);
    }

}



