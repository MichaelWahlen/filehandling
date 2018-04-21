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
			Table newTable = new Table();
			String key = entry.getKey().toUpperCase();
			newTable.setTable(stringLists, true);
			newTable.setName(key);
			tables.put(key, newTable);			
		}			
	}
	
	public Map<String, Table> getTables(){
		return tables;
	}
	
	public void commitAll() {
		for(Map.Entry<String, Table> entry : tables.entrySet()) {
			commitTable(entry.getValue().getName());
		}
	}
	
	public void commitTable(String name) {
		Table retrievedTable = tables.get(name.toUpperCase());
		String createSQL = BuildSQL.getCreateTableString(name, retrievedTable.getHeader(), true);
		String insertSQL = BuildSQL.getInsertString(name, retrievedTable.getTableContents(), true);		
		PerformSQL.performDDL(createSQL);		
		PerformSQL.performDDL(insertSQL);	
	}
	
	public void commitInnerJoin(List<String> key, List<String> tableNames){		
		// not yet properly refactored but works. Steps are, create indexes for all the columns in the required tables
		// next step: Commit those ADD INDEX, one by one as hiber doesnt do multiple commits
		// next step create the string for the creation of the joined table, giving it name TESTING. Syntax is CREATE TABLE X AS SELECT * FROM X JOIN Y ON X.A=Y.A
		// next step commit the creation string
		// next step: retrieve all via Select * from TESTING
		// next step: add the column names via SELECT FROM SCHEMA.COLUMNS
		// next step: print out some lines
		// requires refactor + error handling for all the problems with running craete when there is already a table
		
		
		
		List<String> addIndex = BuildSQL.addIndexOnTable(key, tableNames);
		for(String string: addIndex) {
			PerformSQL.performDDL(string);
		}
		String test = "CREATE TABLE testing AS (" + BuildSQL.getInnerJoinString(key, tableNames) +")";
		PerformSQL.performDDL(test);
		List<String> retrieve  = PerformSQL.performSelect("SELECT * FROM TESTING");	
		retrieve.add(0,PerformSQL.getColumnNames("SELECT column_name FROM information_schema.columns WHERE table_name in ('TESTING')"));
		System.out.println(retrieve.get(0));
		System.out.println(retrieve.get(1));
		System.out.println(retrieve.get(3));
	}
	
}
