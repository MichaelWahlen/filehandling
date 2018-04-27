package main.java.com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;

public class Unzip {
	public static File unzipGZ(File file, File outputDir) {
	    GZIPInputStream in = null;
	    OutputStream out = null;
	    File target = null;
	    try {	        
	        in = new GZIPInputStream(new FileInputStream(file));	       
	        target = new File(outputDir,file.getName().substring(0, file.getName().lastIndexOf(".")));
	        out = new FileOutputStream(target);	
	        byte[] buf = new byte[1024];
	        int len;
	        while ((len = in.read(buf)) > 0) {
	            out.write(buf, 0, len);
	        }	  
	        in.close();
	        out.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } 
	    return target;
	}
}
