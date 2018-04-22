package test.java.com.file;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.com.file.CharSeperatedFile;

class TestFile {
	
	String baseballFolderPath = "src//test//resources//BaseBallTestData//";	
	String tweetFolderPath = "src//test//resources//Tweets//";
	CharSeperatedFile file = new CharSeperatedFile(new File(baseballFolderPath +"AwardsManagers.csv"));
	CharSeperatedFile file2 = new CharSeperatedFile(new File(baseballFolderPath +"Appearances.csv"));
	CharSeperatedFile file3 = new CharSeperatedFile(new File(baseballFolderPath +"Fielding.csv"));
	CharSeperatedFile file4 = new CharSeperatedFile(new File(baseballFolderPath +"Batting.csv"));
	CharSeperatedFile file5 = new CharSeperatedFile(new File(tweetFolderPath +"bbchealth.txt"));	
	
	@BeforeEach
	public void setUp() throws Exception {
	   
	}
	
	@Test
	void fileSize() {
		assertAll("Should return accurate row nrs for CSV files",
				() -> assertEquals(180, file.getSize()),
				() -> assertEquals(104257, file2.getSize()),
				() -> assertEquals(138839, file3.getSize()),
				() -> assertEquals(104325, file4.getSize()),
				() -> assertEquals(3929, file5.getSize())
				);	
	}
	
	@Test
	void getRowWithinBounds() {
		assertEquals("1874,BL1,NA,smithjo01,6,5,6,6,0,0,0,0,0,6,0,0,0,0,0,0,0",file2.getRow(500));
	}
	
	@Test
	void getRowOutsideOfBounds() {
		assertEquals("",file2.getRow(5000000));
	}
	
	@Test
	void deleteFile() {
		file2.storeFileAt("src//test//resources//BaseBallTestData//Storage","COPIED_OFAppearances.csv");
		File temp = new File("src//test//resources//BaseBallTestData//Storage//COPIED_OFAppearances.csv");		
		assertTrue(temp.delete());
	}
	
	@Test
	void getAllRows() {
		assertEquals("coxbo01,BBWAA Manager of the Year,1985,AL,,",file.getParsedRows().get(5));		
	}
	
	@Test
	void addRow() {
		String add = "this is a line";
		file4.addLine(add);		
		assertAll("Should return accurate row nrs for CSV files",				
				() -> assertEquals(104325+1, file4.getSize()),
				() -> assertEquals(add, file4.getRow(104325))
				);	
	}
	
}
