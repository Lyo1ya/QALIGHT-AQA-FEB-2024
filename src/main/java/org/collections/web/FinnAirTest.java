package org.collections.web;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class FinnAirTest {
    public static WebDriver driver = new ChromeDriver();
    private static final String SEARCH_BAR_XPATH = "//div/fin-site-search-header-widget-starter/form/button[1]";
    private static final String SEARCH_INPUT_XPATH =
            "/html/body/fin-app/div/fin-set-language/span/fin-layout/fin-header/header/div[3]/div/div/div[3]/div/fin-site-search-header-widget-starter/form/input";

    public static void main(String[] args) {

        try {
            driver.manage().window().maximize();
            driver.get("https://www.finnair.com/en");
            List<WebElement> allowAllButton = driver.findElements(By.id("allow-all-btn"));
            if (!allowAllButton.isEmpty()) {
                allowAllButton.get(1).click();
            }
            WebElement searchLabel = driver.findElement(By.xpath(SEARCH_BAR_XPATH));
            searchLabel.click();
            WebElement searchBar = driver.findElement(By.xpath(SEARCH_INPUT_XPATH));
            searchBar.sendKeys("Barcelona");
            searchBar.sendKeys(Keys.ENTER);
            List<WebElement> searchHeaders = new WebDriverWait(driver, Duration.ofSeconds(5L))
                    .until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//a/div/em"), 2));
            for (WebElement e : searchHeaders) {
                if (e.getText().contains("Barcelona")) {
                    System.out.println("Barcelona found!");
                    break;
                }
            }
        } finally {
            driver.quit();
        }
    }
}


