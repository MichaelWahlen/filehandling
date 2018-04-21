package main.java.com.data;

import main.java.com.util.StringUtil;

public class HeaderItem {

	private String name;
	private int length;	
	private String type;
	
	public HeaderItem(String name) {
		setType("VARCHAR");
		setLength(255);		
		setName(name);
	}
	
	public String toString(String preface) {
		return preface+"_"+this.name + " "+ this.type + "(" + this.length + ")";
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
