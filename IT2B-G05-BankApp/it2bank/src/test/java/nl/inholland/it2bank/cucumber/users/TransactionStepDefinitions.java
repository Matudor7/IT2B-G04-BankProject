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
import nl.inholland.it2bank.service.BankAccountService;
import nl.inholland.it2bank.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionStepDefinitions extends BaseStepDefinitions {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;
    private ResponseEntity<String> response;

    private String token;
    private TransactionService transactionService;

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
    public void theEndpointIsAvailableTransactions(String endpoint, String method) throws JsonProcessingException {
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

    @When("I retrieve all transctions")
    public void iRetrieveAllTransactions() {
        httpHeaders.clear();
        response = restTemplate.exchange(
                "/transactions",
                HttpMethod.GET,
                new HttpEntity<>(null, httpHeaders),
                String.class
        );
    }

    @Then("I should receive {int} transactions")
    public void iShouldTransactions(int expectedCount) {
        String body = (String) response.getBody();
        int actualAmount = JsonPath.read(body, "$.size()");

        assertEquals(expectedCount, actualAmount);
    }

    @When("I provide registration form with transaction details")
    public void iProvideRegistrationFormWithTransactionDetails() {
        httpHeaders.clear();
        httpHeaders.add("Content-Type", "Application/json");

        response = restTemplate.exchange(
                "/users",
                HttpMethod.POST,
                new HttpEntity<>(
                        """
                                {
                                     "userPerforming": 1,
                                     "accountFrom": "NL01INHO0000000002",
                                     "accountTo": "NL01INHO0000000003",
                                     "amount": 100,
                                     "time": "2023-06-28T15:30:00",
                                     "comment": "comment"
                                }
                                """, httpHeaders),
                String.class
        );
    }

    @Then("I should be getting status {int}")
    public void iShouldGetStatusForTransaction(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode().value();
        assertEquals(expectedStatusCode,actualStatusCode);
    }
}
