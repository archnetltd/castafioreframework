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
 import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

import org.apache.commons.lang.StringUtils;
import org.castafiore.utils.IOUtil;


public class TemplateImporter {
	
	
	
	public static List<Map<String, String>> getThumbNails(String url)throws Exception{
		Source source = new Source(new URL(url));
		
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		 List<Element> elements = source.getAllElements();
		 for(Element elem :elements){
			 
		 }
		 for(Element elem : elements){
			 if("thumbnail".equals(elem.getAttributeValue("class"))){
				 Map<String, String> map = new HashMap<String, String>();
				 List<Element> innerElements = elem.getAllElements();
				 for(Element e : innerElements){
					 if(e.getName().equals("img")){
						 //System.out.println("img:" + e.getAttributeValue("src"));
						 map.put("img", e.getAttributeValue("src"));
					 }else if(e.getName().equals("th")){
						 //System.out.print(e.getTextExtractor().toString() + ":");
						 
						 String key = e.getTextExtractor().toString();
						 List<Element> eee = e.getParentElement().getAllElements();
						 String value = "";
						 for(Element ee : eee){
							 if(ee.getName().equals("td")){
								 value = ee.getTextExtractor().toString();
							 }
						 }
						 //map.put(e.getTextExtractor().toString(), value);
						 //if(e.getTextExtractor().toString().equalsIgnoreCase("Name")){
							 
						 //}
						 map.put(key, value);
					 }
				 }
				 result.add(map);
			 }
		 }
		 
		 return result;
	}
	
	
	public static void downloadInfo(String url)throws Exception{
		
		List<Map<String, String>> items = getThumbNails(url);
		
		//List<String> names = getThumbNails();
		
		//Iterator<Map<String, String>> iter = item
		
		
		for(Map<String, String> item : items){
			
			String name = item.get("Name");
			
			System.out.println("-----------------");
			System.out.println(name);
			String thumbnail = "http://www.freecsstemplates.org/templates/thumbnails/"+name+".gif";
			String template = "http://www.freecsstemplates.org/templates/previews/"+name+"/";
			String path = "/home/kureem/csstemplates/" + name;
			File dir = new File(path);
			dir.mkdir();
			
			addFile(thumbnail ,path + "/" + name + ".gif" );
			//byte[] abImages = IOUtil.getStreamContentAsBytes(new URL(thumbnail).openStream());
			//FileOutputStream fout = new FileOutputStream(new File(path + "/" + name + ".gif"));
			//fout.write(abImages);
			//fout.flush();
			//fout.close();
			
			addFile(template,path + "/" + name + ".html");
			
			
			Source src = new Source(new FileInputStream(path + "/" + name + ".html"));
			List<Element> links = src.getAllElements("link");
			List<StartTag> linkStartTags=src.getAllStartTags(HTMLElementName.LINK);
			for(StartTag link : linkStartTags){
				String css = link.getAttributeValue("href");
				String cssUrl = "http://www.freecsstemplates.org/templates/previews/"+name+"/" + css;
				addFile(cssUrl,path + "/" + css);
				
				String scss = IOUtil.getFileContenntAsString(path + "/" + css);
				String[] asParts = StringUtils.splitByWholeSeparator(scss, "url(");
				for(String s : asParts){
					if(s.startsWith("images/")){
						String aaap = StringUtils.split(s, ")")[0];
						
						new File(path + "/images").mkdir();
						try{
							addFile(template + aaap, path +  "/" + aaap );
						}catch(Exception e){
							System.out.println(template + aaap);
						}
					}
				}
			}		
			
		}
		
		
	}
	
	
	public static void addFile(String url, String path)throws Exception{
		byte[] abImages = IOUtil.getStreamContentAsBytes(new URL(url).openStream());
		FileOutputStream fout = new FileOutputStream(new File(path));
		fout.write(abImages);
		fout.flush();
		fout.close();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		
		for(int i = 1; i < 58; i ++){
			String url = "http://www.freecsstemplates.org/css-templates/" + i;
			downloadInfo(url);
		}

	}

}
