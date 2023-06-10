package nl.inholland.it2bank;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

public class UsersStepDefinitions extends BaseStepDefinitions {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> response;

    HttpHeaders httpHeaders = new HttpHeaders();

    @Given("The endpoint for {string} is available for method {string}")
    public void theEndpointForIsAvailableForMethod(String endpoint, String method) {
        response = restTemplate
                .exchange("/" + endpoint,
                        HttpMethod.OPTIONS,
                        new HttpEntity<>(null, new HttpHeaders()),
                        String.class);
        List<String> options = Arrays.stream(response.getHeaders()
                        .get("Allow")
                        .get(0)
                        .split(","))
                .toList();

        Assertions.assertTrue(options.contains(method.toUpperCase()));
    }

    @When("I retrieve all users")
    public void iRetrieveAllUsers() {
    }

    @Then("I should receive all users")
    public void iShouldReceiveAllUsers() {
    }

    @Given("the email written is not already taken")
    public void theEmailWrittenIsNotAlreadyTaken() {
    }

    @When("I provide details")
    public void iProvideDetails() {
    }

    @Then("I retrieve user")
    public void iRetrieveUser() {
    }
}
