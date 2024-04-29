package PersonalProjects.SeleniumFrameworkDesign;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;

import PageObjects.CartPage;
import PageObjects.ProductCatalogue;
import PersonalProjects.TestComponents.BaseTest;
import PersonalProjects.TestComponents.RetryTest;

public class ErrorValidation extends BaseTest {

	@Test(groups="ErrorHandling", retryAnalyzer=RetryTest.class)
	public void LoginErrorValidation() throws IOException {
		
		// Login
		landingPage.loginApplication("wrongEmail@gmail.com", "wrongPasssword");

		Assert.assertEquals(landingPage.getErrorMessage(), "Incorrect email or password.");
	}
	
	@Test(dataProvider="getData")
	public void ProductErrorValidation(HashMap<String,String> map) throws IOException {
		
		// Login
		ProductCatalogue productCatalogue = landingPage.loginApplication(map.get("email"), map.get("password"));

		// Retrieve all products
		productCatalogue.waitForProductList();

		// Add product to cart
		productCatalogue.addProductToCart(map.get("productName"));

		Assert.assertEquals(driver.findElement(By.id("toast-container")).getText(), "Product Added To Cart");

		// Go to Cart page
		CartPage cartPage = productCatalogue.goToCartPage();

		// Verify that the product has been added by to the cart
		cartPage.waitForCartProducts();
		Boolean productFound = cartPage.checkIfProductFound("ROLEX WATCH");

		Assert.assertFalse(productFound);

	}
}
