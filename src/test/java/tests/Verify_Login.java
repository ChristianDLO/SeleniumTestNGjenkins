package tests;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import java.io.IOException;

import org.testng.ITestContext;
import flightApp.pages.*;
import utils.*;

public class Verify_Login extends BaseDriver{

	@Test	
	public void FlightLogin() throws Exception {
		LoginPage login = new LoginPage(this.driver);	
		HomePage home = new HomePage(this.driver);		
		implicitWait(60);
		login.verifyPageLoad();
		implicitWait(5);
		login.enterUserName("genesist");
		login.enterPassword(this.property.getProperty("perfecto.password"));
		login.clickSendorDone();
		login.enterLastName("Gnanadhas Isaac");
		login.clickSendorDone();	
		login.clickLogin();
		implicitWait(90);
		home.verifyItem(home.lblRecentActivity);
		implicitWait(5);
		home.verifyItem(home.lblSkymiles);
		home.verifyItem(home.lblMyTrips);
		home.verifyItem(home.lblProfile);
		home.clickMore();
		home.clickLogout();		
	}

	@BeforeClass 
	public void beforeClass(ITestContext context) throws Exception, IOException{
		driverObj(context);		
	}

	@AfterClass
	public void afterClass() throws IOException {
		cleanApp();
		closeApp();
		tearDown();
	}

}
