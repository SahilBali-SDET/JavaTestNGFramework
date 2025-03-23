package com.orangeHRM.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.orangeHRM.actionDriver.ActionDriver;


public class BaseClass {

		protected static Properties prop;
		protected static WebDriver driver;
		private static ActionDriver actionDriver;
		
		/**
		 * Method to load config file
		 * @throws IOException
		 */
		@BeforeSuite
		public void loadConfig() throws IOException {
			prop = new Properties();
			FileInputStream fs = new FileInputStream("src/main/resources/config.properties");
			prop.load(fs);
		}
		
		/**
		 * Method to launch browser
		 */
		private void launchBrowser() {
			String browser = prop.getProperty("Browser");
			
			if (browser.equalsIgnoreCase("chrome")) {
				driver = new ChromeDriver();
			}
			else {
				throw new IllegalArgumentException("Invalid browser name!");
			}
		}
		
		/**
		 * Method to configure browser
		 */
		private void configureBrowser() {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.manage().window().maximize();
			driver.get(prop.getProperty("url"));
		}
		
		/**
		 * Method to setup WebDriver
		 * @throws IOException
		 */
		@BeforeClass
		public void setupDriver() throws IOException {
			System.out.println("Setting up WebDriver for: " + this.getClass().getSimpleName());
			loadConfig();
			launchBrowser();
			configureBrowser();
			staticWait(2);
			// Initialize ActionDriver
			if(actionDriver == null) {
				actionDriver = new ActionDriver(driver);
				System.out.println("ActionDriver initialized");
			}
		}
		
		/**
		 * Method to close browser
		 */
		@AfterClass
		public void tearDown() {
			if (driver!=null) {
				try {
					driver.quit();
				} catch (Exception e) {
					System.out.println("Unable to quit driver: " + e.getMessage());
				}
			}
			driver = null;
			actionDriver = null;
		}
		
		/**
		 * Method to get driver
		 * @return WebDriver
		 */
		public static WebDriver getDriver() {
			if (driver == null) {
				throw new IllegalStateException("WebDriver is not initialized!");
			}
			return driver;
		}
		
		/**
		 * Method to get ActionDriver
		 * @return ActionDriver
		 */
		public static ActionDriver getActionDriver() {
			if (actionDriver == null) {
				throw new IllegalStateException("ActionDriver is not initialized!");
			}
			return actionDriver;
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
