package org.frameworkdesign;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Properties;

import org.frameworkdesign.excelhandler.ExcelHandlerLibrary;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentReports;

public class GlobalDeclarations {
	public static File fileObj;
	//public static File fileURL;
	public static FileInputStream fileInputObj;
	//public static FileInputStream fileInputURL;
	
	public static WebDriver driver;
	public static Properties objProp;
	public static Properties urlProp;
	public static String strURL;
	public static String homeURL;	
	public static String strDocumentStamp;
	public static String CUR_DIR = System.getProperty("user.dir");
	public static WebElement ele;
	public static ExcelHandlerLibrary excelModuleFile;
	public static ExcelHandlerLibrary excelDriverFile;
	public static ExcelHandlerLibrary excelDataFile;
	public static Method objMethod;
	//The iteration sheet name and the iteration number being executed are stored in the strIteration 
	//and iIterNum respectively
	public static int iIterNum;
	public static int iCurrStep;
	public static String strIteration;
	public static String URL_LOCATION = CUR_DIR+"//Resources//URL//URLs.properties";
	public static HashMap<String, Integer> hMapForCurrentState = new HashMap<String, Integer>();
	public static ExtentReports extent;
}
