package com.orangeHRM.test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.orangeHRM.base.BaseClass;
import com.orangeHRM.pages.HomePage;
import com.orangeHRM.pages.LoginPage;


public class LoginPageTest extends BaseClass {

	private static String USERNAME;
	private static String PASSWORD;
	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeClass
	public void setup() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
    	USERNAME = prop.getProperty("Username");
    	PASSWORD = prop.getProperty("Password");
	}
	

	@Test(priority = 0)
	public void verifyLoginWithInCorrectCredentials() {
		loginPage.verifyOrangeHRMLoginPageIsLoaded();
		loginPage.loginIntoApplication(USERNAME, "123");
		loginPage.verifyInvalidLoginErrorMessage();
	}
	
	
	@Test(priority = 1)
	public void verifyLoginWithCorrectCredentials() {
		loginPage.verifyOrangeHRMLoginPageIsLoaded();
		loginPage.loginIntoApplication(USERNAME, PASSWORD);
		homePage.verifyHomePageIsLoaded();
	}
}
