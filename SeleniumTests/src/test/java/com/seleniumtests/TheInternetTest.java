package com.seleniumtests;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;
public class TheInternetTest{
	WebDriver driver = null;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(2));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
	}

	@Test
	public void loginSuccess() {
		driver.get("http://localhost:7080/login");
		driver.findElement(By.id("username")).sendKeys("tomsmith");
		driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
		driver.findElement(By.className("radius")).click();
		driver.findElement(By.linkText("Logout"));
		Assert.assertEquals(driver.findElement(By.className("subheader")).getText(), "Welcome to the Secure Area. When you are done click logout below.");
	}

	@Test
	public void loginFailure() {
		driver.get("http://localhost:7080/login");
		driver.findElement(By.id("username")).sendKeys("tomsmith11");
		driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!123");
		driver.findElement(By.className("radius")).click();
		driver.findElement(By.cssSelector(".flash.error"));
	}

	@Test
	public void checkBox() {
		driver.get("http://localhost:7080/checkboxes");
		List<WebElement> checkBoxElement = driver.findElements(By.id("checkboxes"));

		for(WebElement checkBox : checkBoxElement) {

			if(checkBox.getText().equalsIgnoreCase("checbox 1"))
				Assert.assertFalse(checkBox.isSelected());

			if(checkBox.getText().equalsIgnoreCase("checbox 2"))
				Assert.assertTrue(checkBox.isSelected());
		}

	}

	@Test
	public void alertTest() {
		driver.get("http://localhost:7080/context_menu");
		WebElement divAlert = driver.findElement(By.id("hot-spot"));
		Actions a = new Actions(driver);
		a.moveToElement(divAlert).contextClick().build().perform();
		Alert contextAlert = driver.switchTo().alert();
		String alertText = contextAlert.getText();
		contextAlert.accept();
		Assert.assertEquals("You selected a context menu", alertText);
	}

	@Test
	public void dragAnddropTest() throws InterruptedException {
		driver.get("http://localhost:7080/drag_and_drop");
		WebElement BoxB = driver.findElement(By.id("column-b"));
		WebElement BoxA = driver.findElement(By.id("column-a"));

		Actions actions = new Actions(driver);
		//actions.moveToElement(BoxA);
		//actions.clickAndHold();
		//actions.moveToElement(BoxB);

		actions.dragAndDrop(BoxB,BoxA);
		//Thread.sleep(5000);
		actions.build().perform();

	}

	@AfterClass
	public void close() {
		driver.close();
		driver.quit();

	}
}
