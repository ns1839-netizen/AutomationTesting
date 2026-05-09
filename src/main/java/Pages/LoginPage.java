
package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends PageBase {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "[data-qa='login-email']")
    public WebElement emailField;
    @FindBy(css = "[data-qa='login-password']")
    public WebElement passwordField;
    @FindBy(css = "[data-qa='login-button']")
    public WebElement loginButton;
    @FindBy(xpath = "//p[contains(text(),'incorrect')]")
    public WebElement errorMessage;

    public void enterEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);
    }
    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }
    public void clickLogin() {
        loginButton.click();
    }
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }
    public String getErrorMessage() {
        return errorMessage.getText();
    }
    public String getUrl() {
        return driver.getCurrentUrl();
    }
}