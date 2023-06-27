Feature: Bank Account CRUD scenarios

    Scenario: Getting all bank accounts
        Given The endpoint "bankaccounts" is available for method "GET"
        And I am logged in for "accounts" endpoint
        When I retrieve all bank accounts
        Then I should receive 3 accounts

    Scenario: Creating a new a bank account
        Given The endpoint "bankaccounts" is available for method "POST"
        When I provide registration form with bank account details
        Then I should be getting status 201
