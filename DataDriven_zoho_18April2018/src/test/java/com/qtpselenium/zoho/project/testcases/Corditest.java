package com.qtpselenium.zoho.project.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class Corditest {
	
	@Test
	  public void testGoogle() throws InterruptedException
	  {
		    System.setProperty("webdriver.gecko.driver", "E:\\SELENIUM_DOWNLOADS\\Drivers\\geckodriver.exe");
			WebDriver driver= new FirefoxDriver();
		
//		  System.setProperty("webdriver.gecko.driver", "E:\\SELENIUM_DOWNLOADS\\Drivers\\geckodriver.exe");
//		  WebDriver driver = new FirefoxDriver();
		  driver.get("https://accounts.zoho.com/signin?servicename=ZohoCRM&signupurl=https://www.zoho.com/crm/lp/signup.html?plan=enterprise");
		  driver.findElement(By.xpath("//*[@id='lid']")).sendKeys("vrushisa36.13@gmail.com");
		  driver.findElement(By.xpath("//*[@id='pwd']")).sendKeys("VRUSH.123");
		  driver.findElement(By.xpath("//*[@id='signin_submit']")).submit();
		  Thread.sleep(6000);
		 // driver.findElement(By.xpath("//table[@class='newMenuTable']/tbody/tr/td[2]/div[2]/a[4]")).click();
		  WebElement ele =driver.findElement(By.xpath("//*[@id='tab_Leads']"));
		  JavascriptExecutor executor = (JavascriptExecutor)driver;
		  executor.executeScript("arguments[0].click();", ele);

		  
		  
	  }

}
