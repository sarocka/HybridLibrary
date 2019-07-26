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
public class ReturnBookControllerTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void returnBookCopyLibraryNumExisting() {

        given().auth()
                .basic("sara", "petruska").when().get("/api/return?libraryNum=4597-0987-oo")
                .then().body("id", Matchers.equalTo(7)).
                body("libraryNum", Matchers.equalTo("4597-0987-oo")).
                body("dateOfBorrowing", Matchers.equalTo(null)).
                body("bookId", Matchers.equalTo(4)).
                body("bookTitle", Matchers.equalTo("Fantastic night")).
                body("customerId", Matchers.equalTo(null));
    }

    @Test
    public void returnLibraryNumNotExisting() {

        given().auth()
                .basic("sara", "petruska").
                when().get("/api/return?libraryNum=4567-0987-cmo")
                .then().statusCode(404);
    }


}



