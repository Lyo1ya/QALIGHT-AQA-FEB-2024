package org.web.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.collections.web.dto.PersonDto;
import org.collections.web.util.CucumberContainer;
import org.collections.web.util.DbUtil;
import org.testng.asserts.SoftAssert;

import java.util.Map;

import static org.testng.Assert.assertNotNull;
import static org.web.cucumber.FinnairSteps.finnAirPage;

public class DbSteps {
    private final static String INSERT_BASE =
            "INSERT INTO DestinationPrices (City, Price) " +
                    "VALUES ('%s', '%f')";
    private final static String UPDATE_BASE = "UPDATE DestinationPrices SET Price = '%f' WHERE City = '%s'";

    @Given("I pick a random person from DB as {string}")
    public void pickRandomPerson(String alias){
        PersonDto randomPerson = PersonDto.getRandomPersonFromDB();
        assertNotNull(randomPerson, "Failed to extract random person from DB");

        CucumberContainer.map.put(alias,
                randomPerson.getName().getFirst() + " " + randomPerson.getName().getLast());
    }

    @Then("I verify if destinations are stored in db with correct prices")
    public void assertPricesAreEqual() {
        Map<String, Float> destPrice = finnAirPage.getDestinationsCities(4);
        SoftAssert softAssert = new SoftAssert();
        for (Map.Entry<String, Float> e : destPrice.entrySet()) {
            Map<String, Float> cityPriceFromDb = DbUtil.getCityPriceFromDb(e.getKey());
            Float dbPrice = cityPriceFromDb.get(e.getKey());
            if (DbUtil.getCityPriceFromDb(e.getKey()).isEmpty()) {
                String destFromMap = String.format(INSERT_BASE, e.getKey(), e.getValue());
                DbUtil.storeInDB(destFromMap);
                System.out.println("Stored a new destination: " + e.getKey() + " with price " + e.getValue());
            } else if (e.getValue().equals(dbPrice)) {
                System.out.println(e.getKey() + " is already in db with correct price: " + e.getValue());
            } else {
                String updateDest = String.format(UPDATE_BASE, e.getValue(), e.getKey());
                DbUtil.storeInDB(updateDest);
                System.out.println(e.getKey() + " is updated in db with the new price: " + e.getValue());
                softAssert.fail("Price is updated for " + e.getKey() + " to new price: " + e.getValue());
            }
        }
        softAssert.assertAll();
    }
}
