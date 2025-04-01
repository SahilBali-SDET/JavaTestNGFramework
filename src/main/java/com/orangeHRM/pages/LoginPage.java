package com.orangeHRM.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.orangeHRM.actionDriver.ActionDriver;
import com.orangeHRM.base.BaseClass;


public class LoginPage {

	WebDriver driver;
	private ActionDriver actionDriver;
	public static final Logger logger = BaseClass.logger;
	private By orangeHRMLogo = By.cssSelector(".orangehrm-login-logo");
	private By usernameField = By.cssSelector("[name='username']");
	private By passwordField = By.cssSelector("[name='password']");
	private By loginButton = By.cssSelector("button[type='submit']");
	private By invalidLoginErrorMessage = By.cssSelector(".orangehrm-login-error p.oxd-alert-content-text");
	
	public LoginPage(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
	}

	/**
	 * Method to check if OrangeHRM logo is displayed
	 * @return boolean
	 */
	public boolean isOrangeHRMLogoDisplayed() {
		return actionDriver.isElementDisplayed(orangeHRMLogo);
	}
	
	/**
	 * Method to check if Username field is displayed
	 * @return boolean
	 */
	public boolean isUsernameFieldDisplayed() {
		return actionDriver.isElementDisplayed(usernameField);
	}
	
	/**
	 * Method to check if Password field is displayed
	 * @return boolean
	 */
	public boolean isPasswordFieldDisplayed() {
		return actionDriver.isElementDisplayed(passwordField);
	}
	
	/**
	 * Method to check if Login button is displayed
	 * @return boolean
	 */
	public boolean isLoginButtonDisplayed() {
		return actionDriver.isElementDisplayed(loginButton);
	}
	
	/**
	 * Method to verify OrangeHRM login page is loaded
	 */
	public void verifyOrangeHRMLoginPageIsLoaded() {
		Assert.assertTrue(isOrangeHRMLogoDisplayed(), "OrangeHRM logo is not displayed");
		Assert.assertTrue(isUsernameFieldDisplayed(), "Username field is not displayed");
		Assert.assertTrue(isPasswordFieldDisplayed(), "Password field is not displayed");
		Assert.assertTrue(isLoginButtonDisplayed(), "Login button is not displayed");
	}
	
    /**
	 * Method to login into the application
	 * @param username
	 * @param password
	 */
	public void loginIntoApplication(String username, String password) {
		actionDriver.enterTextInTextField(usernameField, username);
		actionDriver.enterTextInTextField(passwordField, password);
		actionDriver.clickOnElement(loginButton);
		logger.info("Logged in with username: " + username + " and password: " + password);
	}
	
	/**
	 * Method to verify invalid login error message
	 */
	public void verifyInvalidLoginErrorMessage() {
		Assert.assertTrue(actionDriver.isElementDisplayed(invalidLoginErrorMessage), "Invalid login error message is not displayed");
		String errorMessage = actionDriver.getElementText(invalidLoginErrorMessage);
		Assert.assertEquals(errorMessage, "Invalid credentials", "Invalid login error message is not as expected");
	}


}
