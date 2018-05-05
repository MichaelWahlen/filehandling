package test.java.com.data;



import java.io.File;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.com.SQL.SQLExecution;
import main.java.com.data.Database;
import main.java.com.data.Table;
import main.java.com.file.LocalFolder;


class ConversionToTable2 {
		
	LocalFolder baseballFolder;
	Database database = new Database();
	Map<String, Table> tables;
	
	@BeforeEach
	public void setUp() throws Exception {
		String string = "src//test//resources//Mixed";
		baseballFolder = new LocalFolder(string);		
		List<File> csvFiles = new ArrayList<File>();
		csvFiles.add(new File(string,"ALLSTARFULL.csv"));
		csvFiles.add(new File(string,"PARKS.csv"));
		csvFiles.add(new File(string,"APPEARANCES.csv"));
		csvFiles.add(new File(string,"PEOPLE.csv"));
		List<File> xlxFiles = new ArrayList<File>();
		xlxFiles.add(new File(string,"VALUTA.xlsx"));
		List<File> xmlFiles = new ArrayList<File>();
		xmlFiles.add(new File(string,"20180329.COVISN.xml"));
		xmlFiles.add(new File(string,"20180329.CUSISN.xml"));
		xmlFiles.add(new File(string,"20180329.REPISN.xml"));
		List<File> txtFiles = new ArrayList<File>();
		txtFiles.add(new File(string,"bbchealth.txt"));		
		baseballFolder.parseFilesToMemory(csvFiles, 0);
		baseballFolder.parseFilesToMemory(xmlFiles, 7);
		baseballFolder.parseFilesToMemory(xlxFiles, 1);
		baseballFolder.parseFilesToMemory(txtFiles, 0);		
		List<String> commaDelisted = new ArrayList<String>();
		commaDelisted.add("ALLSTARFULL.CSV");
		commaDelisted.add("PARKS.CSV");
		commaDelisted.add("APPEARANCES.CSV");
		commaDelisted.add("PEOPLE.CSV");
		commaDelisted.add("VALUTA.TXT");
		commaDelisted.add("20180329.COVISN.XML");
		commaDelisted.add("20180329.REPISN.xml");
		List<String> otherDelimited = new ArrayList<String>();		
		otherDelimited.add("BBCHEALTH");		
		database.addCSVData(baseballFolder.getInMemoryFiles(otherDelimited), '|');	
		database.addCSVData(baseballFolder.getInMemoryFiles(commaDelisted), ',');		
		System.out.println("lawl");
		SQLExecution.dropTables(commaDelisted);
		SQLExecution.dropTables(otherDelimited);
		database.commitAll();
	}
	
	@Test
	void commit() {		
		
	}


}
