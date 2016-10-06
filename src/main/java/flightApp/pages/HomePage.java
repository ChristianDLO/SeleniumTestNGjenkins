package flightApp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.BaseDriver;
import utils.WindTunnelUtils;

public class HomePage {
	WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	// Page elements
	public By lblRecentActivity = By.xpath("//*[@label=\"Recent Activity\" or @text=\"Recent Activity\"]");
	public By lblSkymiles = By.xpath("//*[@label=\"SKYMILES\"or @text=\"SkyMiles\"]");
	public By lblMyTrips= By.xpath("//*[@label=\"MY TRIPS\"or @text=\"My Trips\"]");
	public By lblProfile = By.xpath("//*[@label=\"PROFILE\"or @text=\"Profile\"]");
	public By btnNoThanks = By.xpath("//*[@label=\"No, thanks\"or @text=\"No, thanks\"]");
	public By btnMore = By.xpath("//*[@content-desc=\"More options\" or @label=\"More\"]");
	public By btnLogout = By.xpath("//*[@text=\"Log Out\" or @label=\"LOG OUT\"]");


	public void verifyItem(By by) {		

		if (BaseDriver.wait(by, driver, 30).isDisplayed()) {
			WindTunnelUtils.pointOfInterest(driver,  by.toString() + " is displayed", WindTunnelUtils.SUCCESS);
		} else {			
			WindTunnelUtils.pointOfInterest(driver,  by.toString() + " is not displayed", WindTunnelUtils.FAILURE);
		}

	}

	public void handlePopups() {

		try {

			if (BaseDriver.wait(btnNoThanks, driver, 5).isDisplayed()) {			
				driver.findElement(btnNoThanks).click();
			}

		} catch (Exception e) {}

	}

}
