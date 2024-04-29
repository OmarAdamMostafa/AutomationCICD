package PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent{
	WebDriver driver;
	
	public ProductCatalogue(WebDriver driver) {
		// Send WebDriver driver to parent class
		super(driver);
		
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	// Page Factory Design Pattern
	@FindBy(xpath="//div/div[@class='card']")
	List<WebElement> products;
	
	// By variables
	//By productsBy = By.cssSelector(".mb-3");
	By productsBy = By.xpath("//div/div[@class='card']");

	By toastBy = By.id("toast-container");
	By loadingAnimationBy = By.className("ng-animating");
	//By addToCartBy = By.cssSelector(".card-body button:last-of-type");
	By addToCartBy = By.cssSelector(".card-body button[class='btn w-10 rounded']");
	
	// Main functionality is to wait for products to load
	public void waitForProductList() {
		waitForElementToBeVisible(productsBy);
		waitForElementToBeInvisible(toastBy);
	}
	
	public WebElement getProductByName(String productName) {
		
		WebElement product = products
								.stream()
								.filter(item -> item.findElement(By.cssSelector("b")).getText().equalsIgnoreCase(productName))
								.findFirst().orElse(null);
		
		return product;
	}
	
	public void addProductToCart(String productName) {
		WebElement prod = getProductByName(productName);
		prod.findElement(addToCartBy).click();
		
		waitForElementToBeInvisible(loadingAnimationBy);
		waitForElementToBeVisible(toastBy);
	}
}

	
	
