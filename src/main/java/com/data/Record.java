package main.java.com.data;

import java.util.ArrayList;
import java.util.List;

public class Record<T> {
	private List<Cell<T>> cells = new ArrayList<Cell<T>>(); 
	private int prescribedSize = 0;
	private boolean isIncomplete = false;
	private boolean isTooLarge = false;
	
	public Record (){		
	}
	
	public void changeValueAt(int location, T newValue, boolean isValid) {
		if(cells.size() > location && location >= 0) {
			cells.get(location).changeToValue(newValue, isValid);
		} 
	}
	
	public void insertValueAt(int location, T newValue) {
		if (location >= 0) {
			if(cells.size() < location) {
				int difference = location - cells.size();
				for(int i = 0; i < difference; i++) {
					cells.add(new Cell<T>(null));
				}
				cells.add(new Cell<T>(newValue));
			} else {
				cells.add(location, new Cell<T>(newValue));
			}
		}
	}
	
	public void setPrescribedSize(int size) {
		this.prescribedSize = size;
		if(prescribedSize > 0) {
			if(cells.size() > prescribedSize) {
				cells = cells.subList(0, prescribedSize);
				isTooLarge = true;
			} else if(cells.size() < prescribedSize){
				for (int i = 0; i < (prescribedSize-cells.size());i++) {
					cells.add(null);
				}
				isIncomplete = true;
			}
		}		
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
		if (!(isIncomplete || isTooLarge)) {
			if (cells.size() > 1) {
				for(Cell<T> cell:cells) {
					if (cell.getCurrentValue() != null) {
						DataStatus cellStatus = cell.getStatus();
						if (cellStatus.getPriority() > rowStatus.getPriority()) {
							rowStatus = cellStatus;
						}
					}
				}
			}
		} else {
			rowStatus = DataStatus.FAULTY;
		}		
		return rowStatus;
	}
	
	public List<T> getAll(){
		List<T> returnList = new ArrayList<T>();
		if (cells.size() > 0) {
			for(Cell<T> cell:cells) {
				if (cell != null) {
					returnList.add(cell.getCurrentValue());
				} else {
					returnList.add(null);
				}
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
