package main.java.com.data;

public enum DataStatus {	
	UNPROCESSED(0),
	UNCHANGED_OK(1),
	CHANGED_OK(2),
	UNCHANGED_NOK(3),
	CHANGED_NOK(4),	
	FAULTY(5);
	
	private int priority;
	
	private DataStatus(int priority) {
		this.priority = priority;
	}
	
	public int getPriority() {
		return this.priority;
	}
	
}