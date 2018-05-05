package main.java.com.rest;

import java.io.BufferedOutputStream;
import java.io.File;

import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;




@Path("/controller")
public class Controller {
	  
	  @POST
	  @Path("/upload")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.MULTIPART_FORM_DATA)
	  public List<Message> storeFileAt(@FormDataParam("file") InputStream fileInputStream, @FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {			
			File storeFile = new File("C:\\Users\\Sander\\eclipse-workspace\\filehandling\\src\\test\\resources\\Mixed\\Uploaded","test.csv");
			saveFile(fileInputStream, storeFile);	
			List<Message> returnList = new ArrayList<Message>();
			returnList.add(new Message("Uploaded"));
			returnList.add(new Message("AndMoved"));
			return returnList;
	}
	  
	  private void saveFile(InputStream uploadedInputStream, File storeFile) {
		try {			
			OutputStream outStream = new BufferedOutputStream(new FileOutputStream(storeFile));		    
	    	byte[] buffer = new byte[8 * 1024];
	        int bytesRead;
	        while ((bytesRead = uploadedInputStream.read(buffer)) != -1) {
	        	outStream.write(buffer, 0, bytesRead);
	        }				
			outStream.close();		

		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	  
	  
	  
	  
	  
}
