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
	private By btnLogin = By.xpath("//*[@text=\"Log In\" or @label=\"Log In\"]");
	private By btnDone = By.xpath("//*[@label=\"DONE\"]");
	private By txtUserName   = By.xpath("//*[@text=\"SkyMiles® Number or Username\" or @value=\"SkyMiles® Number or Username\"]");
	private By txtPassword   = By.xpath("//*[@password=\"true\" or @value=\"Password\"]");
	private By txtLastName   = By.xpath("//*[@value=\"Last name\" or @text=\"Last Name\"]");
	private By chkNew        = By.xpath("//*[@resource-id=\"com.delta.mobile.android:id/action_done\"]");


	public void verifyPageLoad() {		
		try{
			if(BaseDriver.fluentWait(txtUserName, (AppiumDriver<WebElement>) driver, 30).isDisplayed()){	
				Assert.assertTrue(!driver.findElement(txtUserName).getText().isEmpty(), "Page loaded. Login is displayed");	
				WindTunnelUtils.pointOfInterest(driver, "Page loaded. User field is displayed", WindTunnelUtils.SUCCESS);
			}
		}catch(Exception e){
			if(BaseDriver.driverType.equalsIgnoreCase("Android")){				
				if(BaseDriver.fluentWait(chkNew, (AppiumDriver<WebElement>) driver, 5).isDisplayed()){						
					handlePopups();
					verifyPageLoad();
				}else{
					WindTunnelUtils.pointOfInterest(driver, "Page not loaded. User field is not displayed", WindTunnelUtils.FAILURE);
				}
			}else{
				WindTunnelUtils.pointOfInterest(driver, "Page not loaded. User field is not displayed", WindTunnelUtils.FAILURE);
			}

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

	public void clickLogin() {
		if(BaseDriver.driverType.equalsIgnoreCase("ios")){			
			WebElement login = driver.findElement(btnLogin);		
			login.click();
		}
	}

	public void clickSendorDone() throws InterruptedException{
		if(BaseDriver.driverType.equalsIgnoreCase("android")){
			Thread.sleep(1000);
			Map<String, Object> send = new HashMap<>();
			send.put("label", "Send");
			send.put("timeout", "30");
			driver.executeScript("mobile:button-text:click", send);
		}else{
			WebElement done = driver.findElement(btnDone);		
			done.click();
		}
	}

	public void handlePopups(){
		new PopUpUtils(driver).addNativePopupBtns(chkNew).clickOnPopUpIfFound();	
	}
}
