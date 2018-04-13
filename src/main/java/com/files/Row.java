package main.java.com.files;

import java.util.ArrayList;
import java.util.List;

public class Row<T> {
	private List<Cell<T>> cells = new ArrayList<Cell<T>>(); 
	
	public Row (){		
	}
	
	public void addValueAt(int location, T newValue, boolean isValid) {
		cells.get(location).changeToValue(newValue, isValid);
	}
	
	public void add(T newValue) {
		Cell<T> newCell = new Cell<T>(newValue);
		cells.add(newCell);		
	}
	
	public T getAt(int location) {
		T returnValue = null;
		if(cells.size() > location && location >= 0) {
			returnValue = cells.get(location).getCurrentValue();
		}			
		return returnValue;
	}
	
	public void remove(int location) {
		if(cells.size() > location && location >= 0) {
			cells.remove(location);
		}
	}
	
	public DataStatus getStatus() {
		DataStatus rowStatus = DataStatus.UNPROCESSED;
		if (cells.size() > 1) {
			for(Cell<T> cell:cells) {
				DataStatus cellStatus = cell.getStatus();
				if (cellStatus.getPriority() > rowStatus.getPriority()) {
					rowStatus = cellStatus;
				}	    
			}
		}
		return rowStatus;
	}
	
	public List<T> getAll(){
		List<T> returnList = new ArrayList<T>();
		if (cells.size() > 0) {
			for(Cell<T> cell:cells) {
				returnList.add(cell.getCurrentValue());
			}
		}
		return returnList;
	}
	
	public int getSize() {
		return cells.size();
	}
	
	public void previousValue() {
		if (cells.size() > 0) {
			for(Cell<T> cell:cells) {
				cell.changeToPreviousValue();		
			}
		}
	}
	
	public void nextValue() {
		if (cells.size() > 0) {
			for(Cell<T> cell:cells) {
				cell.changeToNextValue();
			}
		}
	}
	
}
