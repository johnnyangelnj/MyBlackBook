package com.johnnyangel.myblackbook.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.LoggerFactory;


public class FileIO extends android.app.Application  {
	
	
	private static final Logger logger = LoggerFactory.getLogger();
	
	private static final String activityName = "FileIO";
	
	private static Map<String, String> fileNameList = null;

	
	public FileIO(){

	}

	public static boolean isExternalStoragePresent() {

        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states, but
            // all we need
            // to know is we can neither read nor write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        
        if((mExternalStorageAvailable) && (mExternalStorageWriteable))
        	logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" External Storage is available and writeable ");

        return (mExternalStorageAvailable) && (mExternalStorageWriteable);
    }
	
	public static boolean createDirectory(String directory){
		
		boolean isDirectory = false;
		
		if(!directory.startsWith(Environment.getExternalStorageDirectory().toString()))
			directory = Environment.getExternalStorageDirectory().toString()+directory;
		
		File myDirectory = new File(directory);
		if(!myDirectory.exists() && !myDirectory.isDirectory()) {
			//Create Application Folder
			try {
				File appFolder = new File(directory);
				appFolder.mkdirs();
				isDirectory = true;
				logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" Created directory "+ directory);
			} catch(Exception e){
				isDirectory = false;
				logger.error(MyApplicationProperties.getApplicationName()+" - "+activityName+" Error creating directory on sd card [" + directory +   "] " +e.getMessage());
			}
			
		} else {
			isDirectory = true;
			logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" Directory "+ directory +" Already Exists!");
		}
	
		return isDirectory;

	}
	
	public static boolean createDirectory(String[] appDirectoryList){
		
		boolean isDirectory = false;
		
		for(int i=0; i < appDirectoryList.length; i++){
			if(!appDirectoryList[i].startsWith(Environment.getExternalStorageDirectory().toString()))
				appDirectoryList[i] = Environment.getExternalStorageDirectory().toString()+appDirectoryList[i];
			isDirectory = createDirectory(appDirectoryList[i]);
		}
	
		return isDirectory;

	}
	
	public static boolean isDirectoryEmpty(String directory){
		
		boolean isEmpty= true;
		
		if(!directory.startsWith(Environment.getExternalStorageDirectory().toString()))
			directory = Environment.getExternalStorageDirectory().toString()+directory;
		
		File thisDirectory = new File(directory);
		File[] fileList = thisDirectory.listFiles();
		if(fileList != null){
			for(int i=0; i<fileList.length; i++) {
			    File fileEntry = new File(directory +File.separator+ fileList[i].getName());
			    if(fileEntry.isFile() && fileEntry.canRead()) {
			    	logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" isDirectoryEmpty found file [" + fileEntry.getAbsolutePath() +   "]");
			    	isEmpty = false;
			    } else if(fileEntry.isDirectory()){
			    	logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" isDirectoryEmpty found Directory [" + fileEntry.getAbsolutePath() +   "]");
			    	isEmpty = isDirectoryEmpty(fileEntry.getAbsolutePath());
			    }
			}
		}

		return isEmpty;
	}
	
	public static Map<String, String> getDirectoryFileList(String directory){
		
		if(!directory.startsWith(Environment.getExternalStorageDirectory().toString()))
			directory = Environment.getExternalStorageDirectory().toString()+directory;
		
		File thisDirectory = new File(directory);
		File[] fileList = thisDirectory.listFiles();
		if(fileList != null){
			fileNameList = new HashMap<String, String>();
			for(int i=0; i<fileList.length; i++) {
			    File fileEntry = new File(directory +File.separator+ fileList[i].getName());
			    if(fileEntry.isFile() && fileEntry.canRead()) {
			    	logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" getDirectoryFileList found file [" + fileEntry.getAbsolutePath() +   "]");
			    	fileNameList.put(fileEntry.getName(), directory); 
			    } else if(fileEntry.isDirectory()){
			    	logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" getDirectoryFileList found Directory [" + fileEntry.getAbsolutePath() +   "]");
			    	getSubDirectoryFileList(fileEntry.getAbsolutePath());
			    }
			}
		}

		return fileNameList;
	}
	
	private static void getSubDirectoryFileList(String directory){
		File thisDirectory = new File(directory);
		File[] fileList = thisDirectory.listFiles();
		
		if(fileList != null){
			for(int i=0; i<fileList.length; i++) {
			    File fileEntry = new File(directory +File.separator+ fileList[i].getName());
			    if(fileEntry.isFile() && fileEntry.canRead()) {
			    	logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" getSubDirectoryFileList found file [" + fileEntry.getAbsolutePath() +   "]");
			    	fileNameList.put(fileEntry.getName(), directory); 
			    } else if(fileEntry.isDirectory()){
			    	logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" getSubDirectoryFileList found Directory [" + fileEntry.getAbsolutePath() +   "]");
			    	getSubDirectoryFileList(fileEntry.getAbsolutePath());
			    }
			}
		}
	}
	
	public static File createFile(String directory, String fileName){
		
		if(!directory.startsWith(Environment.getExternalStorageDirectory().toString()))
			directory = Environment.getExternalStorageDirectory().toString()+directory;
		
	    File f = new File(directory,fileName);
	    if(!f.exists()){
		    try {
		    	f.createNewFile();
		    	logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" Wrote new file to sd card [" + directory+File.separator+fileName +   "] ");
		    } catch (IOException e) {
		    	logger.error(MyApplicationProperties.getApplicationName()+" - "+activityName+" Error can not write new file to sd card [" + directory+File.separator+fileName +   "] " +e.getMessage());
		    }
	    } else {
	    	logger.error(MyApplicationProperties.getApplicationName()+" - "+activityName+" Error file exists, can not over write file to sd card [" + directory+File.separator+fileName +   "] ");
	    }
	    return f;
	}
	
	public static boolean doesFileExist(String directory, String fileName){
		boolean exists = false;
		
		if(!directory.startsWith(Environment.getExternalStorageDirectory().toString()))
			directory = Environment.getExternalStorageDirectory().toString()+directory;
		
	    File f = new File(directory,fileName);
	    if(f.exists())
	    	exists = true;
	    else
	    	exists = false;
		
		return exists;
	}
	
	public static boolean moveFile(String sourceDirectory, String fileName, String targetDirectory) {
		boolean successful = false;
	    
	    successful = copyFile(sourceDirectory, fileName, targetDirectory);
	    
	    if(successful)
	    	successful = deleteFile(sourceDirectory, fileName);
	    
	    if(successful)
	    	logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" moved file [" + sourceDirectory+File.separator+fileName +   "] to [" + targetDirectory+File.separator+fileName +   "]");

	    
	    return successful;

	}
	
	public static boolean copyFile(String sourceDirectory, String fileName, String targetDirectory) {
		boolean successful = false;
	    InputStream in = null;
	    OutputStream out = null;
	    
	    createDirectory(targetDirectory);
	    
	    try {
	        in = new FileInputStream(sourceDirectory + File.separator + fileName);        
	        out = new FileOutputStream(targetDirectory + File.separator + fileName);

	        int read;
	        byte[] buffer = new byte[1024];
	        while ((read = in.read(buffer)) != -1) {
	            out.write(buffer, 0, read);
	        }
	        in.close();

	        out.flush();
	        out.close();
		    
	    	logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" copied file [" + sourceDirectory+File.separator+fileName +   "] to [" + targetDirectory+File.separator+fileName +   "]");

	        successful = true;
	    } catch (FileNotFoundException e) {
	    	logger.error(MyApplicationProperties.getApplicationName()+" - "+activityName+" Error file does not exist [" + sourceDirectory+File.separator+fileName +   "] " +e.getMessage());
	    } catch (Exception e) {
			logger.error(MyApplicationProperties.getApplicationName()+" - "+activityName+" Error copying file [" + sourceDirectory+File.separator+fileName +   "] " +e.getMessage());
	    }
	    
	    return successful;

	}
	
	
	public static boolean createResourceImageFile(Context context, int drawableImage, String directory, String fileName, String fileExtension){
		boolean isResourceImageFileCreated = false;
		
		if(!directory.startsWith(Environment.getExternalStorageDirectory().toString()))
			directory = Environment.getExternalStorageDirectory().toString()+directory;
		
		File f = new File(directory, fileName+fileExtension);
		if(!f.exists()){
			Bitmap bm = BitmapFactory.decodeResource( context.getResources(), drawableImage);
			try{
				FileOutputStream outStream = new FileOutputStream(f);
				if(fileExtension.equalsIgnoreCase(".png"))
					bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
				else if (fileExtension.equalsIgnoreCase(".jpg") || fileExtension.equalsIgnoreCase(".jpeg"))
					bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
			    outStream.flush();
			    outStream.close();
			    isResourceImageFileCreated  = true;
			    logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" Wrote new Image file to sd card [" + directory+File.separator+fileName+fileExtension +   "] ");
			} catch(IOException e){
				isResourceImageFileCreated  = false;
				logger.error(MyApplicationProperties.getApplicationName()+" - "+activityName+" Error can not write new Image file to sd card [" + directory+File.separator+fileName+fileExtension +   "] " +e.getMessage());				
			}
		}
		return isResourceImageFileCreated;
	}	

	
	public static boolean createResourceImageFile(Context context, String directory, String[] fileNameList, String fileExtension){
		boolean isResourceImageFileCreated = false;
	
		if(!directory.startsWith(Environment.getExternalStorageDirectory().toString()))
			directory = Environment.getExternalStorageDirectory().toString()+directory;
		
		for(int i=0; i < fileNameList.length; i++){
			isResourceImageFileCreated = createResourceImageFile(context, getDrawable(context,fileNameList[i]), directory, fileNameList[i], fileExtension);
		}

		return isResourceImageFileCreated;
	}	
	
	public static boolean deleteDirectory(String directory) {
		boolean isDirectoryDeleted = false;
		
		if(!directory.startsWith(Environment.getExternalStorageDirectory().toString()))
			directory = Environment.getExternalStorageDirectory().toString()+directory;
		
		File thisDirectory = new File(directory);

		isDirectoryDeleted = deleteDirectory(thisDirectory);
		
		return isDirectoryDeleted;
	}
	
	public static boolean deleteDirectory(File directory) {
		
	    if( directory.exists() ) {
	      File[] files = directory.listFiles();
	      if (files == null) {
	          return true;
	      }
	      for(int i=0; i<files.length; i++) {
	         if(files[i].isDirectory()) {
	           deleteDirectory(files[i]);
	         }
	         else {
	           files[i].delete();
	         }
	      }
	    }
	    return( directory.delete() );
	  }
	
    public static boolean deleteFile(String directory, String file){
    	boolean isDeleted = false;
    	
		if(!directory.startsWith(Environment.getExternalStorageDirectory().toString()))
			directory = Environment.getExternalStorageDirectory().toString()+directory;
    	
	    File f = new File(directory,file);
	    try {
	    	if(f.isFile() && f.canRead()){
		    	f.delete();
		    	isDeleted = true;
		    	logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" Deleted file[" +directory+File.separator+file+"] on SD Card ");
	    	}
	    } catch (Exception e) {
	    	logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" Error: Can not delete file[" +directory+File.separator+file+"] on SD Card " +e.getMessage());
	    }  	
    	
    	return isDeleted;
    }
    
    public static boolean loadProperties(Context context, String fileName) throws IOException {
    	
    	boolean loadedProperties = false;

    	Properties properties = new Properties();
 
	    try {
	        InputStream fileStream = context.getAssets().open(fileName);
	        properties.load(fileStream);
	        fileStream.close();
	        MyApplicationProperties.setApplicationName(properties.getProperty("ApplicationName"));
	        MyApplicationProperties.setApplicationImageDirectory(Environment.getExternalStorageDirectory().toString()+properties.getProperty("ApplicationImageDirectory"));
	        MyApplicationProperties.setApplicationDataDirectory(Environment.getExternalStorageDirectory().toString()+properties.getProperty("ApplicationDataDirectory"));
	        MyApplicationProperties.setApplicationDirectoryList(properties.getProperty("ApplicationDirectoryList").split(","));
	        MyApplicationProperties.setApplicationDefaultBookImageNames(properties.getProperty("ApplicationDefaultBookImageNames").split(","));
	        MyApplicationProperties.setApplicationDefaultBookImage(MyApplicationProperties.getApplicationImageDirectory()+File.separator+properties.getProperty("ApplicationDefaultBookImage"));
	    } catch (FileNotFoundException e) {
	    	
	    }

    	return loadedProperties;
    }
    
    public static int getDrawable(Context context, String name)
    {
        return context.getResources().getIdentifier(name,"drawable", context.getPackageName());
    }
    
    public static Object marshalXml(Object object, String directory, String fileName){
    	
    	Serializer serial = new Persister();
    	
    	Object tmpObject = null;
    	
    	String xmlData = FileIO.readXmlFile(directory,fileName);
    	
		try {
			Reader reader = new StringReader(xmlData);
			tmpObject = serial.read(object.getClass(), reader, false);
		} catch (NotFoundException e) {
			logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" Error: Class object not found  " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" Error: Can not marshal xml data  " + e.getMessage());
			e.printStackTrace();
		}
    	
		return tmpObject;
    }
    
    public static boolean unMarshalXml(Object object, String directory, String fileName){
    	
    	boolean successful = false;
    	
    	Serializer serializer = new Persister();

    	File result = FileIO.createFile(directory,fileName);

        try {
            serializer.write(object, result);
            successful = true;
        } catch (Exception e) {
        	logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" Error: Can not unmarshal xml data  " + e.getMessage());
            e.printStackTrace();
        }
    	
    	return successful;
    }
    
    
    public static String readXmlFile(String directory, String fileName){
    	
		if(!directory.startsWith(Environment.getExternalStorageDirectory().toString()))
			directory = Environment.getExternalStorageDirectory().toString()+directory;
		
		//Read text from file
		StringBuilder xmlData = new StringBuilder();

		try {

		    BufferedReader br = new BufferedReader(new FileReader(directory+File.separator+fileName));
		    String line;

		    while ((line = br.readLine()) != null) {
		    	xmlData.append(line);
		    }
		    
		    br.close();
		}
		catch (IOException e) {
	    	logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" Error: Can not read file[" +directory+File.separator+fileName+"] on SD Card " +e.getMessage());	    		    
		}
		
		return xmlData.toString();

    }
    
    public static String getDefaultBookImageUri(){
    	return MyApplicationProperties.getApplicationDefaultBookImage()+".png";
    }
    
    public static String getFileNameFromUri(String uri){
    	
    	String[] pathNodes = uri.split(File.separator);
    	return pathNodes[pathNodes.length-1];
    }
}
