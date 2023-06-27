package nl.inholland.it2bank.cucumber.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import nl.inholland.it2bank.config.SSLUtils;
import nl.inholland.it2bank.cucumber.BaseStepDefinitions;
import nl.inholland.it2bank.model.dto.LoginDTO;
import nl.inholland.it2bank.model.dto.TokenDTO;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;
public class bankAccountStepDefinitions extends BaseStepDefinitions {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;
    private ResponseEntity<String> response;

    private String token;

    private HttpHeaders httpHeaders = new HttpHeaders();
    private boolean loggedIn;

    @SneakyThrows
    @Before
    public void init() {
        SSLUtils.turnOffSslChecking();
    }
    public void login() throws JsonProcessingException {
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

    @Given("The endpoint {string} is available for method {string}")
    public void theEndpointIsAvailableForBankAccounts(String endpoint, String method) throws JsonProcessingException {
        login();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);

        ResponseEntity<String> response = restTemplate.exchange("/" + endpoint, HttpMethod.OPTIONS,
                new HttpEntity<>(null, httpHeaders), String.class);

        List<String> options = Arrays.stream(response.getHeaders()
                        .get("Allow")
                        .get(0)
                        .split(","))
                .toList();
        Assertions.assertTrue(options.contains(method.toUpperCase()));
    }

    @When("I retrieve all bank accounts")
    public void iRetrieveAllBankAccounts() {
        httpHeaders.clear();
        response = restTemplate.exchange(
                "/bankaccounts",
                HttpMethod.GET,
                new HttpEntity<>(null, httpHeaders),
                String.class
        );
    }

    @Then("I should receive {int} accounts")
    public void iShouldReceiveAccounts(int expectedCount) {
        String body = (String) response.getBody();
        int actualAmount = JsonPath.read(body, "$.size()");

        Assertions.assertEquals(expectedCount, actualAmount);
    }

    @Then("I should be getting status {int}")
    public void iShouldGetStatusForBankAccount(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode().value();
        Assertions.assertEquals(expectedStatusCode,actualStatusCode);
    }

    @When("I provide registration form with bank account details")
    public void iProvideRegistrationFormWithBankAccountDetails() {
httpHeaders.clear();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String body = """
                {
                    "accountNumber": "NL01INHO0000000001",
                    "balance": 1000,
                    "creditLimit": 1000,
                    "accountType": "SAVINGS"
                }""";
        response = restTemplate.exchange(
                "/bankaccounts",
                HttpMethod.POST,
                new HttpEntity<>(body, httpHeaders),
                String.class
        );
    }

    @When("I update the bank account with IBAN {string} using the following details:")
    public void iUpdateTheBankAccountWithIBANUsingTheFollowingDetails(String arg0) {

    }
}