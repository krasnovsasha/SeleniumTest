import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BankTest {
	private WebDriver driver;
	private WebDriverWait wait;
	//use this driver for Linux System
	private final String pathToChromeDriverLinux = "drivers/chromedriver";
	//use this driver for Windows System
	private final String pathToChromeDriverWindows = "drivers/chromedriver.exe";
	private String urlRGSMainPage = "http://www.rgs.ru";

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", pathToChromeDriverLinux);
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 10);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
	}
	@Test
	public void rgsTest(){
		String menuNavBar = "//a[@class='hidden-xs' and @data-toggle='dropdown']";
		//Перейти по ссылке http://www.rgs.ru
		driver.get(urlRGSMainPage);
		//Выбрать Меню
		driver.findElement(By.xpath(menuNavBar)).click();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
}
