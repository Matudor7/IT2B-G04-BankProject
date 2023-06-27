package nl.inholland.it2bank.cucumber.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import nl.inholland.it2bank.config.SSLUtils;
import nl.inholland.it2bank.cucumber.BaseStepDefinitions;
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

    private HttpHeaders httpHeaders = new HttpHeaders();
    private boolean loggedIn;

    @SneakyThrows
    @Before
    public void init() {
        SSLUtils.turnOffSslChecking();
    }
    @Given("The endpoint {string} is available for method {string}")
    public void theEndpointIsAvailableForBankAccounts(String endpoint, String method) {
        response = restTemplate.exchange(
                "/" + endpoint,
                HttpMethod.OPTIONS,
                new HttpEntity<>(null, new HttpHeaders()),
                String.class
        );

        List<String> options = Arrays.stream(response.getHeaders()
                        .get("Allow")
                        .get(0)
                        .split(","))
                .toList();

        Assertions.assertTrue(options.contains(method.toUpperCase()));
    }

    @Given("I am logged in for \"bankaccounts\" endpoint")
    public void iAmLoggedInBankAccount() {
        this.loggedIn = true;
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