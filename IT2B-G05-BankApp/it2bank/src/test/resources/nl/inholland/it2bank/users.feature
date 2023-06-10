Feature: User CRUD scenarios

    Scenario: Getting all users
        Given The endpoint for "users" is available for method "GET"
        When I retrieve all users
        Then I should receive all users

    Scenario: Registering a user
        Given the email written is not already taken
        When I provide details
        Then I retrieve user