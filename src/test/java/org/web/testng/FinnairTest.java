package org.web.testng;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FinnairTest extends AbstractNGTest {

    //TODO: OPTIONAL
    //TODO: Move Finnair test to testNG
    //TODO: Open finnair page through google
    //TODO: Add assertions

    @Test
    public void searchOnFinnairTest() {
        googlePage.setSearchText("finnair");
        googlePage.performSearch();
        googlePage.getSearchHeaders().get(0).click();
        finnAirPage.setLanguage();
        finnAirPage.acceptCookiesIfAvailable();
        finnAirPage.setSearch();
        String expectedSearchValue = "Barcelona";
        finnAirPage.performSearch("Barcelona");
        boolean barcelonaFound = false;
        for (WebElement e : finnAirPage.searchHeaders()) {
            if (e.getText().contains("Barcelona")) {
                System.out.println("Barcelona found!");
                barcelonaFound = true;
                break;
            }
        }
        assertTrue(barcelonaFound,
                "Barcelona was not found in search for " + expectedSearchValue);
    }

    @Test
    public void finnairLoginModalTest() {
        googlePage.setSearchText("finnair");
        googlePage.performSearch();
        googlePage.getSearchHeaders().get(0).click();
        finnAirPage.setLanguage();
        finnAirPage.isPageLoaded("https://www.finnair.com/en");
        finnAirPage.acceptCookiesIfAvailable();
        finnAirPage.openLoginModal();
        System.out.println("blabla");
        WebElement loginModal = driver.findElement(By.className("login-dialog"));
        assertTrue(loginModal.isDisplayed(), "Login modal is not displayed");
        finnAirPage.loginFieldInterracting();
        String loginErrorMessage = finnAirPage.getErrorMessage().get(0).getText();
        String passErrorMessage = finnAirPage.getErrorMessage().get(1).getText();
        assertTrue(loginErrorMessage.contains("Email address or Finnair Plus number is required"),
                "Expected 'Email address or Finnair Plus number is required' but got " + loginErrorMessage);
        assertTrue(passErrorMessage.contains("Password is required"), "Expected 'Password is required' but got " + passErrorMessage);
        System.out.println("blabla");
    }
}
