package com.hybridit.HybridLibrary.controller;

import com.hybridit.HybridLibrary.dto.BookCopyDTO;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookCopyControllerTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void getOneExisting() {

        when().get("/api/copies/{id}", 1).then().
                body("id", Matchers.equalTo(1)).
                body("libraryNum", Matchers.equalTo("4567-0987-ab")).
                body("dateOfBorrowing", Matchers.equalTo(null)).
                body("bookId", Matchers.equalTo(1)).
                body("bookTitle", Matchers.equalTo("Flauberts Parrot"));
    }

    @Test
    public void getOneNonExisting() {

        when().get("/api/copies/{id}", 5).then().statusCode(404);
    }

    @Test
    public void getAll() {

        List<BookCopyDTO> response = when().get("/api/copies").then().statusCode(200).extract().jsonPath().getList("$", BookCopyDTO.class);

        BookCopyDTO bookCopyDTO = response.get(0);
        assertEquals((long) bookCopyDTO.getId(), 1L);
        assertEquals(bookCopyDTO.getLibraryNum(), "4567-0987-ab");
        assertEquals(bookCopyDTO.getDateOfBorrowing(), null);
        assertEquals((long) bookCopyDTO.getBookId(), 1L);
        assertEquals(bookCopyDTO.getBookTitle(), "Flauberts Parrot");
    }

    @Test
    public void createNewBookCopy() {
        BookCopyDTO bookCopy = new BookCopyDTO();
        bookCopy.setLibraryNum("123456");
        bookCopy.setBookTitle("Flauberts Parrot");
        bookCopy.setBookId(1L);

        given()
                .contentType("application/json")
                .body(bookCopy)
                .when().post("/api/copies").then()
                .body("libraryNum", Matchers.equalTo("123456"))
                .body("dateOfBorrowing", Matchers.equalTo(null))
                .body("bookTitle", Matchers.equalTo("Flauberts Parrot"))
                .body("bookId", Matchers.equalTo(1));
    }

    @Test
    public void deleteExistingBookCopyNotRented() {
        when().delete("/api/copies/{id}", 1).then().
                body("id", Matchers.equalTo(1)).
                body("libraryNum", Matchers.equalTo("4567-0987-ab")).
                body("dateOfBorrowing", Matchers.equalTo(null)).
                body("bookId", Matchers.equalTo(1)).
                body("bookTitle", Matchers.equalTo("Flauberts Parrot"));
    }

    @Test
    public void deleteExistingBookCopyRented() {

        when().delete("/api/copies/{id}", 5).then().statusCode(400);
    }

    @Test
    public void deleteNonExistingBookCopy() {

        when().delete("/api/copies/{id}", 19).then().statusCode(404);
    }

    @Test
    public void updateExistingBookCopy() {
        BookCopyDTO bookCopy = new BookCopyDTO();
        bookCopy.setLibraryNum("libraryNum");
        bookCopy.setBookTitle("Flauberts Parrot");
        bookCopy.setBookId(1L);


        given()
                .contentType("application/json")
                .body(bookCopy)
                .when().put("/api/copies/{id}", 1).then()
                .body("libraryNum", Matchers.equalTo("libraryNum"))
                .body("dateOfBorrowing", Matchers.equalTo(null))
                .body("bookId", Matchers.equalTo(1))
                .body("bookTitle", Matchers.equalTo("Flauberts Parrot"));
        ;
    }

    @Test
    public void updateNonExistingBookCopy() {
        BookCopyDTO bookCopyDTO = new BookCopyDTO();
        bookCopyDTO.setId(6L);
        bookCopyDTO.setBookId(2L);

        given()
                .contentType("application/json")
                .body(bookCopyDTO)
                .when().put("/api/copies/{id}", 6).then().statusCode(404);
    }


}
