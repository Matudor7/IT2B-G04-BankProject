Feature: User CRUD scenarios

    Scenario: Getting all users
        Given The endpoint for "users" is available for method "GET"
        When I retrieve all users
        Then I should receive 4 users

    Scenario: Registering a user
        Given The endpoint for "users" is available for method "POST"
        When I provide registration form with user details
        Then I should get status 201