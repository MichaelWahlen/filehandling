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
			returnValue = "CREATE TABLE " + tableName.toUpperCase() + " (" + tableName.toUpperCase()+"_ID int NOT NULL AUTO_INCREMENT, " + columns + ",PRIMARY KEY ("+ tableName.toUpperCase()+"_ID)) ENGINE=InnoDB;";	
		} else {
			returnValue = "CREATE TABLE " + tableName.toUpperCase() + " (" + columns + ",PRIMARY KEY ("+ tableName.toUpperCase()+"_ID)) ENGINE=InnoDB;";	
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
    	StringBuilder stringBuilder = new StringBuilder("INSERT INTO " + tableName.toUpperCase() + " VALUES ");
    	if (data.size()>0) {    		    		
    		List<String> list;
    		for (int j = 0 ; j < data.size() - 1; j++) {    		
    			list = data.get(j);
    			stringBuilder.append('(');
    			if (hasAutoIncrement) {
    				stringBuilder.append("0,");    				
    			} 
    			for(int i=0; i < list.size() - 1;i++) {    				
    				stringBuilder.append("\""+list.get(i)+"\",");    				
    			}
    			stringBuilder.append("\""+list.get(list.size()-1)+"\""+"),");     			
    		}  
    		list = data.get(data.size() - 1);
			stringBuilder.append('(');
			if (hasAutoIncrement) {
				stringBuilder.append("0,");    				
			} 
			for(int i=0; i < list.size() - 1;i++) {    				
				stringBuilder.append("\""+list.get(i)+"\",");       				
			}
			stringBuilder.append("\""+list.get(list.size()-1)+"\""+')');					
    	}
    	return stringBuilder.toString();
    }
    
    public static String getInnerJoinString(List<String> joinColumn, List<String> tableNames) {
    	String returnValue = "SELECT * FROM " +tableNames.get(0);
    	for(int i = 1; i <tableNames.size();i++) {
    		returnValue = returnValue + " JOIN " + tableNames.get(i) + " ON " + tableNames.get(i -1) + "."+ joinColumn.get(i-1)+" = " + tableNames.get(i)+ "."+ joinColumn.get(i);
    	}    	
    	return returnValue + " WHERE " + tableNames.get(0)+"."+joinColumn.get(0) + " IN (\"aardsda01\",\"abercda01\")" ;    	
    }
}
