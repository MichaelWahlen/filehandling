package main.java.com.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import main.java.com.util.StringUtil;


public class Table {	
	
	private List<Record<String>> rows;	
	private Map<String, HeaderItem> headerItemsz;
	private int maxRowSize = 0;	
	private String name = "";
	
	public Table() {		
	}
	
	public String getName() {
		return name;
	}
	
	public void setTable(List<List<String>> table, boolean hasHeader) {		
		if (table.size() > 0) {
			if (hasHeader) {
				setHeader(table.get(0));							
				table.remove(0);			
			}
			setTableContents(table);
		}
	}
	
	public void setHeaderType(String columnName, String type, int length) {
		headerItemsz.get(columnName).setType(type);
		headerItemsz.get(columnName).setLength(length);
	}
	
	public void setTableContents(List<List<String>> contents) {
		rows = new ArrayList<Record<String>>();
		int currentRowSize = 0;
		Record<String> record;
		for(List<String> list:contents) {
			record = new Record<String>();
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
		headerItemsz = new HashMap<String, HeaderItem>();
		int location = 0;
		for (String string:headerNames) {
			HeaderItem header = new HeaderItem(string.toUpperCase(), location);
			headerItemsz.put(string.toUpperCase(), header);
			location++;
		}	
	}
	
	public void setName(String name) {
		this.name = StringUtil.extractAlphaNumeric(name).toUpperCase();
	}
	
	
	public List<List<String>> getTableContents(){
		List<List<String>> returnList = new ArrayList<List<String>>();				
		for(Record<String> record:rows) {
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
			for(Record<String> record:rows) {
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
		String[] holder = new String[headerItemsz.size()];
		for(Entry<String, HeaderItem> entry: headerItemsz.entrySet()) {			
			holder[entry.getValue().getLocation()] = entry.getValue().toString(name);
		}
		for(String string: holder) {
			returnValue.add(string);
		}		
		return returnValue;
	}

}
