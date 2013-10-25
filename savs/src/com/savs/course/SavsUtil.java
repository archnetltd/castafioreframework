package com.savs.course;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;

public class SavsUtil {
	
	public static List<BinaryFile> getHandouts(Product p){
		Directory notes = p.getFile("notes", Directory.class);
		if(notes == null){
			return new ArrayList<BinaryFile>();
		}else{
			return notes.getFiles(BinaryFile.class).toList();
		}
	}
	
	
	public static void addHandout(String name,BinaryFile bf, Product p)throws Exception{
		
		
		Directory notes = p.getFile("notes", Directory.class);
		if(notes == null){
			notes = p.createFile("notes", Directory.class);
		}
		
		BinaryFile note = p.createFile(name, BinaryFile.class);
		note.write(bf.getInputStream());
		
		
		
	}

}
