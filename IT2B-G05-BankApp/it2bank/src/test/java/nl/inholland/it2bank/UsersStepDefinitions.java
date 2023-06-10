package nl.inholland.it2bank;

import io.cucumber.java.en.Given;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

public class UsersStepDefinitions extends BaseStepDefinitions{

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> response;

    HttpHeaders httpHeaders = new HttpHeaders();

    @Given("The endpoint for {string} is available for {string}")
    public void theEndpointIsAvailable(String endpoint, String method){
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
}
