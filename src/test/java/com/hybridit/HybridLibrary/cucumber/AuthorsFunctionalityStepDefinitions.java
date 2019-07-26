package com.hybridit.HybridLibrary.cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class AuthorsFunctionalityStepDefinitions {

    @Given("^list of authors exists$")
    public void list_of_authors_exists() {
        given().auth()
                .basic("ivan", "ivan");
    }

    @When("^I want to get list of all the authors$")
    public void i_want_to_get_list_of_all_the_authors() {
        when().get("http://localhost:8080/api/authors").then().statusCode(200);
    }

    @Then("^all authors are enlisted$")
    public void all_authors_are_enlisted() {
    }


}
