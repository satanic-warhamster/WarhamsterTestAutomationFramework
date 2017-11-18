package org.frameworkdesign.applicationkeywords;

import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import org.apache.commons.lang3.StringUtils;
import org.frameworkdesign.GlobalDeclarations;
import org.frameworkdesign.excelhandler.ExcelHandlerLibrary;
import org.frameworkdesign.frameworkkeywords.FrameworkKeywords;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

@SuppressWarnings("all")
public class ApplicationKeywords extends GlobalDeclarations{
	/*
	 * General structure of an application keyword
	 * public static void <keywordName>(String strObject, String strValue) throws InterruptedException
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
		public static void launchHomepage()
		{
			String validParams = "Browser,@URL";
			ele = FrameworkKeywords.fnInitializeTestStep(validParams);
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
					System.out.println("Unable to launch through Chrome. Find Logs Below");
					e.printStackTrace();
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
		public static void quitApplication()
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
		public static void object_Click()
		{
			String validParams = "";
			ele = FrameworkKeywords.fnInitializeTestStep(validParams);
			try
			{
				ele.click();
				logger.log(Status.PASS, strObject+" was clicked");
			}
			catch(Exception e)
			{
				System.out.println("Unable to click on element");
				logger.log(Status.FAIL, "Unable to click on element "+strObject);
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
		public static void scriptPause()
		{
			String validParams = "Duration";
			ele = FrameworkKeywords.fnInitializeTestStep(validParams); 
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
		public static void grabDataFile()
		{
			String validParams = "Path";
			HashMap hMapParam = FrameworkKeywords.associateParametersWithValues(strValue, validParams);
			ele = FrameworkKeywords.fnInitializeTestStep(validParams);
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
		public static void waitUntilObjectFound()
		{
			String validParams = "Time";
			By by = null;
			ele = FrameworkKeywords.fnInitializeTestStep(validParams);
			//ele = FrameworkKeywords.grabObjectFromOR(objProp, strObject);
			HashMap<String,String> hObjHandler = new HashMap();
			int intDelimPosition = strObject.indexOf(";");
			String strValHolder;
			if(intDelimPosition!= -1)
			{
				hObjHandler = FrameworkKeywords.associateObjectAgainstLiveIT(strObject);
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
				strValHolder = FrameworkKeywords.returnDataInIterationSheetIfExists(strObject);
				by = FrameworkKeywords.returnBy(objProp, strValHolder);					
				//return ele = returnObject(prop, strObject);
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
		public static void hoverOverObject()
		{
			String validParams = "";
			Actions action = new Actions(driver);
			ele = FrameworkKeywords.fnInitializeTestStep(validParams);
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
		public static void setText()
		{
			String validParams = "Text";
			ele = FrameworkKeywords.fnInitializeTestStep(validParams);
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
		public static void fluentWaitForObject()
		{
			String validParams = "WaitPeriod,PollingPeriod";
			ele = FrameworkKeywords.fnInitializeTestStep(validParams);
			try
			{
				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
						.withTimeout(Long.parseLong(hMapParam.get("WaitPeriod")), TimeUnit.SECONDS)
						.pollingEvery(Long.parseLong(hMapParam.get("PollingPeriod")), TimeUnit.SECONDS)
						.ignoring(NoSuchElementException.class);
				wait.until(new Function<WebDriver, WebElement>() 
				{
				  public WebElement apply(WebDriver driver) {
				  return ele;
				}
				});
				logger.log(Status.PASS, "Object was found through fluent Wait");
			}
			catch(Exception e)
			{
				System.out.println("Element not found after fluent wait");
				logger.log(Status.FAIL, "Object not found after fluent wait");
				e.printStackTrace();
			}
			
			
		}
		public static void selectValueFromDropdown()
		{
			String validParams = "SelectByMethod,@Index,@Value,@Text";
			ele = FrameworkKeywords.fnInitializeTestStep(validParams);
			int iCounter;
			String[] strMultiVals;
			try
			{
				Select select = new Select(ele);
				switch(hMapParam.get("SelectByMethod").trim())
				{
					case "VisibleText":
						iCounter = StringUtils.countMatches(hMapParam.get("@Text"), ",");
						if(iCounter==0)
							select.selectByVisibleText(hMapParam.get("@Text"));
						else
						{
							strMultiVals = hMapParam.get("@Text").split(";");
							if(select.isMultiple())
								for(String strVal:strMultiVals)
								{
									select.selectByVisibleText(strVal);
									Thread.sleep(new Long(2000));
								}
							else
								throw new Exception();
						}
						break;
					case "Index":
						iCounter = StringUtils.countMatches(hMapParam.get("@Index"), ",");
						if(iCounter==0)
							select.selectByIndex(Integer.parseInt(hMapParam.get("@Index")));
						else
						{
							strMultiVals = hMapParam.get("@Index").split(";");
							if(select.isMultiple())
								for(String strVal:strMultiVals)
								{
									select.selectByIndex(Integer.parseInt(strVal));
									Thread.sleep(new Long(2000));
									
								}
							else
								throw new Exception();
						}
						break;
					case "Value":
						iCounter = StringUtils.countMatches(hMapParam.get("@Value"), ",");
						if(iCounter==0)
							select.selectByValue(hMapParam.get("@Value"));
						else
						{
							strMultiVals = hMapParam.get("@Value").split(";");
							if(select.isMultiple())
								for(String strVal:strMultiVals)
								{
									select.selectByValue(strVal);
									Thread.sleep(new Long(2000));
								}
							else
								throw new Exception();
						}
						break;
					default:
						iCounter = StringUtils.countMatches(hMapParam.get("@Text"), ",");
						if(iCounter==0)
							select.selectByVisibleText(hMapParam.get("@Text"));
						else
						{
							strMultiVals = hMapParam.get("@Text").split(";");
							if(select.isMultiple())
								for(String strVal:strMultiVals)
								{
									select.selectByVisibleText(strVal);
									Thread.sleep(new Long(2000));
								}
							else
								throw new Exception();
						}
						break;
				}
				logger.log(Status.PASS, "Object was found through fluent Wait");
			}
			catch(Exception e)
			{
				System.out.println("Unable to select value from dropdown");
				logger.log(Status.FAIL, "Unable to select value from dropdown");
				e.printStackTrace();
			}
		}

}
