package org.castafiore.shoppingmall.merchant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.castafiore.KeyValuePair;
import org.castafiore.SimpleKeyValuePair;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.context.CastafioreApplicationContextHolder;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.utils.ResourceUtil;

public class FinanceUtil {
	
	private static List<KeyValuePair> countries = new ArrayList<KeyValuePair>();
	static{
		try{
			Source source = new Source(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/shoppingmall/merchant/Countries.properties"));
			List<Element> options = source.getAllElements("select").get(0).getAllElements("option");
			countries = new ArrayList<KeyValuePair>();
			for(Element o : options){
				countries.add(new SimpleKeyValuePair(o.getAttributeValue("value"), o.getTextExtractor().toString().trim()));
			}
			countries.remove(0);
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	public static List<KeyValuePair> getCurrencies()throws Exception{
//		Source source = new Source(ResourceUtil.readUrl("http://www.google.com/finance/converter"));
//		List<Element> options = source.getAllElements("select").get(0).getAllElements("option");
		List<KeyValuePair> result = new ArrayList<KeyValuePair>();
		result.add(new SimpleKeyValuePair("MUR", "Mauritian Rupee(MUR)"));
		result.add(new SimpleKeyValuePair("USD", "US Dollar(USD)"));
		result.add(new SimpleKeyValuePair("EUR", "Euro(EUR)"));
		result.add(new SimpleKeyValuePair("MZN", "Mozambican Metical"));
//		for(Element o : options){
//			if(o.getAttributeValue("value").equalsIgnoreCase(""))
//			result.add(new SimpleKeyValuePair(o.getAttributeValue("value"), o.getTextExtractor().toString()));
//		}
		return result;
		
	}
	
	public static List<String> getCountryList(){
		List<String> result = new ArrayList<String>();
		for(KeyValuePair kv : countries){
			result.add(kv.getValue());
		}
		return result;
	}
	public static List<KeyValuePair> getCountries()throws Exception{
		return countries;
		
	}
	
	public static String getCode(String country){
		for(KeyValuePair kv : countries){
			if(kv.getValue().equalsIgnoreCase(country)){
				return kv.getKey();
			}
		}return null;
	}
	
	public static KeyValuePair getClientCountry()throws Exception{
//		String ip = getClientIp();
//		Source source = new Source(ResourceUtil.readUrl("http://www.geobytes.com/IpLocator.htm?GetLocation&template=php3.txt&IpAddress=" + ip));
//		List<Element> elements = source.getAllElements("meta");
//		SimpleKeyValuePair re = new SimpleKeyValuePair();
//		for(Element e : elements){
//			if(e.getAttributeValue("name").equalsIgnoreCase("country")){
//				re.setValue(e.getAttributeValue("content"));
//			}else if(e.getAttributeValue("name").equals("iso2")){
//				re.setKey(e.getAttributeValue("content"));
//			}
//		}
//		return re;
		return new SimpleKeyValuePair("MU", "Mauritius");
	}
	
	public static String getClientIp(){
		Application app = CastafioreApplicationContextHolder.getCurrentApplication();
		return app.getConfigContext().get("remoteAddress").toString();
	}
	
	public static String getCurrentCurrency(){
//		Application app = CastafioreApplicationContextHolder.getCurrentApplication();
//		if(app == null){
//			
//			Merchant m  = MallUtil.getCurrentMerchant();
//			if(m != null){
//				return m.getCurrency();
//			}
//			return "MUR";
//		}
//		Container c = app.getDescendentByName("currency");
//		if(c instanceof EXSelect){
//			return ((KeyValuePair)((EXSelect)c).getValue()).getKey();
//		}
//		Merchant m  = MallUtil.getCurrentMerchant();
//		if(m != null){
//			return m.getCurrency();
//		}
		return "MUR";
		
	}
	
	
	public static BigDecimal convert(BigDecimal amount, String fromCur, String toCur)throws Exception{
		//http://www.google.com/finance/converter?a=1&from=ARS&to=AED
		if(!fromCur.equals(toCur)){
			Source source = new Source(ResourceUtil.readUrl("http://www.google.com/finance/converter?a=" + amount.toPlainString() + "&from=" + fromCur + "&to=" + toCur));
			String result = source.getElementById("currency_converter_result").getAllElements("span").get(0).getTextExtractor().toString();
			result = result.replace(toCur, "").trim();
			return new BigDecimal(result);
		}return amount;
	}

}
