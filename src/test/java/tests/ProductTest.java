package tests;
import Pages.ProductPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class ProductTest extends Testbase {
    ProductPage productPage;
    @BeforeMethod
    public void navigateToProductsPage() {
        productPage = new ProductPage(driver);
        driver.navigate().to("https://automationexercise.com/products");
    }
    //TestCase 1 :
    @Test
    public void verifyUserCanSearchForProductWithFullName() {
        String expectedProductName = "Winter Top";
        productPage.searchForProduct(expectedProductName);
        String actualProductName = productPage.getDisplayedProductName();  //return actual product name that appear as result of search
        System.out.println("Actual result is: " + actualProductName);
        Assert.assertEquals(actualProductName,expectedProductName);
    }
    //TestCase 2 :
    @Test
    public void verifyUserCanSearchForProductWithPartialName() {
        String PartialProductName = "Top";
        productPage.searchForProduct(PartialProductName);

        boolean isRelevant = productPage.areAllResultsRelevant(PartialProductName);
        Assert.assertTrue(isRelevant);
    }
    //TestCase 3:
    @Test
    public void verifySearchWithEmptyInput() {
        productPage.searchForProduct("");
        String expectedMassage="You Should Enter A product name ";
        String actualMassage=productPage.getCategoryHeaderText() ;
        Assert.assertEquals(expectedMassage,actualMassage);
    }
    //TestCase 4 :
    @Test
    public void verifySearchForNonExistingProduct() {
        String nonExistingProduct = "Socks";
        productPage.searchForProduct(nonExistingProduct);
        boolean isEmpty = productPage.isProductListEmpty();
        System.out.println("The product list empty for '" + nonExistingProduct  + isEmpty);
        Assert.assertTrue(isEmpty);
    }
    // TestCase 5 :
    @Test
    public void verifyUserCanNavigateToWomenDressCategory() {
        productPage.expandWomenCategory();
        productPage.clickOnWomenDressSubCategory();
        String expectedHeader = "WOMEN - DRESS PRODUCTS";
        String actualHeader = productPage.getCategoryHeaderText();
        System.out.println("Actual Header: " + actualHeader);
        Assert.assertEquals(actualHeader, expectedHeader);
    }
    // TestCase 6:
    @Test
    public void verifyUserCanNavigateToWomenTopsCategory() {
        productPage.expandWomenCategory();
        productPage.clickOnWomenTopsSubCategory();
        String expectedHeader = "WOMEN - TOPS PRODUCTS";
        String actualHeader = productPage.getCategoryHeaderText();
        System.out.println("Actual Header: " + actualHeader);
        Assert.assertEquals(actualHeader, expectedHeader);
    }
    // TestCase 7:
    @Test
    public void verifyUserCanNavigateToWomenSareeCategory() {
        productPage.expandWomenCategory();
        productPage.clickOnWomenSareeSubCategory();
        String expectedHeader = "WOMEN - SAREE PRODUCTS";
        String actualHeader = productPage.getCategoryHeaderText();
        System.out.println("Actual Header: " + actualHeader);
        Assert.assertEquals(actualHeader, expectedHeader);
    }
    // TestCase 8:
    @Test
    public void verifyUserCanNavigateToKidsDressCategory() {
        productPage.expandKidsCategory();
        productPage.clickOnKidsDressSubCategory();
        String expectedHeader = "KIDS - DRESS PRODUCTS";
        String actualHeader = productPage.getCategoryHeaderText();
        System.out.println("Actual Header: " + actualHeader);
        Assert.assertEquals(actualHeader, expectedHeader);
    }
    // TestCase 9:
    @Test
    public void verifyUserCanNavigateToKidsShirtsCategory() {
        productPage.expandKidsCategory();
        productPage.clickOnKidsShirtsSubCategory();
        String expectedHeader = "KIDS - TOPS & SHIRTS PRODUCTS";
        String actualHeader = productPage.getCategoryHeaderText();
        System.out.println("Actual Header: " + actualHeader);
        Assert.assertEquals(actualHeader, expectedHeader);
    }
    // TestCase 10:
    @Test
    public void verifyUserCanNavigateToMenShirtsCategory() {
        productPage.expandMenCategory();
        productPage.clickOnMenShirtsSubCategory();
        String expectedHeader = "MEN - TSHIRTS PRODUCTS";
        String actualHeader = productPage.getCategoryHeaderText();
        System.out.println("Actual Header: " + actualHeader);
        Assert.assertEquals(actualHeader, expectedHeader);
    }
    // TestCase 11:
    @Test
    public void verifyUserCanNavigateToMenJeansCategory() {
        productPage.expandMenCategory();
        productPage.clickOnMenJeansSubCategory();
        String expectedHeader = "MEN - JEANS PRODUCTS";
        String actualHeader = productPage.getCategoryHeaderText();
        System.out.println("Actual Header: " + actualHeader);
        Assert.assertEquals(actualHeader, expectedHeader);
    }
    @DataProvider(name = "brandDataProvider")
    public Object[][] getBrandNames() {
        return new Object[][] {
                {"Polo"},
                {"H&M"},
                {"Mast & Harbour"},
                {"Madame"},
                {"Babyhug"},
                {"Allen Solly Junior"},
                {"Kookie Kids"},
                {"Biba"}
        };
    }
    // TestCase 12:
    @Test(dataProvider = "brandDataProvider")
    public void verifyAllBrandsProductCount(String brandName) {
        int expectedCount = productPage.getExpectedCountFromSidebar(brandName);
        int actualCount = productPage.getActualProductCountAfterClickingBrand(brandName);
        System.out.println("Testing Brand: " + brandName + " | Expected: " + expectedCount + " | Actual: " + actualCount);
        Assert.assertEquals(actualCount, expectedCount);
    }
    // TestCase 13:
    @Test
    public void verifyAddingMultipleProductsToCart() {
        int expected = 3;
        productPage.addMultipleProductsToCart(expected);
        productPage.clickViewCart();
        Assert.assertEquals(productPage.getActualCartCount(), expected);
    }
    // TestCase 14:
    @Test
    public void verifyMultipleViewProductButtons() {
        int numberOfProductsToTest = 3;

        for (int i = 0; i < numberOfProductsToTest; i++) {
            productPage.clickViewProductByIndex(i);
            Assert.assertTrue(driver.getCurrentUrl().contains("product_details"));
            productPage.navigateBack();
        }
    }
    // TestCase 15:
    @Test
    public void verifyCartModalFlow() {
        productPage.addMultipleProductsToCart(3);
        productPage.clickModalViewCart();
        boolean isCartPage = productPage.wait.until(ExpectedConditions.urlContains("view_cart"));
        Assert.assertTrue(isCartPage);
        productPage.navigateBack();
        boolean isBackToProducts = productPage.wait.until(ExpectedConditions.urlContains("products"));
        Assert.assertTrue(isBackToProducts);
        productPage.addMultipleProductsToCart(3);
        productPage.clickContinueShopping();
        Assert.assertTrue(driver.getCurrentUrl().contains("products"));
    }

}
