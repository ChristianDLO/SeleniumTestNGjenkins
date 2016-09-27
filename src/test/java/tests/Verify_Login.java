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
		init(10);
		launchApp();
//		handlePopups();		
		LoginPage login = new LoginPage(this.driver);
		login.verifyPageLoad();
		implicitWait(5);
		login.enterUserName("test");
		login.enterPassword(this.property.getProperty("perfecto.password"));
		login.clickLogin();
		closeApp();
	}

	@BeforeClass 
	public void beforeClass(ITestContext context) throws Exception, IOException{
		driverObj(context);		
	}

	@AfterClass
	public void afterClass() throws IOException {
		tearDown();
	}

}
