package UserFlow;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SwagLabs {
	
	WebDriver driver;
	
@BeforeTest
public void setup() {
	
	WebDriverManager.chromedriver().setup();
	driver = new ChromeDriver();
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	driver.get("https://www.saucedemo.com/");
	driver.manage().window().maximize();
}

@Test(priority = 0)
public void verifyLogin() {
	
	driver.findElement(By.id("user-name")).sendKeys("standard_user");
	driver.findElement(By.id("password")).sendKeys("secret_sauce");
	driver.findElement(By.id("login-button")).click();
	String title = driver.getTitle();
	assertEquals("Swag Labs", title);
}

@Test(priority=1)
public void validateDropdown() {
	
	WebElement element = driver.findElement(By.className("product_sort_container"));
	Select option1 = new Select(element);
	option1.selectByValue("lohi");
}

@Test(priority=2)
public void isAddtoCartButtonDisplayed() {
	
	WebElement button = driver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-onesie\"]"));
	
	assertTrue(button.isDisplayed());
	
}

@Test(priority=3)
public void addToCart() {
	
	driver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-onesie\"]")).click();
	driver.findElement(By.name("add-to-cart-sauce-labs-bolt-t-shirt")).click();
	driver.findElement(By.className("shopping_cart_link")).click();
	
	WebElement itemsInCart = driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a/span"));
	String totalItemsInCart = itemsInCart.getText();
	assertEquals("2",totalItemsInCart);
}

@Test(priority=4)
public void removeItemFromCart() {
	driver.findElement(By.id("remove-sauce-labs-bolt-t-shirt")).click();
}
	
@Test(priority=5)
public void checkout() {	
	
	driver.findElement(By.id("checkout")).click();
	driver.findElement(By.id("first-name")).sendKeys("Suba");
	driver.findElement(By.id("last-name")).sendKeys("shini");
	driver.findElement(By.id("postal-code")).sendKeys("123456");
	driver.findElement(By.id("continue")).click();
}

@Test(priority=6)
public void checkoutPage() {
	WebElement productNameElement = driver.findElement(By.xpath("//*[@id=\"item_2_title_link\"]/div"));
	String productNameInCart = productNameElement.getText();
	String expectedproductName = "Sauce Labs Onesie";
	assertEquals(productNameInCart,expectedproductName);
	driver.findElement(By.id("finish")).click();
	JavascriptExecutor js = (JavascriptExecutor)driver;
    js.executeScript("scrollTo(0,0)");
}
@Test(priority=7)
public void takeScreenshot() throws IOException {	
	
	  File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	  System.out.println("Image Path"+ src.getAbsolutePath()); File destFile = new
	  File ("D:\\Suba_Workspace\\AAABED\\Screenshot\\img.jpg");
	  FileUtils.copyFile(src, destFile);
}

@AfterTest
public void logout() {
	driver.quit();
}

}
