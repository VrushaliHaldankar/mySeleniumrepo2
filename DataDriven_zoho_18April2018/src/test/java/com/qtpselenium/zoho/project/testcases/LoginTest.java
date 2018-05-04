package com.qtpselenium.zoho.project.testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qtpselenium.zoho.project.base.BaseTest;
import com.qtpselenium.zoho.project.util.DataUtil;
import com.qtpselenium.zoho.project.util.ExtentManager;
import com.qtpselenium.zoho.project.util.Xls_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class LoginTest extends BaseTest{
	String testCaseName = "LoginTest";
	SoftAssert srt  ;
	ExtentReports rep  =ExtentManager.getInstance();
	ExtentTest test ;
	Xls_Reader xls;
	
	@BeforeTest
	public void init()
	{srt =new SoftAssert();
	//init();
	}
	
	@Test(dataProvider= "getData")
	public void doLoginTest(Hashtable<String, String> data) throws IOException
	{
		test =rep.startTest("LoginTest");
		test.log(LogStatus.INFO, data.toString());
		if(!DataUtil.isRunnable(testCaseName,xls)||data.get("Runmode").equals("N"))
		{
			test.log(LogStatus.SKIP, "Skipping the test as Runmode is N");
			throw new SkipException("Skipping the test as Runmode is N");
		}
		
		OpenBrowser(data.get("Browser"));
		Navigate("Zoho_URL");
		boolean ActualResult=doLogin(data.get("Username"),data.get("Password"));
		
		boolean expectedResult =false;
		if(data.get("ExpectedResult").equals("Y"))
		{
			expectedResult =true;
		}
		else {
			expectedResult= false;
		}
		
		if(expectedResult!= ActualResult)
		{
			reportFailure("Login Test Failed");
			reportPass("Login Test Passed");
		}
		//driver.close();
	}
	
	@AfterMethod
	public void Quit() {
	try{
		srt.assertAll();
	}
	catch(Error e)
	{
		test.log(LogStatus.INFO, e.getMessage());
	}
	
	
	
  rep.endTest(test);
  rep.flush();
  
  if(driver!=null)
  {
	  driver.quit();
  }
	}
	
	 @DataProvider
	 public Object[][] getData() throws IOException
	 {
		 super.init();
		  xls =new Xls_Reader(prop.getProperty("zoho_path"));
		return DataUtil.getData(xls, testCaseName);
			 }


}
