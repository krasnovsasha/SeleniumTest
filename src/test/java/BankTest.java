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
		//Заполнить поля
		//Имя, Фамилия, Отчество, Регион, Телефон,Эл. почта - qwertyqwerty,
		//Комментарии, Я согласен на обработку
		driver.findElement(By.name("LastName")).sendKeys("Иванов");
		driver.findElement(By.name("FirstName")).sendKeys("Иван");
		driver.findElement(By.name("MiddleName")).sendKeys("Иванович");
		driver.findElement(By.name("Region")).click();
		String fieldRegionToSelect = "//option[contains(text(),'Москва')]";
		driver.findElement(By.xpath(fieldRegionToSelect)).click();
		String fieldPhone = "//label[contains(text(),'Телефон')]//following-sibling::input";
		driver.findElement(By.xpath(fieldPhone)).click();
		driver.findElement(By.xpath(fieldPhone)).sendKeys("9855678946");
		driver.findElement(By.name("Email")).sendKeys("qwertyqwerty");
		driver.findElement(By.name("Comment")).sendKeys("TestTestTest");
		String inputCheckBox = "//input[@type='checkbox']";
		driver.findElement(By.xpath(inputCheckBox)).click();
		//Проверить, что все поля заполнены введенными значениями
		Assert.assertEquals("Поле 'Фамилия' не совпадает",
							"Иванов",driver.findElement(By.name("LastName")).getAttribute("value"));
		Assert.assertEquals("Поле 'Имя' не совпадает",
				"Иван",driver.findElement(By.name("FirstName")).getAttribute("value"));
		Assert.assertEquals("Поле 'Отчество' не совпадает",
				"Иванович",driver.findElement(By.name("MiddleName")).getAttribute("value"));
		Assert.assertEquals("Поле 'Регион' не совпадает",
				"77",driver.findElement(By.name("Region")).getAttribute("value"));
		String phoneActual = driver.findElement(By.xpath(fieldPhone)).getAttribute("value");
		// I changing phone numbers below as I follow info from site that has Inputmask: '+7 (999) 999-99-99'
		String phoneChanged = phoneActual.replaceAll("\\p{Punct}","");
		Assert.assertEquals("Поле 'Телефон' не совпадает",
				"+7 (985) 567-89-46".replaceAll("\\p{Punct}",""),phoneChanged);
		Assert.assertEquals("Поле 'Email' не совпадает",
				"qwertyqwerty",driver.findElement(By.name("Email")).getAttribute("value"));
		Assert.assertEquals("Поле 'Комментарии' не совпадает",
				"TestTestTest",driver.findElement(By.name("Comment")).getAttribute("value"));
		//Нажать Отправить
		driver.findElement(By.xpath("//div[@class='form-footer']//button[@type='button']")).click();
		//Проверить, что у Поля - Эл. почта присутствует сообщение об ошибке - Введите корректный email
		String errorFieldEmail = "//label[contains(text(),'Эл. почта')]/parent::div//span[contains(text(),'Введите адрес электронной почты')]";
		Assert.assertEquals("Поле 'Email' не содержит сообщение об ошибке",
							"Введите адрес электронной почты",driver.findElement(By.xpath(errorFieldEmail)).getText());
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
}
