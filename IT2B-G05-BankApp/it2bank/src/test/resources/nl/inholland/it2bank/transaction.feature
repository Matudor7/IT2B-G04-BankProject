Feature: Transactions CRUD scenarios

  Scenario: Getting all transactions
    Given The endpoint named "transactions" is available for method "GET"
    When I retrieve all transactions
    Then I should receive 5 transactions

  Scenario: Creating a new a transaction
    Given The endpoint named "transactions" is available for method "POST"
    When I provide registration form with transaction details
    Then I should be getting status 201



