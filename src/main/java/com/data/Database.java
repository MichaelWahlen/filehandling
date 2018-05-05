package main.java.com.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.java.com.SQL.SQLExecution;
import main.java.com.SQL.BuildSQLStrings;
import main.java.com.util.StringUtil;


public class Database {
	
	Map<String, Table> tables = new HashMap<String, Table>();
	
	public Database() {		
	}
	
	public void addCSVData(Map<String, List<String>> tableContents, char delimiter) {		
		for(Map.Entry<String, List<String>> entry : tableContents.entrySet()) {
			List<String> strings = entry.getValue();
			List<List<String>> stringLists = new ArrayList<List<String>>();			
			for(String string: strings) {				
				stringLists.add(StringUtil.decomposeValueSeperatedString(string, delimiter));
			}			
			Table newTable = new Table();
			newTable.setTable(stringLists);
			newTable.setName(entry.getKey());
			tables.put(entry.getKey(), newTable);			
		}			
	}
	
	public void setHeaderType(String tableName, String columnName, String type, int length) {
		tables.get(tableName).setColumnType(columnName,type,length);
	}
	
	
	// refactor to list of lists
	public Map<String, Table> getTables(){
		return tables;
	}
	
	public void commitAll() {
		for(Map.Entry<String, Table> entry : tables.entrySet()) {
			commitTable(entry.getValue().getName());
		}
	}
	
	public void commitTable(String tableName) {
		Table retrievedTable = tables.get(tableName.toUpperCase());
		String createSQL = BuildSQLStrings.getCreateTableString(tableName, retrievedTable.getHeader(), true);
		String insertSQL = BuildSQLStrings.getInsertString(tableName, retrievedTable.getTableContents(), true);		
		SQLExecution.performDDL(createSQL);		
		SQLExecution.performDDL(insertSQL);	
	}
	


	

}
