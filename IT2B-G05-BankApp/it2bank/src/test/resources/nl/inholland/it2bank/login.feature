Feature: Logging in scenarios

  Scenario: Log in with valid information
    Given I have valid login information for all data
    When I call the login endpoint
    Then I receive a token

  Scenario: Log in with correct username but wrong password
    Given I have invalid username but valid password
    When I call the login endpoint
    Then I should get http status 403

  Scenario: Log in with valid username but invalid password
    Given I have valid username but invalid password
    When I call the login endpoint
    Then I should get http status 403