package tests;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import flightApp.pages.*;
import utils.DataParameterize;
import utils.PerfectoLabUtils;

public class Verify_Login extends PerfectoLabUtils{

	@Test	
	public void FlightLogin() throws Exception {
		init();			
		launchApp(params);
		handlePopups();
		closeApp(params);
	}

	@BeforeClass 
	public void beforeClass(ITestContext context) throws IOException{
		driverObj(context);
	}

	@AfterClass
	public void afterClass() {
		tearDown();
	}


}
