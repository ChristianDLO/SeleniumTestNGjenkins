package tests;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import utils.BaseDriver;
import utils.WindTunnelUtils;

public class Verify_Login extends BaseDriver {
	WebDriver driver;
	@Test	
	public void FlightLogin() throws Exception {

		// Closes the What's new popup which is found on android devices alone.
		clickbyAndroidXpath(login.chkNew, driver, testParams, true, 60);
		checkXpath(login.txtUserName, driver, testParams, 2);

		//Proceed to login
		common.login(login, driver, testParams, "genesist", "Gnanadhas Isaac");

		//Verify contents
		checkText("Recent Activity", driver, 100);
		checkXpath(home.lblRecentActivity, driver, testParams, 60);
		checkXpath(home.lblSkymiles, driver, testParams, 1);
		checkXpath(home.lblMyTrips, driver, testParams, 1);
		checkXpath(home.lblProfile, driver, testParams, 1);

		//Logout
		common.logout(home, driver, testParams);		

	}

	@BeforeClass 
	public void beforeClass(ITestContext context) throws Exception, IOException{
		driver = driverObj(context);
		initPages();

	}


	@AfterClass
	public void afterClass() throws IOException {

		try {			
			/* Close the application & driver based on the driver object */
			if (perfectoDriver.equalsIgnoreCase("RemoteWebDriver")) {
				Map<String, Object> appParam = new HashMap<>();
				appParam.put("name", appDetail);
				((RemoteWebDriver) driver).executeScript("mobile:application:close", appParam);

			} else {
				cleanApp();
				((AppiumDriver<?>)driver).closeApp();
				((AppiumDriver<?>)driver).close();
			}		

			/* Report Generation*/
			String reportURL = (String)(((RemoteWebDriver) driver).getCapabilities().getCapability(WindTunnelUtils.WIND_TUNNEL_REPORT_URL_CAPABILITY));
			System.out.println(reportURL);
			BaseDriver.downloadReport((RemoteWebDriver) driver, "pdf", "C:\\temp\\" + this.getClass().getName() + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));

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
