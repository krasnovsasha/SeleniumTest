import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

public class BankTest {
	private WebDriver driver;
	 WebDriverWait wait;
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
		//Выбрать категорию - ДМС
		String linkDMS = "//a[contains(text(),'ДМС')]";
		driver.findElement(By.xpath(linkDMS)).click();
		//Проверить наличие заголовка - Добровольное медицинское страхование
		String h1 = "//h1";
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(h1)));
		String actualText = driver.findElement(By.xpath(h1)).getText();
		Assert.assertEquals("Текст заголовка не соответствует ожидаемому",
				"ДМС — добровольное медицинское страхование",actualText);
		//Нажать на кнопку - Отправить заявку
		String buttonToSendRequest = "//a[contains(text(),'Отправить заявку')]";
		driver.findElement(By.xpath(buttonToSendRequest)).click();
		//Проверить, что открылась страница , на которой присутствует текст
		// - Заявка на добровольное медицинское страхование
		String formWithContent = "//div[@class='modal-content']";
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(formWithContent))));
		actualText = driver.findElement(By.xpath("//h4[@class='modal-title']/b")).getText();
		Assert.assertEquals("Текст заголовка не соответствует ожидаемому",
				"Заявка на добровольное медицинское страхование",actualText);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
}
