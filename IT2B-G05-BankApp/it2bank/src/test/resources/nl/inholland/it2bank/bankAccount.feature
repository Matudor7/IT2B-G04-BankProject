Feature: Bank Account CRUD scenarios

    Scenario: Getting all bank accounts
        Given The endpoint "bankaccounts" is available for method "GET"
        When I retrieve all bank accounts
        Then I should receive 5 accounts

    Scenario: Creating a new a bank account
        Given The endpoint "bankaccounts" is available for method "POST"
        When I provide registration form with bank account details
        Then I should be getting status 201

    Scenario: Updating a bank account by IBAN
        Given The endpoint "/bankaccounts/{iban}" is available for method "PUT"
        When I update the bank account with IBAN "{iban}" using the following details:
            | Field           | Value       |
            | iban            | 1234567890  |
            | ownerId         | 1           |
            | statusId        | 2           |
            | balance         | 1000.0      |
            | absoluteLimit   | 5000        |
            | typeId          | 3           |
        Then I should receive a status of 200
        And the updated bank account details should match the provided values


