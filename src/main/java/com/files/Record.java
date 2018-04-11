package main.java.com.files;

public class Record<T> {

	private T value = null;
	private boolean isValid = true;
	
	public Record(T value, boolean status){
		this.value = value;
		this.isValid = status;
	}

	public T getValue() {
		return value;
	}

	public boolean getStatus() {
		return isValid;
	}	
	
}
