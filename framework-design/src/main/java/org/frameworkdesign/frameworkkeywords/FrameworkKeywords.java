package org.frameworkdesign.frameworkkeywords;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.frameworkdesign.GlobalDeclarations;
import org.frameworkdesign.applicationkeywords.ApplicationKeywords;
import org.frameworkdesign.excelhandler.ExcelHandlerLibrary;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
@SuppressWarnings("all")
public class FrameworkKeywords extends GlobalDeclarations{
	
		public static Properties loadProperties(String path)
		{
			//handlesFlow
			fileObj = new File(path);
			try
			{
				fileInputObj = new FileInputStream(fileObj);
			}
			catch(FileNotFoundException e)
			{
				System.out.println(e);
			}
			Properties prop = new Properties();
			try{
				prop.load(fileInputObj);
				return prop;
			}
			catch(IOException e)
			{
				System.out.println(e);
			}
			return null;
		}
		public static WebElement grabObjectFromOR(Properties prop, String objName)
		{
			//handlesFlow
			
			//Retrieving object from OR and verifying object type
			//While providing object in the properties, we'll append it with a delimiter such as '|' followed by what we're using to identify the object
			//for example objString|(xpath,id,class,etc)
			String[] strParams;
			//String key;
			HashMap<String,String> hObjHandler = new HashMap();
			int intDelimPosition = objName.indexOf(";");
			if(intDelimPosition!= -1)
			{
				hObjHandler = associateObjectAgainstLiveIT(objName);
				String strTCEnv = excelModuleFile.getCellData(strIteration, "Environment", iIterNum+1);
				try{
					
					if(strTCEnv.equalsIgnoreCase("Live"))
					{
						return ele = returnObject(prop,hObjHandler.get(strTCEnv));
					}
					else if(strTCEnv.equalsIgnoreCase("IT"))
					{
						return ele = returnObject(prop,hObjHandler.get(strTCEnv));
					}
						
				}
				catch(Exception e)
				{
					System.out.println("Error parsing object string");
				}
			}
			else
			{				
				return ele = returnObject(prop, objName);					
				//return ele = returnObject(prop, objName);
			}
			/*
			
			*/
			return null;
		}
		public static HashMap associateObjectAgainstLiveIT(String objName)
		{
			String[] strParams = objName.split(";");
			int intDelimPosition;
			HashMap<String, String> hObjHandler = new HashMap();
			for(String strParamHolder:strParams)
			{
				try
				{
					
					intDelimPosition = strParamHolder.indexOf(":");
					String key = strParamHolder.substring(0,intDelimPosition);
					String value = strParamHolder.substring(intDelimPosition+1);
					
					hObjHandler.put(key,value);
					
				}
				catch(Exception e)
				{
					System.out.println("Error parsing strings");
				}
			}
			return hObjHandler;
		}
		public static By returnBy(Properties prop, String objName)
		{
			String strValue = prop.getProperty(objName);
			By by = null;
			//String[] arrStrValue = strValue.split("\\|");
			int lastIndex=strValue.lastIndexOf("|");
			String strObjType=strValue.substring(lastIndex+1);
			String strObjValue = strValue.substring(0, lastIndex);
			WebElement ele = null;
			try{
				switch(strObjType)
				{
				case "xpath":
					by = By.xpath(strObjValue);
					break;
				case "id":
					by = By.id(strObjValue);
					break;
				case "class":
					by = By.className(strObjValue);
					break;
				case "css":
					by = By.cssSelector(strObjValue);
					break;
				case "tag":
					by = By.tagName(strObjValue);
					break;
						
				}
				return by;
			}
			catch(Exception e)
			{
				System.out.println("Warning: By Object could not be loaded properly");
			}
			return null;
		}
		public static WebElement returnObject(Properties prop, String objName)
		{
			String strValHolder = returnDataInIterationSheetIfExists(objName);
			String strValue = prop.getProperty(strValHolder);
			//String[] arrStrValue = strValue.split("\\|");
			int lastIndex=strValue.lastIndexOf("|");
			String strObjType=strValue.substring(lastIndex+1);
			String strObjValue = strValue.substring(0, lastIndex);
			WebElement ele = null;
			try{
				switch(strObjType)
				{
				case "xpath":
					ele = driver.findElement(By.xpath(strObjValue));
					break;
				case "id":
					ele = driver.findElement(By.id(strObjValue));
					break;
				case "class":
					ele = driver.findElement(By.className(strObjValue));
					break;
				case "css":
					ele = driver.findElement(By.cssSelector(strObjValue));
					break;
				case "tag":
					ele = driver.findElement(By.tagName(strObjValue));
					break;
						
				}
				return ele;
			}
			catch(Exception e)
			{
				System.out.println("Warning: Object could not be loaded properly");
			}
			return null;
		}
		
		/*public static By returnBy(Properties prop, String objName)
		{
			String strValHolder = returnDataInIterationSheetIfExists(objName);
			String strValue = prop.getProperty(strValHolder);
			//String[] arrStrValue = strValue.split("\\|");
			int lastIndex=strValue.lastIndexOf("|");
			String strObjType=strValue.substring(lastIndex+1);
			String strObjValue = strValue.substring(0, lastIndex);
			WebElement ele = null;
			try{
				switch(strObjType)
				{
				case "xpath":
					ele = driver.findElement(By.xpath(strObjValue));
					break;
				case "id":
					ele = driver.findElement(By.id(strObjValue));
					break;
				case "class":
					ele = driver.findElement(By.className(strObjValue));
					break;
				case "css":
					ele = driver.findElement(By.cssSelector(strObjValue));
					break;
				case "tag":
					ele = driver.findElement(By.tagName(strObjValue));
					break;
						
				}
				return ele;
			}
			catch(Exception e)
			{
				System.out.println("Object could not be found in the repository");
			}
			return null;
		}*/
		
		public static void sampleFunc(String textName)
		{
			//sampleCode
			
			System.out.println(textName);
		}
		
		public static String retrieveURL(String URL)
		{
			//handlesFlow
			
			try{
				urlProp = loadProperties(URL_LOCATION);
				return urlProp.getProperty(URL);
			}
			catch(Exception e)
			{
				System.out.println("Unable to retrieve URL");
				System.out.println("Error: "+e);
			}
			return null;
		}
		public static boolean findMethodInKeywordLibrary(String strFuncName)
		{
			try
			{
				//Method objMethod = keywordLibrary.class.getMethod(strFuncName, WebDriver.class);
				Class[] argumentTypes = {String.class};
				Method objMethod = ApplicationKeywords.class.getMethod(strFuncName,argumentTypes);
			}
			catch(NoSuchMethodException ex){
				System.out.println("Method either doesn't exist " +
		                "or is not public: " + ex.toString());
				return false;
			}
			return true;
		}
		public static void launchTestCase(String strTCName)
		{
			
			Method objMethod;
			iCurrStep = excelModuleFile.getCellRowNum("TestCases", "Test Case Name", strTCName);
			//excelModuleFile = new excelHandlerLibrary("C:\\Users\\C024994\\Desktop\\SeleniumProgramming\\SampleProject\\src\\org\\pack\\common\\excelFiles\\sampleModuleFiles.xls");
			//String strEnd = "END";
			
			//System.out.println(strRowData);
			while(!(excelModuleFile.getCellData("TestCases", "Keyword", iCurrStep).toString().equalsIgnoreCase("END")))
			{
				
				//System.out.println(strRowData);
				try
				{
					if(findObjectInLibrary(iCurrStep)!=null)
					{
						objMethod = findObjectInLibrary(iCurrStep);
						invokeMethod(objMethod,iCurrStep);
						
						
					}					
					iCurrStep++;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			
		}
		public static void ModuleHandler()
		{
			Method objMethod;
			HashMap<String,List> hConfigFile = new HashMap();
			hConfigFile = excelModuleFile.getContentInHashMap("ModuleConfig");
			
			//The purpose of this hashmap is to store the contents of the iteration sheet
			HashMap<String,List> hIterationSheet = new HashMap(); 
			
			//Creating n lists for every column in the "Module Config" sheet
			List<String> lstTCName = new ArrayList();
			List<String> lstExecStatus = new ArrayList();
			List<String> lstIterSheet = new ArrayList();
//			List<String> lstTCDesc = new ArrayList();
			
			
			List<String> lstIterNum = new ArrayList();
			List<String> lstIterName = new ArrayList();
			List<String> lstIterExec = new ArrayList();
			List<String> lstTCDesc = new ArrayList();
			
			//Associating the list values of hash map to the newly created lists
			lstTCName = hConfigFile.get("Test Case Name");
			lstExecStatus= hConfigFile.get("Execution");
			lstIterSheet = hConfigFile.get("Iteration");
//			lstTCDesc = hConfigFile.get("Description");
			
			//Assuming the size of the lists is the same. Gotta handle the scenario when they aren't the same.
			int iTCCount = lstTCName.size();
			
			for(int iter = 0; iter < iTCCount; iter++)
			{
				String strTCName = (String) lstTCName.get(iter);
				String strExecStatus = (String) lstExecStatus.get(iter);
				
				if(strExecStatus.equalsIgnoreCase("ON"))
				{
//					String strTCDesc = lstTCDesc.get(iter);
					
					strIteration = (String) lstIterSheet.get(iter);
					hIterationSheet = excelModuleFile.getContentInHashMap(strIteration);
					//While the contents of the iteration sheet differ from Module to Module, the 
					//only columns that are constant across all iteration sheet are Iteration Number, Name and Execution
					//Hence, creating Lists that reference these columns
					
					
					//Associate lists with the values of hashmap
					lstIterNum = hIterationSheet.get("Iteration Number");
					lstIterName = hIterationSheet.get("Name");
					lstIterExec= hIterationSheet.get("Execution");
					lstTCDesc = hIterationSheet.get("Description");
					
					int iterSize = lstIterNum.size();
					for(int i = 0; i < iterSize; i++)
					{
						String strIterExecStatus = (String) lstIterExec.get(i);
						String strIterName = (String) lstIterName.get(i);
						String strTCDesc = lstTCDesc.get(i);
						iIterNum = Integer.parseInt((String)lstIterNum.get(i));
						//iIterNum = getCellRowNum("TestCases", "Test Case Name", strTCName);
						if(strIterExecStatus.equalsIgnoreCase("ON"))
						{
							logger = extent.createTest(strIterName, strTCDesc);
							logger.log(Status.INFO, "Start Test "+strIterName);
							launchTestCase(strTCName);
							extent.flush();
							logger = null;
						}
					}
				}
			}
		}
		//public static Array getKeysInHashMap()
		public static Method findObjectInLibrary(int stepNo)
		{
			String methodName;
			String testStepKeyword;
			try{
				testStepKeyword = excelModuleFile.getCellData("TestCases", "Keyword", stepNo);
				Class[] argumentTypes = { String.class, String.class };
			
				Method objMethod = ApplicationKeywords.class.getMethod(testStepKeyword,String.class,String.class);
				return objMethod;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}
		public static void invokeMethod(Method objMethod, int stepNo)
		{
			try{
				objMethod.invoke(null, excelModuleFile.getCellData("TestCases", "Object", stepNo), excelModuleFile.getCellData("TestCases", "Value", stepNo));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		public static String createTimeStamp()
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
			Timestamp time = new Timestamp(System.currentTimeMillis());
			return sdf.format(time).toString();
		}
		public static String getHostName()
		{
			String hostname = "unknown";
	    	try
	    	{
	    	    InetAddress addr;
	    	    addr = InetAddress.getLocalHost();
	    	    hostname = addr.getHostName();
	    	}
	    	catch (UnknownHostException ex)
	    	{
	    	    System.out.println("Hostname can not be resolved");
	    	}
	    	return hostname;
		}
		public static void driverHandler()
		{
			HashMap hMap = new HashMap();
			hMap = excelDriverFile.getContentInHashMap("ModuleConfig");
			List lstModuleName = new ArrayList();
			List lstModuleExec = new ArrayList();
			List lstModuleFName = new ArrayList();
			List lstModulePath = new ArrayList();
			List lstModuleOR = new ArrayList();
			List lstModuleSite = new ArrayList();
			
			//Creating list objects for each column in the driver sheet
			lstModuleName = (List)hMap.get("Module Name");
			lstModuleExec = (List)hMap.get("Execution");
			lstModuleFName = (List)hMap.get("Module File Name");
			lstModulePath = (List)hMap.get("Module File Path");
			lstModuleOR = (List)hMap.get("OR Properties File Path");
			lstModuleSite = (List)hMap.get("Site Name");
			
			//Number of Modules in the Driver Sheet is stored in an integer variable lSize
			int lSize = lstModuleName.size();
			for(int i = 0; i < lSize; i++)
			{
				String moduleName = (String) lstModuleName.get(i);
				String execStatus = (String) lstModuleExec.get(i);
				if(execStatus.equals("ON"))
				{
					reports = new ExtentHtmlReporter(System.getProperty("user.dir")+"\\RunResults\\"+moduleName+"_"+createTimeStamp()+".html");
					extent = new ExtentReports();
					extent.attachReporter(reports);
					extent.setSystemInfo("OS",System.getProperty("os.name"));
			        extent.setSystemInfo("Tester",getHostName());
			        extent.setSystemInfo("Module Name", moduleName);
			        reports.config().setDocumentTitle("Test Reports");
			        reports.config().setReportName("Test Report");
					System.out.println("Executing Module :"+ moduleName);
					String filePath = (String) lstModulePath.get(i);
					String orFilePath = (String) lstModuleOR.get(i);
					String siteName = (String) lstModuleSite.get(i);
					
					//Loads the OR File
					objProp = loadProperties(System.getProperty("user.dir")+orFilePath);
					
					homeURL = retrieveURL(siteName);
					
					//The keyword below is to be used later
					//launchHomepage(homeURL);
					
					//Loads Module File to be used
					
					excelModuleFile = new ExcelHandlerLibrary(System.getProperty("user.dir")+filePath);
					ModuleHandler();
					reports = null;
					extent = null;
				}
			}
			
		}
		public static String checkIfLinkIsBroken(URL url) throws IOException
		{
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			try
			{

			    connection.connect();
			    String response = connection.getResponseMessage();     
			    connection.disconnect();
			    return response;

			}

			catch(Exception exp)
			{
				return exp.getMessage();
			}
		}
		public static List findAllLinks()
	    {
	    	 
	  	  List<WebElement> elementList = new ArrayList();
	   
	  	  elementList = driver.findElements(By.tagName("a"));
	   
	  	  elementList.addAll(driver.findElements(By.tagName("div")));
	   
	  	  List<WebElement> finalList = new ArrayList(); ;
	   
	  	  for (WebElement element : elementList)
	   
	  	  {
	   
	  		  if(element.getAttribute("href") != null)
	   
	  		  {
	   
	  			  finalList.add(element);
	   
	  		  }		  
	   
	  	  }	
	   
	  	  return finalList;
	   
	    }
		public static int countOptionalParams(String[] paramList)
		{
			int count = 0;
			for(String strParam:paramList)
			{
				if(strParam.indexOf('@')>=0)
					count++;
			}
			return count;
		}
		public static boolean isKeyValid(String key, String[] arrKeys)
		{
			return Arrays.asList(arrKeys).contains(key);
		}
		public static HashMap associateParametersWithValues(String strParam, String validParams)
		{
			//returns a hashmap with the list of parameters. Also, checks if the parameters are valid
			String[] arrParams = strParam.split(";");
			String[] arrValidParam = validParams.split(",");
			int iOptionalParam = countOptionalParams(arrValidParam);
			int iOptionalInputParam = countOptionalParams(arrParams);
			int iMandatoryParam = arrValidParam.length - iOptionalParam;
			HashMap<String,String> hParamMap = new HashMap();			
			if(arrParams.length > 1)
			{
				if(arrParams.length - iOptionalInputParam == iMandatoryParam)	
				{
					for(String strTemp:arrParams)
					{
						String[] arrParam = strTemp.split(":",2);
						if(isKeyValid(arrParam[0],arrValidParam))
						{
							arrParam[1] = returnDataInIterationSheetIfExists(arrParam[1]);
							hParamMap.put(arrParam[0], arrParam[1]);					
						}
					}
					System.out.println("Great Success 1!");
				}
				else
				{
					System.out.println("Failure1");
				}
			}
			else if(arrParams.length == 1)
			{
				if(arrParams.length == iMandatoryParam)
				{
					String strTemp = arrParams[0];
					String[] arrParam = strTemp.split(":");
					if(isKeyValid(arrParam[0],arrValidParam))
					{
						arrParam[1] = returnDataInIterationSheetIfExists(arrParam[1]);
						hParamMap.put(arrParam[0], arrParam[1]);
					//System.out.println("Great Success!");
					}
				}
				else
				{
					System.out.println("Failure");
				}
			}
			return hParamMap;
		}
		
		public static String returnDataInIterationSheetIfExists(String strColName)
		{
			if(excelModuleFile.columnExists(strIteration, strColName))
				return excelModuleFile.getCellData(strIteration, strColName, iIterNum+1);
			else
				return strColName;
			//return null;
		}
		
		public static HashMap returnTextValuesInHashMap(WebElement we,String[] strCSSValues)
		{
			HashMap<String,String> hCSSMap = new HashMap();
			try{
				for(String strCSSValue:strCSSValues)
				{
					if(strCSSValue.equalsIgnoreCase("text"))
					{
						hCSSMap.put(strCSSValue, we.getText());
					}
					else
					{
						hCSSMap.put(strCSSValue, we.getCssValue(strCSSValue));
					}
				}
			}
			catch(Exception e)
			{
				System.out.println("Error readying hashmap");
			}
			return hCSSMap;
		}
		public static void storeResultInExcel(HashMap hCSSMap, String strSheetName, String[] strValidArray, int rowNum) throws InterruptedException
		{
			
			Iterator iter = hCSSMap.entrySet().iterator();
			int i = 0;
			while(iter.hasNext())
			{
				Map.Entry mapVal = (Map.Entry)iter.next();
				if(Arrays.asList(strValidArray).contains(mapVal.getKey()))
				{
//					Thread.sleep(100);
					excelDataFile.setCellData(strSheetName, mapVal.getKey().toString(), rowNum, mapVal.getValue().toString());
				}
				
			}
		}
		
		public static List storeResultInExcelReturnList(HashMap hCSSMap, String strSheetName, String[] strValidArray, int rowNum) throws InterruptedException
		{
			
			Iterator iter = hCSSMap.entrySet().iterator();
			List l1 = new ArrayList();
			int i = 0;
			while(iter.hasNext())
			{
				Map.Entry mapVal = (Map.Entry)iter.next();
				if(Arrays.asList(strValidArray).contains(mapVal.getKey()))
				{
//					Thread.sleep(100);
					excelDataFile.setCellData(strSheetName, mapVal.getKey().toString(), rowNum, mapVal.getValue().toString());
					System.out.println("ds");
				}
				
			}
			return l1;
		}
		
		public static void verifyCSSProperties(HashMap hMap, String sheetName, String[] validFamily, int rowNum)
		{
			Iterator iter = hMap.entrySet().iterator();
			for(String strFamilyMember:validFamily)
			{
				if(hMap.get(strFamilyMember).toString().equals(excelDataFile.getCellData(sheetName, strFamilyMember, rowNum)))
				{
					//Assert.class
					//SoftAssert.assertEquals(contentText, excelDataFile.getCellData("textData", "Text", j));
					
					//softAssert.assertEquals(contentText, excelDataFile.getCellData("textData", "Text", j));
					System.out.println(strFamilyMember+": Data matches");
					excelDataFile.setCellData(sheetName, "Status-"+strFamilyMember, rowNum, "Passed");
				}
				else
				{
					//softAssert.assertEquals(contentText, excelDataFile.getCellData("textData", "Text", j));
					System.out.println(strFamilyMember+": Data does not match");
					excelDataFile.setCellData(sheetName, "Status-"+strFamilyMember, rowNum, "Failed");
					excelDataFile.setCellData("textData", "Actual-"+strFamilyMember, rowNum,hMap.get(strFamilyMember).toString());
				}
			}
		}
		public static HashMap parseDocumentStamp(String strDocumentStamp)
		{
			HashMap<String,List> hMapDocStamp = new HashMap();
			String[] arrParams = strDocumentStamp.split(";");
			for(String strParam: arrParams)
			{
				/*List<Integer> list1 = new ArrayList();
				//list1 = Arrays.asList(strParam.split(","));
				String[] strTemp = strParam.split(",");
				List<String> lstTemp = strTemp.
				list1.addAll(strTemp.stream().map(Integer::valueOf).collect(Collectors.toList()));*/
				String strHolder[] = strParam.split(":");
				List<Integer> list1 = new ArrayList();
				//int tempArray[] = new int[1];
				String strTemp[] = strHolder[1].split(",");
				for(String strIter: strTemp)
				{
					list1.add(Integer.parseInt(strIter));
//					tempArray[]
				}
				hMapDocStamp.put(strHolder[0], list1);
			}
			return hMapDocStamp;
			
			
		}
		/*public static void verifyCSSProperties(HashMap hMap, String sheetName, String[] validFamily, String strDocumentStamp)
		{
			Iterator iter = hMap.entrySet().iterator();
			for(String strFamilyMember:validFamily)
			{
				if(hMap.get(strFamilyMember).toString().equals(excelDataFile.getCellData(sheetName, strFamilyMember, rowNum)))
				{
					//Assert.class
					//SoftAssert.assertEquals(contentText, excelDataFile.getCellData("textData", "Text", j));
					
					//softAssert.assertEquals(contentText, excelDataFile.getCellData("textData", "Text", j));
					System.out.println("Data matches");
					excelDataFile.setCellData(sheetName, "Status-"+strFamilyMember, rowNum, "Passed");
				}
				else
				{
					//softAssert.assertEquals(contentText, excelDataFile.getCellData("textData", "Text", j));
					System.out.println("Data does not match");
					excelDataFile.setCellData(sheetName, "Status-"+strFamilyMember, rowNum, "Failed");
					excelDataFile.setCellData("textData", "Actual-"+strFamilyMember, rowNum,hMap.get(strFamilyMember).toString());
				}
			}
		}*/
}
