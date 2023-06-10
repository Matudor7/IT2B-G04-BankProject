package nl.inholland.it2bank.cucumber.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import nl.inholland.it2bank.config.SSLUtils;
import nl.inholland.it2bank.cucumber.BaseStepDefinitions;
import nl.inholland.it2bank.model.dto.LoginDTO;
import nl.inholland.it2bank.model.dto.TokenDTO;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@Log
public class LoginStepDefinitions extends BaseStepDefinitions {

    private LoginDTO loginDTO;
    private String token;
    private final HttpHeaders httpHeaders = new HttpHeaders();
    private ResponseEntity<String> response;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObjectMapper mapper;

    @SneakyThrows
    @Before
    public void init(){
        SSLUtils.turnOffSslChecking();
        log.info("Turned off SSL checking");
    }

    @Given("I have valid login information for all data")
    public void ihaveValidLoginInformationForAllData(){
        loginDTO = new LoginDTO("bankje@gmail.com", "Bankje!");
    }

    @When("I call the login endpoint")
    public void iCallTheLoginEndpoint() throws JsonProcessingException {
        httpHeaders.add("Content-Type", "Application/json");
        response = restTemplate.exchange(
                "/login",
                HttpMethod.POST,
                new HttpEntity<>(mapper.writeValueAsString(loginDTO), httpHeaders),
                String.class
        );
    }

    @Then("I receive a token")
    public void iReceiveAToken() throws JsonProcessingException{
        token = mapper.readValue(response.getBody(), TokenDTO.class).token();
        Assertions.assertNotNull(token);
    }

    @Given("I have invalid email but valid password")
    public void iHaveInvalidUsernameButValidPassword() {
        loginDTO = new LoginDTO("invalid@gmail.com", "Bankje!");
    }

    @Then("I should get http status {int}")
    public void iShouldGetHttpStatus(int statusCode) {
        Assertions.assertEquals(statusCode, response.getStatusCode().value());
    }

    @Given("I have valid email but invalid password")
    public void iHaveValidUsernameButInvalidPassword() {
        loginDTO = new LoginDTO("bankje@gmail.com", "Invalid!");
    }
}
