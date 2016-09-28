package flightApp.pages;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.appium.java_client.AppiumDriver;
import utils.BaseDriver;
import utils.PopUpUtils;
import utils.WindTunnelUtils;

public class LoginPage {
	AppiumDriver<?> driver;

	public LoginPage(AppiumDriver<?> driver) {
		this.driver = driver;
	}

	// Page elements
	private By btnLogin = By.xpath("//*[@text=\"Log In\"]");
	private By btnDone = By.xpath("//*[@text=\"DONE\"]");
	private By txtUserName   = By.xpath("//*[@text=\"SkyMiles® Number or Username\" or @value=\"SkyMiles® Number or Username\"]");
	private By txtPassword   = By.xpath("//*[@password=\"true\" or @value=\"Password\"]");
	private By txtLastName   = By.xpath("//*[@value=\"Last name\" or @text=\"Last Name\"]");

	public void handlePopups(){
		new PopUpUtils(driver).addNativePopupBtns(
				By.xpath("//*[@resource-id=\"com.delta.mobile.android:id/action_done\"]"),
				By.xpath("//*[@label=\"DONE\"]"),
				By.xpath("//*[@label=\"No, thanks\"]"))
		.clickOnPopUpIfFound();	
	}

	public void verifyPageLoad() {
		if(BaseDriver.fluentWait(btnLogin, (AppiumDriver<WebElement>) driver, 60).isDisplayed()){	
			Assert.assertTrue(!driver.findElement(btnLogin).getText().isEmpty(), "Page loaded. Login is displayed");	
			WindTunnelUtils.pointOfInterest(driver, "Page loaded. Login is displayed", WindTunnelUtils.SUCCESS);
		}else{
			WindTunnelUtils.pointOfInterest(driver, "Page not loaded. Login is not displayed", WindTunnelUtils.FAILURE);
		}
	}

	public void enterUserName(String user) {
		WebElement userName = driver.findElement(txtUserName);		
		userName.click();
		userName.sendKeys(user);
	}

	public void enterPassword(String pass) {
		WebElement password = driver.findElement(txtPassword);		
		password.click();
		password.sendKeys(pass);
	}

	public void enterLastName(String name) {
		WebElement lastName = driver.findElement(txtLastName);		
		lastName.click();
		lastName.sendKeys(name);
	}

	public void clickLogin(String type) {
		if(type.equalsIgnoreCase("ios")){			
			WebElement login = driver.findElement(btnLogin);		
			login.click();
		}
	}

	public void clickSendorDone(String type){
		if(type.equalsIgnoreCase("android")){
			Map<String, Object> send = new HashMap<>();
			send.put("label", "Send");
			send.put("timeout", "30");
			driver.executeScript("mobile:button-text:click", send);
		}else{
			WebElement done = driver.findElement(btnDone);		
			done.click();
		}
	}
}
