package denny.selenum.test.GmailSelenumTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

public class ConfigHandler {
	public  static Config conf;
	public ConfigHandler(String confPath) throws FileNotFoundException {
		 File confFile = new File(confPath);
		 if(!confFile.exists()) {
			 throw new FileNotFoundException("Config not exist: "+confPath);
		 }
		 conf=(Config)load(confPath,new Config());
	}
	public static Object load(String confPath,Object object) throws FileNotFoundException {
		System.out.println("confPath:"+confPath);
		 File file = new File(confPath);
		 Scanner scan = new Scanner(file);
		 
		 StringBuilder confString = new StringBuilder();
	     while(scan.hasNextLine()){
	    	 confString.append(scan.nextLine());	       	         
	     }   
	     scan.close();
	     try {
	    	 Object conf = new Gson().fromJson(confString.toString(),object.getClass());
	    	 System.out.println(conf);
	    	 return conf;
	     }catch(JSONException e) {
	    	 System.out.println("json format error: "+ confString.toString());
	    	 e.getStackTrace();
	     }
		return conf;
	     
	    
	}
	   

 


}
