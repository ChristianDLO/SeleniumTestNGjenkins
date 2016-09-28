package flightApp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.appium.java_client.AppiumDriver;
import utils.BaseDriver;
import utils.WindTunnelUtils;

public class HomePage {
	AppiumDriver<?> driver;

	public HomePage(AppiumDriver<?> driver) {
		this.driver = driver;
	}

	// Page elements
	public By lblRecentActivity = By.xpath("//*[@label=\"Recent Activity\"or @text=\"Recent Activity\"]");
	public By lblSkymiles = By.xpath("//*[@label=\"SKYMILES\"or @text=\"SKYMILES\"]");
	public By lblMyTrips= By.xpath("//*[@label=\"MY TRIPS\"or @text=\"MY TRIPS\"]");
	public By lblProfile = By.xpath("//*[@label=\"PROFILE\"or @text=\"PROFILE\"]");
	private By btnNoThanks = By.xpath("//*[@label=\"No, thanks\"or @text=\"No, thanks\"]");
	private By btnMore = By.xpath("//*[@content-desc=\"More options\"]");
	private By btnLogout = By.xpath("//*[@text=\"Log Out\"]");


	public void verifyItem(By by) {		
		if(BaseDriver.fluentWait(by, (AppiumDriver<WebElement>) driver, 10).isDisplayed()){	
			Assert.assertTrue(!driver.findElement(by).getText().isEmpty(),  by.toString() + " is displayed");	
			WindTunnelUtils.pointOfInterest(driver,  by.toString() + " is displayed", WindTunnelUtils.SUCCESS);
		}else{
			clickNoThanks();
			verifyItem(by);
		}
	}

	protected void clickNoThanks() {		
		if(BaseDriver.fluentWait(btnNoThanks, (AppiumDriver<WebElement>) driver, 5).isDisplayed()){	
			WebElement thanks = driver.findElement(btnNoThanks);		
			thanks.click();	
		}
	}

	public void clickMore() {		
		WebElement more = driver.findElement(btnMore);		
		more.click();	
	}

	public void clickLogout() {		
		WebElement out = driver.findElement(btnLogout);		
		out.click();	
	}
}
