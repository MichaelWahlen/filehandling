package main.java.com.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
	
	public static List<String> lineSplit(String sourceString, char delimiter){
		List<String> row = new ArrayList<String>();
		char[] searchArray = sourceString.toCharArray();
		int startPosition = 0;
		for (int i =0; i < searchArray.length;i++) {
			if(searchArray[i]==delimiter) {			
				row.add(sourceString.substring(startPosition,i));			
				startPosition = i + 1;			
			}
		}
		row.add(sourceString.substring(startPosition));	
		return row;
	}
	
	public static String getCreateTableString(String tableName, List<String> columnNames, boolean hasAutoIncrement) {
		String returnValue = "";
		String columns = toCommaSeperatedList(columnNames);
		if (hasAutoIncrement) {
			returnValue = "CREATE TABLE " + tableName.toUpperCase() + " (ID int NOT NULL AUTO_INCREMENT, " + columns + ",PRIMARY KEY (ID))";	
		} else {
			returnValue = "CREATE TABLE " + tableName.toUpperCase() + " (" + columns + ",PRIMARY KEY (ID))";	
		}
		return returnValue;
	}
	
    public static String extractAlphaNumeric(String input) {
        return input.replaceAll("[^A-Za-z0-9]", "");
    } 
    
    public static String toCommaSeperatedList(List<String> strings) {
    	String returnValue = "";
    	if (strings.size() > 0) {    		
    		for (String string: strings) {
    			returnValue = returnValue + "," + string;
    		}
    		returnValue = returnValue.substring(1);
    	}
    	return returnValue;
    }
    
    public static String getInsertString(String tableName, List<List<String>> data, boolean hasAutoIncrement) {
    	String returnValue = "INSERT INTO " + tableName.toUpperCase() + " VALUES";
    	if (data.size()>0) {
    		String inbetween = "";
    		for (List<String> list: data) {
    			String addString = "";
    			if (hasAutoIncrement) {
    				addString = "0";
    			} else {
    				addString = "";
    			}
    			for(String string: list) {
    				addString = addString + ","+ '"'+string +'"';
    			}    			
    			if(!hasAutoIncrement) {
    				addString = addString.substring(1);
    			}    			
    			inbetween = inbetween + "," + "("+ addString+")";
    		}
    		inbetween = inbetween.substring(1);
    		returnValue = returnValue + inbetween;
    	}    	
    	return returnValue;
    }
}
