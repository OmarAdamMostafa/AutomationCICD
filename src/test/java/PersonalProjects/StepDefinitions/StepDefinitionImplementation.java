package PersonalProjects.StepDefinitions;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;

import PageObjects.CartPage;
import PageObjects.CheckOutPage;
import PageObjects.ConfirmationPage;
import PageObjects.LandingPage;
import PageObjects.ProductCatalogue;
import PersonalProjects.TestComponents.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionImplementation extends BaseTest {

	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public CartPage cartPage;
	public CheckOutPage checkOutPage;
	public ConfirmationPage confirmPage;

	@Given("I landed on E-Commerce website")
	public void I_landed_on_e_commerce_website() throws IOException {
		landingPage = launchApplication();
	}

	@Given("^Logged in with email (.+) and password (.+)$")
	public void logged_in_with_email_and_password(String email, String password) {
		// Login
		productCatalogue = landingPage.loginApplication(email, password);
	}

	@When("^I add the product (.+) to Cart$")
	public void I_add_the_product_to_cart(String productName) {
		// Retrieve all products
		productCatalogue.waitForProductList();

		// Add ZARA COAT 3 product to cart
		productCatalogue.addProductToCart(productName);

		Assert.assertEquals(driver.findElement(By.id("toast-container")).getText(), "Product Added To Cart");
	}

	@And("^Checkout product (.+) and submit the order$")
	public void checkout_product_and_submit_the_order(String productName) {
		// Go to Cart page
		cartPage = productCatalogue.goToCartPage();

		// Verify that the product has been added by to the cart
		cartPage.waitForCartProducts();
		Boolean productFound = cartPage.checkIfProductFound(productName);

		Assert.assertTrue(productFound);

		checkOutPage = cartPage.goToCheckOutPage();

		// Fill Payment form
		checkOutPage.fillPaymentForm();
		checkOutPage.submitOrder();

		confirmPage = checkOutPage.submitOrder();
	}

	@Then("{string} is displayed in ConfirmationPage")
	public void text_is_displayed_in_confirmationpage(String string) {
		// Verify that order has been placed
		Assert.assertEquals(confirmPage.getConfirmationText(), string);
	}

	@Then("{string} error message is displayed")
	public void incorrect_email_or_password_error_message_is_displayed(String string) {
		Assert.assertEquals(landingPage.getErrorMessage(), "Incorrect email or password.");
	}

	@After()
	public void closeBrowser() {
		tearDown();
	}
}
