package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductPage extends PageBase{
    public ProductPage(WebDriver driver) {
        super(driver);
    }
    @FindBy(id = "search_product")
    public WebElement searchField;
    @FindBy(id = "submit_search")
    public WebElement searchButton;
    @FindBy(xpath = "//*[text()='Winter Top']")
    public WebElement productNameResult;
    @FindBy(xpath = "//div[@class='productinfo text-center']/p")
    public List<WebElement> allProductNames;

    public void searchForProduct(String productName) {
        searchField.clear();
        searchField.sendKeys(productName);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();

    }
    public boolean areAllResultsRelevant(String partialName) {
        if (allProductNames.isEmpty()) {
            return false;
        }

        for (WebElement product : allProductNames) {
            String name = product.getText().toLowerCase();
            if (!name.contains(partialName.toLowerCase())) {
                System.out.println("Found irrelevant product: " + name);
                return false;
            }
        }
        return true;
    }
    public String getDisplayedProductName() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement firstResult = wait.until(ExpectedConditions.visibilityOf(productNameResult));

        String name = firstResult.getText();
        System.out.println("DEBUG: Text found on page is -> " + name);
        return name;
    }
    }




