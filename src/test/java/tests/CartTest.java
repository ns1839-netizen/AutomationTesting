package tests;

import Pages.CartPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class CartTest extends Testbase {

    private CartPage getCartPage() {
        return new CartPage(driver);
    }

    @Test(priority = 1)
    public void verifyEmailSubscription() {

        CartPage cartPage = getCartPage();

        cartPage.openCartPage();

        cartPage.subscribe("");

        String emptyValidationMessage =
                cartPage.subscribeEmailField.getAttribute("validationMessage");

        Assert.assertNotNull(emptyValidationMessage);

        Assert.assertTrue(
                emptyValidationMessage.toLowerCase().contains("fill")
        );

        cartPage.subscribeEmailField.clear();

        cartPage.subscribe("ayaelkassas13.com");

        String invalidValidationMessage =
                cartPage.subscribeEmailField.getAttribute("validationMessage");

        Assert.assertNotNull(invalidValidationMessage);

        Assert.assertFalse(invalidValidationMessage.isEmpty());

        cartPage.subscribeEmailField.clear();

        cartPage.subscribe("ayaelkassas13@gmail.com");

        String actualMessage =
                cartPage.getSubscriptionSuccessMessage();

        Assert.assertEquals(
                actualMessage,
                "You have been successfully subscribed!"
        );
    }

    @Test(priority = 2)
    public void verifyViewingProductDetailsFromCartAndRemovingIt() {

        CartPage cartPage = getCartPage();

        cartPage.addProductToCart();

        cartPage.openProductDetails();

        Assert.assertTrue(
                Objects.requireNonNull(driver.getCurrentUrl())
                        .contains("/product_details/")
        );

        driver.navigate().back();

        cartPage.proceedToCheckout();

        String actualMessage =
                cartPage.getCheckoutMessage();

        Assert.assertTrue(
                actualMessage.contains(
                        "Register / Login account to proceed on checkout."
                )
        );

        cartPage.clickRegisterLoginButton();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/login")
        );

        driver.navigate().back();

        cartPage.continueOnCartButton.click();

        cartPage.removeProductFromCart();

        Assert.assertTrue(
                cartPage.isProductRemoved()
        );
    }
}