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
		baseballFolder.parseAllToMemory(0);
		assertEquals(baseballFolder.getAllInMemoryFiles().size(),27);
		tweetFolder.parseAllToMemory(0);
		assertEquals(tweetFolder.getAllInMemoryFiles().size(),1);
	}
	
	@Test
	void loadAllContentCount() {
		baseballFolder.parseAllToMemory(0);
		List<String> returns = baseballFolder.getAllInMemoryFiles().get("PITCHING");		
		assertEquals(45807,returns.size());
		tweetFolder.parseAllToMemory(0);
		returns = tweetFolder.getAllInMemoryFiles().get("BBCHEALTH");
		assertEquals(3929,returns.size());
	}
	


}
