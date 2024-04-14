package org.web.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.collections.web.page.FinnAirPage;

public class FinnairSteps {

    public static FinnAirPage finnAirPage;

    @Given("I load Finnair page")
    public void loadFinnairPage() {
        finnAirPage.setLanguage();
        finnAirPage.acceptCookiesIfAvailable();
    }

    @When("I navigate to Finland destinations page")
    public void navigateToDestinations() {
        finnAirPage.getDestinationsTab();
        finnAirPage.isPageLoaded("https://www.finnair.com/en/destinations?country=fi");
    }

}
