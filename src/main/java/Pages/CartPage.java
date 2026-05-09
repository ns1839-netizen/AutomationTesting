package Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends PageBase {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[@href='/view_cart']")
    public WebElement cartButton;

    @FindBy(xpath = "//u[text()='here']")
    public WebElement hereLink;

    @FindBy(id = "susbscribe_email")
    public WebElement subscribeEmailField;

    @FindBy(id = "subscribe")
    public WebElement subscribeButton;

    @FindBy(xpath = "//*[contains(text(),'You have been successfully subscribed!')]")
    public WebElement successSubscriptionMessage;

    @FindBy(xpath = "(//a[contains(text(),'Add to cart')])[1]")
    public WebElement firstAddToCartButton;

    @FindBy(xpath = "//button[text()='Continue Shopping']")
    public WebElement continueShoppingButton;

    @FindBy(xpath = "//td[@class='cart_description']/h4/a")
    public WebElement productDescription;

    @FindBy(xpath = "//a[@class='cart_quantity_delete']")
    public WebElement deleteProductButton;

    @FindBy(xpath = "//a[contains(text(),'Proceed To Checkout')]")
    public WebElement proceedToCheckoutButton;

    @FindBy(xpath = "//*[contains(text(),'Register / Login account to proceed on checkout.')]")
    public WebElement checkoutMessage;

    @FindBy(xpath = "//button[text()='Continue On Cart']")
    public WebElement continueOnCartButton;

    @FindBy(xpath = "//u[text()='Register / Login']")
    public WebElement registerLoginButton;


    public void openCartPage() {
        cartButton.click();
    }

    public void goToProductsPageUsingHereLink() {

        wait.until(ExpectedConditions.visibilityOf(hereLink));

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("arguments[0].scrollIntoView(true);", hereLink);
        js.executeScript("arguments[0].click();", hereLink);
    }

    public void subscribe(String email) {

        subscribeEmailField.clear();
        subscribeEmailField.sendKeys(email);
        subscribeButton.click();
    }

    public String getSubscriptionSuccessMessage() {
        return successSubscriptionMessage.getText();
    }

    public void addProductToCart() {

        openCartPage();

        goToProductsPageUsingHereLink();

        wait.until(ExpectedConditions.visibilityOf(firstAddToCartButton));

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                firstAddToCartButton
        );

        js.executeScript("arguments[0].click();", firstAddToCartButton);

        wait.until(ExpectedConditions.elementToBeClickable(
                continueShoppingButton
        )).click();

        openCartPage();
    }

    public void openProductDetails() {
        productDescription.click();
    }

    public void removeProductFromCart() {

        wait.until(ExpectedConditions.elementToBeClickable(deleteProductButton))
                .click();
    }

    public boolean isProductRemoved() {

        try {
            return wait.until(
                    ExpectedConditions.invisibilityOf(deleteProductButton)
            );
        } catch (Exception e) {
            return true;
        }
    }

    public void proceedToCheckout() {
        proceedToCheckoutButton.click();
    }

    public String getCheckoutMessage() {
        return checkoutMessage.getText();
    }

    public void clickRegisterLoginButton() {
        registerLoginButton.click();
    }
}