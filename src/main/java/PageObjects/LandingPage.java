package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent{
	WebDriver driver;
	
	public LandingPage(WebDriver driver) {
		super(driver);
		
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	// Page Factory Design Pattern
	@FindBy(id="userEmail")
	WebElement userEmail;
	@FindBy(id="userPassword")
	WebElement userPassword;
	@FindBy(id="login")
	WebElement submit;
	@FindBy(css="[class*='flyInOut']")
	WebElement errorMessage;
	
	// WebElement userEmail = driver.findElement(By.id("userEmail"));
	// WebElement userPassword = driver.findElement(By.id("userPassword"));
	// WebElement submit = driver.findElement(By.id("login"));
	// WebElement errorMessage = driver.findElement(By.cssSelector("[class*='flyInOut']"));
	
	By errorMessageBy = By.cssSelector("[class*='flyInOut']");
	
	public ProductCatalogue loginApplication(String email, String password) {
		userEmail.sendKeys(email);
		userPassword.sendKeys(password);
		submit.click();
		
		return new ProductCatalogue(driver);
	}
	
	public void goToLandingPage() {
		driver.get("https://rahulshettyacademy.com/client");
	}
	
	public String getErrorMessage() {
		waitForWebElementToBeVisible(errorMessage);
		return errorMessage.getText();
	}
}
