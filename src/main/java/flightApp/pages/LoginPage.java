package flightApp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.appium.java_client.AppiumDriver;
import utils.BaseDriver;
import utils.WindTunnelUtils;

public class LoginPage {
	AppiumDriver<?> driver;
	
	public LoginPage(AppiumDriver<?> driver) {
	this.driver = driver;
}

	// Page elements
	private By btnLogin = By.xpath("//*[@text=\"Log In\"]");
	private By txtUserName   = By.xpath("//*[@text=\"SkyMiles® Number or Username\" or @value=\"SkyMiles® Number or Username\"]");
	private By txtPassword   = By.xpath("//*[@password=\"true\" or @value=\"Password\"]");


	

	/*public void searchResult(String expected) {
		if(BaseDriver.fluentWait(By.xpath("//*[@text=\""+expected+"\"]"), driver, 60).isDisplayed()){	
			expResult(expected);			
		}else{
			//WindTunnelUtils.pointOfInterest(driver, "First search item is not displayed", WindTunnelUtils.FAILURE);			
		}
		if(BaseDriver.fluentWait(dist, driver, 60).isDisplayed()){ 
			WebElement timeTaken = driver.findElement(dist);
			if(!(timeTaken.getText().isEmpty())){		
			}else{			
				WindTunnelUtils.pointOfInterest(driver, "TimeTaken is not displayed", WindTunnelUtils.FAILURE);
			}	 
		}else{
			WindTunnelUtils.pointOfInterest(driver, "Distance button object is not displayed", WindTunnelUtils.FAILURE);
		}
	}*/

	public void verifyPageLoad() {
		if(BaseDriver.fluentWait(btnLogin, (AppiumDriver<WebElement>) driver, 60).isDisplayed()){	
			System.out.println("Pass");
		}else{
			System.out.println("Fail");
		}
		Assert.assertTrue(!driver.findElement(btnLogin).getText().isEmpty(), "Page loaded. Login is displayed");	
		System.out.println(driver.findElement(btnLogin).getText());
		WindTunnelUtils.pointOfInterest(driver, "Page loaded. Login is displayed", WindTunnelUtils.SUCCESS);
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
	
	public void clickLogin() {
		WebElement login = driver.findElement(btnLogin);		
		login.click();
	}
}
