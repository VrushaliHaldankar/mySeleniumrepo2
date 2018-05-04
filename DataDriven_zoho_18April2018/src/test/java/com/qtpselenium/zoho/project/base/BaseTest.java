package com.qtpselenium.zoho.project.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.testng.Assert;


import com.qtpselenium.zoho.project.util.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



public class BaseTest {

	public WebDriver driver;
	public Properties prop;
	public static ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test =rep.startTest("BaseTest");
	//SoftAssert srt  =new SoftAssert();
	
	
	public void init() throws IOException
	{
		if(prop ==null)
		{
			prop =new Properties();
			try {
				FileInputStream fs =new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//projectconfig.properties");
				prop.load(fs);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void OpenBrowser(String bType) throws IOException
	{
		
		//System.out.println(prop.getProperty("Zoho_URL"));
		
		if(bType.equals("Morzilla"))
		{
			  System.setProperty("webdriver.gecko.driver", prop.getProperty("geckodriver_exe_Xpath"));
			driver= new FirefoxDriver();
		}
		else if (bType.equals("Chrome"))
		{
			System.setProperty("webdriver.chrome.driver",  prop.getProperty("chromedriver_exe_Xpath"));
			driver= new ChromeDriver();
		}
		else if (bType.equals("IE"))
		{
			System.setProperty("webdriver.ie.driver", "E:\\SELENIUM_DOWNLOADS\\Drivers\\IEDriverServer_Win32_3.8.0\\IEDriverServer.exe");
			//driver= new InternetExplorerDriver();
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		
	}
	public void Navigate(String url) throws IOException
	{
		test.log(LogStatus.INFO, " Navigating to : "+url);
		driver.navigate().to(prop.getProperty(url));
	}
	
	
	public void type(String xPathName, String Typeusername)
	{   
		test.log(LogStatus.INFO, " Typing in : "+xPathName + " Data" +Typeusername);
		getElement(xPathName).sendKeys(Typeusername );
		test.log(LogStatus.INFO, " Typed successfully : "+xPathName + " Data" +Typeusername);
	}
	
	public void ClickLink(String LinkText)
	{
		test.log(LogStatus.INFO, " clicking  on: "+LinkText);
		////getElement(Button).click();
		WebElement ele =getElement(LinkText);
		
		  JavascriptExecutor executor = (JavascriptExecutor)driver;
		  executor.executeScript("arguments[0].click();", ele);

			test.log(LogStatus.INFO, " clicking  successfully: "+LinkText);
	}
	public void Click(String Button)
	{
		test.log(LogStatus.INFO, " clicking  on: "+Button);
		getElement(Button).click();
		//driver.findElement(By.xpath(Button)).click();;
		test.log(LogStatus.INFO, " clicking  successfully: "+Button);
	}
	
	public void ClickandWait(String locator_clicked ,String Locator_Press) throws InterruptedException
	{
		test.log(LogStatus.INFO, "Clicking and waiting  on : " +locator_clicked);
		int count =5;
		for(int i= 0;i<count;i++)
		{
			getElement(locator_clicked).click();
			wait(3);
			if(isElementPresent(Locator_Press))
				break;
			
		}
	}
	
	public int getLeadRowNum(String LeadName)
	{
		test.log(LogStatus.INFO, "Finding the Lead : " +LeadName);
		List<WebElement> leadNames= driver.findElements(By.xpath(prop.getProperty("LeadNames_columns_Xpath")));
		for (int i=0;i<leadNames.size();i++)
		{
			System.out.println(leadNames.get(i).getText());
			if(leadNames.get(i).getText().trim().equals(LeadName))
			{
				test.log(LogStatus.INFO, "Lead found in rowNum " +i);
				return i+1;
			}
		}
		test.log(LogStatus.INFO, "Lead not Found");
		return -1;
	}
	
	public int getAccountRowNum(String AccountName)
	{
		test.log(LogStatus.INFO, "Finding the Lead : " +AccountName);
		List<WebElement> AccountNames= driver.findElements(By.xpath(prop.getProperty("AccountNames_Column_Xpath")));
		for (int i=1;i<AccountNames.size();i++)
		{
			System.out.println(AccountNames.get(i).getText());
			if(AccountNames.get(i).getText().trim().equals(AccountName))
			{
				test.log(LogStatus.INFO, "AccountName found in rowNum " +i);
				return i+1;
			}
		}
		test.log(LogStatus.INFO, "AccountName not Found");
		return -1;
	}
	
	
	
	public WebElement getElement(String locatorKey)
	{
		WebElement e =null;
		try {
		if(locatorKey.endsWith("_id"))
		{
			e= driver.findElement(By.id(prop.getProperty(locatorKey)));
		}
		else if(locatorKey.endsWith("_name"))
		{
			e= driver.findElement(By.name(prop.getProperty(locatorKey)));
		}
		else if(locatorKey.endsWith("_Xpath"))
		{
			e= driver.findElement(By.xpath(prop.getProperty(locatorKey)));
		}
		
		else
		{
			reportFailure("Locator not correct");
			Assert.fail("Failed the Test " + locatorKey);
		}
		}
		catch(Exception ex)
		{
			reportFailure(ex.getMessage());
			ex.printStackTrace();
			Assert.fail("Failed the Test " + ex.getMessage());
		}
		return e;
	}
	
	public void wait(int TimeTowait) throws InterruptedException
	{
		Thread.sleep(TimeTowait*1000);
	}
	/******************************* Validations *****************************/
	
	public boolean verifyTitle()
	{
		return false;
	}
	
	public boolean isElementPresent(String locatorKey){
		List<WebElement> elementList=null;
		if(locatorKey.endsWith("_id"))
			elementList = driver.findElements(By.id(prop.getProperty(locatorKey)));
		else if(locatorKey.endsWith("_name"))
			elementList = driver.findElements(By.name(prop.getProperty(locatorKey)));
		else if(locatorKey.endsWith("_Xpath"))
			elementList = driver.findElements(By.xpath(prop.getProperty(locatorKey)));
		else{
			reportFailure("Locator not correct - " + locatorKey);
			Assert.fail("Locator not correct - " + locatorKey);
		}
		
		if(elementList.size()==0)
			return false;	
		else
			return true;
	}
	
	public boolean VerifyText(String LoactorKey,String ExpectedKey)
	{
		String actualText = getElement(LoactorKey).getText().trim();
		String expectedText =prop.getProperty(ExpectedKey);
		if(actualText.equals(expectedText))
		return true;
		else
		return false;
	}
	
	
	public void ClickOnLead(String LeadName)
	{
		test.log(LogStatus.INFO, "Finding the Lead : " +LeadName);
		int rNum = getLeadRowNum(LeadName);
		driver.findElement(By.xpath(prop.getProperty("leadPart1_xpath")+(rNum+1)+(prop.getProperty("leadPart2_xpath")))).click();
		
	}
	
	
	public void ClickOnAccount(String AccountName)
	{
		test.log(LogStatus.INFO, "Finding the Lead : " +AccountName);
		int rNum = getAccountRowNum(AccountName);
		driver.findElement(By.xpath(prop.getProperty("AccountPart1_Xpath")+(rNum+1)+(prop.getProperty("AccountPart2_Xpath")))).click();
		
	}
	/**********************Reporting***************************************/
	
	public void reportPass(String msg)
	{
		test.log(LogStatus.PASS, "");
	}
	
	public void reportFailure(String msg)
	{
		test.log(LogStatus.FAIL, "Incorrect data");
		Assert.fail();
		takeScreenshot();
	}
	
	public void takeScreenshot()
	{
		Date  d =new Date();
		//String screenshotFile  = d.toString().replace(":", "_").replace(" ", "_")+".png";
		String screenshotFile  = d.toString().replace(":", "_").replace(" ", "_")+".png";
		try 
		{
		
		File scrFile  =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"\\Screenshots\\"+screenshotFile));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
	     
		}
		
		//test.log(LogStatus.INFO,"Screenshot-> "+ test.addScreenCapture("E:\\selenium_practice\\Maven_Workspace\\Data_Driven_core_Framework\\Screenshots"+screenshotFile));
		
		test.log(LogStatus.INFO,"Screenshots -> "+test.addScreenCapture(System.getProperty("user.dir")+"//Screenshots//"+screenshotFile));
	}
	
	/******************* App Functions *************************/
	public boolean doLogin(String username,String Password)
	{
		type("zoho_CRM_Id_id", username);
		type("zoho_CRM_Pwd_id",Password);
		WebElement ele =driver.findElement(By.xpath(prop.getProperty("SignIn_Xpath")));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", ele);


		//Click("SignIn_Xpath");
		
		if(isElementPresent("Welcome_screen_Xpath"))
		{
			test.log(LogStatus.INFO, "Login Success");
		return true;
		}
		else
			test.log(LogStatus.INFO, "Login Failed");
			return false;
	}

}
