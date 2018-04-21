package test.java.com.data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import main.java.com.data.Database;
import main.java.com.data.Table;
import main.java.com.file.LocalFolder;


class ConversionToTable {
		
	LocalFolder baseballFolder;
	Database database = new Database();
	Map<String, Table> tables;
	
	@BeforeEach
	public void setUp() throws Exception {		
		baseballFolder = new LocalFolder("src//test//resources//BaseBallTestData");
		baseballFolder.loadContainedFiles("","csv");		
		database.addCSVData(true, baseballFolder.getFile("ALLSTARFULL"), ',');
		database.addCSVData(true, baseballFolder.getFile("PARKS"), ',');
		database.addCSVData(true, baseballFolder.getFile("APPEARANCES"), ',');
		database.addCSVData(true, baseballFolder.getFile("PEOPLE"), ',');	
		tables = database.getTables();		
	}
	@Test
	void convertFile() {
		assertEquals("ansonca01",tables.get("APPEARANCES").getColumn(3).get(4));
	}
	
	@Test
	void commit() {		
		database.commitAll();
		database = new Database();
		List<String> tableNames = new ArrayList<String>();
		tableNames.add("APPEARANCES");
		tableNames.add("PEOPLE");
		List<String> keys = new ArrayList<String>();
		keys.add("APPEARANCES_PLAYERID");
		keys.add("PEOPLE_PLAYERID");		
		database.commitInnerJoin(keys, tableNames);		
	}


}
