package PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent{
	WebDriver driver;
	
	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//div[@class='cartSection']/h3")
	List<WebElement> cartProducts;
	@FindBy(xpath="//li[@class='totalRow']/button")
	WebElement checkOutButton;
	
	By cartProductsBy = By.xpath("//div[@class='cartSection']/h3");
	
	public void waitForCartProducts() {
		waitForElementToBeVisible(cartProductsBy);
	}
	
	public Boolean checkIfProductFound(String productName) {
		return cartProducts
					.stream()
					.anyMatch(cartItem -> cartItem.getText().equalsIgnoreCase(productName));
	}
	
	public CheckOutPage goToCheckOutPage() {
		checkOutButton.click();
		
		return new CheckOutPage(driver);
	}
}
