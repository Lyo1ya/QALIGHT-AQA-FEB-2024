package org.collections.web.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class FinnAirPage extends AbstractPage {

    private final By ENG_LANGUAGE_BUTTON = By.xpath("//ul/li[33]/div[2]/a");
    private final By SEARCH_BAR = By.xpath("//div/fin-site-search-header-widget-starter/form/button[1]");
    private final By SEARCH_INPUT = By.xpath("//div/fin-site-search-header-widget-starter/form/input");
    private final By LOGIN_LABEL = By.xpath("//div[2]/fin-login-button");
    private final By LOGIN_CHECKBOX = By.xpath("//fcom-checkbox");
    public static final String FINNAIR_URL = "https://www.finnair.com/";

    public FinnAirPage(WebDriver driver) {
        super(driver, FINNAIR_URL);
    }

    public void setLanguage() {
        WebElement engLanguageButton = driver.findElement(ENG_LANGUAGE_BUTTON);
        engLanguageButton.click();
    }

    public void acceptCookiesIfAvailable() {
        List<WebElement> allowAllButton = new WebDriverWait(driver, Duration.ofSeconds(20L))
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(By.id("allow-all-btn"), 0));
        if (!allowAllButton.isEmpty()) {
            allowAllButton.get(0).click();
        }
    }

    public void setSearch() {
        WebElement searchLabel = driver.findElement(SEARCH_BAR);
        searchLabel.click();
    }

    public void performSearch(String value) {
        WebElement searchBar = driver.findElement(SEARCH_INPUT);
        searchBar.sendKeys(value);
        searchBar.sendKeys(Keys.ENTER);
    }

    public List<WebElement> searchHeaders() {
        return new WebDriverWait(driver, Duration.ofSeconds(5L))
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//a/div/em"), 2));
    }

    public void openLoginModal() {
        WebElement loginLabel = driver.findElement(LOGIN_LABEL);
        loginLabel.click();
    }

    public void loginFieldInterracting() {
        WebElement loginInput = driver.findElement(By.name("member"));
        WebElement passInput = driver.findElement(By.name("pwd"));
        WebElement loginCheckbox = driver.findElement(LOGIN_CHECKBOX);
        loginInput.click();
        passInput.click();
        loginCheckbox.click();
    }

    public List<WebElement> getErrorMessage() {
        return driver.findElements(By.className("error-label"));
    }
}
