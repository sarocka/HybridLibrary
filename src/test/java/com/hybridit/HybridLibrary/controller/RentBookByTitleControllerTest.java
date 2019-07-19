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
public class RentBookByTitleControllerTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void rentByExistingBookTitleAndExistingCustomer_CopyAvailable() {

        given().auth()
                .basic("sara", "petruska").when().get("/api/rentByTitle?title=Flauberts Parrot&membershipNo=123456")
                .then().body("id", Matchers.equalTo(1)).
                body("libraryNum", Matchers.equalTo("4567-0987-ab")).
                body("dateOfBorrowing", Matchers.equalTo(LocalDate.now().toString())).
                body("bookId", Matchers.equalTo(1)).
                body("bookTitle", Matchers.equalTo("Flauberts Parrot"));
    }

    @Test
    public void rentByExistingBookTitleAndExistingCustomer_CopyNotAvailable() {

        given().auth()
                .basic("sara", "petruska").when().get("/api/rentByTitle?title=Hamlet&membershipNo=123456")
                .then().statusCode(404);
    }

    @Test
    public void rentByExistingBookTitleCustomerNotExisting() {

        given().auth()
                .basic("sara", "petruska").when().get("/api/rentByTitle?title=Hamlet&membershipNo=23456")
                .then().statusCode(404);
    }

    @Test
    public void rentByNonExistingBookTitle() {

        given().auth()
                .basic("sara", "petruska").when().get("/api/rentByTitle?title=Sometitle&membershipNo=23456")
                .then().statusCode(404);
    }

}



