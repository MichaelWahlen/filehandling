package test.java.com.file;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import main.java.com.file.LocalFolder;
import main.java.com.util.StringUtil;

class UnzipAndParser {
	
	static String xmlpathParsed = "src//test//resources//XMLExamples//PARSED//";
	static String xmlpathUnzipped = "src//test//resources//XMLExamples//UNZIPPED//";
	static String xmlpath = "src//test//resources//XMLExamples//";
	File[] files;	
	
	
	@BeforeAll
	static void  setup() {
		File file = new File(xmlpathParsed);
		LocalFolder.deleteDirectory(file);
		file = new File(xmlpathUnzipped);
		LocalFolder.deleteDirectory(file);
		LocalFolder folder = new LocalFolder(xmlpath);
		folder.unzipContainedGZ();
		folder.parseXMLToCSV(7);	
	}
	
	
	@AfterAll
	static void  tearDown() {

	}
	
	@Test
	void unzippedFilesExist() {		
		File file = new File(xmlpath);
		files = file.listFiles();		
		for(File file2:files) {
			if(file2.isFile()) {		
				File testFile = new File(xmlpathUnzipped,StringUtil.getUpperCaseNameWithoutExtension(file2.getName()));
				assertTrue(testFile.exists()&&testFile.isFile());			
			}
		}
					
	}
	
	@Test
	void parsedFilesExist() {	
		File file = new File(xmlpath);
		files = file.listFiles();		
		for(File file2:files) {
			if(file2.isFile()) {		
				File testFile = new File(xmlpathParsed,StringUtil.getUpperCaseNameWithoutExtension(StringUtil.getUpperCaseNameWithoutExtension(file2.getName()))+".txt");
				assertTrue(testFile.exists()&&testFile.isFile());			
			}
		}
					
	}

}
