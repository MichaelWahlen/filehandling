package main.java.com.parser;

import java.io.File;
import java.util.List;

public interface Parser {
	
	public List<String> parseToStrings(File sourceFile); 
	public void parseToDB(File sourceFile, String tableName);
	public void parseToCSV(File sourceFile, File targetFile);
	public void setStartRow(int startingElement);	

}
