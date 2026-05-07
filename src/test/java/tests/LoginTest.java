package tests;

import Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends Testbase {

    LoginPage loginPage;

    @BeforeMethod
    public void goToLoginPage() {
        driver.navigate().to("https://automationexercise.com/login");
        loginPage = new LoginPage(driver);
    }

    // 1 - valid email and valid password
    @Test
    public void verifyLoginWithValidEmailAndPassword() {
        loginPage.login("yasmeen@gmail.com", "123456");
        String currentUrl = loginPage.getUrl();
        System.out.println("Current URL: " + currentUrl);
        Assert.assertTrue(currentUrl.contains("automationexercise.com"),
                "Should navigate to Home page");
    }

    // 2 - both empty
    @Test
    public void verifyLoginWithEmptyEmailAndPassword() {
        loginPage.clickLogin();
        String currentUrl = loginPage.getUrl();
        Assert.assertTrue(currentUrl.contains("/login"),
                "Should stay on login page when fields are empty");
    }

    // 3 - empty email only
    @Test
    public void verifyLoginWithEmptyEmail() {
        loginPage.enterPassword("123456");
        loginPage.clickLogin();
        String currentUrl = loginPage.getUrl();
        Assert.assertTrue(currentUrl.contains("/login"),
                "Should stay on login page when email is empty");
    }

    // 4 - empty password only
    @Test
    public void verifyLoginWithEmptyPassword() {
        loginPage.enterEmail("yasmeen@gmail.com");
        loginPage.clickLogin();
        String currentUrl = loginPage.getUrl();
        Assert.assertTrue(currentUrl.contains("/login"),
                "Should stay on login page when password is empty");
    }

    // 5 - invalid email format
    @Test
    public void verifyLoginWithInvalidEmailFormat() {
        loginPage.enterEmail("yasmeen.gmail.com");
        loginPage.enterPassword("123456");
        loginPage.clickLogin();
        // Browser shows "Please include '@' in the email address."
        String currentUrl = loginPage.getUrl();
        Assert.assertTrue(currentUrl.contains("/login"),
                "Should stay on login page with invalid email format");
    }

    // 6 - wrong password
    @Test
    public void verifyLoginWithWrongPassword() {
        loginPage.login("yasmeen@gmail.com", "wrong123");
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(
                error.contains("incorrect"),
                "Error message should appear for wrong password"
        );
    }

    // 7 - unregistered email
    @Test
    public void verifyLoginWithUnregisteredEmail() {
        loginPage.login("test123@gmail.com", "123456");
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(
                error.contains("incorrect"),
                "Error message should appear for wrong password"
        );
    }

    // 8 - email with spaces
    @Test
    public void verifyLoginWithEmailContainingSpaces() {
        loginPage.enterEmail(" yasmeen@ gmail .com");
        loginPage.enterPassword("123456");
        loginPage.clickLogin();
        // Browser shows native validation for invalid email
        String currentUrl = loginPage.getUrl();
        Assert.assertTrue(currentUrl.contains("/login"),
                "Should stay on login page when email contains spaces");
    }
    //9- Muliple clicks
    @Test
    public void verifyLoginButtonMultipleClicks() {
        loginPage.enterEmail("yasmeenMulti@gmail.com");
        loginPage.enterPassword("wrong123");
        loginPage.clickLogin();
        loginPage.clickLogin();
        loginPage.clickLogin();
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(
                error.contains("incorrect"),
                "Error message should appear after multiple invalid login attempts"
        );
    }


}