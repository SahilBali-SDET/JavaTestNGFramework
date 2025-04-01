package com.orangeHRM.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.orangeHRM.actionDriver.ActionDriver;
import com.orangeHRM.utilities.LoggerManager;


public class BaseClass {

		protected static Properties prop;
		//protected static WebDriver driver;
		//private static ActionDriver actionDriver;
		// For parallel execution using ThreadLocal
		private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
		private static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();
		public static final Logger logger = LoggerManager.getLogger(BaseClass.class);
		
		/**
		 * Method to load config file
		 * @throws IOException
		 */
		@BeforeSuite
		public void loadConfig() throws IOException {
			prop = new Properties();
			FileInputStream fs = new FileInputStream("src/main/resources/config.properties");
			prop.load(fs);
			logger.info("Config file loaded successfully");
		}
		
		/**
		 * Method to launch browser
		 */
		private synchronized void launchBrowser() {
			String browser = prop.getProperty("Browser");
			
			if (browser.equalsIgnoreCase("chrome")) {
				// driver = new ChromeDriver();
				// For parallel execution using ThreadLocal
				driver.set(new ChromeDriver());
				logger.info("Chrome browser initialized");
			} else if (browser.equalsIgnoreCase("firefox")) {
				// driver = new FirefoxDriver();
				// For parallel execution using ThreadLocal
				driver.set(new FirefoxDriver());
				logger.info("Firefox browser initialized");
			} else if (browser.equalsIgnoreCase("edge")) {
				// driver = new EdgeDriver();
				// For parallel execution using ThreadLocal
				driver.set(new EdgeDriver());
				logger.info("Edge browser initialized");
			}
			else {
				throw new IllegalArgumentException("Invalid browser name!");
			}
		}
		
		/**
		 * Method to configure browser
		 */
		private void configureBrowser() {
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			getDriver().manage().window().maximize();
			getDriver().get(prop.getProperty("url"));
			logger.info("browser configured successfully");
		}
		
		/**
		 * Method to setup WebDriver
		 * @throws IOException
		 */
		@BeforeClass
		public synchronized void setupDriver() throws IOException {
			logger.info("Setting up WebDriver for: " + this.getClass().getSimpleName());
			loadConfig();
			launchBrowser();
			configureBrowser();
			staticWait(2);
			logger.info("WebDriver setup completed");

			// Initialize ActionDriver
			//if(actionDriver == null) {
			//	actionDriver = new ActionDriver(driver);
			//	actionDriver.set(new ActionDriver(getDriver()));
			//	logger.info("ActionDriver initialized, Thread id is: "+Thread.currentThread().getId());
			//}
			
			// For parallel execution using ThreadLocal
			actionDriver.set(new ActionDriver(getDriver()));
			logger.info("ActionDriver initialized, Thread id is: "+Thread.currentThread().getId());
		}
		
		/**
		 * Method to close browser
		 */
		@AfterClass
		public synchronized void tearDown() {
			if (getDriver()!=null) {
				try {
					getDriver().quit();
				} catch (Exception e) {
					logger.info("Unable to quit driver: " + e.getMessage());
				}
			}
			//driver = null;
			//actionDriver = null;
			// For parallel execution using ThreadLocal
			driver.remove();
			actionDriver.remove();
		}
		
		/**
		 * Method to get driver
		 * @return WebDriver
		 */
		public static WebDriver getDriver() {
			if (driver.get() == null) {
				throw new IllegalStateException("WebDriver is not initialized!");
			}
			return driver.get();
		}
		
		/**
		 * Method to get ActionDriver
		 * @return ActionDriver
		 */
		public static ActionDriver getActionDriver() {
			if (actionDriver.get() == null) {
				throw new IllegalStateException("ActionDriver is not initialized!");
			}
			return actionDriver.get();
		}
		
		/**
		 * Method to get properties
		 * @return Properties
		 */
		public static Properties getProperties() {
			return prop;
		}
		
		/**
		 * Method to wait for specified seconds
		 * @param seconds
		 */
		public static void staticWait(int seconds) {
			LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
		}
}
