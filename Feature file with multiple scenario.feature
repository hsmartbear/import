Feature: User Login

  As a registered user
  I want to log into the application
  So that I can access my dashboard

  Background:
    Given the user is on the login page

  Scenario: Successful login with valid credentials
    When the user enters a valid username and password
    And clicks on the login button
    Then the user should be redirected to the dashboard
    And a welcome message should be displayed

  Scenario: Unsuccessful login with invalid credentials
    When the user enters an invalid username and password
    And clicks on the login button
    Then an error message should be displayed
    And the user should remain on the login page

  Scenario: Login attempt with empty fields
    When the user leaves the username and password fields blank
    And clicks on the login button
    Then the user should see a validation message