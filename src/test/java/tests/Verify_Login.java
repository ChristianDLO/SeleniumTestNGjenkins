package tests;

import java.awt.Desktop;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.exception.ReportiumException;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;

import io.appium.java_client.AppiumDriver;
import utils.BaseDriver;
import utils.WindTunnelUtils;

public class Verify_Login extends BaseDriver {

	WebDriver driver;
	ReportiumClient reportiumClient;
	@Test	
	public void FlightLogin() throws Exception {
		reportiumClient.testStep("App is opened");
		
		// Closes the What's new popup which is found on android devices alone.
		clickbyAndroidXpath(login.chkNew, driver, testParams, true, 60);
		checkXpath(login.txtUserName, driver, reportiumClient, testParams, 2);

		/*		//Proceed to login
		common.login(login, driver, testParams, "genesist", "Gnanadhas Isaac");

		//Verify contents
		checkText("Recent Activity", driver, 100);
		checkXpath(home.lblRecentActivity, driver, testParams, 60);
		checkXpath(home.lblSkymiles, driver, testParams, 1);
		checkXpath(home.lblMyTrips, driver, testParams, 1);
		checkXpath(home.lblProfile, driver, testParams, 1);

		//Logout
		common.logout(home, driver, testParams);		*/

	}

	@BeforeClass 
	public void beforeClass(ITestContext context) throws Exception, IOException{
		driver = driverObj(context);
		reportiumClient = createReportiumClient(driver);
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
			System.out.println("Single test report url = " + reportURL);
			System.out.println("Report url = " + reportiumClient.getReportUrl());

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


	@BeforeMethod(alwaysRun = true)
	public void beforeTest(Method method) {
		String testName = method.getDeclaringClass().getSimpleName() + "." + method.getName();
		reportiumClient.testStart(testName, new TestContext());
	}

	@AfterMethod(alwaysRun = true)
	public void afterTest(ITestResult testResult) {
		int status = testResult.getStatus();
		switch (status) {
		case ITestResult.FAILURE:
			reportiumClient.testStop(TestResultFactory.createFailure("An error occurred", testResult.getThrowable()));
			break;
		case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
		case ITestResult.SUCCESS:
			reportiumClient.testStop(TestResultFactory.createSuccess());
			break;
		case ITestResult.SKIP:
			// Ignore
			break;
		default:
			throw new ReportiumException("Unexpected status " + status);
		}
	}


}
