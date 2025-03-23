package com.orangeHRM.test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeHRM.base.BaseClass;
import com.orangeHRM.pages.HomePage;
import com.orangeHRM.pages.LoginPage;

public class MyInfoPageTest extends BaseClass {

	private static String USERNAME;
	private static String PASSWORD;
	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setup() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
    	USERNAME = prop.getProperty("Username");
    	PASSWORD = prop.getProperty("Password");
	}
	
	@AfterMethod
	public void logout() throws Exception {
		try {
			homePage.logout();
		} catch (Exception e) {
			throw new Exception("Unable to logout due to: " + e.getMessage());
		}
	}
	
	@Test
	public void verifyLoginWithCorrectCredentials() {
		loginPage.verifyOrangeHRMLoginPageIsLoaded();
		loginPage.loginIntoApplication(USERNAME, PASSWORD);
		homePage.verifyHomePageIsLoaded();
	}
	
	@Test
	public void verifyLoginWithInCorrectCredentials() {
		loginPage.verifyOrangeHRMLoginPageIsLoaded();
		loginPage.loginIntoApplication(USERNAME, "123");
		loginPage.verifyInvalidLoginErrorMessage();
	}
	
}
