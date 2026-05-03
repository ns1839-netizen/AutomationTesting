package tests;

import Pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductTest extends Testbase {


    ProductPage productPage;
    @Test
    public void verifyUserCanSearchForProductWithFullNameً() {

        productPage = new ProductPage(driver);
        driver.navigate().to("https://automationexercise.com/products");
        String expectedProductName = "Winter Top";
        String actualProductName = productPage.getDisplayedProductName();
        System.out.println("Actual result is: " + actualProductName);
        productPage.searchForProduct(expectedProductName);
        Assert.assertEquals(actualProductName,expectedProductName);


    }
    @Test
    public void verifyUserCanSearchForProductWithParitalNameً() {

        productPage = new ProductPage(driver);
        driver.navigate().to("https://automationexercise.com/products");
        String PartialProductName = "Top";
        productPage.searchForProduct(PartialProductName);
        boolean isRelevant = productPage.areAllResultsRelevant(PartialProductName);
        Assert.assertTrue(isRelevant);


    }

}
