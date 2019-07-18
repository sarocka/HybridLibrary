package com.hybridit.HybridLibrary.controller;

import com.hybridit.HybridLibrary.dto.RoleDTO;
import com.hybridit.HybridLibrary.dto.UserDTO;
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
public class UserControllerTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void getExistingUser() {
        given().auth()
                .basic("aron", "aron").
                when().get("/api/users/{id}", 2).then()
                .body("id", Matchers.equalTo(2))
                .body("username", Matchers.equalTo("aron"));

    }

    @Test
    public void getNonExistingUser() {
        given().auth()
                .basic("aron", "aron").
                when().get("/api/users/{id}", 9).then().statusCode(404);
    }

    @Test
    public void getAll() {

        List<UserDTO> response = given().auth()
                .basic("aron", "aron").when().get("/api/users").then().
                        statusCode(200).extract().jsonPath().getList("$", UserDTO.class);

        UserDTO user = response.get(0);
        assertEquals((long) user.getId(), 1L);
        assertEquals(user.getUsername(), "sara");
    }

    @Test
    public void createUser() {
        UserDTO user = new UserDTO();
        user.setUsername("username");
        user.setRoleId(1L);


        given().auth()
                .basic("aron", "aron")
                .contentType("application/json")
                .body(user).when().post("/api/users").then()
                .body("username", Matchers.equalTo("username"));
    }

    @Test
    public void updateExistingUser() {
        UserDTO user = new UserDTO();
        user.setId(1L);
       user.setUsername("username");
       user.setRoleId(1L);

        given().auth()
                .basic("aron", "aron")
                .contentType("application/json")
                .body(user)
                .when().put("/api/users/{id}", 1).then()
                .body("username", Matchers.equalTo("username"));
    }

    @Test
    public void updateNonExistingUser() {
        UserDTO user = new UserDTO();
        user.setRoleId(1L);

        given().auth()
                .basic("aron", "aron")
                .contentType("application/json")
                .body(user)
                .when().put("/api/users/{id}", 6).then().statusCode(404);
    }

    @Test
    public void deleteExistingUser() {
        given().auth()
                .basic("aron", "aron").
                when().delete("/api/users/{id}", 1).then()
                .body("id", Matchers.equalTo(1))
                .body("username", Matchers.equalTo("sara"));
    }

    @Test
    public void deleteNonExistingRole() {
        given().auth()
                .basic("aron", "aron").
                when().delete("/api/users/{id}", 9).then().statusCode(404);
    }
}
