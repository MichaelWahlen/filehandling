package main.java.com.file;

import java.io.File;
import java.util.List;

public interface Parser {
	
	public void parseToMemory(File sourceFile); 
	public void parseToDB(File sourceFile, String tableName);
	public void parseToCSV(File sourceFile, File targetFile);
	public void setStartRow(int row);
	public List<String> getInMemoryParse();

}
