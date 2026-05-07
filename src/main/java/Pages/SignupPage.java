package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignupPage extends PageBase {

    public SignupPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "[data-qa='signup-name']")
    public WebElement nameField;
    @FindBy(css = "[data-qa='signup-email']")
    public WebElement emailField;
    @FindBy(css = "[data-qa='signup-button']")
    public WebElement signupButton;
    @FindBy(xpath = "//p[contains(text(),'Email Address already exist')]")
    public WebElement errorMessage;

    public void enterName(String name) {
        nameField.clear();
        nameField.sendKeys(name);
    }
    public void enterEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);
    }
    public void clickSignup() {
        signupButton.click();
    }
    public void signup(String name, String email) {
        enterName(name);
        enterEmail(email);
        clickSignup();
    }
    public String getErrorMessage() {
        return errorMessage.getText();
    }
    public String getUrl() {
        return driver.getCurrentUrl();
    }
}