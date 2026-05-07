package tests;

import Pages.SignupPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SignupTest extends Testbase {

    SignupPage signupPage;

    @BeforeMethod
    public void goToLoginPage() {
        driver.navigate().to("https://automationexercise.com/login");
        signupPage = new SignupPage(driver);
    }

    // 1 - valid name and email
    @Test
    public void verifySignupWithValidNameAndEmail() {
        signupPage.signup("Yasmeen", "yasmeen_new@gmail.com");
        String currentUrl = signupPage.getUrl();
        System.out.println("Current URL: " + currentUrl);
        Assert.assertTrue(currentUrl.contains("/signup"),
                "Should navigate to create account page");
    }

    // 2 - both empty
    @Test
    public void verifySignupWithEmptyNameAndEmail() {
        signupPage.clickSignup();
        String currentUrl = signupPage.getUrl();
        Assert.assertTrue(currentUrl.contains("/login"),
                "Should stay on login page when both fields are empty");
    }

    // 3 - empty name, valid email
    @Test
    public void verifySignupWithEmptyName() {
        signupPage.enterEmail("yasmeen@gmail.com");
        signupPage.clickSignup();
        String currentUrl = signupPage.getUrl();
        Assert.assertTrue(currentUrl.contains("/login"),
                "Should stay on login page when name is empty");
    }

    // 4 - valid name, empty email
    @Test
    public void verifySignupWithEmptyEmail() {
        signupPage.enterName("Yasmeen");
        signupPage.clickSignup();
        String currentUrl = signupPage.getUrl();
        Assert.assertTrue(currentUrl.contains("/login"),
                "Should stay on login page when email is empty");
    }

    // 5 - valid name, invalid email format
    @Test
    public void verifySignupWithInvalidEmailFormat() {
        signupPage.enterName("Yasmeen");
        signupPage.enterEmail("yagmail.com");
        signupPage.clickSignup();
        // Browser shows "Please include '@' in the email address."
        String currentUrl = signupPage.getUrl();
        Assert.assertTrue(currentUrl.contains("/login"),
                "Should stay on login page with invalid email format");
    }

    // 6 - name with special characters
    @Test
    public void verifySignupWithSpecialCharactersInName() {
        signupPage.signup("%yasmeen", "yasmeen@gmail.com");
        String currentUrl = signupPage.getUrl();
        Assert.assertTrue(currentUrl.contains("/login"),
                "Should not allow special characters in name - Known Bug");
    }

    // 7 - name with numbers
    @Test
    public void verifySignupWithNumbersInName() {
        signupPage.signup("123", "y900@gmail.com");
        String currentUrl = signupPage.getUrl();
        // Expected: stay on login page - Actual: navigates to signup (Bug)
        Assert.assertTrue(currentUrl.contains("/login"),
                "Should not allow numbers in name - Known Bug");
    }
    // 8 -verify Signup With Registered Email
    @Test
    public void verifySignupWithRegisteredEmail() {
        signupPage.signup("Yasmeen", "yasmeen@gmail.com");
        String error = signupPage.getErrorMessage();
        Assert.assertTrue(
                error.contains("Email Address already exist"),
                "System should not allow duplicate email"
        );
    }
}