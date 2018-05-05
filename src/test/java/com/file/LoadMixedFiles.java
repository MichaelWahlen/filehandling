package test.java.com.file;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.java.com.file.LocalFolder;

class LoadMixedFiles {
	
	static String mixedPath = "src//test//resources//Mixed";	
	static LocalFolder baseballFolder = null;
	
	
	@BeforeAll
	static void  setup() {
		baseballFolder = new LocalFolder(mixedPath);		
	}	
	
	@Test
	void loadedFileSize() {
		baseballFolder.parseAllToMemory(0);
		Map<String, List<String>> returns = baseballFolder.getAllInMemoryFiles();	
		assertAll("Should return accurate row nrs for different file types",				
				() -> assertEquals(104325, returns.get("BATTING").size()),
				() -> assertEquals(180, returns.get("AWARDSMANAGERS").size()),
				() -> assertEquals(165222, returns.get("20180329.COVISN").size()),
				() -> assertEquals(131713, returns.get("20180329.CUSISN").size()),
				() -> assertEquals(3929, returns.get("BBCHEALTH").size()),
				() -> assertEquals(6, returns.get("VALUTA").size())
				);	
	}
	
	@Test
	void numberOfAllLoadedFiles() {
		baseballFolder.parseAllToMemory(0);
		Map<String, List<String>> returns = baseballFolder.getAllInMemoryFiles();	
		assertAll("Should return accurate row nrs for different file types",				
				() -> assertEquals(17, returns.size())
				);
	}
	
	@Test
	void numberOfLoadedFiles() {
		List<File> files = new ArrayList<File>();
		baseballFolder.clearInMemoryFiles();
		files.add(new File(mixedPath,"AwardsManagers.csv"));
		files.add(new File(mixedPath,"20180329.COVISN.xml"));
		baseballFolder.parseFilesToMemory(files, 100);		
		Map<String, List<String>> returns = baseballFolder.getAllInMemoryFiles();	
		assertAll("Should return accurate row nrs for different file types",				
				() -> assertEquals(2, returns.size())
				);
	}
	
	@Test
	void loadFileSizeAfterAllSubstract() {
		baseballFolder.parseAllToMemory(7);
		Map<String, List<String>> returns = baseballFolder.getAllInMemoryFiles();	
		assertAll("Should return accurate row nrs for different file types",				
				() -> assertEquals(104318, returns.get("BATTING").size()),
				() -> assertEquals(165219, returns.get("20180329.COVISN").size()),
				() -> assertEquals(3922, returns.get("BBCHEALTH").size())
				);	
	}
	
	@Test
	void loadedFileSizeDifferent() {
		baseballFolder.parseAllToMemory(0);
		List<File> files = new ArrayList<File>();
		files.add(new File(mixedPath,"AwardsManagers.csv"));
		files.add(new File(mixedPath,"20180329.COVISN.xml"));
		baseballFolder.parseFilesToMemory(files, 100);
		Map<String, List<String>> returns = baseballFolder.getAllInMemoryFiles();	
		assertAll("Should return accurate row nrs for different file types",				
				() -> assertEquals(104325, returns.get("BATTING").size()),
				() -> assertEquals(80, returns.get("AWARDSMANAGERS").size()),
				() -> assertEquals(165188, returns.get("20180329.COVISN").size()),
				() -> assertEquals(131713, returns.get("20180329.CUSISN").size()),
				() -> assertEquals(3929, returns.get("BBCHEALTH").size())
				);	
	}
	
	@Test
	void parseToFolder() {		
		baseballFolder.parseAllToMemory(0);
		baseballFolder.allInMemoryFilesToCSV(0);
	}
	
	@Test
	void unzip() {
		baseballFolder.unzipContainedGZ();
	}
}
