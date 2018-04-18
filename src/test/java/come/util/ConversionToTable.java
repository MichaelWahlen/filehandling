package test.java.come.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.com.data.Table;
import main.java.com.file.LocalFolder;
import main.java.com.util.StringUtil;
import main.java.com.util.StringsToTables;

class ConversionToTable {
	String baseballFolderPath ="";	
	String tweetFolderPath = "";
	LocalFolder baseballFolder;
	LocalFolder tweetFolder;
	
	@BeforeEach
	public void setUp() throws Exception {
		baseballFolderPath = "src//test//resources//BaseBallTestData";	
		tweetFolderPath = "src//test//resources//Tweets";
		baseballFolder = new LocalFolder(baseballFolderPath);
		tweetFolder = new LocalFolder(tweetFolderPath);
		baseballFolder.loadContainedFiles("","csv");
	}
	@Test
	void convertFile() {
		
		Map<String, Table> tables = StringsToTables.convertFolderToTable(baseballFolder.getAllFiles());
		assertEquals("ansonca01",tables.get("Appearances.csv").getColumn(3).get(4));
	}
	@Test
	void convertToSQL() {
		
		Map<String, Table> tables = StringsToTables.convertFolderToTable(baseballFolder.getFile("Parks.csv"));
		assertEquals("CREATE TABLE PARKS(ID int NOT NULL AUTO_INCREMENT, PARKKEY VARCHAR(255),PARKNAME VARCHAR(255),PARKALIAS VARCHAR(255),CITY VARCHAR(255),STATE VARCHAR(255),COUNTRY VARCHAR(255),PRIMARY KEY (ID))",
				StringUtil.getCreateTableString("Parks", tables.get("Parks.csv").getHeaderAsString()));
	}
	
	@Test
	void convertToInsert() {	
		Map<String, Table> tables = StringsToTables.convertFolderToTable(baseballFolder.getFile("Parks.csv"));
		Table retrievedTable = tables.get("Parks.csv");
		List<List<String>> retrievedData = retrievedTable.getTable(false).subList(1, 3);		
		assertEquals("INSERT INTO PARKS VALUES(0,\"ALT01\",\"Columbia Park\",\"\",\"Altoona\",\"PA\",\"US\"),(0,\"ANA01\",\"Angel Stadium of Anaheim\",\"Edison Field; Anaheim Stadium\",\"Anaheim\",\"CA\",\"US\")",
				StringUtil.getInsertString("Parks", retrievedData, true));
	}
	
}
