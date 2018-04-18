package main.java.com.file;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LocalFolder {

	private HashMap<String,CharSeperatedFile> loadedFiles = null;
	private File directory = null;
	
	public LocalFolder(String path) {
		setPath(path);		
	} 
	
	public void setPath(String path) {
		File tempFile = new File(path);
		if (tempFile.exists() && tempFile.isDirectory()) {
			this.directory = new File(path);
			loadedFiles = new HashMap<String, CharSeperatedFile>();
		}		
	}
	
	public HashMap<String, List<String>> getAllFiles() {		
		HashMap<String, List<String>> returnList = new HashMap<String, List<String>>();		
		for(Map.Entry<String, CharSeperatedFile> entry : loadedFiles.entrySet()) {
			CharSeperatedFile file = entry.getValue();
			String key = file.getFileName().substring(0,file.getFileName().lastIndexOf('.'));	
			returnList.put(key,file.getParsedRows());
		}			
		return returnList;
	}  
	
	public HashMap<String, List<String>> getFile(String fileName) {		
		HashMap<String, List<String>> returnList = new HashMap<String, List<String>>();		
		if (loadedFiles.size()>0) {
				CharSeperatedFile file = loadedFiles.get(fileName);
				if(file != null) {
					String key = file.getFileName().substring(0,file.getFileName().lastIndexOf('.'));					
					returnList.put(key,file.getParsedRows());
				}
			
		}		
		return returnList;
	}   

	public void loadContainedFiles(String fileIdentifier, String fileExtension) {					
			File[] directoryListing = directory.listFiles();
			if (directoryListing != null) {				
				int lastIndex;				
				for (File file : directoryListing) {
					String name = file.getName();
					lastIndex = name.lastIndexOf('.');
					if (file.isFile() && lastIndex != -1) {																		
							if(name.substring(lastIndex+1).equals(fileExtension) && (name.contains(fileIdentifier))) {
								loadedFiles.put(name.substring(0,lastIndex),new CharSeperatedFile(file));
							} 
						
					}
				} 
			}
		 
	}
	

	
}
