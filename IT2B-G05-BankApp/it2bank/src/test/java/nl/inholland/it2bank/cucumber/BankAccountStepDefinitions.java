package nl.inholland.it2bank.cucumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import nl.inholland.it2bank.config.SSLUtils;
import nl.inholland.it2bank.cucumber.BaseStepDefinitions;
import nl.inholland.it2bank.model.BankAccountModel;
import nl.inholland.it2bank.model.dto.BankAccountDTO;
import nl.inholland.it2bank.model.dto.LoginDTO;
import nl.inholland.it2bank.model.dto.TokenDTO;
import nl.inholland.it2bank.service.BankAccountService;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountStepDefinitions extends BaseStepDefinitions {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;
    private ResponseEntity<String> response;

    private String token;
    private BankAccountService bankAccountService;

    private HttpHeaders httpHeaders = new HttpHeaders();
    private boolean loggedIn;

    @SneakyThrows
    @Before
    public void init() {
        SSLUtils.turnOffSslChecking();

    }
    @Given("I am logged in for bank accounts")
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

    @Given("The endpoint {string} is available for method {string}")
    public void theEndpointIsAvailableForBankAccounts(String endpoint, String method) throws JsonProcessingException {
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
        httpHeaders.add("Authorization", "Bearer " + token);
        response = restTemplate.exchange(
                "/bankaccounts",
                HttpMethod.GET,
                new HttpEntity<>(null, httpHeaders),
                String.class
        );
    }

    @Then("I should receive {int} accounts")
    public void iShouldReceiveAccounts(int expectedCount){
        String body = (String) response.getBody();
        int actualAmount = JsonPath.read(body, "$.size()");
        Assertions.assertEquals(expectedCount, actualAmount);
    }

    @Then("I should be getting status {int}")
    public void iShouldGetStatusForBankAccount(int expectedStatusCode) throws JsonProcessingException {
        httpHeaders.clear();
        httpHeaders.add("Authorization", "Bearer " + token);
        int actualStatusCode = response.getStatusCode().value();
        assertEquals(expectedStatusCode,actualStatusCode);
    }

    @When("I provide registration form with bank account details")
    public void iProvideRegistrationFormWithBankAccountDetails() throws JsonProcessingException {
        httpHeaders.clear();
        httpHeaders.add("Authorization", "Bearer " + token);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String body = """
                {
                  "ownerId": 2,
                  "statusId": 2,
                  "balance": 550,
                  "absoluteLimit": 500,
                  "typeId": 1
                }""";
        response = restTemplate.exchange(
                "/bankaccounts",
                HttpMethod.POST,
                new HttpEntity<>(body, httpHeaders),
                String.class
        );
    }

   @When("I update the bank account with IBAN {string} using the following details")
    public void updateBankAccount(String iban) {
       httpHeaders.clear();
       httpHeaders.add("Authorization", "Bearer " + token);
       httpHeaders.setContentType(MediaType.APPLICATION_JSON);

       httpHeaders.setContentType(MediaType.APPLICATION_JSON);
       String body = """
                {
                  "iban": "NL01INHO0000000030",
                  "ownerId": 2,
                  "statusId": 2,
                  "balance": 0,
                  "absoluteLimit": 500,
                  "typeId": 1
                }""";

        response = restTemplate.exchange(
                "/bankaccounts/" + iban,
                HttpMethod.PUT,
                new HttpEntity<>(body, httpHeaders),
                String.class
        );
    }

    @Then("I should receive a status of {int}")
    public void verifyResponseStatus(int expectedStatus) {
        httpHeaders.add("Authorization", "Bearer " + token);
        assertEquals(expectedStatus, response.getStatusCodeValue());
    }

    @Then("the updated bank account details should match the provided values")
    public void verifyUpdatedBankAccountDetails() throws JsonProcessingException {
        BankAccountModel expectedBankAccount = new BankAccountModel();
        // Set the expected values for the bank account
        expectedBankAccount.setIban("NL01INHO0000000030");
        expectedBankAccount.setOwnerId(2L);
        expectedBankAccount.setStatusId(2);
        expectedBankAccount.setBalance(0.0);
        expectedBankAccount.setAbsoluteLimit(500);
        expectedBankAccount.setTypeId(1);

        String responseBody = response.getBody();
        BankAccountModel updatedBankAccount = mapper.readValue(responseBody, BankAccountModel.class);

        assertEquals(expectedBankAccount.getIban(), updatedBankAccount.getIban());
        assertEquals(expectedBankAccount.getOwnerId(), updatedBankAccount.getOwnerId());
        assertEquals(expectedBankAccount.getStatusId(), updatedBankAccount.getStatusId());
        assertEquals(expectedBankAccount.getBalance(), updatedBankAccount.getBalance());
        assertEquals(expectedBankAccount.getAbsoluteLimit(), updatedBankAccount.getAbsoluteLimit());
        assertEquals(expectedBankAccount.getTypeId(), updatedBankAccount.getTypeId());
    }
}