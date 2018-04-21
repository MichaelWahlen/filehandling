package main.java.com.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
	
	public static List<String> decomposeValueSeperatedString(String sourceString, char delimiter){
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
    

}
