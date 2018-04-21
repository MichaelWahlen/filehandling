package main.java.com.data;

import java.util.ArrayList;
import java.util.List;

import main.java.com.util.StringUtil;


public class Table {	
	
	private List<Record<String>> rows = new ArrayList<Record<String>>();	
	private List<HeaderItem> headerItems = new ArrayList<HeaderItem>();
	private int maxRowSize = 0;	
	private String name = "";
	
	public Table() {		
	}
	
	public String getName() {
		return name;
	}
	
	public void setTable(List<List<String>> table, boolean hasHeader) {
		int currentRowSize = 0;
		if (table.size() > 0) {
			if (hasHeader) {
				List<String> strings = table.get(0);
				for (String string:strings) {
					HeaderItem header = new HeaderItem(string);
					headerItems.add(header);
				}				
				table.remove(0);			
			}
			if(table.size() > 0) {
				for(List<String> list:table) {
					Record<String> record = new Record<String>();
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
		}
	}
	
	public void setName(String name) {
		this.name = StringUtil.extractAlphaNumeric(name).toUpperCase();
	}
	
	public void applyHeaderSize() {		
		for(Record<String> row:rows) {
			row.setPrescribedSize(headerItems.size());
		}		
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
		for(HeaderItem headerItem:headerItems) {
			returnValue.add(headerItem.toString(name));
		}		
		return returnValue;
	}

}
