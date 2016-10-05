package tests;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import flightApp.pages.*;
import io.appium.java_client.AppiumDriver;
import utils.*;

public class Verify_Login extends BaseDriver {

	@Test	
	public void FlightLogin() throws Exception {

		//implicitWait(60);

		/* Verify Application */
		login.verifyPageLoad();
		//	implicitWait(5);

		//	clickbyXpath(login.enterLastName(name), timeOut)
/*
		login.enterUserName("genesist");
		login.enterPassword(this.property.getProperty("perfecto.password"));
		login.clickSendorDone();
		login.enterLastName("Gnanadhas Isaac");
		login.clickSendorDone();	
		login.clickLogin();
		home.verifyItem(home.lblRecentActivity);
		home.verifyItem(home.lblSkymiles);
		home.verifyItem(home.lblMyTrips);
		home.verifyItem(home.lblProfile);
		home.clickMore();
		home.clickLogout();	*/

		//		clickElementByXpath(Xpath, 5);
		//		
		//		WebElement testElement = findElementByXpath("Xpath", 5);
		//		if (testElement!=null) {
		//			testElement.click();
		//			/* Asser True */
		//		} else if (testElement==null) {
		//			/* Assert False */
		//		}
	}

	@BeforeClass 
	public void beforeClass(ITestContext context) throws Exception, IOException{
		driverObj(context);		
	}

	@AfterClass
	public void afterClass() throws IOException {

		try {
			super.cleanApp();
			/* Close the applicaiton based on the driver object */
			if(perfectoDriver.equalsIgnoreCase("RemoteWebDriver")){
				((RemoteWebDriver) driver).close();
			}else{
				((AppiumDriver<?>)driver).closeApp();
			}

			/* Report Generation*/
			String reportURL = (String)(((RemoteWebDriver) driver).getCapabilities().getCapability(WindTunnelUtils.WIND_TUNNEL_REPORT_URL_CAPABILITY));
			System.out.println(reportURL);
			BaseDriver.downloadReport((RemoteWebDriver)driver, "pdf", "C:\\temp\\" + this.getClass().getName() + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));

			/* Close the driver object */
			driver.close();
			/* Quits the driver object */
			driver.quit();

			/* Opens the report */
			Desktop desktop = Desktop.getDesktop();
			desktop.browse(URI.create(reportURL));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.quit();
		}
	}

}
