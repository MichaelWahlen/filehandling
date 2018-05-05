package main.java.com.parser;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.IOException;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ParserCSV implements Parser{
	
	private int startRow = 0;

	@Override
	public List<String> parseToStrings(File sourceFile) {
		BufferedReader reader = null;
	    String line = null;
	    List<String> parsedRows = new ArrayList<String>();
		try {
			reader = new BufferedReader(new FileReader (sourceFile.getPath()));
			for(int i = 0 ; i<startRow;i++) {
				reader.readLine();
			}				
			while((line = reader.readLine()) != null) {
                parsedRows.add(line);
            }
			reader.close();
		} catch (Exception e) {			
			e.printStackTrace();
		}       
		return parsedRows;		
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
			Files.copy(sourceFile.toPath(), targetFile.toPath());
		} catch (IOException e) {		
			e.printStackTrace();
		}	    
	}

	@Override
	public void setStartRow(int row) {
		this.startRow = row;		
	}



}
