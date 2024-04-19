package org.collections.web.page;

import dev.failsafe.internal.util.Assert;
import org.collections.web.util.DbUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.*;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Float.parseFloat;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class FinnAirPage extends AbstractPage {

    private final By ENG_LANGUAGE_BUTTON = By.xpath("//ul/li[33]/div[2]/a");
    private final By SEARCH_BAR = By.xpath("//div/fin-site-search-header-widget-starter/form/button[1]/span[1]");
    private final By SEARCH_INPUT = By.xpath("//div/fin-site-search-header-widget-starter/form/input");
    private final By LOGIN_LABEL = By.xpath("//div[2]/fin-login-button");
    private final By LOGIN_CHECKBOX = By.xpath("//fcom-checkbox");
    private final By DESTINATIONS_FINLAND_BUTTON = By.xpath("//*[text()=' Finland ']");
    public static final String FINNAIR_URL = "https://www.finnair.com/";
    private Connection connection = null;
    private Statement statement = null;
    private final static String INSERT_BASE =
            "INSERT INTO DestinationPrices (City, Price) " +
                    "VALUES ('%s', '%f')";

    public final static String UPDATE_BASE = "UPDATE DestinationPrices SET Price = '%f' WHERE City = '%s'";
    public FinnAirPage(WebDriver driver) {
        super(driver, FINNAIR_URL);
    }

    public void setLanguage() {
        WebElement engLanguageButton = driver.findElement(ENG_LANGUAGE_BUTTON);
        engLanguageButton.click();
    }

    public void acceptCookiesIfAvailable() {
        List<WebElement> allowAllButton = new WebDriverWait(driver, Duration.ofSeconds(20L))
                .until(numberOfElementsToBeMoreThan(By.id("allow-all-btn"), 0));
        if (!allowAllButton.isEmpty()) {
            allowAllButton.get(0).click();
        }
    }

    public void setSearch() {
        WebElement searchLabel = new WebDriverWait(driver, Duration.ofSeconds(5L)).until(presenceOfElementLocated(SEARCH_BAR));
        searchLabel.click();
    }

    public void performSearch(String value) {
        WebElement searchBar = driver.findElement(SEARCH_INPUT);
        searchBar.sendKeys(value);
        searchBar.sendKeys(Keys.ENTER);
    }

    public List<WebElement> searchHeaders() {
        return new WebDriverWait(driver, Duration.ofSeconds(5L))
                .until(numberOfElementsToBeMoreThan(By.xpath("//a/div/em"), 2));
    }

    public void closeFinnairPlusModal() {
        WebElement finnairPlusModalCloseButton = driver.findElement(By.className("tooltip-close-x"));
        finnairPlusModalCloseButton.click();
    }
    public void openLoginModal() {
        WebElement loginLabel = driver.findElement(LOGIN_LABEL);
        loginLabel.click();
        WebElement loginModal = driver.findElement(By.className("login-dialog"));
        Assert.isTrue(loginModal.isDisplayed(), "Login modal is not displayed");
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

    public void getDestinationsTab() {
        WebElement menuButton = driver.findElement(By.className("icon-button"));
        menuButton.click();
        WebElement destinationsButton = driver.findElements(By.className("navigation-category")).get(0);
        destinationsButton.click();
        WebElement destinationsInFinland = new WebDriverWait(driver, Duration.ofSeconds(10L))
                .until(presenceOfElementLocated(DESTINATIONS_FINLAND_BUTTON));
        destinationsInFinland.click();
    }

    public Map<String, Float> getDestinationsCities(int amount) {
        Map<String, Float> destinations = new HashMap<>();
        List<WebElement> city = new WebDriverWait(driver, Duration.ofSeconds(5L))
                .until(presenceOfAllElementsLocatedBy(By.xpath("//a/h3")));
        List<WebElement> price = driver.findElements(By.xpath("//section[2]/a/span"));
        for (int i = 1; i <= amount; i++) {
            destinations.put(city.get(i).getText(), parseFloat(price.get(i).getText().substring(1)));
        }
        return destinations;
    }

    public void getDBConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/db",
                "user",
                "password"
        );
        statement = connection.createStatement();
    }

    public void disonnectFromDB() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public void storeInDb(String key, Float value) {
        DbUtil.storeInDB(INSERT_BASE);
    }

}
