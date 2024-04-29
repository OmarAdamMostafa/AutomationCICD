package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponents.AbstractComponent;

public class ConfirmationPage extends AbstractComponent{
	WebDriver driver;
	
	public ConfirmationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".hero-primary")
	WebElement confirmText;
	@FindBy(css="label[class='ng-star-inserted']")
	WebElement orderID;
	
	public String getConfirmationText() {
		waitForWebElementToBeVisible(confirmText);
		
		return confirmText.getText();
	}
	
	public String getConfirmationOrderID() {
		waitForWebElementToBeVisible(orderID);
		
		String orderText = orderID.getText();
		String[] words = orderText.split(" ");
		String finalOrderID = words[1].trim();
		
		return finalOrderID;
	}
}
