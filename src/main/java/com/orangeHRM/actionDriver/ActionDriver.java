package com.orangeHRM.actionDriver;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orangeHRM.base.BaseClass;
import com.orangeHRM.utilities.LoggerManager;
import com.orangeHRM.utilities.Utility;

public class ActionDriver {
	
	private WebDriver driver;
	public static final Logger logger = BaseClass.logger;
	
	public ActionDriver(WebDriver driver) {
		this.driver = driver;
	}
	
	// Method to navigate to URL
	public void navigateToURL(String url) {
		driver.get(url);
	}

    // Method to wait for element to be visible
    public void waitForElementToBeVisible(By locator) throws TimeoutException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Utility.MEDIUM_WAIT));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            throw new TimeoutException("Element not visible: " + locator);
        }
    }
    
	// Method to wait for element to be invisible
    public void waitForElementToBeInvisible(By locator) throws TimeoutException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Utility.SMALL_WAIT));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            throw new TimeoutException("Element didn't get invisible within " + Utility.SMALL_WAIT + " seconds");
        }
    }

    // Method to wait for element to be clickable
    public void waitForElementToBeClickable(By locator) throws TimeoutException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Utility.SMALL_WAIT));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            throw new TimeoutException("Element not clickable: " + locator);
        }
    }
    
    // Method to wait until title of page contains
    public void waitUntilPageTitleContains(String expectedTitle) throws TimeoutException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Utility.SMALL_WAIT));
            wait.until(ExpectedConditions.titleContains(expectedTitle));
        } catch (TimeoutException e) {
            throw new TimeoutException("Page title did not contain " + expectedTitle);
        }
    }
    
	// Method to wait and return found element
    public WebElement waitAndFindElement(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Utility.MEDIUM_WAIT));
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            throw new TimeoutException("locator not visible: " + locator);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("locator not visible: " + locator);
        }
    }

    // Method to wait and return list of elements found
    public List<WebElement> waitAndFindElements(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Utility.SMALL_WAIT));
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } catch (TimeoutException e) {
            throw new TimeoutException("Elements not found: " + locator);
        }
    }

	/** Wait and return element found within parent element */
    public WebElement waitAndFindElementWithinElement(WebElement parentElement, By childElementLocator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Utility.MEDIUM_WAIT));
            return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(parentElement, childElementLocator));
            // return parentElement.findElement(childElementLocator);
        } catch (TimeoutException e) {
            throw new TimeoutException("Locator not visible: " + childElementLocator);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Locator not visible: " + childElementLocator);
        }
    }

    /** Wait and return list of elements found within parent element */
    public List<WebElement> waitAndFindElementsWithinElement(WebElement parentElement, By childElementLocator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Utility.SMALL_WAIT));
            wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(parentElement, childElementLocator));
            return parentElement.findElements(childElementLocator);
        } catch (NoSuchElementException | TimeoutException e) {
            throw new TimeoutException("Elements not found: " + childElementLocator);
        }
    }
    
    /** Check if element is displayed within parent element */
    public boolean isNestedElementDisplayed(WebElement parentElement, By childElementLocator) {
        try {
            return waitAndFindElementWithinElement(parentElement, childElementLocator).isDisplayed();
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }
    
    // Method to click on element
    public void clickOnElement(By locator) {
        WebElement element = waitAndFindElement(locator);
        element.click();
        logger.info("Clicked on element: " + locator);
    }

    // Method to enter text in text field
    public void enterTextInTextField(By locator, String text) {
        WebElement element = waitAndFindElement(locator);
        element.sendKeys(Keys.CONTROL+"a");
        element.sendKeys(Keys.DELETE);
        element.sendKeys(text);
        logger.info("Entered text: " + text + " in element: " + locator);
    }

    // Method to get text of an element
    public String getElementText(By locator) {
    	logger.info("Getting text of element: " + locator);
        return waitAndFindElement(locator).getText();
    }

    // Method to get attribute of an web element
    public String getElementAttribute(By locator, String attribute) {
    	logger.info("Getting attribute: " + attribute + " of element: " + locator);
    	return waitAndFindElement(locator).getDomAttribute(attribute);
    }

    // Method to check if element is displayed
    public boolean isElementDisplayed(By locator) {
        try {
            return waitAndFindElement(locator).isDisplayed();
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }

    // Method to check if element is displayed
    public boolean isElementPresent(By locator) {
        try {
        	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Utility.SMALL_WAIT));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }
    
    // Method to get JS validation message
    public String getJSValidation(By locator) {
		WebElement element = waitAndFindElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String validationMessage = (String) js.executeScript("return arguments[0].validationMessage;", element);
        return validationMessage;
    }

    // Method to upload file
	public void uploadFile(By locator, String fileName) {
		String filePath = System.getProperty("user.dir") + "/src/test/resources/testData/" + fileName;
		WebElement element = waitAndFindElement(locator);
		element.sendKeys(filePath);
		logger.info("Uploaded file " + fileName + " successfully!");
	}

	// Method to select option from Select dropdown by text
	public void selectOptionFromDropdownByText(By locator, String text) {
		WebElement dropdown = waitAndFindElement(locator);
		Select sel = new Select(dropdown);
		sel.selectByVisibleText(text);
	}

    // Handle alert actions
    
    // return alert present on page
    public Alert getAlert() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Utility.MEDIUM_WAIT));
        return wait.until(ExpectedConditions.alertIsPresent());
    }
    
    // accept alert
    public void acceptAlert() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Utility.SMALL_WAIT));
        wait.until(ExpectedConditions.alertIsPresent()).accept();
    }

    // dismiss alert
    public void dismissAlert() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Utility.SMALL_WAIT));
        wait.until(ExpectedConditions.alertIsPresent()).dismiss();
    }

    //get alert text
    public String getAlertText() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Utility.SMALL_WAIT));
        return wait.until(ExpectedConditions.alertIsPresent()).getText();
    }
    
    // Handle window
    
    // Method to switch to a new window
    public void switchToNewWindow() {
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        logger.info("Switched to new window");
    }

    // Method to switch to the main window
    public void switchToMainWindow() {
        String mainWindow = driver.getWindowHandles().iterator().next();
        driver.switchTo().window(mainWindow);
        logger.info("Switched to main window");
    }

    // Method to close current window and switch back to the main window
    public void closeCurrentWindow() {
        driver.close();
        switchToMainWindow();
        logger.info("Closed current window and switched back to main window");
    }
    
    // Handle Frames
    
    /** Switch to the frame by index */
    public void switchToFrameByIndex(int frameIndex) {
        driver.switchTo().frame(frameIndex);
        logger.info("Switched to frame: " + frameIndex);
    }

    /** Switch to the frame by name */
    public void switchToFrameByName(String frameName) {
        driver.switchTo().frame(frameName);
        logger.info("Switched to frame: " + frameName);
    }

    /** Switch to the frame by ID */
    public void switchToFrameById(String frameId) {
        driver.switchTo().frame(frameId);
        logger.info("Switched to frame: " + frameId);
    }

    /** Switch to the frame by locator */
    public void switchToFrameByLocator(By frameLocator) {
        WebElement frame = driver.findElement(frameLocator);
        driver.switchTo().frame(frame);
        logger.info("Switched to frame: " + frameLocator);
    }

    /** Switch back to the main content */
    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
        logger.info("Switched back to default content");
    }
    
    // Action Class
    
    // Hover over element
    public void hoverOverElement(By locator) {
    	Actions action = new Actions(driver);
    	WebElement element = waitAndFindElement(locator);
    	action.moveToElement(element).perform();	
    	logger.info("Hovered over element: " + locator);
    }
    
    // Hover and click on element
    public void hoverAndClickOnElement(By locator) {
    	Actions action = new Actions(driver);
    	WebElement element = waitAndFindElement(locator);
    	action.moveToElement(element).click().perform();
    	logger.info("Hovered and clicked on element: " + locator);
    }
    
    // Right click on element
    public void rightClickOnElement(By locator) {
    	Actions action = new Actions(driver);
    	WebElement element = waitAndFindElement(locator);
    	action.contextClick(element).perform();
    	logger.info("Right clicked on element: " + locator);
    }
    
    // double click on element
    public void doubleClickOnElement(By locator) {
    	Actions action = new Actions(driver);
    	WebElement element = waitAndFindElement(locator);
    	action.doubleClick(element).perform();	
    	logger.info("Double clicked on element: " + locator);
    }
    
    // enter word in capital letter
    public void enterValueInTextFieldInCapitalLetter(By locator, String text) {
    	Actions action = new Actions(driver);
    	WebElement textField = waitAndFindElement(locator);
        // Hold Shift and type the text
        action.keyDown(Keys.SHIFT)
               .sendKeys(textField, text)
               .keyUp(Keys.SHIFT)
               .perform();
    }
    
	// Method to refresh page
	public void refreshPage() {
		driver.navigate().refresh();
	}

	// Method to determine type of element
    public String getFieldType(By locator, String fieldName) {
        String fieldType = "";
        String eleType = getElementAttribute(locator, "type");
        if (eleType != null) {
        	if (eleType.equalsIgnoreCase("text")) {
                fieldType = "Textbox";
            } else if (eleType.equalsIgnoreCase("radio")) {
                fieldType = "Radio Button";
            }
		}
		else if (eleType == null) {
        	WebElement element = waitAndFindElement(locator);
    		Select sel = new Select(element);
    		List<WebElement> options = sel.getOptions();
//    		List<WebElement> options = element.findElements(By.cssSelector("option[value]"));
			if (options.size() > 0) {
				fieldType = "Dropdown";
			}
			else {
				fieldType = "Unknown";
				logger.error("Not able to identifty Field type, please use tagName method");
			}
        }
        logger.info("Field: " + fieldName + " -> Type: " + fieldType);
        return fieldType;
    }

    /** Method to scroll into view */
	public void scrollIntoView(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
}
