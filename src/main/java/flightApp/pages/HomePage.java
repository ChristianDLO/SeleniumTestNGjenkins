package flightApp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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

}
