package com.qtpselenium.zoho.project.testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
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

public class LeadTest extends BaseTest {
	Xls_Reader xls;
	SoftAssert srt;
	//String testCaseName = "CreateLeadTest";
	ExtentReports rep  =ExtentManager.getInstance();
	ExtentTest test ;
	

	
	@BeforeMethod
	public void init()
	{srt =new SoftAssert();
		//init();
	}
	@Test(priority=1, dataProvider= "getData")
	public void CreateLeadTest(Hashtable<String, String> data) throws IOException, InterruptedException
	{
		test =rep.startTest("CreateLeadTest");
		
		if(!DataUtil.isRunnable("CreateLeadTest",xls)||data.get("Runmode").equals("N"))
		{
			test.log(LogStatus.SKIP, "Skipping the test as Runmode is N");
			throw new SkipException("Skipping the test as Runmode is N");
		}
		 
		//init();
		OpenBrowser(data.get("Browser"));
		Navigate("Zoho_URL");
		doLogin(prop.getProperty("Zoho_username"), prop.getProperty("Zoho_Password"));
	
	    wait(6);
		ClickLink("Link_Lead_Xpath");
		wait(2);
		ClickLink("Create_Lead_Xpath");
		type("Company_name_id",data.get("Lead_Company"));
		type("Lastname_id",data.get("Lead_Lastname"));
		ClickLink("Save_id");
		ClickLink("Link_Lead_Xpath");
		int rNum  =getLeadRowNum(data.get("Lead_Lastname"));
		
		if(rNum ==-1)
		{
			reportFailure("Lead not found in Lead Table "+ data.get("Lead_Lastname"));
			
		}
		reportPass("Lead found in Lead Table "  +data.get("Lead_Lastname"));
		takeScreenshot();
		//Assert.fail();
	}
	@Test(priority=2, dataProvider ="getData")
	public void ConvertLeadTest(Hashtable<String, String> data) throws IOException, InterruptedException
	{
        test =rep.startTest("ConvertLeadTest");
		
		if(!DataUtil.isRunnable("ConvertLeadTest",xls)||data.get("Runmode").equals("N"))
		{
			test.log(LogStatus.SKIP, "Skipping the test as Runmode is N");
			throw new SkipException("Skipping the test as Runmode is N");
		}
		OpenBrowser(data.get("Browser"));
		Navigate("Zoho_URL");
		doLogin(prop.getProperty("Zoho_username"), prop.getProperty("Zoho_Password"));
	
	    wait(6);
		ClickLink("Link_Lead_Xpath");
		//int rNum  =getLeadRowNum(data.get("Lead_Lastname"));
		ClickOnLead(data.get("Lead_Lastname"));
		Click("Convert_Xpath");
		Click("Convert_Save_Xpath");
		
		
		
	}
	@Test(priority=3,dataProvider="getDataDeleteLead")
	public void deleteLeadAccountTest(Hashtable<String, String> data) throws IOException, InterruptedException
	{
     test =rep.startTest("DeleteLeadAccountTest");
		
		if(!DataUtil.isRunnable("DeleteLeadAccountTest",xls)||data.get("Runmode").equals("N"))
		{
			test.log(LogStatus.SKIP, "Skipping the test as Runmode is N");
			throw new SkipException("Skipping the test as Runmode is N");
		}
		OpenBrowser(data.get("Browser"));
		Navigate("Zoho_URL");
		doLogin(prop.getProperty("Zoho_username"), prop.getProperty("Zoho_Password"));
//		ClickLink("Create_Lead_Xpath");
//		type("Company_name_id",data.get("Lead_Company"));
//		type("Lastname_id",data.get("Lead_Lastname"));
//		ClickLink("Save_id");
//		
	 //   wait(3);
	    ClickLink("Link_Account_Xpath");
		wait(2);
		ClickOnAccount(data.get("Lead_Lastname"));
		Click("CustomAction_Xpath");
		ClickLink("Delete_Xpath");
		Click("Confirm_delete_Xpath");
		int rNum  =getAccountRowNum(data.get("Lead_Lastname"));
		if(rNum ==-1)
		{
			reportFailure("AccountName not found in Lead Table "+ data.get("Lead_Lastname"));
			
		}
		reportPass("AccountName found in Lead Table "  +data.get("Lead_Lastname"));
		takeScreenshot();
		
		
	}
	 @DataProvider
	 public Object[][] getData() throws IOException
	 {
		 super.init();
		  xls =new Xls_Reader(prop.getProperty("zoho_path"));
		return DataUtil.getData(xls, "CreateLeadTest");
			 }
//	 @DataProvider
//	 public Object[][] getDataConvertLead() throws IOException
//	 {
//		 super.init();
//		  xls =new Xls_Reader(prop.getProperty("zoho_path"));
//		return DataUtil.getData(xls, "ConvertLeadTest");
//			 }



	 @DataProvider
	 public Object[][] getDataDeleteLead() throws IOException
	 {
		 super.init();
		  xls =new Xls_Reader(prop.getProperty("zoho_path"));
		return DataUtil.getData(xls,"DeleteLeadAccountTest");
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
		
		
		if(rep!=null)
	  rep.endTest(test);
	  rep.flush();
	  
//	  if(driver!=null)
//	  {
//		  driver.quit();
//	  }
		}
}
