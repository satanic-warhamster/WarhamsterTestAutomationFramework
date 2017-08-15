package org.frameworkdesign.applicationkeywords;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;


















import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import org.frameworkdesign.GlobalDeclarations;
import org.frameworkdesign.excelhandler.ExcelHandlerLibrary;
import org.frameworkdesign.frameworkkeywords.FrameworkKeywords;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@SuppressWarnings("all")
public class ApplicationKeywords extends GlobalDeclarations{
	/*
	 * General structure of an application keyword
	 * public static void <keywordName>(String objName, String keywordValue) throws InterruptedException
	 * Grab Object From Object Repository using method grabObjectFromOR
	 * Get Valid Params using associateParametersWithValues
	 * Perform actions
	 * 
	 */
		/*
		 * Function Name - launchHomepage
		 * Description - Launches the application based on the provided input
		 * Input Parameters - Mandatory - Browser. Optional - URL
		 * 
		 */
		public static void launchHomepage(String objName, String keywordValue)
		{
			String validParams = "Browser,@URL";
			HashMap<String,String> hMapParam = new HashMap();
			hMapParam = FrameworkKeywords.associateParametersWithValues(keywordValue, validParams);
			if(!hMapParam.get("@URL").isEmpty())
			{
				homeURL = FrameworkKeywords.retrieveURL(hMapParam.get("@URL"));
			}
			switch(hMapParam.get("Browser"))
			{
			case "CHROME":
				try
				{
					System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");
					
					driver = new ChromeDriver();
					driver.manage().window().maximize();
					driver.get(homeURL);
					logger.log(Status.PASS, "Chrome launched successfully");
					
				}
				catch(Exception e)
				{
					System.out.println("Unable to launch through Chrome");
					logger.log(Status.FAIL,"Unable to launch through Chrome");
				}
				break;
				
			case "FF":
				try {
					System.setProperty("webdriver.firefox.marionette",System.getProperty("user.dir")+"\\Drivers\\geckodriver.exe");
					
					driver = new FirefoxDriver();
					driver.manage().window().maximize();
					driver.get(homeURL);
					logger.log(Status.PASS, "Firefox launched successfully");
				}
				catch(Exception e)
				{
					e.printStackTrace();
					System.out.println("Unable to launch through Firefox");
					logger.log(Status.FAIL,"Unable to launch through Firefox");
				}
				
				break;
				
			case "IE":
				try {
					System.setProperty("webdriver.firefox.marionette",System.getProperty("user.dir")+"Drivers\\geckodriver.exe");
					driver = new InternetExplorerDriver();
					driver.manage().window().maximize();
					driver.get(homeURL);
					logger.log(Status.PASS, "Internet Explorer launched successfully");
				}
				catch(Exception e)
				{
					e.printStackTrace();
					System.out.println("Unable to launch through Internet Explorer");
					logger.log(Status.FAIL,"Unable to launch through Internet Explorer");
				}
				
				break;
			default:
				System.out.println("Error in browser selection");
				break;
			}
		}
		/*
		 * Function Name - quitApplication
		 * Description - Closes the browser
		 * Input Parameters - NA
		 * 
		 */
		public static void quitApplication(String objName, String keywordValue)
		{
			try
			{
				driver.quit();
				logger.log(Status.PASS, "Closed the browser");
			}
			catch(Exception e)
			{
				logger.log(Status.FAIL, "Unable to close browser");
			}			
		}
		/*
		 * Function Name - object_Click
		 * Description - Clicks on any given object on the screen
		 * Input Parameters - NA
		 * Created By-
		 * Modified By-
		 * 
		 */
		public static void object_Click(String objName, String keywordValue)
		{
			ele = FrameworkKeywords.grabObjectFromOR(objProp, objName);
			try
			{
				ele.click();
				logger.log(Status.PASS, objName+" was clicked");
			}
			catch(Exception e)
			{
				System.out.println("Unable to click on element");
				logger.log(Status.FAIL, "Unable to click on element "+objName);
			}
		}
		/*
		 * Function Name - scriptPause
		 * Description - Pauses the script for a fixed period 
		 * Input Parameters - NA
		 * Created By- Karthik
		 * Modified By-
		 * 
		 */
		public static void scriptPause(String objName, String keywordValue)
		{
			String validParams = "Duration";
			HashMap hMapParam = FrameworkKeywords.associateParametersWithValues(keywordValue, validParams); 
			//hashHap
			try
			{
				Thread.sleep(Integer.parseInt((String) hMapParam.get("Duration") + "000"));
				logger.log(Status.PASS, "Paused the script for "+hMapParam.get("Duration")+" seconds.");
			}
			catch(Exception e)
			{
				System.out.println(e);
				logger.log(Status.FAIL, "Unable to pause the script");
			}
		}
		/*
		 * Function Name - grabDataFile
		 * Description - Grabs a particular data file  
		 * Input Parameters - NA
		 * 
		 */
		public static void grabDataFile(String objName, String keywordValue)
		{
			String validParams = "Path";
			HashMap hMapParam = FrameworkKeywords.associateParametersWithValues(keywordValue, validParams);
			try
			{
				excelDataFile = new ExcelHandlerLibrary(hMapParam.get("Path").toString());
				logger.log(Status.PASS, "Date file loaded");
			}
			catch(Exception e)
			{
				System.out.println("Error loading file");
				logger.log(Status.FAIL, "Unable to load file");
			}
		}		
		/*
		 * Function Name - waitUntilObjectFound
		 * Description - Waits until an object is found or until the time runs out  
		 * Input Parameters - NA
		 * Modified By-
		 * 
		 */
		public static void waitUntilObjectFound(String objName, String keywordValue)
		{
			String validParams = "Time";
			By by = null;
			HashMap hMapParam = FrameworkKeywords.associateParametersWithValues(keywordValue, validParams);
			//ele = FrameworkKeywords.grabObjectFromOR(objProp, objName);
			HashMap<String,String> hObjHandler = new HashMap();
			int intDelimPosition = objName.indexOf(";");
			String strValHolder;
			if(intDelimPosition!= -1)
			{
				hObjHandler = FrameworkKeywords.associateObjectAgainstLiveIT(objName);
				String strTCEnv = excelModuleFile.getCellData(strIteration, "Environment", iIterNum+1);
				try{
					
					if(strTCEnv.equalsIgnoreCase("Live"))
					{
						strValHolder = FrameworkKeywords.returnDataInIterationSheetIfExists(hObjHandler.get("Live"));
						by = FrameworkKeywords.returnBy(objProp, strValHolder);
					}
					else if(strTCEnv.equalsIgnoreCase("IT"))
					{
						strValHolder = FrameworkKeywords.returnDataInIterationSheetIfExists(hObjHandler.get("IT"));
						by = FrameworkKeywords.returnBy(objProp, strValHolder);
					}
						
				}
				catch(Exception e)
				{
					System.out.println("Error parsing object string");
				}
			}
			else
			{
				strValHolder = FrameworkKeywords.returnDataInIterationSheetIfExists(objName);
				by = FrameworkKeywords.returnBy(objProp, strValHolder);					
				//return ele = returnObject(prop, objName);
			}
			try
			{
				WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(hMapParam.get("Time").toString()));
				wait.until(ExpectedConditions.visibilityOfElementLocated(by));
				logger.log(Status.PASS, "Wait timer set successfully");
			}
			catch(Exception e)
			{
				logger.log(Status.FAIL, "Unable to set wait timer");
			}
			//WebDriverWait wait = new WebDriverWait(driver, (Long)hMapParam.get("Time"));			
		}		
		/*
		 * Function Name - hoverOverObject
		 * Description - Hovers over a given object  
		 * Input Parameters - NA
		 * 
		 */
		public static void hoverOverObject(String objName, String keywordValue)
		{
			Actions action = new Actions(driver);
			ele = FrameworkKeywords.grabObjectFromOR(objProp, objName);
			try
			{
				action.moveToElement(ele).build().perform();
				logger.log(Status.PASS, "Hovered over object");
			}
			catch(Exception e)
			{
				System.out.println("Unable to initialize or hover over object");
				logger.log(Status.FAIL, "Unable to initialize or hover over object");
			}
		}
		/*
		 * Function Name - setText
		 * Description - Sets text in a text field
		 * Input Parameters - Text
		 * 
		 */
		public static void setText(String objName, String keywordValue)
		{
			String validParams = "Text";
			ele = FrameworkKeywords.grabObjectFromOR(objProp, objName);
			HashMap<String,String> hMapParam;
			hMapParam = FrameworkKeywords.associateParametersWithValues(keywordValue, validParams);
			try
			{
				ele.sendKeys(hMapParam.get("Text"));
				Thread.sleep(1000);
				logger.log(Status.PASS, "Set text "+hMapParam.get("Text"));
			}
			catch(Exception e)
			{
				System.out.println("Unable to set text");
				logger.log(Status.FAIL, "Unable to set text "+ hMapParam.get("Text"));
			}
		}

}
