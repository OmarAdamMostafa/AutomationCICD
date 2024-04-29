package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponents.AbstractComponent;

public class CheckOutPage extends AbstractComponent {
	WebDriver driver;

	public CheckOutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[text()='CVV Code ']/following-sibling::input")
	WebElement cvvCode;
	@FindBy(xpath = "//div[text()='Name on Card ']/following-sibling::input")
	WebElement cardName;
	@FindBy(xpath = "//input[@placeholder='Select Country']")
	WebElement country;
	@FindBy(xpath = "//a[text()='Place Order ']")
	WebElement submitButton;

	By countryOptionsBy = By.xpath("//following-sibling::section/button");
	By polandLocatorBy = By.xpath("//following-sibling::section/button/span[text()=' Poland']");

	public void fillPaymentForm() {
		cvvCode.sendKeys("123");
		cardName.sendKeys("OMAR ADAM");
		selectCountry();	
	}

	public void selectCountry() {
		country.sendKeys("Pol");
		waitForElementToBeVisible(countryOptionsBy);
		country.findElement(polandLocatorBy).click();
	}
	
	public ConfirmationPage submitOrder() {
		submitButton.click();
		
		return new ConfirmationPage(driver);
	}
}
