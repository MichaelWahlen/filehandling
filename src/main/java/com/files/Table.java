package main.java.com.files;

import java.util.ArrayList;
import java.util.List;


public class Table {
	private List<String> header = new ArrayList<String>();
	private List<Row<String>> rows = new ArrayList<Row<String>>();
	
	public Table(List<String> tableContents, char delimiter) {
		for(String currentLine: tableContents) {
			char[] currentChars = currentLine.toCharArray();
			for(int count = 0; count<currentChars.length; count++) {
				if(currentChars[count] ==  delimiter) {
					header.add("Stuff");
					Row<String> row = new Row<String>(currentLine,delimiter);
					
				}
			}
		}	
		
	}
	
	
}
