package org.web.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.collections.web.page.FinnAirPage;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FinnairSteps {

    public static FinnAirPage finnAirPage;

    @Given("I load Finnair page")
    public void loadFinnairPage() {
        finnAirPage.setLanguage();
    }

    @Given("I accept Finnair cookies")
    public void acceptFinnairCookiesIfPresent() {
        finnAirPage.acceptCookiesIfAvailable();
        finnAirPage.closeFinnairPlusModal();
    }

    @When("I navigate to Finland destinations page")
    public void navigateToDestinations() {
        finnAirPage.getDestinationsTab();
        finnAirPage.isPageLoaded("https://www.finnair.com/en/destinations?country=fi");
    }

    @Given("I open Login modal")
    public void openLoginModal() {
        finnAirPage.openLoginModal();
    }

    @When("I interact with Login fields")
    public void interactWithLoginFields() {
        finnAirPage.loginFieldInterracting();
    }

    @Then("I get error messages")
    public void getLoginErrorMessages() {
        String loginErrorMessage = finnAirPage.getErrorMessage().get(0).getText();
        String passErrorMessage = finnAirPage.getErrorMessage().get(1).getText();
        assertTrue(loginErrorMessage.contains("Email address or Finnair Plus number is required"),
                "Expected 'Email address or Finnair Plus number is required' but got " + loginErrorMessage);
        assertTrue(passErrorMessage.contains("Password is required"), "Expected 'Password is required' but got " + passErrorMessage);
    }
    @When("I search for {string} on Finnair page")
    public void searchForCitiesArticles(String textValue) {
        finnAirPage.setSearch();
        finnAirPage.performSearch(textValue);
    }

    @Then("I can see at least {int} entries containing {string}")
    public void getSearchHeaders(int number, String textValue) {
        boolean barcelonaFound = false;
        for (WebElement e : finnAirPage.searchHeaders()) {
            if (e.getText().contains(textValue)) {
                System.out.println(textValue + " found!");
                barcelonaFound = true;
                break;
            }
        }
        assertTrue(barcelonaFound,
                textValue + " was not found in search for " + textValue);
    }

}
