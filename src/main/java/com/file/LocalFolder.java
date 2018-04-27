package main.java.com.file;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.com.util.StringUtil;

public class LocalFolder {

	private HashMap<String,CharSeperatedFile> loadedFiles = null;
	private File sourceFolder = null;
	private File extractedFolder = null;
	private File parsedFolder = null;
	private Parser parser = new ParserXML();
	
	public LocalFolder(String contentDirectoryPath) {
		setContentDirectory(contentDirectoryPath);		
	} 
	
	public void setContentDirectory(String path) {
		File tempFile = new File(path);
		if (tempFile.exists() && tempFile.isDirectory()) {
			sourceFolder = new File(path);
			extractedFolder = new File(path+"\\UNZIPPED");
			parsedFolder = new File(path+"\\PARSED");			
			loadedFiles = new HashMap<String, CharSeperatedFile>();
		}		
	}
	
	public HashMap<String, List<String>> getAllFiles() {			
		List<String> fileNames =  new ArrayList<String>();
		for(Map.Entry<String, CharSeperatedFile> entry : loadedFiles.entrySet()) {
			fileNames.add(entry.getKey());			
		}			
		return getFiles(fileNames);
	}
	
	public HashMap<String, List<String>> getFiles(List<String> fileNames) {		
		HashMap<String, List<String>> filesContents = new HashMap<String, List<String>>();		
		for(String fileName:fileNames) {			
			CharSeperatedFile file = loadedFiles.get(fileName);
			if(file != null) {								
				filesContents.put(file.getFileName(),file.getParsedRows());
			}	
		}			
		return filesContents;
	}   

	public void loadFilesIntoMemory(String fileIdentifier, String fileExtension) {					
		File[] directoryListing = sourceFolder.listFiles();
		if (directoryListing != null) {				
			int lastIndex;				
			for (File file : directoryListing) {
				String name = file.getName();
				lastIndex = name.lastIndexOf('.');
				if (file.isFile() && lastIndex != -1) {																		
						if(name.substring(lastIndex+1).equals(fileExtension) && (name.contains(fileIdentifier))) {								
							loadedFiles.put(StringUtil.getUpperCaseNameWithoutExtension(name),new CharSeperatedFile(file));
						} 
					
				}
			} 
		}		 
	}
	
	public void unzipContainedGZ() {
		File[] directoryListing = sourceFolder.listFiles();
		extractedFolder.mkdir();
		for(File file: directoryListing) {
			if (file.isFile()&&StringUtil.getExtension(file.getName()).equals("GZ")) {
				Unzip.unzipGZ(file, extractedFolder);
			}
		}		
	}
	
	public void parseXMLToCSV(int startAtRow) {
		parsedFolder.mkdir();
		File[] directoryListing = extractedFolder.listFiles();
		for(File file: directoryListing) {
			String fileName = file.getName();
			if (file.isFile()&&StringUtil.getExtension(fileName).equals("XML")) {				
				String newFileName = StringUtil.getUpperCaseNameWithoutExtension(fileName) + ".txt";				
				parser.setStartRow(startAtRow);
				parser.parseToCSV(file, new File(parsedFolder,newFileName));				
			}
		}	
	}
	
	public static boolean deleteDirectory(File directory) {
	    if(directory.exists()){
	        File[] files = directory.listFiles();
	        if(null!=files){
	            for(int i=0; i<files.length; i++) {
	                if(files[i].isDirectory()) {
	                    deleteDirectory(files[i]);
	                }
	                else {
	                    files[i].delete();
	                }
	            }
	        }
	    }
	    return(directory.delete());
	}

	
}
