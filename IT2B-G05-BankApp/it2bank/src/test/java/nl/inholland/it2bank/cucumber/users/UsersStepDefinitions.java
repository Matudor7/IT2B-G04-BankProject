package nl.inholland.it2bank.cucumber.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import nl.inholland.it2bank.cucumber.BaseStepDefinitions;
import nl.inholland.it2bank.config.SSLUtils;
import nl.inholland.it2bank.model.dto.LoginDTO;
import nl.inholland.it2bank.model.dto.TokenDTO;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@Log
public class UsersStepDefinitions extends BaseStepDefinitions {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    private String token;

    private ResponseEntity<String> response;

    private HttpHeaders httpHeaders = new HttpHeaders();

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
        httpHeaders.clear();
        httpHeaders.add("Content-Type", "Application/json");

        response = restTemplate.exchange(
                "/users",
                HttpMethod.POST,
                new HttpEntity<>(
                        """
                                {
                                        "firstName": "Big",
                                        "lastName": "Boss",
                                        "bsn": 121312454454,
                                        "phoneNumber": "0116777777",
                                        "email": "nakedsnake@gmail.com",
                                        "password": "$2a$12$qG0w6PENZgVnH7Jl2tA4nut66yfZGzVcyT/wmCOgZsaeLghoItZNa",
                                        "role": "User",
                                        "dailyLimit": 50.0,
                                        "transactionLimit": 100.0
                                }
                                """, httpHeaders),
                String.class
        );
    }

    @Then("I should get status {int}")
    public void iShouldGetStatus(int statusCode) {
        int actualStatusCode = response.getStatusCode().value();
        Assertions.assertEquals(statusCode,actualStatusCode);
    }

    @When("I provide an edited user")
    public void iProvideAnEditedUser() {
        httpHeaders.clear();
        httpHeaders.add("Content-Type", "Application/json");
        httpHeaders.add("Authorization", "Bearer " + token);

        response = restTemplate.exchange(
                "/users/3",
                HttpMethod.PUT,
                new HttpEntity<>(
                        """
                                {
                                    "firstName": "Softerer",
                                    "lastName": "Kitten",
                                    "bsn": 123435791,
                                    "phoneNumber": "0620117001",
                                    "email": "null@email.com",
                                    "password": "nullPass",
                                    "role": "User",
                                    "transactionLimit": 500,
                                    "dailyLimit": 1000
                                }
                                """, httpHeaders),
                String.class
        );
    }

    @Given("I am logged in")
    public void iAmLoggedIn() throws JsonProcessingException{
        httpHeaders.clear();
        httpHeaders.add("Content-Type", "application/json");

        LoginDTO loginDTO = new LoginDTO("bankje@gmail.com", "Bankje!");
        token = getToken(loginDTO);
    }

    private String getToken(LoginDTO loginDTO) throws JsonProcessingException {
        response = restTemplate.exchange(
                "/login", HttpMethod.POST,
                new HttpEntity<>(mapper.writeValueAsString(loginDTO), httpHeaders),
                String.class
        );

        TokenDTO tokenDTO = mapper.readValue(response.getBody(), TokenDTO.class);

        return tokenDTO.token();
    }

    @When("I want to delete user with ID {int}")
    public void iWantToDeleteUserWithID(int userId) {
        httpHeaders.clear();
        httpHeaders.add("Authorization", "Bearer " + token);

        response = restTemplate.exchange(
                "/users/3",
                HttpMethod.DELETE,
                new HttpEntity<>(
                        null, httpHeaders),
                String.class
        );
    }
}
