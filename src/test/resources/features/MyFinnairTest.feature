Feature: Test Finnair site DB integration

Scenario: Search Finland destinations to store them in DB
Given I load google page
Given I accept cookies if present
Given I search for "Finnair"
Given I load Finnair page
When I navigate to Finland destinations page
Then I verify if destinations are stored in db with correct prices
