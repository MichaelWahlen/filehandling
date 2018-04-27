package main.java.com.parser;

import main.java.com.util.StringUtil;

public class ParserFactory {
	
	private ParserFactory() {
		
	}
	
	public static Parser getParser(String fileName) {
		Parser parser = null;
		fileName  = StringUtil.getExtension(fileName);
		switch(fileName) {				
		case "XML":			
			parser = new ParserXML();
			break;			
		case "CSV":
		case "TXT":	
			parser = new ParserCSV();
			break;
		case "XLS":
		case "XLSX":
			parser = new ParserExcel();
			break;
		}		
		return parser;
	}
	
}
