package main.java.com.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ParserExcel implements Parser{
	
	private int startRow =0;
	
	@Override
	public List<String> parseToStrings(File sourceFile) {
        List<String> listOfStrings = new ArrayList<String>();
        Workbook workbook;
		try {
			workbook = WorkbookFactory.create(sourceFile);	
			Iterator<Sheet> sheetIterator = workbook.sheetIterator();			
	        while (sheetIterator.hasNext()) {
	            Sheet sheet = sheetIterator.next();	            
		        DataFormatter dataFormatter = new DataFormatter();
		        Iterator<Row> rowIterator = sheet.rowIterator();
		       for(int i = 0; i<startRow;i++) {
		    	   if(rowIterator.hasNext()) {
		    		   rowIterator.next();
		    	   }
		       }		        
		        while (rowIterator.hasNext()) {
		            Row row = rowIterator.next();		   
		            Iterator<Cell> cellIterator = row.cellIterator();
		            String rowString = "";
		            while (cellIterator.hasNext()) {
		                Cell cell = cellIterator.next();
		                String cellValue = dataFormatter.formatCellValue(cell);
		                rowString = rowString + "," + cellValue;
		            }
		            listOfStrings.add(rowString.substring(1));
		        }
	        }	        
		} catch (Exception e) {			
			e.printStackTrace();
		}        
		return listOfStrings;
	}

	@Override
	public void parseToDB(File sourceFile, String tableName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void parseToCSV(File sourceFile, File targetFile) {       
		if(targetFile.exists()) {
			targetFile.delete();
		}
		try {
			 Workbook workbook;
			PrintWriter out = null;    
			out = new PrintWriter(new BufferedWriter(new FileWriter(targetFile, true)));			
			workbook = WorkbookFactory.create(sourceFile);	
			Iterator<Sheet> sheetIterator = workbook.sheetIterator();			
	        while (sheetIterator.hasNext()) {
	            Sheet sheet = sheetIterator.next();	            
		        DataFormatter dataFormatter = new DataFormatter();
		        Iterator<Row> rowIterator = sheet.rowIterator();
		       for(int i = 0; i<startRow;i++) {
		    	   rowIterator.next();
		       }		        
		        while (rowIterator.hasNext()) {
		            Row row = rowIterator.next();		   
		            Iterator<Cell> cellIterator = row.cellIterator();
		            String rowString = "";
		            while (cellIterator.hasNext()) {
		                Cell cell = cellIterator.next();
		                String cellValue = dataFormatter.formatCellValue(cell);
		                rowString = rowString + "," + cellValue;
		            }
		            out.write(rowString.substring(1));
					out.write("\n");
		        }
	        }
	        out.close();	
		} catch (Exception e) {			
			e.printStackTrace();
		}        
		
	}

	@Override
	public void setStartRow(int startingElement) {
		startRow =  startingElement;
		
	}

}
