package com.pentonix.apache.apiservice;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.pentonix.apache.apiservice.MainSheet;

@Service
public class ApiDataService {
//	private static final String excelFilePath =  "s3://pentonix-workflow-storage/organization-1/new_data_model_updated_v2_Layout-1.xlsx";
	
//	BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials("AKIAVVSJEAQ6HHA4VUE7", "sSS2D5BGuTeIuPrvgkyc27WtjaSvqJIAT0CjdCiR");
	BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials("AKIAXD5CLWJVTHPMIR76",
			"UfTNFtKk3G/kuBm4AR5Say8vHhibmn8hEFnvhQu8");
	AmazonS3Client amazonS3 = (AmazonS3Client) AmazonS3Client.builder().withRegion("us-east-2").withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials)).build();
//	AmazonDynamoDB dynamoDB = AmazonDynamoDBClient.builder().withRegion("us-east-2").withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials)).build();
	
	//Router service class
	public List getReadExcelFile(String sheetname,String Filename) throws InvalidFormatException {
		List values = new ArrayList<>();
		switch(sheetname) {
		case "Text" :
			values = readExcelFileText("Text",Filename);
			break;
		case "Image" :
			values = readExcelFileText("Image",Filename);
			break;
		case "Header" :
			values = readExcelFileText("Header",Filename);
			break;
		case "BulletText" :
			values = readExcelFileText("BulletText",Filename);
			break;
		case "Button" :
			values = readExcelFileText("Button",Filename);
			break;
		case "TextWithSearch" :
			values = readExcelFileText("TextWithSearch",Filename);
			break;
		}
		return values;
	}
	
	public File getFileFromBucket(String fileName) {
        GetObjectRequest getObjectRequest = new GetObjectRequest("workflow-storage-bucket", fileName);
        S3Object s3Object = amazonS3.getObject(getObjectRequest);
        File s3File = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(s3File)) { //throws Exception
            IOUtils.copy(s3Object.getObjectContent(), fos); 
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s3File;
    }
	
	public List readExcelFileMain(String filename) throws InvalidFormatException{
		try {
//			FileInputStream excelFile = new FileInputStream(new File("/home/computer/Desktop/Workflow/Updated_Model/subnodes_data_model.xlsx"));
//    		Workbook workbook = new XSSFWorkbook(excelFile);
    		
    		Workbook workbook = new XSSFWorkbook(getFileFromBucket(filename));
//    		Workbook wb = WorkbookFactory.create(excelFile);
//    		FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
    		DataFormatter objDefaultFormat = new DataFormatter();


    		Sheet sheet = workbook.getSheet("Main");
    		Iterator rows = sheet.iterator();
    		
    		List mainSheetValues = new ArrayList<>();
    		
    		int rowNumber = 0;
    		while (rows.hasNext()) {
    			Row currentRow = (Row) rows.next();
    			
    			if(rowNumber == 0) {
    				rowNumber++;
    				continue;
    			}   			
    			Iterator cellsInRow = currentRow.iterator();
    			MainSheet main = new MainSheet();
    			
    			int cellIndex = 0;
    			
    			while (cellsInRow.hasNext()) {
    				Cell currentCell = (Cell) cellsInRow.next();
				    String cellValueStr;

    				if( currentCell == null ) {
    					cellValueStr = "";
    				}else {
    					cellValueStr = objDefaultFormat.formatCellValue(currentCell);
    				}
				    
    				if(cellIndex==0) { // node
    					main.setNode( cellValueStr );
    				} else if(cellIndex==1) { // nodename
    					main.setNodename( cellValueStr );
    				}else if(cellIndex==2) { // screentype
    					main.setScreentype( cellValueStr );
    				} else if(cellIndex==3) { // header
    					main.setHeader( cellValueStr );
    				} else if(cellIndex==4) { // layout
    					main.setLayout( cellValueStr );
    				}else if(cellIndex==5) { // footer
    					main.setSubNode( cellValueStr );
    				}else if(cellIndex==6) { // footer
    					main.setPrevNode( cellValueStr );
    				}else if(cellIndex==7) { // footer
    					main.setNextNode( cellValueStr );
    				}
   				
    				cellIndex++;
    			}
    			
    			mainSheetValues.add(main);
    		}
    		
    		workbook.close();
    		
    		return mainSheetValues;

        } catch (IOException e) {
        	throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
	}
	
	public List readExcelFileSubNode(String filename) throws InvalidFormatException{
		try {
//			FileInputStream excelFile = new FileInputStream(new File("/home/computer/Desktop/Workflow/Updated_Model/subnodes_data_model.xlsx"));
//    		Workbook workbook = new XSSFWorkbook(excelFile);
    		
    		Workbook workbook = new XSSFWorkbook(getFileFromBucket(filename));
//    		Workbook wb = WorkbookFactory.create(excelFile);
//    		FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
    		DataFormatter objDefaultFormat = new DataFormatter();


    		Sheet sheet = workbook.getSheet("SubNode");
    		Iterator rows = sheet.iterator();
    		
    		List mainSheetValues = new ArrayList<>();
    		
    		int rowNumber = 0;
    		while (rows.hasNext()) {
    			Row currentRow = (Row) rows.next();
    			
    			if(rowNumber == 0) {
    				rowNumber++;
    				continue;
    			}   			
    			Iterator cellsInRow = currentRow.iterator();
    			MainSheet main = new MainSheet();
    			
    			int cellIndex = 0;
    			
    			while (cellsInRow.hasNext()) {
    				Cell currentCell = (Cell) cellsInRow.next();
				    String cellValueStr = objDefaultFormat.formatCellValue(currentCell);
				    
    				if(cellIndex==0) { // node
    					main.setNode( cellValueStr );
    				} else if(cellIndex==1) { // nodename
    					main.setNodename( cellValueStr );
    				}else if(cellIndex==2) { // screentype
    					main.setScreentype( cellValueStr );
    				} else if(cellIndex==3) { // header
    					main.setHeader( cellValueStr );
    				} else if(cellIndex==4) { // layout
    					main.setLayout( cellValueStr );
    				}else if(cellIndex==5) { // footer
    					main.setSubNode( cellValueStr );
    				}else if(cellIndex==6) { // footer
    					main.setPrevNode( cellValueStr );
    				}else if(cellIndex==7) { // footer
    					main.setNextNode( cellValueStr );
    				}
   				
    				cellIndex++;
    			}
    			
    			mainSheetValues.add(main);
    		}
    		
    		workbook.close();
    		
    		return mainSheetValues;

        } catch (IOException e) {
        	throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
	}
	
	
	public List readExcelFileText(String filename,String Filename) throws InvalidFormatException{
		try {
//			FileInputStream excelFile = new FileInputStream(new File("/home/computer/Desktop/Workflow/Updated_Model/subnodes_data_model.xlsx"));
//    		Workbook workbook = new XSSFWorkbook(excelFile);

    		Workbook workbook = new XSSFWorkbook(getFileFromBucket(Filename));

    		Sheet sheet = workbook.getSheet(filename);
    		Iterator rows = sheet.iterator();

    		List sheetValue = new ArrayList<TextSheet>();

    		int rowNumber = 0;
    		while (rows.hasNext()) {
    			Row currentRow = (Row) rows.next();

    			if(rowNumber == 0) {
    				rowNumber++;
    				continue;
    			}

    			Iterator cellsInRow = currentRow.iterator();

    			TextSheet textsheet = new TextSheet();

    			int cellIndex = 0;
    			while (cellsInRow.hasNext()) {
    				Cell currentCell = (Cell) cellsInRow.next();

    				if(cellIndex==0) { // node
    					textsheet.setId((int) currentCell.getNumericCellValue());
    				} else if(cellIndex==1) { // Name
    					textsheet.setText(currentCell.getStringCellValue());
    				}

    				cellIndex++;
    			}

    			sheetValue.add(textsheet);
    		}

    		workbook.close();

    		return sheetValue;

        } catch (IOException e) {
        	throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
	}
	public List readExcelFileNextPrev(String filename,String Filename) throws InvalidFormatException{
		try {
//			FileInputStream excelFile = new FileInputStream(new File(excelFilePath));
//    		Workbook workbook = new XSSFWorkbook(excelFile);

    		Workbook workbook = new XSSFWorkbook(getFileFromBucket(Filename));

    		Sheet sheet = workbook.getSheet(filename);
    		Iterator rows = sheet.iterator();

    		List sheetValue = new ArrayList<>();

    		int rowNumber = 0;
    		while (rows.hasNext()) {
    			Row currentRow = (Row) rows.next();

    			if(rowNumber == 0) {
    				rowNumber++;
    				continue;
    			}

    			Iterator cellsInRow = currentRow.iterator();

    			PreviousSheet previousSheet = new PreviousSheet();

    			int cellIndex = 0;
    			while (cellsInRow.hasNext()) {
    				Cell currentCell = (Cell) cellsInRow.next();

    				if(cellIndex==0) { // node
    					previousSheet.setId((int) currentCell.getNumericCellValue());
    				} else if(cellIndex==1) { // Name
    					previousSheet.setText(currentCell.getNumericCellValue());
    				}

    				cellIndex++;
    			}

    			sheetValue.add(previousSheet);
    		}

    		workbook.close();

    		return sheetValue;

        } catch (IOException e) {
        	throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
	}

	public List readExcelFileRadio(String filename) throws InvalidFormatException{
		try {
//			FileInputStream excelFile = new FileInputStream(new File("/home/computer/Downloads/subnodes_data_model.xlsx"));
//  		Workbook workbook = new XSSFWorkbook(excelFile);
  		
		Workbook workbook = new XSSFWorkbook(getFileFromBucket(filename));
		DataFormatter objDefaultFormat = new DataFormatter();


  		Sheet sheet = workbook.getSheet("Radio");
  		Iterator rows = sheet.iterator();

  		List sheetValue = new ArrayList<RadioSheet>();

  		int rowNumber = 0;
  		while (rows.hasNext()) {
  			Row currentRow = (Row) rows.next();

  			if(rowNumber == 0) {
  				rowNumber++;
  				continue;
  			}

  			Iterator cellsInRow = currentRow.iterator();

  			RadioSheet radioSheet = new RadioSheet();

  			int cellIndex = 0;
  			while (cellsInRow.hasNext()) {
  				Cell currentCell = (Cell) cellsInRow.next();
  				
  				String cellValueStr;

				if( currentCell == null ) {
					cellValueStr = "";
				}else {
					cellValueStr = objDefaultFormat.formatCellValue(currentCell);
				}

  				if(cellIndex==0) { // node
  					radioSheet.setId( cellValueStr );
  				} else if(cellIndex==1) { // Name
  					radioSheet.setNext(cellValueStr);
  				} else if(cellIndex==2) { // Name
  					radioSheet.setText(cellValueStr);
  				}

  				cellIndex++;
  			}

  			sheetValue.add(radioSheet);
  		}

  		workbook.close();

  		return sheetValue;

      } catch (IOException e) {
      	throw new RuntimeException("FAIL! -> message = " + e.getMessage());
      }
	}
	
}



