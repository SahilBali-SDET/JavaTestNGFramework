package com.orangeHRM.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.orangeHRM.actionDriver.ActionDriver;
import com.orangeHRM.base.BaseClass;

public class HomePage {
	
	WebDriver driver;
	private ActionDriver actionDriver;

	private By sidePanel = By.cssSelector("[aria-label='Sidepanel']");
	private By sidePanelItems = By.cssSelector("ul.oxd-main-menu li a");
	private By sidePanelItemsName = By.cssSelector("span:nth-child(2)");
	private By userDropdownTab = By.cssSelector(".oxd-userdropdown-tab");
	private By userDropdownMenuItems = By.cssSelector(".oxd-dropdown-menu li a");
	
	
	public HomePage(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
	}
	
	/**
	 * Method to verify if Home page is loaded
	 */
	public void verifyHomePageIsLoaded() {
		Assert.assertTrue(actionDriver.isElementDisplayed(sidePanel), "Side panel is not displayed");
		Assert.assertTrue(actionDriver.isElementDisplayed(userDropdownTab), "User dropdown tab is not displayed");
	}
	
	/**
	 * Method to logout from the application
	 */
	public void logout() {
		actionDriver.clickOnElement(userDropdownTab);
		BaseClass.staticWait(2);
		List<WebElement> items = actionDriver.waitAndFindElements(userDropdownMenuItems);
		for (WebElement item : items) {
			if (item.getText().equalsIgnoreCase("Logout")) {
				item.click();
				break;
			}
		}
	}

	/**
	 * Method to click on specific tab on side navigation panel
	 */
	public void clickOnSpecificTabOnLeftPanel(String tabName) {
		WebElement sidePanelElement = actionDriver.waitAndFindElement(sidePanel);
		List<WebElement> items = actionDriver.waitAndFindElementsWithinElement(sidePanelElement, sidePanelItems);
		for (WebElement item : items) {
			WebElement itemName = actionDriver.waitAndFindElementWithinElement(item, sidePanelItemsName);
			if (itemName.getText().equalsIgnoreCase(tabName)) {
				item.click();
				break;
			}
		}
	}
	
}
