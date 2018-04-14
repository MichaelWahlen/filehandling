package main.java.com.files;

import java.util.ArrayList;
import java.util.List;


public class Table {	
	
	private List<Record<String>> rows = new ArrayList<Record<String>>();
	private List<String> header = new ArrayList<String>();
	private int maxRowSize = 0;	
	
	public Table() {		
	}
	
	public void setTable(List<List<String>> table, boolean hasHeader) {
		int currentRowSize = 0;
		if (table.size() > 0) {
			if (hasHeader) {
				header = table.get(0);
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
	
	public void applyHeaderSize() {
		if (header.size()> 0) {
			for(Record<String> row:rows) {
				row.setPrescribedSize(header.size());
			}
		}
	}
	
	public List<List<String>> getTable(){
		List<List<String>> returnList = new ArrayList<List<String>>();
		if (header.size() > 0) {
			returnList.add(header);
		}
		if(rows.size()>0) {
			for(Record<String> record:rows) {
				returnList.add(record.getAll());
			}
		}
		return returnList;
	}	
	
	public void updateValue(int row, int column, String newValue, boolean isValid) {
		if(row < rows.size() && column < header.size()) {
			rows.get(row).changeValueAt(column, newValue, isValid);			
		}
	}
	
	public DataStatus getRowStatus(int row) {
		DataStatus returnStatus = DataStatus.UNPROCESSED;
		if(row < rows.size()) {
			Record<String> retrievedRow = rows.get(row);
			returnStatus = retrievedRow.getStatus();			
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
}
