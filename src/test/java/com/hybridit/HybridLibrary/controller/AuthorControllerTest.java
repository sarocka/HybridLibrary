package com.hybridit.HybridLibrary.controller;

import com.hybridit.HybridLibrary.dto.AuthorDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
public class AuthorControllerTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void getOneExisting() {
        given().auth()
                .basic("ivan", "ivan").
                when().get("/api/authors/{id}", 1).then().body("id", Matchers.equalTo(1)).
                body("name", Matchers.equalTo("Julian Barnes"));
    }

    @Test
    public void getOneNonExisting() {
        given().auth()
                .basic("ivan", "ivan").
                when().get("/api/authors/{id}", 5).then().statusCode(404);
    }

    @Test
    public void getAll() {

        List<AuthorDTO> response = given().auth()
                .basic("ivan", "ivan").when().get("/api/authors").then().statusCode(200).extract().jsonPath().getList("$", AuthorDTO.class);

        AuthorDTO author1 = response.get(0);
        assertEquals((long) author1.getId(), 1L);
        assertEquals(author1.getName(), "Julian Barnes");
    }

    @Test
    public void createNewAuthor() {
        AuthorDTO author = new AuthorDTO();
        author.setName("new Author");

        given().auth()
                .basic("ivan", "ivan")
                .contentType(ContentType.JSON)
                .with().body(author)
                .when().post("/api/authors").then().statusCode(201)
                .body("name", Matchers.equalTo("new Author"));
    }

    @Test
    public void deleteExistingAuthorWithNoBooks() {
        given().auth()
                .basic("ivan", "ivan").
                when().delete("/api/authors/{id}", 3).
                then().body("id", Matchers.equalTo(3)).
                body("name", Matchers.equalTo("Milos Crnjanski"));
    }

    @Test
    public void deleteExistingAuthorWithBooks() {

        given().auth()
                .basic("ivan", "ivan").
                when().delete("/api/authors/{id}", 1).then().statusCode(400);
    }

    @Test
    public void deleteNonExistingAuthor() {

        given().auth()
                .basic("ivan", "ivan").
                when().delete("/api/authors/{id}", 19).then().statusCode(404);
    }

    @Test
    public void updateExistingAuthor() {
        AuthorDTO author = new AuthorDTO();
        author.setName("updated Author");

        given().auth()
                .basic("ivan", "ivan")
                .contentType("application/json")
                .body(author)
                .when().put("/api/authors/{id}", 1).then().statusCode(200)
                .body("name", Matchers.equalTo("updated Author"));
    }

    @Test
    public void updateNonExistingAuthor() {
        AuthorDTO author = new AuthorDTO();
        author.setName("updated Author");

        given().auth()
                .basic("ivan", "ivan")
                .contentType("application/json")
                .body(author)
                .when().put("/api/authors/{id}", 6).then().statusCode(404);
    }


}
