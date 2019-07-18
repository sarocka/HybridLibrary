package com.hybridit.HybridLibrary.controller;

import com.hybridit.HybridLibrary.dto.AuthorDTO;
import com.hybridit.HybridLibrary.model.Author;
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
public class AuthorControllerTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void getOneExisting() {

        when().get("/api/authors/{id}", 1).then().body("id", Matchers.equalTo(1)).
                body("name", Matchers.equalTo("Julian Barnes"));
    }

    @Test
    public void getOneNonExisting() {

        when().get("/api/authors/{id}", 5).then().statusCode(404);
    }

    @Test
    public void getAll() {

        List<AuthorDTO> response = when().get("/api/authors").then().statusCode(200).extract().jsonPath().getList("$", AuthorDTO.class);

        AuthorDTO author1 = response.get(0);
        assertEquals((long) author1.getId(), 1L);
        assertEquals(author1.getName(), "Julian Barnes");
    }

    @Test
    public void createNewAuthor() {
        Author author = new Author();
        author.setName("new Author");

        given()
                .contentType("application/json")
                .body(author)
                .when().post("/api/authors").then()
                .body("name", Matchers.equalTo(" new Author"));
    }

    @Test
    public void deleteExistingAuthorWithNoBooks() {
        when().delete("/api/authors/{id}", 3).then().body("id", Matchers.equalTo(3)).
                body("name", Matchers.equalTo("Milos Crnjanski"));
    }

    @Test
    public void deleteExistingAuthorWithBooks() {
        when().delete("/api/authors/{id}", 1).then().statusCode(400);
    }

    @Test
    public void deleteNonExistingAuthor() {
        when().delete("/api/authors/{id}", 19).then().statusCode(404);
    }

    @Test
    public void updateExistingAuthor() {
        Author author = new Author();
        author.setName("updated Author");

        given()
                .contentType("application/json")
                .body(author)
                .when().put("/api/authors/{id}", 1).then()
                .body("name", Matchers.equalTo("updated Author"));
    }

    @Test
    public void updateNonExistingAuthor() {
        Author author = new Author();
        author.setName("updated Author");

        given()
                .contentType("application/json")
                .body(author)
                .when().put("/api/authors/{id}", 6).then().statusCode(404);
    }


}
