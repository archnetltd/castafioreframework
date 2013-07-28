package org.castafiore.ecm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.StringUtil;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleSheet;

import com.steadystate.css.parser.CSSOMParser;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
//		InputStream xml = Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/ecm/Categories.xml");
//		
//		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//		DocumentBuilder db = dbf.newDocumentBuilder();
//		Document doc = db.parse(xml);
//		doc.getDocumentElement().normalize();
//		NodeList nodeList = doc.getChildNodes();
//		nodeList = nodeList.item(0).getChildNodes();
//		for(int i = 0; i < nodeList.getLength(); i ++){
//			Node n = nodeList.item(i);
//			
//			String name = n.getNodeName();
//			if(name.equals("category")){
//				String category = n.getAttributes().getNamedItem("name").getTextContent();
//				System.out.println(category);
//				NodeList categories = n.getChildNodes();
//				for(int j = 0; j < categories.getLength(); j ++){
//					if(categories.item(j).getNodeName().equalsIgnoreCase("category")){
//						String sCategory = categories.item(j).getAttributes().getNamedItem("name").getTextContent();
//						if(sCategory != null && sCategory.length() > 0){
//							System.out.println("\t" +sCategory);
//						}
//					}
//				}
//			}
//		}
		
//		File f = new File("C:\\apache-tomcat-6.0.18\\webapps\\casta-ui\\designer\\menu\\vertical");
//		
//		File[] files = f.listFiles();
//		for(File img : files){
//			if(img.getName().endsWith(".bmp")){
//				String folder = "C:\\Program Files\\CSS Tab Designer 2\\styles\\" + img.getName().replace(".bmp", "");
//				File templateFolder = new File(folder);
//				File[] tmps = templateFolder.listFiles();
//				File n = new File("C:\\apache-tomcat-6.0.18\\webapps\\casta-ui\\designer\\menu\\vertical\\contents\\"+ img.getName().replace(".bmp", "").replace(" ", "").replace("'", ""));
//				n.mkdir();
//				for(File item : tmps){
//					if(item.isDirectory()){
//						continue;
//					}
//					if(item.getName().equals("template.html")){
//						continue;
//					}
//					if(item.getName().endsWith(".ini")){
//						continue;
//					}
//					if(item.getName().endsWith("css")){
//						css(item, n.getAbsolutePath());
//					}else if(item.getName().equals("preview.html")){
//						template(item, n.getAbsolutePath(),img.getName().replace(".bmp", "").replace(" ", "").replace("'", ""));
//					}else{
//						other(item, n.getAbsolutePath());
//					}
//				}
//			}
//		}
		
		//System.out.println(NumberFormat.getCurrencyInstance().format(300.4567));
		
//		String req = "http://query.yahooapis.com/v1/public/yql?q=SELECT%20*%20FROM%20flickr.people.publicphotos(0%2C5)%20WHERE%20user_id%3D'60594171%40N02'%20AND%20extras%3D'url_sq'";
//		
//		Source s = new Source(new URL(req));
//		
//		for(Element e : s.getAllElements("photo"))
//		{
//			System.out.println(e.getAttributeValue("url_sq"));
//			//System.out.println(e.getStartTag());
//		}
		
//		DefaultCategoryDataset data = new DefaultCategoryDataset();
//		for(int row = 0; row < 10; row++){
//			for(int col = 0; col < 5; col++){
//				data.addValue(row * col, "row " + 0	,"col " + col);
//			}
//		}
//		
//		JFreeChart chart = ChartFactory.createBarChart("Title", "CategoryAxislabel", "valueAxisLabel", data, PlotOrientation.VERTICAL, true, true, false);
//		
//		BufferedImage img = chart.createBufferedImage(800, 600);
//		ImageIO.write(img, "png", new File("c:\\java\\ppp.png"));
		
		
		
		
	System.out.println(StringUtil.toCurrency("MUR",new BigDecimal( 101467567.2356)));

	}
	
	private static void other(File template, String dir)throws Exception{
		byte[] bufs = IOUtil.getFileContentAsBytes(template.getAbsolutePath());
		
		FileOutputStream out = new FileOutputStream(new File(dir + "\\" + template.getName()  ));
		out.write(bufs);
		out.flush();
		out.close();
	}
	
	private static void template(File template, String dir, String name)throws Exception{
		String scss = IOUtil.getFileContenntAsString(template);
		scss = scss.replace("#", ".");
		scss = scss.replace("id=", "class=");
		if(scss.contains("</h4>"))
			scss = StringUtils.splitByWholeSeparator(scss, "</h4>")[1];
		else if(scss.contains("</h2>"))
			scss = StringUtils.splitByWholeSeparator(scss, "</h2>")[1];
		else
			scss = StringUtils.splitByWholeSeparator(scss, "<body>")[1];
		scss = StringUtils.splitByWholeSeparator(scss, "</body>")[0];
		scss = "<div><div class=\""+name+"\"><link href=\"designer/menu/vertical/contents/"+name+"/style.css\" rel=\"stylesheet\" type=\"text/css\" />"  + scss + "</div></div>"; 
		FileOutputStream out = new FileOutputStream(new File(dir + "\\preview.html"  ));
		out.write(scss.getBytes());
		out.flush();
		out.close();
	}
	private static void css(File css, String dir)throws Exception{
		//String scss = IOUtil.getFileContenntAsString(css);
		//scss = scss.replace("#", ".");
		
		String scss=Parse(css, css.getParentFile().getName().replace(" ", "").replace("'", ""));
		
		FileOutputStream out = new FileOutputStream(new File(dir + "\\style.css"  ));
		out.write(scss.getBytes());
		out.flush();
		out.close();
	}
	
	public static String Parse(File css, String name) throws Exception
    {	String result = "";
        
        	
              InputStream stream = new FileInputStream(css);
              InputSource source = new InputSource(new InputStreamReader(stream));
              CSSOMParser parser = new CSSOMParser();
              CSSStyleSheet stylesheet = parser.parseStyleSheet(source, null, null);
              CSSRuleList ruleList = stylesheet.getCssRules();
              for (int i = 0; i < ruleList.getLength(); i++) 
              {
                CSSRule rule = ruleList.item(i);
                if (rule instanceof CSSStyleRule) 
                {
                    CSSStyleRule styleRule=(CSSStyleRule)rule;
                    String selector = styleRule.getSelectorText().replace("*", "").replace("html", "").trim();
                    StringBuilder b = new StringBuilder();
                    if(selector.startsWith("body")){
                    	selector = "." + name;
                    	b.append(selector);
                    }else{
                    
	                    String[] asSelector = StringUtils.split(selector, ",");
	                    
	                    for(String s : asSelector){
	                    	if(b.length() > 0){
	                    		b.append(" , ");
	                    	}
	//                    	if(s.trim().startsWith("a:")){
	//                    		
	//                    	}
	                    	s = s.replace("#", ".").trim();
	                    	
	                    	s = "." + name  + " "+ s;
	                    	b.append(s);
	                    }
                    }
                    
                    System.out.println(b.toString() + "{");
                    result = result+ b.toString() + "{\n";
                    CSSStyleDeclaration styleDeclaration = styleRule.getStyle();
                    for (int j = 0; j < styleDeclaration.getLength(); j++) 
                    {
                         String property = styleDeclaration.item(j);
                         String value = styleDeclaration.getPropertyValue(property);
                         System.out.println("\t" +property + ":" + value);
                         result = result +"\t" +property + ":" + value + ";\n";
                    }
                    result = result + "}\n";
                    System.out.println("}");

                 }
               }

           
              if (stream != null) stream.close();
            
          
           
           return result;
   }


	private static String[] replace = new String[]{
			 "if", "public", "private", "protected", "static", "interface", "implements",
			"extends", "while", "else", "package", "import"
		};
	public static void prepareCodes(File file)throws Exception{
		File[] files = file.listFiles();
		if(files != null){
			for(File f : files){
				String name = f.getName();
				if(name.endsWith(".cd")){
					
					String content = IOUtil.getFileContenntAsString(f);
					content = content.replace("\n", "<br>");
					content = content.replace("\r\n", "<br>");
					content = content.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
					
					for(String s : replace){
						content = content.replace(s, "<span style='color:red'>" + s + "</span>");
					}
					
					FileOutputStream fout = new FileOutputStream(f);
					fout.write(content.getBytes());
					fout.flush();
					fout.close();
					
				}
			}
		}
	}
	
	
	public static void changeAuthor(File file)throws Exception{
		File[] files = file.listFiles();
		if(files != null){
			for(File f : files){
				String name = f.getName();
				if(name.endsWith(".java")){
					
					String content = IOUtil.getFileContenntAsString(f);
					
					System.out.println(f.getAbsolutePath());
					
					//content = content.replace("Created by Kureem Rossaye", "");
					content = content.replace("kureem.rossaye@hotmail.com", "kureem@gmail.com");
					//content = content.replace("Author : Kureem Rossaye", "@author Kureem Rossaye<br>");
					
					FileOutputStream fout = new FileOutputStream(f);
					fout.write(content.getBytes());
					fout.flush();
					fout.close();
					
				}else if( f.isDirectory() && !f.getName().equals("build")){
					changeAuthor(f);
				}
			}
		}
	}
	
	public static void treadDir(File file, String license)throws Exception{
		//String license = IOU
		File[] files = file.listFiles();
		if(files != null){
			for(File f : files){
				String name = f.getName();
				if(name.endsWith(".java")){
					
					String content = IOUtil.getFileContenntAsString(f);
					if(!content.trim().startsWith("/*")){
						System.out.println(name);
						content = license + content;
						FileOutputStream fout = new FileOutputStream(f);
						fout.write(content.getBytes());
						fout.flush();
						fout.close();
					}
				}else if( f.isDirectory()){
					treadDir(f, license);
				}
			}
		}
	}
}
