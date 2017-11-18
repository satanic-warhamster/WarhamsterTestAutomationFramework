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
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

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
	public static ExcelHandlerLibrary excelBlockFile;
	public static Method objMethod;
	//The iteration sheet name and the iteration number being executed are stored in the strIteration 
	//and iIterNum respectively
	public static int iIterNum;
	public static int iCurrStep;
	public static String strIteration;
	public static String URL_LOCATION = CUR_DIR+"\\Resources\\URL\\URLs.properties";
	public static HashMap<String, Integer> hMapForCurrentState = new HashMap<String, Integer>();
	public static HashMap<String, String> hMapParam = new HashMap<String, String>();
	//For reporting purposes
	public static ExtentHtmlReporter reports;
	public static ExtentReports extent;
    public static ExtentTest logger;
    //End of For Reporting Purposes
    //For test step initiation
    public static String strObject;
    public static String strValue;
    public static String strErrorAction;
    //End of For Test step initiation
    public static boolean boolInBlock=false;
}
