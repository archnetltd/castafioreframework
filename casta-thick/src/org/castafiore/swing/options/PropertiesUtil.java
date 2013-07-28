package org.castafiore.swing.options;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Properties;

public class PropertiesUtil {
	
	
	public static Properties properties = new Properties();
	
	static{
		try{
		init();
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}
	private static void init()throws Exception{
		File configs = new File("configs.properties");
		if(!configs.exists()){
			configs.createNewFile();
			FileOutputStream fout = new FileOutputStream(new File("configs.properties"));
			fout.write("server.endpoint=http://68.68.109.26/elie\n".getBytes());
			fout.flush();
			fout.close();
		}
		
		properties.load(new FileInputStream(configs));
		
	}
	
	public static void saveProperties(String key, String value)throws Exception{
		properties.put(key, value);
	}
	
	public static void saveAll()throws Exception{
		Iterator<Object> iter = properties.keySet().iterator();
		StringBuilder txt = new StringBuilder();
		while(iter.hasNext()){
			String key = iter.next().toString();
			String value = properties.getProperty(key);
			
			txt.append(key).append("=").append(value).append("\n");
		}
		
		
		FileOutputStream fout = new FileOutputStream(new File("configs.properties"));
		fout.write(txt.toString().getBytes());
		fout.flush();
		fout.close();
		init();
		
	}

}
