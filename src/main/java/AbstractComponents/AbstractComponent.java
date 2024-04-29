package AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import PageObjects.CartPage;
import PageObjects.OrdersPage;

public class AbstractComponent {
	WebDriver driver;
	
	public AbstractComponent(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void waitForElementToBeVisible(By findBy) {
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
		w.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	
	public void waitForWebElementToBeVisible(WebElement element) {
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
		w.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForElementToBeInvisible(By findBy) {
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
		w.until(ExpectedConditions.invisibilityOfElementLocated(findBy));
	}
	
	public void waitForWebElementToBeInvisible(WebElement element) {
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
		w.until(ExpectedConditions.invisibilityOf(element));
	}
	
	// Navigates to cart page
	@FindBy(xpath="//button[@routerlink='/dashboard/cart']")
	WebElement cartPageButton;
	public CartPage goToCartPage() {
		cartPageButton.click();
		return new CartPage(driver);
	}
	
	// Navigates to orders page
	@FindBy(xpath="//button[@routerlink='/dashboard/myorders']")
	WebElement orderPageButton;
	public OrdersPage goToOrdersPage() {
		waitForElementToBeVisible(By.xpath("//button[@routerlink='/dashboard/myorders']"));
		orderPageButton.click();
		return new OrdersPage(driver);
	}
}
