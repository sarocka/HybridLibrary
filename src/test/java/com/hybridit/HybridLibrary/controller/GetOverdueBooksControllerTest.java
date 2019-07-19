package com.hybridit.HybridLibrary.controller;

import com.hybridit.HybridLibrary.dto.BookCopyDTO;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetOverdueBooksControllerTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }


    @Test
    public void getOverdueCopies() {

        List<BookCopyDTO> response = given().auth()
                .basic("sara", "petruska").when().get("/api/overdueBooks")
                .then().statusCode(200).extract().jsonPath().getList("$", BookCopyDTO.class);

        BookCopyDTO bookCopyDTO = response.get(0);
        assertEquals((long) bookCopyDTO.getId(), 5L);
        assertEquals(bookCopyDTO.getDateOfBorrowing(), LocalDate.of(2019, 06, 07));
        assertEquals(bookCopyDTO.getLibraryNum(), "4597-0987-cm");
        assertEquals(bookCopyDTO.getBookTitle(), "The Late Mattia Pascalt");
        assertEquals((long) bookCopyDTO.getBookId(), 2L);

    }

}
