package PersonalProjects.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import PageObjects.LandingPage;

public class BaseTest {
	public WebDriver driver;
	public LandingPage landingPage;
	
	public WebDriver initializeDriver() throws IOException {
		//Properties
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\Resources\\GlobalData.properties");
		prop.load(fis);
		
		// Either we use maven-sent browser value OR use default browser value from GlobalData 
		String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : prop.getProperty("browser");
		
		if(browserName.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			if(browserName.contains("headless")) {
				options.addArguments("headless");
			}
			driver = new ChromeDriver(options);
			driver.manage().window().setSize(new Dimension(1440,900));
		}
		else if(browserName.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		}
		else if(browserName.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		}
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.manage().window().maximize();
		
		return driver;
	}
	
	// Converts JSON file to HashMaps (Data for DataProvider in tests)
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {

		// Read json to string
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

		// String to hashmap (Jackson Databind Dependency)
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {

				});

		return data;
	}
	
	// Takes Screenshot, stores it in a path then returns that path
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		
		// Store in local workspace
		File dest = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, dest);
		
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
	}
	
	@DataProvider(name = "getData")
	// Valid Login Credentials
	public Object[][] getData() throws IOException{
		
		// Manually created HashMaps
		/*
		
		HashMap<String,String> map1 = new HashMap<String, String>();
		map1.put("email", "polegywork@gmail.com");
		map1.put("password", "Roltoma1234");
		map1.put("productName", "ZARA COAT 3");
		
		HashMap<String,String> map2 = new HashMap<String, String>();
		map2.put("email", "omarahmed123443@gmail.com");
		map2.put("password", "Roltoma1234");
		map2.put("productName", "ADIDAS ORIGINAL");
		
		*/
		
		// Automatically created HashMaps using JSON file PurchaseOrder
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "\\src\\test\\java\\PersonalProjects\\Data\\PurchaseOrder.json");
		
		return new Object[][] {{data.get(0)},{data.get(1)}};
		
		// return new Object[][] {{"polegywork@gmail.com","Roltoma1234","ZARA COAT 3"},{"omarahmed123443@gmail.com","Roltoma1234","ADIDAS ORIGINAL"}};
	}
	
	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goToLandingPage();
		return landingPage;
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}
}
