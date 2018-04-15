package main.java.com.data;

public class Data<T> {

	private T value = null;
	private boolean isValid = true;
	
	public Data(T value, boolean status){
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
