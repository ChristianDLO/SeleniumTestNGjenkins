package flightApp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.appium.java_client.AppiumDriver;

public class LoginPage {
	AppiumDriver<WebElement> driver;

	public LoginPage(WebDriver driver) {
		this.driver = (AppiumDriver<WebElement>) driver;
	}

	// Page elements
	public By btnLogin = By.xpath("//*[@text=\"Log In\" or @label=\"Log In\"]");
	public By btnDone = By.xpath("//*[@label=\"DONE\"]");
	public By txtUserName   = By.xpath("//*[@text=\"SkyMiles® Number or Username\" or @value=\"SkyMiles® Number or Username\"]");
	public By txtPassword   = By.xpath("//*[@password=\"true\" or @value=\"Password\"]");
	public By txtLastName   = By.xpath("//*[@value=\"Last name\" or @text=\"Last Name\"]");
	public By chkNew        = By.xpath("//*[@resource-id=\"com.delta.mobile.android:id/action_done\"]");

}
