package PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponents.AbstractComponent;

public class OrdersPage extends AbstractComponent{
	WebDriver driver;
	
	public OrdersPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(className ="table-responsive")
	WebElement orderedProductsTable;
	@FindBy(css="tr td:nth-child(3)")
	List<WebElement> orderedProducts;
	
	By orderedProductsBy = By.cssSelector("tr td:nth-child(3)");
	
	public void waitForOrders() {
		waitForElementToBeVisible(orderedProductsBy);
	}
	
	public Boolean verifyOrderDisplay(String orderProductName) {
		return orderedProducts
					.stream()
					.anyMatch(orderedProduct -> orderedProduct.getText().equalsIgnoreCase(orderProductName));
	}
}
