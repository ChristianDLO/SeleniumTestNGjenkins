package flightApp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.appium.java_client.android.AndroidDriver;
import utils.BaseDriver;
import utils.WindTunnelUtils;

public class LoginPage {
	AndroidDriver<WebElement> driver;

	// Page elements
	private By btnLogin = By.xpath("//*[@text=\"Log In\"]");
	private By searchValue   = By.xpath("//*[@resource-id=\"com.google.android.apps.gmm:id/edit_textbox\"]");


	public LoginPage(AndroidDriver<WebElement> driverAndroid) {
		this.driver = driverAndroid;
	}

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
		
		Assert.assertTrue(!driver.findElement(btnLogin).getText().isEmpty(), "Page loaded. Login is displayed");	
		System.out.println(driver.findElement(btnLogin).getText());
		//WindTunnelUtils.pointOfInterest(driver, "Page loaded. Login is displayed", WindTunnelUtils.SUCCESS);
	}

	/*private void expResult(String expected) {
		WebElement firstItem = driver.findElement(By.xpath("//*[@text=\""+expected+"\"]"));
		Assert.assertTrue(!(firstItem.equals("")), "Search result is displayed.");
		WindTunnelUtils.pointOfInterest(driver, "First search item is displayed", WindTunnelUtils.SUCCESS);
		firstItem.click();
	}*/
}
