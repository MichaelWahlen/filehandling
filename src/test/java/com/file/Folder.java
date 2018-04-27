package test.java.com.file;

import static org.junit.jupiter.api.Assertions.*;


import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import main.java.com.file.LocalFolder;


class Folder {
	
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
	}
	
	@Test
	void loadAllCount() {
		baseballFolder.loadFilesIntoMemory("","csv");
		assertEquals(baseballFolder.getAllFiles().size(),27);
		tweetFolder.loadFilesIntoMemory("","txt");
		assertEquals(tweetFolder.getAllFiles().size(),1);
	}
	
	@Test
	void loadAllContentCount() {
		baseballFolder.loadFilesIntoMemory("","csv");
		List<String> returns = baseballFolder.getAllFiles().get("PITCHING");		
		assertEquals(45807,returns.size());
		tweetFolder.loadFilesIntoMemory("","txt");
		returns = tweetFolder.getAllFiles().get("BBCHEALTH");
		assertEquals(3929,returns.size());
	}
	


}
