import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTagType;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXMaskableInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;

/*
 * Copyright (C) 2007-2008 Castafiore
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


public class Main {

	

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

//		String[] as = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
//				"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
//				"X", "Y", "Z",
//
//				"AA", "AB", "AC", "AD", "AE", "AF", "AG", "AH", "AI", "AJ",
//				"AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT",
//				"AU", "AV", "AW", "AX", "AY", "AZ", "BA", "BB", "BC", "BD",
//				"BE", "BF", "BG", "BH", "BI", "BJ", "BK", "BL", "BM", "BN",
//				"BO", "BP", "BQ", "BR", "BS", "BT", "BU", "BV", "BW", "BX",
//				"BY", "BZ", "CA", "CB", "CC", "CD", "CE", "CF", "CG", "CH",
//				"CI", "CJ", "CK", "CL", "CM", "CN", "CO", "CP", "CQ", "CR",
//				"CS", "CT", "CU", "CV", "CW", "CX", "CY", "CZ", "DA", "DB",
//				"DC", "DD", "DE", "DF", "DG", "DH", "DI", "DJ", "DK", "DL",
//				"DM", "DN", "DO", "DP", "DQ", "DR", "DS", "DT", "DU", "DV",
//				"DW", "DX", "DY", "DZ", "EA", "EB", "EC", "ED", "EE", "EF",
//				"EG", "EH", "EI", "EJ", "EK", "EL", "EM", "EN", "EO", "EP",
//				"EQ", "ER", "ES", "ET", "EU", "EV", "EW", "EX", "EY", "EZ",
//				"FA", "FB", "FC", "FD", "FE", "FF", "FG", "FH", "FI", "FJ",
//				"FK", "FL", "FM", "FN", "FO", "FP", "FQ", "FR", "FS", "FT",
//				"FU", "FV", "FW", "FX", "FY", "FZ", "GA", "GB", "GC", "GD",
//				"GE", "GF", "GG", "GH", "GI", "GJ", "GK", "GL", "GM", "GN",
//				"GO", "GP", "GQ", "GR", "GS", "GT", "GU", "GV", "GW", "GX",
//				"GY", "GZ", "HA", "HB", "HC", "HD", "HE", "HF", "HG", "HH",
//				"HI", "HJ", "HK", "HL", "HM", "HN", "HO", "HP", "HQ", "HR",
//				"HS", "HT", "HU", "HV", "HW", "HX", "HY", "HZ", "IA", "IB",
//				"IC", "ID", "IE", "IF", "IG", "IH", "II", "IJ", "IK", "IL",
//				"IM", "IN", "IO", "IP", "IQ", "IR", "IS", "IT", "IU", "IV",
//				"IW", "IX", "IY", "IZ", "JA", "JB", "JC", "JD", "JE", "JF",
//				"JG", "JH", "JI", "JJ", "JK", "JL", "JM", "JN", "JO", "JP",
//				"JQ", "JR", "JS", "JT", "JU", "JV", "JW", "JX", "JY", "JZ" };
//
//		for (int i = 0; i < 10; i++) {
//			for (int j = 0; j < as.length; j++)
//				System.out.print("\"" + as[i] + as[j] + "\",");
//		}
		
		//testMail("kureem@gmail.com");
		
		
		// String url ="http://68.68.109.26/upstage/castafiore/resource?spec=ecm:/root/users/DPRUNIERES/AZ%20FACADES/HA/PRAXAZHAP12012001%20img20120721_0023.pdf";
		 
		 //S//ystem.out.println(ResourceUtil.read(url).length);
		
		//Source source = new Source(new FileInputStream("C:\\castafioera\\casta-searchengine\\web\\templates\\v2\\registrations\\EXCompanyInformation.xhtml"));
		
//		for(Element e : source.getAllElements()){
//			if(e.getStartTag().getName().equalsIgnoreCase("input")){
//				String name = e.getAttributeValue("name");
//				
//				String type = e.getAttributeValue("type");
//				
//				//EXInput input = new EXInput(name);
//				if(type.equalsIgnoreCase("password")){
//					System.out.println("addChild(new EXPassword(\""+name+"\").addClass(\"input-text\"));");
//				}else if(StringUtil.isNotEmpty(e.getAttributeValue("mask"))){
//					String mask =e.getAttributeValue("mask");
//					//input = new EXMaskableInput(name, "", e.getAttributeValue("mask"));
//					System.out.println("addChild(new EXMaskableInput(\""+name+"\", \"\",\""+mask+"\").addClass(\"input-text\"));");
//				}else{
//					System.out.println("addChild(new EXInput(\""+name+"\").addClass(\"input-text\"));");
//				}
//			}
//		}
//		 for(int i =5;i <=13;i++)
//			 downloadImages("http://www.rewritables.net/freebanners"+i+".htm", "http://www.rewritables.net");
		
		
		
		
		
//		String url = "https://graph.facebook.com/fql?q=select uid, first_name, last_name, pic  from user  where uid in (select uid2 from friend WHERE uid1 = me() )&access_token=AAACEdEose0cBAJv3YtDZCJwayPFhTnxGuONAhGZB3UrC8fe9K54dJViTYbEqWyemchZBkOrODjQw5GDRmw5hlHiOsZBapnGlhAXX6uP4ZBkrgSZCJkOpPx";
//		
//		
//		String tmpurl = "https://graph.facebook.com/fql?q=" + URLEncoder.encode("select uid, first_name, last_name, pic  from user  where uid in (select uid2 from friend WHERE uid1 = me() )", "ISO-8859-1") + "&access_token=AAACEdEose0cBAJv3YtDZCJwayPFhTnxGuONAhGZB3UrC8fe9K54dJViTYbEqWyemchZBkOrODjQw5GDRmw5hlHiOsZBapnGlhAXX6uP4ZBkrgSZCJkOpPx"; 
//		String result = ResourceUtil.readUrl("https://graph.facebook.com/fql?q=select%20uid,%20first_name,%20last_name,%20pic%20%20from%20user%20%20where%20uid%20in%20%28select%20uid2%20from%20friend%20WHERE%20uid1%20=%20me%28%29%20%29&access_token=AAACEdEose0cBAJv3YtDZCJwayPFhTnxGuONAhGZB3UrC8fe9K54dJViTYbEqWyemchZBkOrODjQw5GDRmw5hlHiOsZBapnGlhAXX6uP4ZBkrgSZCJkOpPx");
//		ResourceUtil.read(tmpurl);
//		
//		System.out.println(tmpurl);
		
		System.out.print(Charset.availableCharsets());
		
	}	
	
	private static void downloadImages(String path, String rootPath)throws Exception{
		Source source = new Source(new URL(path));
		
		List<Element> elements = source.getAllElements("img");
		for(Element e : elements){
			
			FileOutputStream fout = new FileOutputStream("c:\\java\\banners\\" + e.getAttributeValue("src"));
			fout.write(ResourceUtil.readUrlBinary(rootPath + "/" + e.getAttributeValue("src")));
			fout.flush();
			fout.close();
		}
	}

	
	public static boolean testMail(String email)throws Exception{
		 	DefaultHttpClient httpclient = new DefaultHttpClient();
	       

	        HttpPost httpPost = new HttpPost("http://www.mailtester.com/testmail.php");
	        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	        nvps.add(new BasicNameValuePair("email", email));
	        nvps.add(new BasicNameValuePair("lang", "en"));
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
	        HttpResponse response2 = httpclient.execute(httpPost);

	        try {
	        	
	            HttpEntity e = response2.getEntity();
	           
	           System.out.println( EntityUtils.toString(e));
	           
	        } finally {
	            httpPost.releaseConnection();
	        }
	        
	        return true;
	}
	

}
