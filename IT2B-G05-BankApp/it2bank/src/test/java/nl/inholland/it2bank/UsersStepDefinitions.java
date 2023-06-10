package nl.inholland.it2bank;

import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import nl.inholland.it2bank.config.SSLUtils;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

@Log
public class UsersStepDefinitions extends BaseStepDefinitions {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> response;

    HttpHeaders httpHeaders = new HttpHeaders();

    @SneakyThrows
    @Before
    public void init() {
        SSLUtils.turnOffSslChecking();
        log.info("Turned off SSL checking");
    }

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
        httpHeaders.clear();
        response = restTemplate.exchange(
                "/users",
                HttpMethod.GET,
                new HttpEntity<>(null, httpHeaders),
                String.class
        );
    }

    @Then("I should receive {int} users")
    public void iShouldReceiveUsers(int expectedAmount) {
        String body = (String) response.getBody();
        int actualAmount = JsonPath.read(body, "$.size()");

        Assertions.assertEquals(expectedAmount, actualAmount);
    }


    @When("I provide registration form with user details")
    public void iProvideRegistrationFormWithUserDetails() {
    }

    @Then("I should get status {int}")
    public void iRetrieveUser(int statusCode) {

    }

}
