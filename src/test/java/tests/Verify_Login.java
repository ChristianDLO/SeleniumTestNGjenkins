package tests;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import utils.BaseDriver;
import utils.WindTunnelUtils;

public class Verify_Login extends BaseDriver {

	@Test	
	public void FlightLogin() throws Exception {
		/*Proceed to login*/
		login();

		/*Verify contents*/
		checkXpath(home.lblRecentActivity, 30);
		checkXpath(home.lblSkymiles, 1);
		checkXpath(home.lblMyTrips, 1);
		checkXpath(home.lblProfile, 1);

		/*Logout*/
		logout();		
		
	}

	@BeforeClass 
	public void beforeClass(ITestContext context) throws Exception, IOException{
		driverObj(context);		
		initPages();
	}


	@BeforeMethod
	public void beforeTests() throws Exception {
		// Closes the What's new popup which is found on android devices alone.
		clickbyAndroidXpath(login.chkNew, false, 5);
		checkXpath(login.txtUserName, 2);
		
	}

	@AfterClass
	public void afterClass() throws IOException {

		try {			
			/* Close the applicaiton based on the driver object */
			if(perfectoDriver.equalsIgnoreCase("RemoteWebDriver")){
				((RemoteWebDriver) driver).close();
			}else{
				cleanApp();
				((AppiumDriver<?>)driver).closeApp();
			}

			/* Close the driver object */
			driver.close();			

			/* Report Generation*/
			String reportURL = (String)(((RemoteWebDriver) driver).getCapabilities().getCapability(WindTunnelUtils.WIND_TUNNEL_REPORT_URL_CAPABILITY));
			System.out.println(reportURL);
			BaseDriver.downloadReport((RemoteWebDriver)driver, "pdf", "C:\\temp\\" + this.getClass().getName() + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));

			/* Quits the driver object */
			driver.quit();

			/* Opens the report */
			Desktop desktop = Desktop.getDesktop();
			desktop.browse(URI.create(reportURL));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.quit();
			driver.quit();
		}
	}

	public void login() throws Exception {
		
		sendKeysbyXpath(login.txtUserName, "genesist", 2);
		sendKeysbyXpath(login.txtPassword, this.property.getProperty("perfecto.password"), 2);
		clickDoneorSend();
		sendKeysbyXpath(login.txtLastName, "Gnanadhas Isaac", 2);
		clickDoneorSend();
		clickbyIOSXpath(login.btnLogin, false, 5);
		
	}

	public void clickDoneorSend() throws Exception {
		
		clickifTextExistsAndroid("Send", 3);
		clickbyIOSXpath(login.btnDone, false, 3);
		
	}

	public void logout() throws Exception {
		
		clickbyXpath(home.btnMore, false, 4);
		clickbyXpath(home.btnLogout, false, 5);
		
	}


}
