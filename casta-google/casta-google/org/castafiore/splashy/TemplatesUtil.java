package org.castafiore.splashy;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.castafiore.catalogue.CatalogueService;
import org.castafiore.catalogue.Product;
import org.castafiore.resource.FileData;
import org.castafiore.spring.SpringUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;

public final class TemplatesUtil {

	public static List<Product> getTemplates(String category, String subcategory) {
		return SpringUtil.getBeanOfType(CatalogueService.class).getProductsWithCategoryAndSubCategory(category, subcategory,"splashy");
	}


	public static void uploadTemplate(FileData uploaded, Product destination)throws Exception {

		byte[] buffer = new byte[2048];

		InputStream theFile = uploaded.getInputStream();
		ZipInputStream stream = new ZipInputStream(theFile);
		//Directory destination = SpringUtil.getRepositoryService().getDirectory(dest, Util.getRemoteUser());
		try {
			
			BinaryFile portal = destination.getFile("template.ptl",BinaryFile.class) == null? destination.createFile("template.ptl", BinaryFile.class):destination.getFile("template.ptl",BinaryFile.class);
			Directory files = destination.getFile( "template_files", Directory.class)==null? destination.createFile("template_files", Directory.class):destination.getFile( "template_files", Directory.class);
			BinaryFile css = portal.getFile( "template.css", BinaryFile.class)==null?portal.createFile("template.css", BinaryFile.class):portal.getFile("template.css", BinaryFile.class);
			Directory pages = portal.getFile("pages", Directory.class)==null?portal.createFile("pages", Directory.class):portal.getFile("pages", Directory.class);
			Directory javascripts = portal.getFile("javascripts", Directory.class)==null? portal.createFile("javascripts", Directory.class):portal.getFile("javascripts", Directory.class);
			Directory modules = portal.getFile("modules", Directory.class)==null?portal.createFile("modules", Directory.class):portal.getFile("modules", Directory.class);;
			Directory images = files.getFile("images", Directory.class)==null?files.createFile("images", Directory.class):files.getFile("images", Directory.class);;
			Directory templates = files.getFile("templates", Directory.class)==null?files.createFile("templates", Directory.class):files.getFile("templates", Directory.class);;
			
			Directory assets = files.getFile("assets", Directory.class)==null?files.createFile("assets", Directory.class):files.getFile("assets", Directory.class);;
			
			ZipEntry entry;
			while ((entry = stream.getNextEntry()) != null) {
				OutputStream output = null;
				try {
					String name = entry.getName();
					if(!entry.isDirectory()){
					
						if(name.endsWith(".css"))
							output = css.getOutputStream();
						else if(name.endsWith(".pg")){
							String fname =entry.getName().replace(".pg", "");
							fname = StringUtil.split(fname, "/")[StringUtil.split(fname, "/").length-1];
							BinaryFile page = pages.getFile(fname, BinaryFile.class)==null?pages.createFile(fname, BinaryFile.class):pages.getFile(fname, BinaryFile.class);
							output =page.getOutputStream();
						}else if(name.endsWith("jpg") || name.endsWith("png") || name.endsWith("gif")){
							String fname =entry.getName();
							fname = StringUtil.split(fname, "/")[StringUtil.split(fname, "/").length-1];
							BinaryFile page = images.getFile(fname, BinaryFile.class)==null?images.createFile(fname, BinaryFile.class):images.getFile(fname, BinaryFile.class);
							output =page.getOutputStream();
						}else if(name.endsWith("xhtml")){
							String fname =entry.getName();
							fname = StringUtil.split(fname, "/")[StringUtil.split(fname, "/").length-1];
							BinaryFile page = templates.getFile(fname, BinaryFile.class)==null?templates.createFile(fname, BinaryFile.class):templates.getFile(fname, BinaryFile.class);
							output =page.getOutputStream();
						}else if(name.endsWith("js")){
							String fname =entry.getName();
							fname = StringUtil.split(fname, "/")[StringUtil.split(fname, "/").length-1];
							BinaryFile page = javascripts.getFile(fname, BinaryFile.class)==null?javascripts.createFile(fname, BinaryFile.class):javascripts.getFile(fname, BinaryFile.class);
							output =page.getOutputStream();
						}else if(name.endsWith("groovy")){
							String fname =entry.getName();
							fname = StringUtil.split(fname, "/")[StringUtil.split(fname, "/").length-1];
							BinaryFile page = modules.getFile(fname, BinaryFile.class)==null?modules.createFile(fname, BinaryFile.class):modules.getFile(fname, BinaryFile.class);
							output =page.getOutputStream();
						}else if(name.endsWith("ptl")){
							//BinaryFile page = modules.getFile(entry.getName(), BinaryFile.class)==null?modules.createFile(entry.getName(), BinaryFile.class):modules.getFile(entry.getName(), BinaryFile.class);
							output =portal.getOutputStream();
						}else{
							String fname =entry.getName();
							fname = StringUtil.split(fname, "/")[StringUtil.split(fname, "/").length-1];
							BinaryFile page = assets.getFile(fname, BinaryFile.class)==null?assets.createFile(fname, BinaryFile.class):assets.getFile(fname, BinaryFile.class);
							output =page.getOutputStream();
						}

						int len = 0;
						while ((len = stream.read(buffer)) > 0) {
							output.write(buffer, 0, len);
						}
						
					}
					
				} finally {
					// we must always close the output file
					if (output != null)
						output.close();
				}
				
				
			}
			
		} finally {
			// we must always close the zip file.
			stream.close();
			
			
		}
	}
}
