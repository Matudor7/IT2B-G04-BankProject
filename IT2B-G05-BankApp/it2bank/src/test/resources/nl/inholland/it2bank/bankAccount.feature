Feature: Bank Account CRUD scenarios

    Scenario: Getting all bank accounts
        Given I am logged in for bank accounts
        And The endpoint "bankaccounts" is available for method "GET"
        When I retrieve all bank accounts
        Then I should receive 5 accounts

    Scenario: Creating a new a bank account
        Given I am logged in for bank accounts
        And The endpoint "bankaccounts" is available for method "POST"
        When I provide registration form with bank account details
        Then I should be getting status 201

    Scenario: Updating a bank account by IBAN
        Given I am logged in for bank accounts
        When I update the bank account with IBAN "NL01INHO0000000030" using the following details
        Then I should receive a status of 200
        And the updated bank account details should match the provided values


