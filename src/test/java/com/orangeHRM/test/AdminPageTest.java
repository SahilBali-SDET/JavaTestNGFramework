package com.orangeHRM.test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeHRM.base.BaseClass;
import com.orangeHRM.pages.AdminPage;
import com.orangeHRM.pages.HomePage;
import com.orangeHRM.pages.LoginPage;

public class AdminPageTest extends BaseClass {

	private static String USERNAME;
	private static String PASSWORD;
	private LoginPage loginPage;
	private HomePage homePage;
	private AdminPage adminPage;
	
	@BeforeClass
	public void setup() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
		adminPage = new AdminPage(getDriver());
		USERNAME = prop.getProperty("Username");
		PASSWORD = prop.getProperty("Password");
		loginPage.verifyOrangeHRMLoginPageIsLoaded();
		loginPage.loginIntoApplication(USERNAME, PASSWORD);
		homePage.verifyHomePageIsLoaded();
    	homePage.clickOnSpecificTabOnLeftPanel("Admin");
	}
	
	@BeforeMethod
	public void naviagteToAdminPage() {
		homePage.clickOnSpecificTabOnLeftPanel("Admin");
		adminPage.verifyAdminPageIsLoaded();
	}
	
	@Test(priority = 0)
	public void verifyRecordsAreUpdatedWithCorrectDetails() {
		adminPage.searchEmployee(USERNAME);
		adminPage.verifyCorrectEmployeeIsDisplayed(USERNAME);
	}
	
	@Test(priority = 1)
	public void verifyRecordsAreEmptyIfNoRecordsArePresent() {
		adminPage.clickOnResetButton();
		adminPage.searchEmployee("123");
		adminPage.verifyRecordsTableIsEmpty();
	}
	
}
