package main.java.com.data;

import main.java.com.util.StringUtil;

public class HeaderItem {

	private String name;
	private int length;	
	private String type;
	private int location;
	
	public HeaderItem(String name, int location) {
		setType("VARCHAR");
		setLength(255);		
		setName(name);
		setLocation(location);
	}
	
	public String toString(String preface) {
		String lengthAddition = "";
		if(this.length != 0) {
			lengthAddition = "(" + this.length + ")";
		}		
		return preface+"_"+this.name + " "+ this.type + lengthAddition;
	}
	
	public void setLocation(int location) {
		this.location = location;
	}
	
	public int getLocation() {
		return this.location;
	}
	
	public void setName(String name) {
		this.name = StringUtil.extractAlphaNumeric(name.toUpperCase());;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
