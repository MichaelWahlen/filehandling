package test.java.com.file;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.com.file.LocalFile;

class MyFile {
	
	String baseballFolderPath = "src//test//resources//BaseBallTestData//";	
	String tweetFolderPath = "src//test//resources//Tweets//";
	List<LocalFile> localFiles = new ArrayList<LocalFile>();
	
	

	
	@BeforeEach
	public void setUp() throws Exception {
		localFiles.add(new LocalFile(new File(baseballFolderPath +"AwardsManagers.csv")));	
		localFiles.add(new LocalFile(new File(baseballFolderPath +"Appearances.csv")));
		localFiles.add(new LocalFile(new File(baseballFolderPath +"Fielding.csv")));
		localFiles.add(new LocalFile(new File(baseballFolderPath +"Batting.csv")));
		localFiles.add(new LocalFile(new File(tweetFolderPath +"bbchealth.txt")));
		for(LocalFile localFile:localFiles) {
			localFile.loadToMemory(0);
		}
	}
	
	@Test
	void fileSize() {
		assertAll("Should return accurate row nrs for CSV files",
				() -> assertEquals(180, localFiles.get(0).getSize()),
				() -> assertEquals(104257, localFiles.get(1).getSize()),
				() -> assertEquals(138839, localFiles.get(2).getSize()),
				() -> assertEquals(104325, localFiles.get(3).getSize()),
				() -> assertEquals(3929, localFiles.get(4).getSize())
				);	
	}
	
	@Test
	void getRowWithinBounds() {
		assertEquals("1874,BL1,NA,smithjo01,6,5,6,6,0,0,0,0,0,6,0,0,0,0,0,0,0",localFiles.get(1).getRow(500));
	}
	
	@Test
	void getRowOutsideOfBounds() {
		assertEquals("",localFiles.get(1).getRow(5000000));
	}
	
	@Test
	void deleteFile() {
		localFiles.get(1).storeAsCSV(new File("src//test//resources//BaseBallTestData//Storage","COPIED_OFAppearances.csv"));
		File temp = new File("src//test//resources//BaseBallTestData//Storage//COPIED_OFAppearances.csv");		
		assertTrue(temp.delete());
	}
	
	@Test
	void getAllRows() {
		assertEquals("coxbo01,BBWAA Manager of the Year,1985,AL,,",localFiles.get(0).getParsedRows().get(5));		
	}
	
	@Test
	void addRow() {
		String add = "this is a line";
		localFiles.get(4).addLineToMemory(add);		
		assertAll("Should return accurate row nrs for CSV files",				
				() -> assertEquals(3929+1, localFiles.get(4).getSize()),
				() -> assertEquals(add, localFiles.get(4).getRow(3929))
				);	
	}
	
}
