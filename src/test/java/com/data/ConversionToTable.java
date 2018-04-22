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
		List<String> listOfTables = new ArrayList<String>();
		listOfTables.add("ALLSTARFULL");
		listOfTables.add("PARKS");
		listOfTables.add("APPEARANCES");
		listOfTables.add("PEOPLE");
		listOfTables.add("HELLO");
		database.dropTables(listOfTables);		
		database.commitAll();
		database = new Database();
		List<String> tableNames = new ArrayList<String>();
		tableNames.add("APPEARANCES");
		tableNames.add("PEOPLE");
		List<String> keys = new ArrayList<String>();
		keys.add("APPEARANCES_PLAYERID");
		keys.add("PEOPLE_PLAYERID");		
		database.commitInnerJoin("HELLO",keys, tableNames);	
		List<String> returnss =database.retrieveTableContent("HELLO");
		assertEquals("ID,APPEARANCES_ID,APPEARANCES_YEARID,APPEARANCES_TEAMID,APPEARANCES_LGID,APPEARANCES_PLAYERID,APPEARANCES_GALL,APPEARANCES_GS,APPEARANCES_GBATTING,APPEARANCES_GDEFENSE,APPEARANCES_GP,APPEARANCES_GC,APPEARANCES_G1B,APPEARANCES_G2B,APPEARANCES_G3B,APPEARANCES_GSS,APPEARANCES_GLF,APPEARANCES_GCF,APPEARANCES_GRF,APPEARANCES_GOF,APPEARANCES_GDH,APPEARANCES_GPH,APPEARANCES_GPR,PEOPLE_ID,PEOPLE_PLAYERID,PEOPLE_BIRTHYEAR,PEOPLE_BIRTHMONTH,PEOPLE_BIRTHDAY,PEOPLE_BIRTHCOUNTRY,PEOPLE_BIRTHSTATE,PEOPLE_BIRTHCITY,PEOPLE_DEATHYEAR,PEOPLE_DEATHMONTH,PEOPLE_DEATHDAY,PEOPLE_DEATHCOUNTRY,PEOPLE_DEATHSTATE,PEOPLE_DEATHCITY,PEOPLE_NAMEFIRST,PEOPLE_NAMELAST,PEOPLE_NAMEGIVEN,PEOPLE_WEIGHT,PEOPLE_HEIGHT,PEOPLE_BATS,PEOPLE_THROWS,PEOPLE_DEBUT,PEOPLE_FINALGAME,PEOPLE_RETROID,PEOPLE_BBREFID",returnss.get(0));
		assertEquals("0,84595,2004,SFN,NL,aardsda01,11,0,11,11,11,0,0,0,0,0,0,0,0,0,0,0,0,1,aardsda01,1981,12,27,USA,CO,Denver,,,,,,,David,Aardsma,David Allan,215,75,R,R,4/6/2004,8/23/2015,aardd001,aardsda01",returnss.get(1));
		assertEquals("0,87269,2006,CHN,NL,aardsda01,45,0,43,45,45,0,0,0,0,0,0,0,0,0,0,0,0,1,aardsda01,1981,12,27,USA,CO,Denver,,,,,,,David,Aardsma,David Allan,215,75,R,R,4/6/2004,8/23/2015,aardd001,aardsda01",returnss.get(2));
	}


}
