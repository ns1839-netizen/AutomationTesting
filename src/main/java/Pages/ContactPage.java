package Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ContactPage extends PageBase {

    public ContactPage(WebDriver driver) {
        super(driver);
    }

    // ─── Locators ──────────────────────────────────────────────────────────────
    @FindBy(name = "name")
    public WebElement nameField;

    @FindBy(name = "email")
    public WebElement emailField;

    @FindBy(name = "subject")
    public WebElement subjectField;

    @FindBy(id = "message")
    public WebElement messageField;

    @FindBy(name = "upload_file")
    public WebElement fileUpload;

    @FindBy(name = "submit")
    public WebElement submitButton;

    @FindBy(css = ".status.alert.alert-success")
    public WebElement successMessage;

    // ─── Basic Actions ─────────────────────────────────────────────────────────

    public void enterName(String name) {
        nameField.clear();
        nameField.sendKeys(name);
    }

    public void enterEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void enterSubject(String subject) {
        subjectField.clear();
        subjectField.sendKeys(subject);
    }

//    public void enterMessage(String message) {
//        messageField.clear();
//        messageField.sendKeys(message);
//    }

    public void enterMessage(String message) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = arguments[1];", messageField, message);
    }

    public void uploadFile(String filePath) {
        fileUpload.sendKeys(filePath);
    }

    public void clickSubmit() {
        submitButton.click();
    }

    public void acceptAlert()  {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public String getSuccessMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(successMessage));
        return successMessage.getText();
    }


    //Returns true if the email field is invalid per HTML5 browser validation.

    //check if email is true from js
    public boolean isEmailFieldInvalid() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return !(boolean) js.executeScript("return arguments[0].validity.valid;", emailField);
    }


    // Returns true if the success message is visible within 5 seconds.


    // check if mess is appear true
    public boolean isSuccessMessageVisible() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(successMessage));
            return true;
        } catch (Exception e) {
            return false;
        }
    }



    //Fill all text fields + click submit + accept alert
    public void submitForm(String name, String email, String subject, String message) {
        enterName(name);
        enterEmail(email);
        enterSubject(subject);
        enterMessage(message);
        clickSubmit();
        acceptAlert();
    }

    //     Fill all text fields + upload file + click submit + accept alert
    public void submitFormWithFile(String name, String email, String subject,
                                   String message, String filePath) {
        enterName(name);
        enterEmail(email);
        enterSubject(subject);
        enterMessage(message);
        uploadFile(filePath);
        clickSubmit();
        acceptAlert();
    }
}
