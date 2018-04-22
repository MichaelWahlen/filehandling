package main.java.com.SQL;


import java.util.List;

import main.java.com.util.StringUtil;

public class BuildSQLStrings {
	
	public static String getCreateTableString(String tableName, List<String> columnNames, boolean hasAutoIncrement) {
		String returnValue = "";
		String columns = StringUtil.toCommaSeperatedList(columnNames);
		if (hasAutoIncrement) {
			returnValue = "CREATE TABLE " + tableName.toUpperCase() + " (" + tableName.toUpperCase()+"_ID int NOT NULL AUTO_INCREMENT, " + columns + ",PRIMARY KEY ("+ tableName.toUpperCase()+"_ID)) ENGINE=InnoDB;"; //	
		} else {
			returnValue = "CREATE TABLE " + tableName.toUpperCase() + " (" + columns + ",PRIMARY KEY ("+ tableName.toUpperCase()+"_ID)) ENGINE=InnoDB;";	//
		}
		return returnValue;
	}
	
    public static String getInsertString(String tableName, List<List<String>> data, boolean hasAutoIncrement) {    	
    	StringBuilder stringBuilder = new StringBuilder("INSERT INTO " + tableName.toUpperCase() + " VALUES ");    	 		    		
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
		stringBuilder.append("\""+list.get(list.size()-1)+"\""+");");
    	return stringBuilder.toString();
    }
    
    public static String getInnerJoinSelect(List<String> joinColumn, List<String> tableNames) {
    	String returnValue = "SELECT * FROM " +tableNames.get(0);
    	for(int i = 1; i <tableNames.size();i++) {
    		returnValue = returnValue + " JOIN " + tableNames.get(i) + " ON " + tableNames.get(i -1) + "."+ joinColumn.get(i-1)+" = " + tableNames.get(i)+ "."+ joinColumn.get(i);
    	}    	
    	return returnValue;   	
    }
    
    public static String getCreateAsSQL(String tableName,String nativeSQL) {    	
    	return "CREATE TABLE " + tableName +" AS (" + nativeSQL +")";
    }
    
    public static String addIndexOnTable(String joinColumn, String tableName) {   	
    	return "ALTER TABLE "+ tableName+ " ADD INDEX (" + joinColumn+"); ";
    }
    
    public static String getDrop(String tableName) {    	
    	return "DROP TABLE "+tableName;    			
    }
    
    public static String getSelect(String tableName) {
    	return "SELECT * FROM " + tableName +";";
    }
    
    public static String getColumnNames(String tableName) {
    	return "SELECT column_name FROM information_schema.columns WHERE table_name in ('" + tableName+"');";
    }
    
    public static String getTableNames() {
    	return "SELECT table_name FROM information_schema.tables where table_schema='localetl';";
    }
}
