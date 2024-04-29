package PersonalProjects.SeleniumFrameworkDesign;

import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import PageObjects.CartPage;
import PageObjects.CheckOutPage;
import PageObjects.ConfirmationPage;
import PageObjects.OrdersPage;
import PageObjects.ProductCatalogue;
import PersonalProjects.TestComponents.BaseTest;

public class UpgradedStandAloneTest extends BaseTest { 
	
	
	@Test(dataProvider="getData", groups = {"Purchase"})
	public void SubmitOrder(HashMap<String, String> map) throws IOException {
		
		// Login
		ProductCatalogue productCatalogue = landingPage.loginApplication(map.get("email"), map.get("password"));

		// Retrieve all products
		productCatalogue.waitForProductList();

		// Add ZARA COAT 3 product to cart
		productCatalogue.addProductToCart(map.get("productName"));

		Assert.assertEquals(driver.findElement(By.id("toast-container")).getText(), "Product Added To Cart");

		// Go to Cart page
		CartPage cartPage = productCatalogue.goToCartPage();

		// Verify that the product has been added by to the cart
		cartPage.waitForCartProducts();
		Boolean productFound = cartPage.checkIfProductFound(map.get("productName"));

		Assert.assertTrue(productFound);

		CheckOutPage checkOutPage = cartPage.goToCheckOutPage();

		// Fill Payment form
		checkOutPage.fillPaymentForm();
		checkOutPage.submitOrder();

		ConfirmationPage confirmPage = checkOutPage.submitOrder();

		// Verify that order has been placed
		Assert.assertEquals(confirmPage.getConfirmationText(), "THANKYOU FOR THE ORDER.");
	}
	
	@Test(dataProvider="getData", dependsOnMethods="SubmitOrder")
	public void VerifyOrder(HashMap<String, String> map) {
		ProductCatalogue productCatalogue = landingPage.loginApplication(map.get("email"), map.get("password"));
		OrdersPage ordersPage = productCatalogue.goToOrdersPage();
		
		ordersPage.waitForOrders();
		
		Assert.assertTrue(ordersPage.verifyOrderDisplay(map.get("productName")));
	}

}
