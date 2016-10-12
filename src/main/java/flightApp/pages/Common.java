package flightApp.pages;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.BaseDriver;

public class Common extends BaseDriver {
	WebDriver driver;

	public Common(WebDriver driver) {
		this.driver = driver;
	}

	public By btnDone = By.xpath("//*[@label=\"DONE\"]");
	
	/**
	@description Function to Login
	@param login 
	@param user
	@param name
	 **/
	public void login(LoginPage login, WebDriver driver, Map<String, String> testParams, String user, String name) throws Exception {

		sendKeysbyXpath(login.txtUserName, driver, testParams, user, 2);
		sendKeysbyXpath(login.txtPassword, driver, testParams, getProperty().getProperty("perfecto.password"), 2);
		clickDoneorSend(driver, testParams);
		sendKeysbyXpath(login.txtLastName, driver, testParams, name, 2);
		clickDoneorSend(driver, testParams);
		clickbyIOSXpath(login.btnLogin, driver, testParams, false, 5);

	}

	/**
	@description Function to click done or send
	 **/
	public void clickDoneorSend(WebDriver driver, Map<String, String> testParams) throws Exception {

		clickifTextExistsAndroid("Send", driver, testParams, 3);
		clickbyIOSXpath(btnDone, driver, testParams, false, 3);

	}

	/**
	@description Function to logout
		@param home 
	 **/
	public void logout(HomePage home, WebDriver driver, Map<String, String> testParams) throws Exception {

		clickbyXpath(home.btnMore, driver, testParams, false, 4);
		clickbyXpath(home.btnLogout, driver, testParams, false, 5);

	}
}
