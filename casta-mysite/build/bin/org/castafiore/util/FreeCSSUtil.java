/*
 * Copyright (C) 2007-2010 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
 package org.castafiore.util;

import java.io.InputStream;

import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.futil;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.File;

public class FreeCSSUtil {
	
	
//	public static void copyTemplates(String username, String templateName)throws Exception{
//		
//		// copy Binaryfiles ending with html to 
//		RepositoryService service = SpringUtil.getRepositoryService();
//		Directory dir = service.getDirectory("/root/portal-data/master-templates", username);
//		
//		Directory nDir = new Directory();
//		nDir.setName("templates");
//		nDir.makeOwner(username);
//		
//		service.saveIn("/root/users/"+ username + "/portal-data", nDir, username);
//		
//		FileIterator iter = dir.getChildren(BinaryFile.class);
//		while(iter.hasNext()){
//			File file = iter.next();
//			
//			if(file.getName().endsWith("html")){
//				BinaryFile bf = (BinaryFile)file;
//				InputStream in = bf.getInputStream();
//				
//				BinaryFile nBf = new BinaryFile();
//				nBf.setName(bf.getName());
//				nBf.makeOwner(username);
//				nBf.write(in);
//				service.saveIn("/root/users/" + username + "/portal-data/templates", file, username);
//			}
//		}
//		
//		Directory templateDir = service.getDirectory("/root/portal-data/master-templates/" + templateName, username);
//		//Directory userTemplateDir = service.getDirectory("", username)
//		Directory themeDir = new Directory();
//		themeDir.setName(templateName);
//		themeDir.makeOwner(username);
//		nDir.addChild(themeDir);
//		
//		Directory images = new Directory();
//		images.setName("images");
//		images.makeOwner(username);
//		themeDir.addChild(images);
//		
//		
//		FileIterator iterTemplates = templateDir.getChildren(BinaryFile.class);
//		while(iterTemplates.hasNext()){
//			File file = iterTemplates.next();
//			BinaryFile bf = (BinaryFile)file;
//			InputStream in = bf.getInputStream();
//			
//			BinaryFile nBf = new BinaryFile();
//			nBf.setName(bf.getName());
//			nBf.makeOwner(username);
//			nBf.write(in);
//			themeDir.addChild(nBf);
//			//service.saveIn("/root/users/" + username + "/portal-data/templates", file, username);
//		}
//		
//		Directory imgDir = service.getDirectory("/root/portal-data/master-templates/" + templateName + "/images", username);
//		FileIterator iterImages = imgDir.getChildren(BinaryFile.class);
//		while(iterImages.hasNext()){
//			File file = iterImages.next();
//			BinaryFile bf = (BinaryFile)file;
//			InputStream in = bf.getInputStream();
//			
//			BinaryFile nBf = new BinaryFile();
//			nBf.setName(bf.getName());
//			nBf.makeOwner(username);
//			nBf.write(in);
//			images.addChild(nBf);
//			//service.saveIn("/root/users/" + username + "/portal-data/templates", file, username);
//		}
//		
//		
//		service.update(nDir, username);
//		
//		
//	}
	
//	public static Container newBlogPage(String portalPath, String pageName, String username, String companyName, String companyDescription)throws Exception{
//		
//		Container page = new EXContainer("blog", "div");
//		page.setAttribute("des-id", "blog:postcontainer");
//		page.setAttribute("commentstemplate", ResourceUtil.getDownloadURL("ecm", "/root/users/"+username+"/portal-data/templates/comments.html"));
//		page.setAttribute("templatelocation", ResourceUtil.getDownloadURL("ecm", "/root/users/"+username+"/portal-data/templates/post.html"));
//		page.setName(pageName);
//		page.setAttribute("datasource", "{\"value\":\"/root/users/"+username+"/applications/blog\",\"type\":\"Folders\",\"entitytype\":\"org.castafiore.blog.BlogPost\"}");
//		
//		
//		page.setAttribute("Text", ""); 
//		page.setStyle("width", "100%");
//		page.setStyle("min-height", "500px");
//		page.setStyle("padding", "0");
//		page.setStyle("margin", "0");
//
//		String file = portalPath + "/pages";
//		RepositoryService service = BaseSpringUtil.getBeanOfType(RepositoryService.class);
//		BinaryFile bf = new BinaryFile();
//		bf.setName(pageName);
//		bf.write(DesignableUtil.generateXML(page, null).getBytes());
//		bf.makeOwner(username);
//		service.saveIn(file, bf, username);
//		page.setAttribute("pagepath", bf.getAbsolutePath());
//		
//		
////		BlogPost post = new BlogPost();
////		post.setTitle("Welcome to " + companyName );
////		post.setSummary(companyDescription);
////		post.setDetail(companyDescription);
////		post.setName("welcome post");
////		post.setOwner(username);
////		
////		service.saveIn("/root/users/" + username + "/applications/blog", post, username);
////		
//		
//		
//		return page;
//	}
	
	public static void copyPortalData(String destination){
		
	}
	
	
	public static Container newHomePage(String portalPath, String pageName,String username, String companyName, String companyDescription)throws Exception{
		
		File file = futil.getDirectory(portalPath + "/pages");
		RepositoryService service = BaseSpringUtil.getBeanOfType(RepositoryService.class);
		BinaryFile template = (BinaryFile)service.getFile("/root/portal-data/master-templates/home-template.ptl", username);
		String sTemplate = IOUtil.getStreamContentAsString(template.getInputStream());
		sTemplate = sTemplate.replace("${username}", username);
		
		
		
		
		BinaryFile bf = file.createFile(pageName, BinaryFile.class);
		
		bf.write(sTemplate.getBytes());
		bf.makeOwner(username);
		futil.update(file);
		
		
		
		Directory userDir = futil.getDirectory("/root/users/" + username);
		Directory dir = userDir.createFile("home", Directory.class);
		
		
		Article fmainArticle = dir.createFile("mainarticle", Article.class);
		fmainArticle.setTitle("Welcome to " + companyName );
		fmainArticle.setSummary(companyDescription);
		fmainArticle.setDetail(companyDescription);
		
		fmainArticle.setOwner(username);
		
		Article columnarticle = dir.createFile("columnarticle", Article.class);
		columnarticle.setTitle("Welcome to " + companyName );
		columnarticle.setSummary(companyDescription);
		columnarticle.setDetail(companyDescription);
		
		columnarticle.setOwner(username);
		
		userDir.save();
		
		
		return null;
		
	}
	
//	public static Container newCataloguePage(String portalPath,String pageName, String username)throws Exception{
//		
//		String file = portalPath + "/pages";
//		RepositoryService service = BaseSpringUtil.getBeanOfType(RepositoryService.class);
//		BinaryFile template = (BinaryFile)service.getFile("/root/portal-data/master-templates/catalogue-template.ptl", username);
//		String sTemplate = IOUtil.getStreamContentAsString(template.getInputStream());
//		sTemplate = sTemplate.replace("${username}", username);
//		
//		
//		
//		
//		BinaryFile bf = new BinaryFile();
//		bf.setName(pageName);
//		bf.makeOwner(username);
//		bf.write(sTemplate.getBytes());
//		service.saveIn(file, bf, username);
//		
//		
//		//SpringUtil.getBeanOfType(ProductImporter.class).createProducts(username);
//		return null;
//		//body.setAttribute("pagepath", bf.getAbsolutePath());
//	}

}
