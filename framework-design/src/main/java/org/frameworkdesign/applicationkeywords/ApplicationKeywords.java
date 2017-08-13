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
		 * Function Name - findPadding
		 * Description - Finds padding information of an object
		 * Input Parameters - Mandatory - Operation
		 * Created By - Karthik
		 * 
		 */
		public static void findPadding(String objName, String keywordValue)
		{
			//handlesApplication
			
			//The below line of code grabs the object from the object repository
			ele = FrameworkKeywords.grabObjectFromOR(objProp,objName);
			
			System.out.println("Sample Test Text");
			System.out.println("Padding Left"+ele.getCssValue("padding-left"));
			System.out.println("Padding Right"+ele.getCssValue("padding-right"));
			System.out.println("Padding Top"+ele.getCssValue("padding-top"));
			System.out.println("Padding Bottom"+ele.getCssValue("padding-bottom"));
			
			int i = 2;
			if(keywordValue.equals("Store"))
			{
				excelDataFile.setCellData("paddingData", "Padding", i, "padding-left");
				excelDataFile.setCellData("paddingData", "Value", i, ele.getCssValue("padding-left"));
				i++;
				excelDataFile.setCellData("paddingData", "Padding", i, "padding-right");
				excelDataFile.setCellData("paddingData", "Value", i, ele.getCssValue("padding-right"));
				i++;
				excelDataFile.setCellData("paddingData", "Padding", i, "padding-top");
				excelDataFile.setCellData("paddingData", "Value", i, ele.getCssValue("padding-top"));
				i++;
				excelDataFile.setCellData("paddingData", "Padding", i, "padding-bottom");
				excelDataFile.setCellData("paddingData", "Value", i, ele.getCssValue("padding-bottom"));
				i=2;
				
			}				
			else if(keywordValue.equals("Verify"))
			{
				
			}
			ele = null;
		}
		/*
		 * Function Name - verifyContentInDivTag
		 * Description - Stores or Verifies content placed within a tag-child object
		 * Input Parameters - Mandatory - Operation
		 * 
		 */
		public static void verifyContentInDivTag(String objName, String keywordValue) throws InterruptedException
		{
			//handlesApplication
			
			//The below line of code grabs the object from the object repository
			ele = FrameworkKeywords.grabObjectFromOR(objProp, objName);
			HashMap<String,String> hCSSProperties = new HashMap();
			HashMap<String,String> hMapParam = new HashMap();
			
			String validParams = "Tag,Action";
			
			/*String[] strCSSValues = {"padding-left","padding-right","padding-top","padding-bottom","font-style","font-family",
					"font-size","color","Text"};			
			String[] strPaddingValues = {"padding-left","padding-right","padding-top","padding-bottom","Text"};
			String[] strFontValues = {"font-style","font-family","font-size","color","Text"};*/
			String[] strCSSValues = {"Text"};			
			String[] strPaddingValues = {"Text"};
			String[] strFontValues = {"Text"};
			
			hMapParam = FrameworkKeywords.associateParametersWithValues(keywordValue, validParams);
			//String strTagName = "div";
			List<WebElement> elementList = ele.findElements(By.tagName(hMapParam.get("Tag").toString()));
			Iterator<WebElement> iter = elementList.iterator();
			int i,j, tempi, tempj;
			int intPaddingDataStart, intTextDataStart, intPaddingDataEnd, intTextDataEnd;
			HashMap<String,List> tempMap = new HashMap();
			
			i = excelDataFile.getLastRowNumber("paddingData") + 1;
			j = excelDataFile.getLastRowNumber("textData") + 1;
			tempi = i;
			tempj = j;
			
			
			if(hMapParam.get("Action").toString().equals("Verify"))
			{
				String strTempText = excelModuleFile.getCellData("TestCases", "DocumentStamp", iCurrStep);
				tempMap = FrameworkKeywords.parseDocumentStamp(strTempText);
				List tempList = tempMap.get("paddingData");
				i = Integer.parseInt(tempList.get(0).toString());
				//iLimit = Integer.parseInt(tempList.get(1).toString());
				tempList = (List) tempMap.get("textData");
				j = Integer.parseInt(tempList.get(0).toString());
				//jLimit = Integer.parseInt(tempList.get(1).toString());
				
			}
			while(iter.hasNext())
			{
				WebElement we = iter.next();
				
				hCSSProperties=FrameworkKeywords.returnTextValuesInHashMap(we, strCSSValues);
				if(hMapParam.get("Action").toString().equals("Store"))
				{
					FrameworkKeywords.storeResultInExcel(hCSSProperties, "paddingData", strPaddingValues, i);
				
					i++;
					FrameworkKeywords.storeResultInExcel(hCSSProperties, "textData", strFontValues, j);
					
					j++;
					
				}
			}
			if(hMapParam.get("Action").toString().equals("Store"))
			{
				intPaddingDataStart = tempi;
				intTextDataStart = tempj;
				intPaddingDataEnd = i;
				intTextDataEnd = j;
				strDocumentStamp = "textData:" + intTextDataStart + "," + intTextDataEnd + ";paddingData:" + intPaddingDataStart + "," + intPaddingDataEnd;
				excelModuleFile.setCellData("TestCases", "DocumentStamp", iCurrStep, strDocumentStamp);
			}
			else if(hMapParam.get("Action").toString().equals("Verify"))
			{
				iter = elementList.iterator();
				int x = i;
				int y = j;
				while(iter.hasNext())
				{
					WebElement we = iter.next();
					
					hCSSProperties=FrameworkKeywords.returnTextValuesInHashMap(we, strCSSValues);
					FrameworkKeywords.verifyCSSProperties(hCSSProperties, "paddingData", strPaddingValues, x);
					x++;
					FrameworkKeywords.verifyCSSProperties(hCSSProperties, "textData", strFontValues, y);
					y++;
				}				
			}			
		ele = null;
		}
		/*
		 * Function Name - findFontContent
		 * Description - Stores or Verifies information about font content of a particular object
		 * Input Parameters - Mandatory - Operation
		 * 
		 */
		
		public static void findDataAndClickOnNext(String objName, String keywordValue) throws InterruptedException
		{
			ele = FrameworkKeywords.grabObjectFromOR(objProp, objName);
			HashMap<String,String> hCSSProperties = new HashMap();
			HashMap<String,String> hMapParam = new HashMap();
			
			String validParams = "Tag,@AdditionalObjects,@AdditionalTag";
			
			/*String[] strCSSValues = {"padding-left","padding-right","padding-top","padding-bottom","font-style","font-family",
					"font-size","color","Text"};			
			String[] strPaddingValues = {"padding-left","padding-right","padding-top","padding-bottom","Text"};
			String[] strFontValues = {"font-style","font-family","font-size","color","Text"};*/
			String[] strCSSValues = {"Text"};			
			String[] strPaddingValues = {"Text"};
			String[] strFontValues = {"Text"};
			
			hMapParam = FrameworkKeywords.associateParametersWithValues(keywordValue, validParams);
			//String strTagName = "div";
//			List<WebElement> elementList = ele.findElements(By.tagName(hMapParam.get("Tag").toString()));
//			Iterator<WebElement> iter = elementList.iterator();
			int i,j, tempi, tempj;
			int intPaddingDataStart, intTextDataStart, intPaddingDataEnd, intTextDataEnd;
			HashMap<String,List> tempMap = new HashMap();
			
			//i = excelDataFile.getLastRowNumber("paddingData") + 1;
			j = excelDataFile.getLastRowNumber("textData") + 1;
			//tempi = i;
			tempj = j;
			WebElement fooBar;
			while(ele.isDisplayed() && ele.isEnabled() && !ele.getCssValue("color").toString().equalsIgnoreCase("rgba(85, 85, 85, 1)"))
			{
				
//				Thread.sleep(2000);
				if(!hMapParam.get("@AdditionalObjects").isEmpty())
				{
					fooBar = FrameworkKeywords.grabObjectFromOR(objProp, hMapParam.get("@AdditionalObjects"));
					List<WebElement> listFooBar = fooBar.findElements(By.tagName(hMapParam.get("@AdditionalTag")));
					Iterator<WebElement> iter1 = listFooBar.iterator();
					while(iter1.hasNext())
					{
						WebElement we = iter1.next();
						hCSSProperties=FrameworkKeywords.returnTextValuesInHashMap(we, strCSSValues);
//						if(hMapParam.get("Action").toString().equals("Store"))
//						{							
						FrameworkKeywords.storeResultInExcel(hCSSProperties, "textData", strFontValues, j);
						
						j++;
							
//						}
						System.out.println(we.getText());
					}
				}
				System.out.println(ele.getCssValue("color"));
//				FrameworkKeywords.storeResultInExcel(hCSSProperties, "textData", strFontValues, j);
				ele = FrameworkKeywords.grabObjectFromOR(objProp, objName);
				ele.click();
				Thread.sleep(2000);
				//j++;
			}
		}
		/*
		 * Function Name - findFontContent
		 * Description - Stores or Verifies information about font content of a particular object
		 * Input Parameters - Mandatory - Operation
		 * 
		 */
		public static void findFontContent(String objName, String keywordValue)
		{
			//handlesApplication
			
			//The below line of code grabs the object from the object repository
			ele = FrameworkKeywords.grabObjectFromOR(objProp,objName);
			
			System.out.println("Sample Test Text");
			System.out.println("Font Family:"+ele.getCssValue("font-family"));
			System.out.println("Font Style:"+ele.getCssValue("font-style"));
			
			//The content below will be pasted into an excel sheet
			//Trial-1 9/16/2016 8:06 PM Status - 
			//excelModuleFile.setCellData("excelData", "Name", 3, ele.getCssValue("font-family"));
			
			ele = null;
		}
		/*
		 * Function Name - launchHomepage
		 * Description - Launches the application based on the provided input
		 * Input Parameters - Mandatory - Browser. Optional - URL
		 * 
		 */
		public static void launchHomepage(String objName, String keywordValue)
		{
			
			/*
			System.setProperty("webdriver.firefox.marionette","C:\\Users\\C024994\\Desktop\\geckodriver.exe");
			
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.get(homeURL);
			*/
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
				System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");
				
				driver = new ChromeDriver();
				driver.manage().window().maximize();
				driver.get(homeURL);
				break;
				
			case "FF":
				try {
					System.setProperty("webdriver.firefox.marionette",System.getProperty("user.dir")+"\\Drivers\\geckodriver.exe");
					
					driver = new FirefoxDriver();
					driver.manage().window().maximize();
					driver.get(homeURL);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				break;
				
			case "IE":
				System.setProperty("webdriver.firefox.marionette",System.getProperty("user.dir")+"Drivers\\geckodriver.exe");
				
				driver = new InternetExplorerDriver();
				driver.manage().window().maximize();
				driver.get(homeURL);
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
			driver.quit();
		}
		/*
		 * Function Name - verifyLinksInPage
		 * Description - Verifies if the links are valid
		 * Input Parameters - NA
		 * 
		 */
		public static void verifyLinksInPage(String objName, String keywordValue)
		{
			List<WebElement> list=driver.findElements(By.cssSelector("a"));
		    for(WebElement link:list)                             
			{
			   System.out.println("Links in the cuurent pages are:  " +link.getText());
			  // System.out.println(link.getText());
			}
		    List<WebElement> allImages = FrameworkKeywords.findAllLinks();    		  
		    System.out.println("Total number of elements found " + allImages.size());
		    for( WebElement element : allImages)
		    {
		    	try
		    	{
			        System.out.println("URL: " + element.getAttribute("href")+ " returned " + FrameworkKeywords.checkIfLinkIsBroken(new URL(element.getAttribute("href"))));
		    		//System.out.println("URL: " + element.getAttribute("outerhtml")+ " returned " + isLinkBroken(new URL(element.getAttribute("href"))));
		    	}
		    	catch(Exception exp)
		    	{
		    		System.out.println("At " + element.getAttribute("innerHTML") + " Exception occured -&gt; " + exp.getMessage());	    		
		    	}

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
			}
			catch(Exception e)
			{
				System.out.println("Unable to click on element");
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
				Thread.sleep(Integer.parseInt((String) hMapParam.get("Duration")));
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		/*
		 * Function Name - grabDataFile
		 * Description - Grabs a particular data file  
		 * Input Parameters - NA
		 * Created By- Karthik
		 * Modified By- Karthik
		 * Modification Details - Implementing a feature that distinguishes which environment 
		 * the script is running in and performs action accordingly. 
		 * 
		 */
		public static void grabDataFile(String objName, String keywordValue)
		{
			String validParams = "Path";
			HashMap hMapParam = FrameworkKeywords.associateParametersWithValues(keywordValue, validParams);
			try
			{
				excelDataFile = new ExcelHandlerLibrary(hMapParam.get("Path").toString());
			}
			catch(Exception e)
			{
				System.out.println("Error loading file");
			}
		}
		
		/*
		 * Function Name - waitUntilObjectFound
		 * Description - Waits until an object is found or until the time runs out  
		 * Input Parameters - NA
		 * Created By- Karthik
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
			//WebDriverWait wait = new WebDriverWait(driver, (Long)hMapParam.get("Time"));
			WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(hMapParam.get("Time").toString()));
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			
		}
		
		/*
		 * Function Name - hoverOverObject
		 * Description - Hovers over a given object  
		 * Input Parameters - NA
		 * Created By- Diksha
		 * Modified By- Karthik
		 * 
		 */
		public static void hoverOverObject(String objName, String keywordValue)
		{
			Actions action = new Actions(driver);
			ele = FrameworkKeywords.grabObjectFromOR(objProp, objName);
			try
			{
				action.moveToElement(ele).build().perform();
				
			}
			catch(Exception e)
			{
				System.out.println("Unable to initialize or hover over object");
			}
		}
		/*
		 * Function Name - verifyContentWithinLink
		 * Description - Verifies   
		 * Input Parameters - NA
		 * Created By- Diksha
		 * Modified By- Karthik
		 * 
		 */
		public static void verifyContentWithinLink(String objName, String keywordValue) throws InterruptedException
		{
			String validParams = "Tag,Action,@Back,@AdditionalObjects,@AdditionalTag";
			ele = FrameworkKeywords.grabObjectFromOR(objProp, objName);
			HashMap<String,String> hCSSProperties = new HashMap();
			//HashMap<String,String> hMapParam = new HashMap();

			String[] strCSSValues = {"padding-left","padding-right","padding-top","padding-bottom","font-style","font-family",
								"font-size","color","Text"};			
			String[] strPaddingValues = {"padding-left","padding-right","padding-top","padding-bottom","Text"};
			String[] strFontValues = {"font-style","font-family","font-size","color","Text"};
			HashMap<String,String> hMapParam = new HashMap();
			hMapParam = FrameworkKeywords.associateParametersWithValues(keywordValue, validParams);
			String firstParam = hMapParam.get("Tag");
			List<WebElement> sampleList = ele.findElements(By.tagName(firstParam));
			int i = 0,j = 0,tempi = 0,tempj = 0;
			HashMap<String,List> tempMap = new HashMap();			
			int intPaddingDataStart, intTextDataStart, intPaddingDataEnd, intTextDataEnd;
			List<String> lstContentStore = new ArrayList();
			WebElement fooBar;
			
			Actions action = new Actions(driver);
			int listSize = sampleList.size();
			if(!hMapParam.get("@AdditionalObjects").isEmpty())
			{
				if(hMapParam.get("Action").toString().equals("Store"))
				{
					i = excelDataFile.getLastRowNumber("paddingData") + 1;
					j = excelDataFile.getLastRowNumber("textData") + 1;
					tempi = i;
					tempj = j;
				}
				else if(hMapParam.get("Action").toString().equals("Verify"))
				{
					String strTempText = excelModuleFile.getCellData("TestCases", "DocumentStamp", iCurrStep);
					tempMap = FrameworkKeywords.parseDocumentStamp(strTempText);
					List tempList = tempMap.get("paddingData");
					i = Integer.parseInt(tempList.get(0).toString());
					//iLimit = Integer.parseInt(tempList.get(1).toString());
					tempList = (List) tempMap.get("textData");
					j = Integer.parseInt(tempList.get(0).toString());
					//jLimit = Integer.parseInt(tempList.get(1).toString());
				}
				
			}
			for(int x=0;x<listSize;x++)
			{
//				action.moveToElement(sampleList.get(i)).build().perform();
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sampleList.get(x));
				//Thread.sleep(500); 
				sampleList.get(x).click();
				System.out.println("Successfully Clicked on link");
				if(!hMapParam.get("@AdditionalObjects").isEmpty())
				{
					/*i = excelDataFile.getLastRowNumber("paddingData") + 1;
					j = excelDataFile.getLastRowNumber("textData") + 1;
					tempi = i;
					tempj = j;*/
					
					
					
					if(hMapParam.get("Action").toString().equals("Store"))
					{
						intPaddingDataStart = tempi;
						intTextDataStart = tempj;
						/*intPaddingDataEnd = i;
						intTextDataEnd = j;*/
						strDocumentStamp = "textData:" + intTextDataStart + ";paddingData:" + intPaddingDataStart;
						excelModuleFile.setCellData("TestCases", "DocumentStamp", iCurrStep, strDocumentStamp);
					}
					fooBar = FrameworkKeywords.grabObjectFromOR(objProp, hMapParam.get("@AdditionalObjects"));
					List<WebElement> listFooBar = fooBar.findElements(By.tagName(hMapParam.get("@AdditionalTag")));
					Iterator<WebElement> iter = listFooBar.iterator();
					while(iter.hasNext())
					{
						WebElement we = iter.next();
						hCSSProperties=FrameworkKeywords.returnTextValuesInHashMap(we, strCSSValues);
						if(hMapParam.get("Action").toString().equals("Store"))
						{
							FrameworkKeywords.storeResultInExcel(hCSSProperties, "paddingData", strPaddingValues, i);
						
							i++;
							FrameworkKeywords.storeResultInExcel(hCSSProperties, "textData", strFontValues, j);
							
							j++;
							
						}
						System.out.println(we.getText());
					}
					/*if(hMapParam.get("Action").toString().equals("Store"))
					{
						intPaddingDataStart = tempi;
						intTextDataStart = tempj;
						intPaddingDataEnd = i;
						intTextDataEnd = j;
						strDocumentStamp = "textData:" + intTextDataStart + "," + intTextDataEnd + ";paddingData:" + intPaddingDataStart + "," + intPaddingDataEnd;
						excelModuleFile.setCellData("TestCases", "DocumentStamp", iCurrStep, strDocumentStamp);
					}*/
					
					//here be else matey
					if(hMapParam.get("Action").toString().equals("Verify"))
					{
						iter = listFooBar.iterator();
						int z = i;
						int y = j;
						while(iter.hasNext())
						{
							WebElement we = iter.next();
							
							hCSSProperties=FrameworkKeywords.returnTextValuesInHashMap(we, strCSSValues);
							FrameworkKeywords.verifyCSSProperties(hCSSProperties, "paddingData", strPaddingValues, z);
							z++;
							FrameworkKeywords.verifyCSSProperties(hCSSProperties, "textData", strFontValues, y);
							y++;
						}				
					}			
				//ele = null;
					
				}
				if(hMapParam.get("@Back").equalsIgnoreCase("Yes"))
				{
					driver.navigate().back();
					try {
						Thread.sleep(1000);
						ele = FrameworkKeywords.grabObjectFromOR(objProp, objName);
						sampleList = ele.findElements(By.tagName(firstParam));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}				
			}
		}
		
}
