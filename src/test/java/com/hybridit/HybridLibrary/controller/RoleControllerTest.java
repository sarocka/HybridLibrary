package com.hybridit.HybridLibrary.controller;

import com.hybridit.HybridLibrary.dto.RoleDTO;
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
public class RoleControllerTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void getExistingRole() {
        given().auth()
                .basic("aron", "aron").
                when().get("/api/roles/{id}", 1).then()
                .body("id", Matchers.equalTo(1))
                .body("name", Matchers.equalTo("LIBRARIAN"));
    }

    @Test
    public void getNonExistingRole() {
        given().auth()
                .basic("aron", "aron").
                when().get("/api/roles/{id}", 9).then().statusCode(404);
    }

    @Test
    public void getAll() {

        List<RoleDTO> response = given().auth()
                .basic("aron", "aron").when().get("/api/roles").then().statusCode(200).extract().jsonPath().getList("$", RoleDTO.class);

        RoleDTO role = response.get(0);
        assertEquals((long) role.getId(), 1L);
        assertEquals(role.getName(), "LIBRARIAN");
    }

    @Test
    public void createRole() {
        RoleDTO role = new RoleDTO();
        role.setName("TEST");


        given().auth()
                .basic("aron", "aron")
                .contentType("application/json")
                .body(role).when().post("/api/roles").then()
                .body("name", Matchers.equalTo("TEST"));
    }

    @Test
    public void updateExistingRole() {
        RoleDTO role = new RoleDTO();
        role.setId(1L);
        role.setName("UPDATED ROLE");

        given().auth()
                .basic("aron", "aron")
                .contentType("application/json")
                .body(role)
                .when().put("/api/roles/{id}", 1).then()
                .body("name", Matchers.equalTo("UPDATED ROLE"));
    }

    @Test
    public void updateNonExistingRole() {
        RoleDTO role = new RoleDTO();

        given().auth()
                .basic("aron", "aron")
                .contentType("application/json")
                .body(role)
                .when().put("/api/roles/{id}", 6).then().statusCode(404);
    }

    @Test
    public void deleteExistingRole() {
        given().auth()
                .basic("aron", "aron").
                when().delete("/api/roles/{id}", 1).then()
                .body("id", Matchers.equalTo(1))
                .body("name", Matchers.equalTo("LIBRARIAN"));
    }

    @Test
    public void deleteNonExistingRole() {
        given().auth()
                .basic("aron", "aron").
                when().delete("/api/roles/{id}", 9).then().statusCode(404);
    }
}
