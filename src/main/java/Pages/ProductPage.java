package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import javax.naming.Name;
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
    @FindBy(xpath = "//div[@class='productinfo text-center']/p")
    public List<WebElement> results;
    @FindBy(xpath = "//h2[@class='title text-center']")
    public WebElement categoryHeader;
    @FindBy(xpath = "//div[@class='single-products']")
    public List<WebElement> displayedProducts;
    @FindBy(xpath = "//div[@class='productinfo text-center']//a[contains(@class,'add-to-cart')]")
    public List<WebElement> allAddToCartButtons;
    @FindBy(xpath = "//button[text()='Continue Shopping']")
    public WebElement continueShoppingBtn;
    @FindBy(xpath = "//u[text()='View Cart']")
    public WebElement viewCartLink;
    @FindBy(xpath = "//div[@class='choose']//a[contains(text(),'View Product')]")
    public List<WebElement> allViewProductButtons;
    public void searchForProduct(String productName) {
        searchField.clear();
        searchField.sendKeys(productName);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();

    }
    public boolean areAllResultsRelevant(String partialName) {
        if (allProductNames.isEmpty()) {
            return false;
        }   //check if no products return

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
        WebElement firstResult = wait.until(ExpectedConditions.visibilityOf(productNameResult));
        String name = firstResult.getText();
        System.out.println("WebElement found on page is : " + firstResult);
        System.out.println("WebElement found on page is : " + name);
        return name;
    }
    public boolean isProductListEmpty() {
        return results.isEmpty();
    }
    public void expandWomenCategory() {
        WebElement plusIcon = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@href='#Women']//span[@class='badge pull-right']/i")));
        js.executeScript("arguments[0].click();", plusIcon);
    }
    public void clickOnWomenDressSubCategory() {
        WebElement dressLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath ("//div[@id='Women']//a[contains(text(),'Dress')]")));
        js.executeScript("arguments[0].click();", dressLink);
    }
    public String getCategoryHeaderText() {
        return wait.until(ExpectedConditions.visibilityOf(categoryHeader)).getText();
    }
    public void clickOnWomenTopsSubCategory() {
        WebElement topsLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@id='Women']//a[contains(text(),'Tops')]")));
        js.executeScript("arguments[0].click();", topsLink);
    }
    public void clickOnWomenSareeSubCategory() {
        WebElement sareeLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='Women']//a[contains(text(),'Saree')]")));
        js.executeScript("arguments[0].click();", sareeLink);
    }
    public void expandKidsCategory() {
        WebElement plusIcon = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@href='#Kids']//span[@class='badge pull-right']/i")));
        js.executeScript("arguments[0].click();", plusIcon);
    }
    public void clickOnKidsDressSubCategory() {
        WebElement dressLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath ("//div[@id='Kids']//a[contains(text(),'Dress')]")));
        js.executeScript("arguments[0].click();", dressLink);
    }
    public void clickOnKidsShirtsSubCategory() {
        WebElement shirtsLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath ("//div[@id='Kids']//a[contains(text(),'Tops & Shirts')]")));
        js.executeScript("arguments[0].click();", shirtsLink);
    }
    public void expandMenCategory() {
        WebElement plusIcon = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@href='#Men']//span[@class='badge pull-right']/i")));
        js.executeScript("arguments[0].click();", plusIcon);
    }
    public void clickOnMenShirtsSubCategory() {
        WebElement shirtsLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath ("//div[@id='Men']//a[contains(text(),'Tshirts')]")));
        js.executeScript("arguments[0].click();", shirtsLink);
    }
    public void clickOnMenJeansSubCategory() {
        WebElement JeansLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath ("//div[@id='Men']//a[contains(text(),'Jeans')]")));
        js.executeScript("arguments[0].click();", JeansLink);
    }
    public int getExpectedCountFromSidebar(String brandName) {
               String xpathExpression = "//a[contains(translate(., 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), '"
                + brandName.toUpperCase() + "')]//span";
        WebElement brandBadge = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathExpression)));
        String sidebarCountText = brandBadge.getText().replaceAll("[()]", "").trim();
        return Integer.parseInt(sidebarCountText);
    }
    public int getActualProductCountAfterClickingBrand(String brandName) {
        WebElement brandLink = driver.findElement(By.xpath("//a[contains(@href, '/brand_products/" + brandName + "')]"));
        js.executeScript("arguments[0].click();", brandLink);
        wait.until(ExpectedConditions.visibilityOfAllElements(displayedProducts));
        return displayedProducts.size();
    }
    public void addMultipleProductsToCart(int count) {
        for (int i = 0; i < count; i++) {
            js.executeScript("arguments[0].click();", allAddToCartButtons.get(i));
            if (i < count - 1) {
                clickContinueShopping();
            }
        }
    }
    public int getActualCartCount() {
        return driver.findElements(By.xpath("//table[@id='cart_info_table']/tbody/tr")).size();
    }
    public void clickViewCart() {
        wait.until(ExpectedConditions.visibilityOf(viewCartLink)).click();
    }
    public void clickViewProductByIndex(int index) {
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElements(allViewProductButtons));
        js.executeScript("arguments[0].click();", allViewProductButtons.get(index));
    }
    public void clickModalViewCart() {
        wait.until(ExpectedConditions.elementToBeClickable(viewCartLink)).click();
    }
    public void clickContinueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(continueShoppingBtn)).click();
        wait.until(ExpectedConditions.invisibilityOf(continueShoppingBtn));
    }

    }




