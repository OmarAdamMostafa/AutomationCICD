package PersonalProjects.SeleniumFrameworkDesign;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import PageObjects.LandingPage;

public class StandAloneTest {
		public static void main(String[] args) {
			// Added this line for GitHub Jenkins WebHook testing
			
			WebDriver driver = new ChromeDriver();
			WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
			String productName = "ZARA COAT 3";
			
			driver.manage().window().maximize();
			
			driver.get("https://rahulshettyacademy.com/client");
			
			// Login
			LandingPage landingPage = new LandingPage(driver);
			driver.findElement(By.id("userEmail")).sendKeys("polegywork@gmail.com");
			driver.findElement(By.id("userPassword")).sendKeys("Roltoma1234");
			driver.findElement(By.id("login")).click();
			
			//Wait until login confirmation is invisible and all products are visible
			w.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div/div[@class='card']")));
			w.until(ExpectedConditions.invisibilityOfElementLocated(By.id("toast-container")));
			List<WebElement> items = driver.findElements(By.xpath("//div/div[@class='card']"));
			
			// Get ZARA COAT 3 product
			WebElement product = items.stream().filter(item -> item.findElement(By.tagName("b")).getText().equalsIgnoreCase(productName))
											   .findFirst().orElse(null);
			
			// Add ZARA COAT 3 product to cart
			product.findElement(By.xpath("//div/button[text()=' Add To Cart']")).click();
			
			//Wait until loading is invisible and add to cart confirmation is visible
			w.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ng-animating")));
			w.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));
			
			Assert.assertEquals(driver.findElement(By.id("toast-container")).getText(), "Product Added To Cart");
			
			// Go to Cart page
			driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();
			
			// Verify that the product has been added by to the cart
			w.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='cartSection']/h3")));
			List<WebElement> cartItems = driver.findElements(By.xpath("//div[@class='cartSection']/h3"));
			Boolean itemFound = cartItems.stream().anyMatch(cartItem -> cartItem.getText().equalsIgnoreCase(productName));
			
			Assert.assertTrue(itemFound);
			
			driver.findElement(By.xpath("//li[@class='totalRow']/button")).click();
			
			// Fill payment form
			driver.findElement(By.xpath("//div[text()='CVV Code ']/following-sibling::input")).sendKeys("123");
			driver.findElement(By.xpath("//div[text()='Name on Card ']/following-sibling::input")).sendKeys("Omar Adam");
			
			WebElement suggestDropdown = driver.findElement(By.xpath("//input[@placeholder='Select Country']"));
			suggestDropdown.sendKeys("Pol");
			w.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//following-sibling::section/button")));
			suggestDropdown.findElement(By.xpath("//following-sibling::section/button/span[text()=' Poland']")).click();

			driver.findElement(By.xpath("//a[text()='Place Order ']")).click();
			
			w.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".hero-primary")));
			
			// Verify that order has been placed
			Assert.assertEquals(driver.findElement(By.cssSelector(".hero-primary")).getText(), "THANKYOU FOR THE ORDER.");
			driver.close();
		}
}
