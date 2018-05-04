package com.qtpselenium.zoho.project.util;

import java.util.Hashtable;



public class DataUtil {
	 public static Object[][] getData(Xls_Reader xlsr ,String testName)
	 {
		 
		 Xls_Reader xls =new Xls_Reader("E:\\temp\\zoho.xlsx");
		// Xls_Reader xls =new Xls_Reader("E:\\temp\\Data.xlsx");
		 String sheetName ="Data";
		 //String testCaseName = "CreateLeadTest";
				
			 int testStartRowNum  =1;
			 while(!xls.getCellData(sheetName, 0, testStartRowNum).equals(testName))
			 {
				 testStartRowNum++;
			 }
			 System.out.println("Test starts from row "+testStartRowNum);
			 int  colStartRowNum = testStartRowNum+1;
			 int  dataStartRowNum = testStartRowNum+2;
			 
			 //calculate row of data
			 int rows =0;
			 while(!xls.getCellData(sheetName, 0, dataStartRowNum+rows).equals(""))
			 {
				 rows++;
			 }
			 System.out.println("Total rows are "+rows);
			 
			 int cols =0;
			 while(!xls.getCellData(sheetName, cols, colStartRowNum).equals(""))
			 {
				 cols++;
			 }
			 System.out.println("Total cols are "+cols);
			// Object[][] data =new Object[rows][cols];
			 Object[][] data =new Object[rows][1];
			 int dataRow =0;
			 Hashtable<String,String> table =null;
			 for (int rNum =dataStartRowNum;rNum< dataStartRowNum+rows;rNum++)
			 {
				 table  =new Hashtable<String,String>();
				 for (int cNum =0;cNum <cols;cNum++)
				 {
					 String Key = xls.getCellData(sheetName,cNum,colStartRowNum);
					 String value =xls.getCellData(sheetName, cNum, rNum);
					//data[dataRow][cNum] = xls.getCellData(sheetName, cNum, rNum);
					 table.put(Key, value);
				 }
				 data[dataRow][0] =table;
				// System.out.println();
				 dataRow++;
				
			 }
			
				return data;
	 }
	 
	 public static  boolean isRunnable(String testName,Xls_Reader xls)
	 {
		 String sheet ="Testcases";
		 int rows= xls.getRowCount(sheet);
		 for(int r =2;r<=rows;r++)
		 {
			 String tName =xls.getCellData(sheet, "TCID", r);
			 if(tName.equals(testName))
			 {
				 String runmode = xls.getCellData(sheet, "Runmode", r);
				 if(runmode.equals("Y"))
				 {
					 return true;
				 }
				 else
					 return false;
				 
			 }
		 }
		return false;
		 
	 }
}



