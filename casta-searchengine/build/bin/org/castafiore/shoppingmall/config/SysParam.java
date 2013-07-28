package org.castafiore.shoppingmall.config;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.castafiore.designer.marshalling.DesignableDTO;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SysParam {

	
	public static String getParameter(String key, String sDefault){
		return sDefault;
	}
	
	
	public static void readXML()
	throws ParserConfigurationException, SAXException, IOException {
			InputStream xml = Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/shoppingmall/config/Categories.xml");
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(xml);
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getChildNodes();
			
			for(int i = 0; i < nodeList.getLength(); i ++){
				Node n = nodeList.item(i);
				
				String name = n.getNodeName();
				if(name.equals("categories")){
					String category = n.getAttributes().getNamedItem("name").getTextContent();
					System.out.println(category);
					NodeList categories = n.getChildNodes();
					for(int j = 0; j < categories.getLength(); j ++){
						System.out.println(categories.item(j).getAttributes().getNamedItem("name").getTextContent());
					}
				}
			}
		
		
		}

}
