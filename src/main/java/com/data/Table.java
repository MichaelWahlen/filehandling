package main.java.com.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import main.java.com.util.StringUtil;


public class Table {	
	
	private List<Row<String>> rows;	
	private Map<String, Column> columns;
	private int maxRowSize = 0;	
	private String name = "";
	private int maxRowsInMemory = 500000;
	
	public Table() {		
	}
	
	public String getName() {
		return name;
	}
	
	public void setTable(List<List<String>> table) {
			setHeader(table.get(0));							
			table.remove(0);		
			setRows(table);		
	}
	
	public void setColumnType(String columnName, String type, int length) {
		columns.get(columnName).setType(type);
		columns.get(columnName).setLength(length);
	}
	
	public void setRows(List<List<String>> contents) {
		rows = new ArrayList<Row<String>>();
		int currentRowSize = 0;
		Row<String> record;
		int max = Math.min(contents.size(), maxRowsInMemory);
		contents = contents.subList(0, max-1);		
		for(List<String> list:contents) {
			record = new Row<String>();
			currentRowSize = 0;
			for(String string:list) {
				record.add(string);
				currentRowSize++;
			}
			rows.add(record);
			if (currentRowSize > maxRowSize) {
				maxRowSize = currentRowSize;
			}
		}
	}
	
	public void setHeader(List<String> headerNames) {
		columns = new HashMap<String, Column>();
		int location = 0;
		for (String string:headerNames) {
			Column header = new Column(string.toUpperCase(), location);
			columns.put(string.toUpperCase(), header);
			location++;
		}	
	}
	
	public void setName(String name) {
		this.name = StringUtil.extractAlphaNumeric(name).toUpperCase();
	}
	
	
	public List<List<String>> getTableContents(){
		List<List<String>> returnList = new ArrayList<List<String>>();				
		for(Row<String> record:rows) {
			returnList.add(record.getAll());
		}		
		return returnList;
	}	
	
	public void updateValue(int row, int column, String newValue, boolean isValid) {
		if(row < rows.size() && column < rows.get(row).getSize()) {
			rows.get(row).changeValueAt(column, newValue, isValid);			
		}
	}
	
	public void insertValueAt(int row, int column, String newValue) {		
		if(row < rows.size()) {	
			rows.get(row).insertValueAt(column, newValue);
		}
	}
	
	public DataStatus getRowStatus(int row) {
		DataStatus returnStatus = DataStatus.UNPROCESSED;
		if(row < rows.size()) {			
			returnStatus = rows.get(row).getStatus();			
		}
		return returnStatus;
	}
	
	public List<String> getColumn(int column){
		List<String> returnList = new ArrayList<String>();
		if(maxRowSize>column) {
			for(Row<String> record:rows) {
				if(record.getSize() > column) {
					returnList.add(record.getAt(column));
				} else {
					returnList.add(null);
				}
			}			
		}
		return returnList;
	}
	
	public List<String> getRow(int row){
		List<String> returnList = new ArrayList<String>();
		if(rows.size()>row) {			
				returnList= rows.get(row).getAll();			
		}
		return returnList;
	}
	
	public List<String> getHeader() {
		List<String> returnValue = new ArrayList<String>();
		String[] holder = new String[columns.size()];
		for(Entry<String, Column> entry: columns.entrySet()) {			
			holder[entry.getValue().getLocation()] = entry.getValue().toString(name);
		}
		for(String string: holder) {
			returnValue.add(string);
		}		
		return returnValue;
	}

}
