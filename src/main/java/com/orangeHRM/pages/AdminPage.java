package com.orangeHRM.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.orangeHRM.actionDriver.ActionDriver;
import com.orangeHRM.base.BaseClass;

public class AdminPage {

	WebDriver driver;
	private ActionDriver actionDriver;

	private By systemUsersContainerHeader = By.xpath("//h5[text()='System Users']");
	private By userNameField = By.xpath("//div[label[text()='Username']]//following-sibling::div/input");
	private By employeeNameField = By.xpath("//div[label[text()='Employee Name']]//following-sibling::div//input");
	private By userRoleField = By.xpath("//div[label[text()='User Role']]//following-sibling::div//div[@class='oxd-select-text-input']");
	private By statusField = By.xpath("//div[label[text()='Status']]//following-sibling::div//div[@class='oxd-select-text-input']");
	private By resetButton = By.xpath("//button[normalize-space()='Reset']");
	private By searchButton = By.xpath("//button[normalize-space()='Search']");
	private By addButton = By.xpath("//button[normalize-space()='Add']");
	
	private By recordTable = By.cssSelector(".orangehrm-container");
	private By employeeNames = By.cssSelector(".oxd-table-cell:nth-child(2) div");

	
	public AdminPage(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
	}
	
	/**
	 * Method to verify if Admin page is loaded
	 */
	public void verifyAdminPageIsLoaded() {
		Assert.assertTrue(actionDriver.isElementDisplayed(systemUsersContainerHeader), "System Users container header is not displayed");
		Assert.assertTrue(actionDriver.isElementDisplayed(userNameField), "Username field is not displayed");
		Assert.assertTrue(actionDriver.isElementDisplayed(employeeNameField), "Employee Name field is not displayed");
		Assert.assertTrue(actionDriver.isElementDisplayed(userRoleField), "User Role field is not displayed");
		Assert.assertTrue(actionDriver.isElementDisplayed(statusField), "Status field is not displayed");
		Assert.assertTrue(actionDriver.isElementDisplayed(resetButton), "Reset button is not displayed");
		Assert.assertTrue(actionDriver.isElementDisplayed(searchButton), "Search button is not displayed");
		Assert.assertTrue(actionDriver.isElementDisplayed(addButton), "Add button is not displayed");
	}
	
	/**
	 * Method to search employee
	 * @param userName
	 */
	public void searchEmployee(String userName) {
		actionDriver.enterTextInTextField(userNameField, userName);
		actionDriver.clickOnElement(searchButton);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to verify Records table is updated with correct details
	 * @param employeeName
	 */
	public void verifyCorrectEmployeeIsDisplayed(String employeeName) {
		WebElement recordTableEle = actionDriver.waitAndFindElement(recordTable);
		actionDriver.scrollIntoView(recordTableEle);
		List<WebElement> employeeNamesEle = actionDriver.waitAndFindElementsWithinElement(recordTableEle, employeeNames);
		System.out.println("Record table size: " + employeeNamesEle.size());
		boolean isEmployeeFound = false;
		for (WebElement name : employeeNamesEle) {
			if (name.getText().equalsIgnoreCase(employeeName)) {
				isEmployeeFound = true;
				break;
			}
		}
		Assert.assertTrue(isEmployeeFound, "Employee is not found in the list");
	}
	
	/**
	 * Method to verify if records table is empty
	 */
	public void verifyRecordsTableIsEmpty() {
		WebElement recordTableEle = actionDriver.waitAndFindElement(recordTable);
		Assert.assertEquals(false, actionDriver.isNestedElementDisplayed(recordTableEle, employeeNames), "Records table is not empty");
	}

	/**
	 * Method to click on Reset button
	 */
	public void clickOnResetButton() {
		actionDriver.clickOnElement(resetButton);
		
	}
}
