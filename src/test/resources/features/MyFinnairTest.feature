Feature: Test Finnair site DB integration

  @severity=minor
  Scenario: Search articles on Finnair
    Given I load google page
    Given I accept cookies if present
    Given I search for "Finnair"
    Given I load Finnair page
    Given I accept Finnair cookies
    When I search for "Barcelona" on Finnair page
    Then I can see at least 2 entries containing "Barcelona"

    @severity=normal
  Scenario: Search Finland destinations to store them in DB
    Given I load google page
    Given I accept cookies if present
    Given I search for "Finnair"
    Given I load Finnair page
    When I navigate to Finland destinations page
    Then I verify if destinations are stored in db with correct prices

      @severity=critical
  Scenario: Fail logging in
    Given I load google page
    Given I accept cookies if present
    Given I search for "Finnair"
    Given I load Finnair page
    Given I open Login modal
    When I interact with Login fields
    Then I get error messages
