package org.castafiore.shoppingmall.merchant;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Properties;

import org.castafiore.utils.IOUtil;
import org.castafiore.utils.StringUtil;

public final class DeliveryUtil {
	
	
	private static Properties zones;
	
	private static String[][] rates;
	
	static{
		try{
		zones = new Properties();
		zones.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/shoppingmall/merchant/ups-zone.properties"));
		Iterator iter =zones.keySet().iterator();
		while(iter.hasNext()){
			String key = iter.next().toString();
			String val = zones.getProperty(key);
			System.out.println(key.trim() + "=" + val.trim());
			
		}
		String content = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/shoppingmall/merchant/ups.properties"));
		
		String[] lines = StringUtil.split(content, "\n");
		rates = new String[lines.length][9];
		for(int l = 0; l < lines.length; l++){
			String[] parts = StringUtil.split(lines[l], " ");
			for(int p=0;p<parts.length;p++){
				//System.out.print(part + "\t");
				rates[l][p] = parts[p];
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	private static int getNormalizedIndex(double weight){
		if(weight <= 0.5){
			return 0;
		}else if(weight <= 1){
			return 1;
		}else if(weight <= 1.5){
			return 2;
		}else if(weight <= 2){
			return 3;
		}else if(weight <= 2.5){
			return 4;
		}else if(weight <= 3){
			return 5;
		}else if(weight <= 3.5){
			return 6;
		}else if(weight <= 4){
			return 7;
		}else if(weight <= 4.5){
			return 8;
		}else if(weight <= 5){
			return 9;
		}else if(weight <= 5.5){
			return 10;
		}else if(weight <= 6){
			return 11;
		}else if(weight <= 6.5){
			return 12;
		}else if(weight <= 7){
			return 13;
		}else if(weight <= 7.5){
			return 14;
		}else if(weight <= 8){
			return 15;
		}else if(weight <= 8.5){
			return 16;
		}else if(weight <= 9){
			return 17;
		}else if(weight <= 9.5){
			return 18;
		}else if(weight <= 10){
			return 19;
		}else if(weight <= 11){
			return 20;
		}else if(weight <= 12){
			return 21;
		}else if(weight <= 13){
			return 22;
		}else if(weight <= 14){
			return 23;
		}else if(weight <= 15){
			return 24;
		}else if(weight <= 16){
			return 25;
		}else if(weight <= 17){
			return 26;
		}else if(weight <= 18){
			return 27;
		}else if(weight <= 19){
			return 28;
		}else if(weight <= 20){
			return 29;
		}else{
			return 29;
		}
	}
	public static BigDecimal lookup(BigDecimal weight, String countryCode){
		
		if(countryCode.equals("MU")){
			if(weight.doubleValue() <= 30){
				return new BigDecimal(150);
			}else if(weight.doubleValue() <= 70){
				BigDecimal remainder = weight.subtract(new BigDecimal(30)).multiply(new BigDecimal(9));
				remainder = remainder.add(new BigDecimal(150));
				return remainder;
			
				
			}else{
				return null;
			}
		}
		
		if(zones.containsKey(countryCode)){
			int zone = Integer.parseInt(zones.getProperty(countryCode));
			int weiIndex = getNormalizedIndex(weight.doubleValue());
			String val = rates[weiIndex][zone].replace(",", "").trim();
			return new BigDecimal(val);
			
		}else{
			return null;
		}
		
	}
	
	public static void main(String[] args){
		
	}

}
