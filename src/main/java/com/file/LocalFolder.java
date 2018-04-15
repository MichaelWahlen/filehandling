package main.java.com.file;

import java.io.File;
import java.util.HashMap;
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
		if (loadedFiles.size()>0) {
			for(Map.Entry<String, CharSeperatedFile> entry : loadedFiles.entrySet()) {
				CharSeperatedFile file = entry.getValue();
				returnList.put(file.getFileName(),file.getParsedRows());
			}
		}		
		return returnList;
	}  
	
	public HashMap<String, List<String>> getFile(String fileName) {		
		HashMap<String, List<String>> returnList = new HashMap<String, List<String>>();		
		if (loadedFiles.size()>0) {
				CharSeperatedFile file = loadedFiles.get(fileName);
				if(file != null) {
					returnList.put(file.getFileName(),file.getParsedRows());
				}
			
		}		
		return returnList;
	}   

	public void loadContainedFiles(String fileIdentifier, String fileExtension) {
		if (directory != null) {			
			File[] directoryListing = directory.listFiles();
			if (directoryListing != null) {   
				String fileExtensionType;
				int lastIndex;
				for (File child : directoryListing) {
					lastIndex = 0;
					if (child.isFile()) {
						lastIndex = child.getName().lastIndexOf('.');
						if (lastIndex > 0) {
							fileExtensionType = child.getName().substring(lastIndex+1);
							if(fileExtensionType.equals(fileExtension) && (child.getName().contains(fileIdentifier) || fileIdentifier.equals(""))) {
								loadedFiles.put(child.getName(),new CharSeperatedFile(child));
							} 
						}
					}
				} 
			}
		} 
	}
	

	
}
