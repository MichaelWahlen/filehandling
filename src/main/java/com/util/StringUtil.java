package main.java.com.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
	
	public static List<String> decomposeValueSeperatedString(String sourceString, char delimiter){
		List<String> row = new ArrayList<String>();
		char[] searchArray = sourceString.toCharArray();
		int startPosition = 0;
		String addString;
		for (int i =0; i < searchArray.length;i++) {
			if(searchArray[i]==delimiter) {			
				addString = sourceString.substring(startPosition,i).replace("'", "");
				addString = addString.replace("\"", "");
				row.add(addString);			
				startPosition = i + 1;			
			}
		}
		row.add(sourceString.substring(startPosition).replace("\"", ""));	
		return row;
	}
	
    public static String extractAlphaNumeric(String input) {
        return input.replaceAll("[^A-Za-z0-9]", "");
    } 
    
    public static String toCommaSeperatedList(List<String> strings) {
    	StringBuilder returnValue = new StringBuilder();    	
    	if (strings.size() > 0) {    		
    		for (String string: strings) {
    			returnValue.append("," + string);
    		}    		
    	}
    	return returnValue.toString().substring(1);
    }
    
    public static String getUpperCaseNameWithoutExtension(String source) {
    	String returnValue;
    	if(source.lastIndexOf(".") == -1) {
    		returnValue = source.toUpperCase();
    	} else {
    		returnValue = source.substring(0,source.lastIndexOf(".")).toUpperCase(); 
    	}
    	return returnValue;
    	
    }
    
    public static String getExtension(String source) {
    	return source.substring(source.lastIndexOf(".")+1).toUpperCase();    	
    }

}
