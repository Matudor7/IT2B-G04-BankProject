Feature: User CRUD scenarios

    Scenario: Getting all users
        Given The endpoint for "users" is available for method "GET"
        And I am logged in
        When I retrieve all users
        Then I should receive 4 users

    Scenario: Registering a user
        Given The endpoint for "users" is available for method "POST"
        When I provide registration form with user details
        Then I should get status 201

    Scenario: Changing a user
        Given I am logged in
        When I provide an edited user
        Then I should get status 200

    Scenario: Deleting a user
        Given I am logged in
        When I want to delete user with ID 1
        Then I should get status 204

    Scenario: Trying to delete an employee
        Given I am logged in
        When I want to delete user with ID 3
        Then I should get status 400