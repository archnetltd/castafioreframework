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
 package org.castafiore.catalogue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import org.castafiore.utils.IOUtil;
import org.castafiore.utils.StringUtil;

public class SampleExtractor {
	
	public static void main(String[] args)throws Exception{
		getLinks("http://www.alapage.com/m/pl/malid:15631840");
	}	
	public static void getLinks(String spec)throws Exception{
		Source source = new Source(new URL(spec));
		List<Element> elements = source.getAllElements(HTMLElementName.A);
		for(Element elem : elements){
			String sClass = elem.getAttributeValue("class");
			if("bouton".equals(sClass)){
				//System.out.println(elem.getAttributeValue("href"));
				getDetails(elem.getAttributeValue("href"));
			}
		}
	}
	
	
	public static void getDetails(String url)throws Exception{
		Source source = new Source(new URL("http://www.alapage.com" + url));
		List<Element> tags = source.getAllElements();
		String code = (StringUtil.split(StringUtil.split(url, "#")[1], ":")[1]).substring(0, 6);
		System.out.println("code:" + code);
		
		File file = new File("C:\\Users\\kureem\\castafiore\\casta-catalogue\\WEB-INF\\product-sample\\" + code);
		file.mkdir();
		
		FileOutputStream mainProperties = new FileOutputStream(new File("C:\\Users\\kureem\\castafiore\\casta-catalogue\\WEB-INF\\product-sample\\" + code + "\\main.properties" ));
		FileOutputStream description = new FileOutputStream(new File("C:\\Users\\kureem\\castafiore\\casta-catalogue\\WEB-INF\\product-sample\\" + code + "\\description.txt" ));
		FileOutputStream metadata = new FileOutputStream(new File("C:\\Users\\kureem\\castafiore\\casta-catalogue\\WEB-INF\\product-sample\\" + code + "\\metadata.properties" ));
		FileOutputStream imgOut = new FileOutputStream(new File("C:\\Users\\kureem\\castafiore\\casta-catalogue\\WEB-INF\\product-sample\\" + code + "\\" + code + ".gif" ));
		//Map<String, String> details = new HashMap<String, String>();
		for(Element t : tags){
			if(t instanceof Element){
				if("zm_main_image".equals(t.getAttributeValue("id"))){
					mainProperties.write(("imgUrl=" + t.getAttributeValue("src") + "\n").getBytes());
					readImage(imgOut, t.getAttributeValue("src"));
				}else if("zm_name_description".equals(t.getAttributeValue("id"))){
					mainProperties.write(("title=" + t.getContent().getTextExtractor().toString() + "\n").getBytes());
				}else if("zm_store_name_expedition".equals(t.getAttributeValue("id"))){
					mainProperties.write(("soldBy=" + t.getContent().getTextExtractor().toString() + "\n").getBytes());
				}else if("zm_description_long".equals(t.getAttributeValue("id"))){
					description.write(t.getContent().getTextExtractor().toString().getBytes());
				}else if("listeCarac".equals(t.getAttributeValue("class"))){
					getMetadata(t,metadata);
				}else if("underlined linkmasker".equals(t.getAttributeValue("class"))){
					mainProperties.write(("from=" + t.getContent().getTextExtractor().toString() + "\n").getBytes());
				}
			}
		}
		description.flush();
		description.close();
		mainProperties.flush();
		mainProperties.close();
	}
	
	
	public static void readImage(FileOutputStream fOut, String url)throws Exception{
		InputStream in = new URL(url).openStream();
		byte[] bytes = IOUtil.getStreamContentAsBytes(in);
		fOut.write(bytes);
		fOut.flush();
		fOut.close();
	}
	public static void getMetadata(Element element, FileOutputStream fout)throws Exception{
		List<Element> elements = element.getAllElements(HTMLElementName.LI);
		for(Element elem : elements){
			String text =elem.getContent().getTextExtractor().toString();
			text.replace("<strong>", "").replace("</strong>", "");
			String[] parts= StringUtil.split(text, ":");
			fout.write((parts[0] + "=" + parts[1] + "\n").getBytes());	
		}
		fout.flush();
		fout.close();
		
	}

}
