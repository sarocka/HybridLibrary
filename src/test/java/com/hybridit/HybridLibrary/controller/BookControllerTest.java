package com.hybridit.HybridLibrary.controller;

import com.hybridit.HybridLibrary.dto.BookDTO;
import com.hybridit.HybridLibrary.model.Book;
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
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void getExistingBook() {
        given().auth()
                .basic("ivan", "ivan").
                when().get("/api/books/{id}", 1).then().body("id", Matchers.equalTo(1)).
                body("title", Matchers.equalTo("Flauberts Parrot"))
                .body("isbn", Matchers.equalTo("4567-897-98")).
                body("publisher", Matchers.equalTo("Jonathan Cape"));
    }

    @Test
    public void getNonExistingBook() {
        given().auth()
                .basic("ivan", "ivan").
                when().get("/api/books/{id}", 9).then().statusCode(404);
    }

    @Test
    public void getAll() {

        List<BookDTO> response = given().auth()
                .basic("ivan", "ivan").when().get("/api/books").then().statusCode(200).extract().jsonPath().getList("$", BookDTO.class);

        BookDTO book1 = response.get(0);
        assertEquals((long) book1.getId(), 1L);
        assertEquals(book1.getTitle(), "Flauberts Parrot");
        assertEquals(book1.getPublisher(), "Jonathan Cape");
        assertEquals(book1.getIsbn(), "4567-897-98");
    }

    @Test
    public void createBook() {
        Book book = new Book();
        book.setIsbn("isbn");
        book.setPublisher("publisher");
        book.setTitle("title");

        given().auth()
                .basic("ivan", "ivan")
                .contentType("application/json")
                .body(book).when().post("/api/books").then().
                body("isbn", Matchers.equalTo("isbn")).
                body("publisher", Matchers.equalTo("publisher")).body("title", Matchers.equalTo("title"));
    }

    @Test
    public void updateExistingBook() {

        Book book = new Book();
        book.setTitle("book title");
        book.setPublisher("book publisher");
        book.setIsbn("isbn");

        given().auth()
                .basic("ivan", "ivan")
                .contentType("application/json")
                .body(book)
                .when().put("/api/books/{id}", 1).then()
                .body("title", Matchers.equalTo("book title")).
                body("publisher", Matchers.equalTo("book publisher"))
                .body("isbn", Matchers.equalTo("isbn"));
    }

    @Test
    public void updateNonExistingBook() {
        Book book = new Book();
        book.setTitle("book title");
        book.setPublisher("book publisher");
        book.setIsbn("isbn");

        given().auth()
                .basic("ivan", "ivan")
                .contentType("application/json")
                .body(book)
                .when().put("/api/books/{id}", 6).then().statusCode(404);
    }

    @Test
    public void deleteExistingBook() {
        given().auth()
                .basic("ivan", "ivan").
                when().delete("/api/books/{id}", 1).then().
                body("id", Matchers.equalTo(1)).
                body("publisher", Matchers.equalTo("Jonathan Cape")).
                body("title", Matchers.equalTo("Flauberts Parrot")).
                body("isbn", Matchers.equalTo("4567-897-98"));
    }

    @Test
    public void deleteNonExistingBook() {
        given().auth()
                .basic("ivan", "ivan").
                when().delete("/api/books/{id}", 9).then().statusCode(404);
    }
}
