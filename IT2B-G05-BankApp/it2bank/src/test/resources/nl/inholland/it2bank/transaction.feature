Feature: Transactions CRUD scenarios

  Scenario: Getting all transactions
    Given I am logged in for transactions
    And The endpoint named "transactions" is available for method "GET"
    When I retrieve all transactions
    Then I should receive 12 transactions

  Scenario: Creating a new a transaction
    Given I am logged in for transactions
    And The endpoint named "transactions" is available for method "POST"
    When I provide registration form with transaction details
    Then I should receive a status of transaction 201



