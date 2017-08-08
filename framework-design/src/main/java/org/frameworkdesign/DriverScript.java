package org.frameworkdesign;

import org.frameworkdesign.excelhandler.ExcelHandlerLibrary;
import org.frameworkdesign.frameworkkeywords.FrameworkKeywords;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.Test;

@SuppressWarnings("all")
public class DriverScript extends FrameworkKeywords{
	//All methods related to handling the flow of execution have been moved to frameworkKeywords.java
	@BeforeClass
	@Parameters({"driverFile"})
	public static void storeURLValue(String driverFile)
	{
		driverFile = System.getProperty("user.dir") + driverFile;
		excelDriverFile = new ExcelHandlerLibrary(driverFile);
		System.out.println("Check");
	}
	@Test
	public static void mainArea() throws Exception
	{
				
		FrameworkKeywords.driverHandler();
		
	}
}
