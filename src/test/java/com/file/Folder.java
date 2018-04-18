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
		baseballFolder.loadContainedFiles("","csv");
		assertEquals(baseballFolder.getAllFiles().size(),27);
		tweetFolder.loadContainedFiles("","txt");
		assertEquals(tweetFolder.getAllFiles().size(),1);
	}
	
	@Test
	void loadAllContentCount() {
		baseballFolder.loadContainedFiles("","csv");
		List<String> returns = baseballFolder.getAllFiles().get("AwardsManagers");		
		assertEquals(180,returns.size());
		tweetFolder.loadContainedFiles("","txt");
		returns = tweetFolder.getAllFiles().get("bbchealth");
		assertEquals(3929,returns.size());
	}
	


}
