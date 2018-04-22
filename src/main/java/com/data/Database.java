package main.java.com.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.com.SQL.PerformSQL;
import main.java.com.SQL.BuildSQL;
import main.java.com.util.StringUtil;


public class Database {
	
	Map<String, Table> tables = new HashMap<String, Table>();
	
	public Database() {		
	}
	
	public void addCSVData(boolean hasHeader, Map<String, List<String>> tableContents, char delimiter) {		
		for(Map.Entry<String, List<String>> entry : tableContents.entrySet()) {
			List<String> strings = entry.getValue();
			List<List<String>> stringLists = new ArrayList<List<String>>();
			for(String string: strings) {				
				stringLists.add(StringUtil.decomposeValueSeperatedString(string, delimiter));
			}			
			String key = entry.getKey().toUpperCase();
			Table newTable = new Table();
			newTable.setTable(stringLists, hasHeader);
			newTable.setName(key);
			tables.put(key, newTable);			
		}			
	}
	
	public void setHeaderType(String tableName, String columnName, String type, int length) {
		tables.get(tableName).setHeaderType(columnName,type,length);
	}
	
	
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
		String createSQL = BuildSQL.getCreateTableString(tableName, retrievedTable.getHeader(), true);
		String insertSQL = BuildSQL.getInsertString(tableName, retrievedTable.getTableContents(), true);		
		PerformSQL.performDDL(createSQL);		
		PerformSQL.performDDL(insertSQL);	
	}
	
	private void addIndexes(List<String> key, List<String> tableNames) {
		for(int i = 0 ; i<tableNames.size();i++) {
			PerformSQL.performDDL(BuildSQL.addIndexOnTable(key.get(i), tableNames.get(i)));			
		}		
	}
	
	public void commitInnerJoin(String tableName,List<String> key, List<String> tableNames){
		addIndexes(key,tableNames);		
		PerformSQL.performDDL(BuildSQL.getCreateAsSQL(tableName,BuildSQL.getInnerJoinSelect(key, tableNames)));
	}
	
	public List<String> retrieveTableContent(String tableName) {
		List<String> returnValue = PerformSQL.performSelect(BuildSQL.getSelect(tableName));	
		returnValue.add(0,getColumnNames(tableName));
		return returnValue;		
	}
	
	public void dropTables(List<String> tableNames) {
		for(String string: tableNames) {
			String SQL = BuildSQL.getDrop(string);
			PerformSQL.performDDL(SQL);
		}		
	}
	
	public String getTableNames(){
		return PerformSQL.getSingle(BuildSQL.getTableNames());		
	}
	
	public String getColumnNames(String tableName){
		return PerformSQL.getSingle(BuildSQL.getColumnNames(tableName));		
	}
}
