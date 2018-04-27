package main.java.com.file;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.com.util.StringUtil;
import main.java.com.util.Unzip;

public class LocalFolder {

	private HashMap<String,LocalFile> loadedFiles = null;
	private File sourceFolder = null;
	private File extractedFolder = null;
	private File parsedFolder = null;
	private File zippedFolder = null;
	
	public LocalFolder(String contentDirectoryPath) {
		setContentDirectory(contentDirectoryPath);		
	} 
	
	public void setContentDirectory(String path) {
		loadedFiles = null;
		File tempFile = new File(path);		
		if (tempFile.exists() && tempFile.isDirectory()) {
			sourceFolder = new File(path);
			extractedFolder = new File(path+"\\UNZIPPED");
			parsedFolder = new File(path+"\\PARSED");
			zippedFolder = new File(path+"\\ZIPPED");
			loadedFiles = new HashMap<String, LocalFile>();
		}		
	}
	
	public HashMap<String, List<String>> getAllInMemoryFiles() {			
		List<String> fileNames =  new ArrayList<String>();
		for(Map.Entry<String, LocalFile> entry : loadedFiles.entrySet()) {
			fileNames.add(entry.getKey());			
		}			
		return getInMemoryFiles(fileNames);
	}
	
	public HashMap<String, List<String>> getInMemoryFiles(List<String> fileNames) {		
		HashMap<String, List<String>> filesContents = new HashMap<String, List<String>>();		
		for(String fileName:fileNames) {			
			LocalFile file = loadedFiles.get(fileName);
			if(file != null) {								
				filesContents.put(fileName,file.getParsedRows());
			}	
		}			
		return filesContents;
	}   
	
	public List<String> getInMemoryFileNames(){
		List<String> fileNames = new ArrayList<String>();
		for(Map.Entry<String, LocalFile> entry: loadedFiles.entrySet()) {
			fileNames.add(entry.getKey());
		}
		return fileNames;
	}
	
	public void parseFilesToMemory(List<File> files, int parseAsOfRow) {
		for(File file:files) {
			if (file.exists()&&!file.isDirectory()) {
				LocalFile newFile = new LocalFile(file);
				newFile.loadToMemory(parseAsOfRow);
				loadedFiles.put(StringUtil.getUpperCaseNameWithoutExtension(file.getName()),newFile);
			}
		}		
	}
	
	public void parseAllToMemory(int parseAsOfRow) {					
		File[] directoryListing = sourceFolder.listFiles();			
		parseFilesToMemory(Arrays.asList(directoryListing),parseAsOfRow);	 
	}
	
	public void inMemoryFilesToCSV(List<String> fileNames,int startAtRow) {
		parsedFolder.mkdir();		
		for(String fileName:fileNames) {
			File targetFile = new File(parsedFolder,fileName+".CSV");
			loadedFiles.get(fileName).storeAsCSV(targetFile);	
		}	
	}
	
	public void allInMemoryFilesToCSV(int parseAsOfRow) {					
		List<String> fileNames = new ArrayList<String>();		
		for(Map.Entry<String, LocalFile> entry : loadedFiles.entrySet()) {
			fileNames.add(entry.getKey());
		}
		inMemoryFilesToCSV(fileNames,parseAsOfRow);
	}
	
	public void clearInMemoryFiles() {
		loadedFiles = new HashMap<String, LocalFile>();
	}
	
	public void unzipContainedGZ() {
		File[] directoryListing = zippedFolder.listFiles();
		extractedFolder.mkdir();
		for(File file: directoryListing) {
			if (file.isFile()&&StringUtil.getExtension(file.getName()).equals("GZ")) {
				Unzip.unzipGZ(file, extractedFolder);
			}
		}		
	}	
	
	private void deleteDirectory(File directory) {
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
	    directory.delete();
	}

	public void deleteParsedFiles() {
		deleteDirectory(parsedFolder);
	}
	
	public void deleteUnzippedFiles() {
		deleteDirectory(extractedFolder);
	}
	
}
