package com.hybridit.HybridLibrary.controller;

import com.hybridit.HybridLibrary.model.Customer;
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
public class CustomerControllerTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void getExistingCustomer() {
        given().auth()
                .basic("sara", "petruska").when().get("/api/customers/{id}", 1).then().statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("firstname", Matchers.equalTo("Vladimir"))
                .body("lastname", Matchers.equalTo("Danilovic"))
                .body("address", Matchers.equalTo("Cirpanova 1a"))
                .body("phoneNo", Matchers.equalTo("063510250"));
    }

    @Test
    public void getNonExistingCustomer() {
        given().auth()
                .basic("sara", "petruska").
                when().get("/api/customers/{id}", 9).then().statusCode(404);
    }

    @Test
    public void getAll() {

        List<Customer> response = given().auth()
                .basic("sara", "petruska").when().get("/api/customers").then().statusCode(200).extract().jsonPath().getList("$", Customer.class);

        Customer customer = response.get(0);
        assertEquals((long) customer.getId(), 1L);
        assertEquals(customer.getFirstname(), "Vladimir");
        assertEquals(customer.getLastname(), "Danilovic");
        assertEquals(customer.getAddress(), "Cirpanova 1a");
        assertEquals(customer.getPhoneNo(), "063510250");

    }

    @Test
    public void createCustomer() {
        Customer customer = new Customer();
        customer.setFirstname("Firstname");
        customer.setLastname("Lastname");
        customer.setAddress("Address");
        customer.setPhoneNo("PhoneNo");

        given().auth()
                .basic("sara", "petruska")
                .contentType("application/json")
                .body(customer).when().post("/api/customers").then()
                .body("firstname", Matchers.equalTo("Firstname"))
                .body("lastname", Matchers.equalTo("Lastname"))
                .body("address", Matchers.equalTo("Address"))
                .body("phoneNo", Matchers.equalTo("PhoneNo"));
    }

    @Test
    public void updateExistingCustomer() {
        Customer customer = new Customer();
        customer.setFirstname("Firstname");
        customer.setLastname("Lastname");
        customer.setAddress("Address");
        customer.setPhoneNo("PhoneNo");

        given().auth()
                .basic("sara", "petruska")
                .contentType("application/json")
                .body(customer)
                .when().put("/api/customers/{id}", 1).then()
                .body("firstname", Matchers.equalTo("Firstname"))
                .body("lastname", Matchers.equalTo("Lastname"))
                .body("address", Matchers.equalTo("Address"))
                .body("phoneNo", Matchers.equalTo("PhoneNo"));
    }

    @Test
    public void updateNonExistingCustomer() {
        Customer customer = new Customer();

        given().auth()
                .basic("sara", "petruska")
                .contentType("application/json")
                .body(customer)
                .when().put("/api/customers/{id}", 6).then().statusCode(404);
    }

    @Test
    public void deleteExistingCustomer() {
        given().auth()
                .basic("sara", "petruska").
                when().delete("/api/customers/{id}", 1).then()
                .body("id", Matchers.equalTo(1))
                .body("firstname", Matchers.equalTo("Vladimir"))
                .body("lastname", Matchers.equalTo("Danilovic"))
                .body("address", Matchers.equalTo("Cirpanova 1a"))
                .body("phoneNo", Matchers.equalTo("063510250"));

    }

    @Test
    public void deleteNonExistingCustomer() {
        given().auth()
                .basic("sara", "petruska").
                when().delete("/api/customers/{id}", 9).then().statusCode(404);
    }
}
