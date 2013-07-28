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

package org.castafiore.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.castafiore.Constant;
import org.springframework.util.Assert;

/**
 *  @author Kureem Rossaye<br> kureem@gmail.com
 * June 27 2008
 */
public final class StringUtil {
	
	private final static int REC_SIZE = 3;
	
	
	
	private static final char[] symbols = new char[36];

	  static {
	    for (int idx = 0; idx < 10; ++idx)
	      symbols[idx] = (char) ('0' + idx);
	    for (int idx = 10; idx < 36; ++idx)
	      symbols[idx] = (char) ('a' + idx - 10);
	  }

	  private final static Random random = new Random();

	  public static String nextString(int length)
	  {
		 char[] buf = new char[length];
	    for (int idx = 0; idx < buf.length; ++idx) 
	      buf[idx] = symbols[random.nextInt(symbols.length)];
	    return new String(buf);
	  }


	public static boolean getBooleanValue(String s, boolean Default) {
		try {
			return Boolean.parseBoolean(s);
		} catch (Exception e) {

		}

		return Default;

	}
	
	private static String sep(String s){
		StringBuilder b = new StringBuilder();
		if(s.length() > 3){
			int counter = 0;
			s = StringUtils.reverse(s);
			for(char c : s.toCharArray()){
				b.append(c);
				counter++;
				if(counter == 3){
					b.append(' ');
					counter=0;
				}
			}
			
			return  StringUtils.reverse( b.toString().trim());
		}else{
			return s;
		}
	}
	
	private static String TwoDp(String s){
		String[] as = StringUtil.split(s, ".");
		if(as.length > 1){
			if(as[1].length() > 2){
				as[1] = as[1].substring(0, 2);
			}else if(as[1].length() == 1){
				as[1] = as[1] + "0";
			}
			return sep(as[0]) + "." + as[1];
		}else{
			return sep(as[0]) + ".00";
		}
	}
	public static String toCurrency(String currency, BigDecimal b){
		return TwoDp(b.toPlainString()) + " " + currency;
	}
	
	public static String toCurrency(String currency, double b){
		return TwoDp(b + "") + " " + currency;
	}

	public static String getValue(String key, Map<String, String> map) {
		String result = map.get(key);

		if (result == null)
			return "";
		return result;
	}
	
	public static String getValue(String key, String[][] map){
		if(map != null && map.length > 0){
			for(String[] vals :map){
				if(vals.length == REC_SIZE){
					if(vals[0].equals(key)){
						return vals[1];
					}
				}
			}
		}
		return "";
	}
	
	public static String[] getKeys(String[][] map){
		if(map != null && map.length >0){
			String[] result = new String[map.length];
			for(int i = 0; i < map.length; i++){
				result[i] = map[i][0];
			}
			return result;
		}else{ 
			return new String[]{};
		}
	}
	
	public static String[] getChangedKeys(String[][] map){
		
		if(map != null && map.length >0){
			List<String> result = new ArrayList<String>(map.length);
			for(int i = 0; i < map.length; i++){
				if(map[i][2] != null){
					result.add(map[i][0]);
				}
			}
			return result.toArray(new String[result.size()]);
		}else{ 
			return new String[]{};
		}
	}
	
	
	public static String[][] flushChanged(String[][] map){
		if(map != null && map.length > 0){
			for(int i = 0; i < map.length; i ++){
				map[i][2] = null;
			}
		}
		
		return map;
	}
	
	
	public static String[][] addOrUpdateItem(String key, String value, String[][] map){
		if(value == null){
			String curVal = getValue(key, map);
			if(curVal!= null && curVal.length() > 0){
				String[][] tmp =new  String[map.length-1][REC_SIZE];
				int indexTmp = 0;
				for(int i = 0; i < map.length; i++){
					if(!map[i][0].equals(key)){
						tmp[indexTmp][0]=map[i][0];
						tmp[indexTmp][1] = map[i][1];
						tmp[indexTmp][2] = map[i][2];
						indexTmp++;
					}
				}
				
			}
			
		}
		if(map == null){
			map = new String[1][REC_SIZE];
			map[0][0]= key;
			map[0][1] = value;
			map[0][2] = "c";
			return map;
		}else{
			
			for(int i =0; i < map.length; i++){
				if(map[i].length == REC_SIZE){
					if(map[i][0].equals(key)){
						map[i][1] = value;
						map[i][2] = "c";
						return map;
					}
				}
			}
			
			
			String[][] tmp =new  String[map.length + 1][REC_SIZE];
			for(int i = 0; i < map.length; i++){
				tmp[i][0]=map[i][0];
				tmp[i][1] = map[i][1];
				tmp[i][2] = map[i][2];
			}
			tmp[map.length][0] = key;
			tmp[map.length][1]=value;
			tmp[map.length][2]="c";
			return tmp;
			
		}
	}

	public static String getJSonString(Map<String, String> map) {
		Iterator<String> iterKey = map.keySet().iterator();
		StringBuilder result = new StringBuilder();
		result.append("{");
		while (iterKey.hasNext()) {
			String key = iterKey.next();

			String value = map.get(key);

			if (!value.toString().startsWith(Constant.NO_CONFLICT)) {
				value = "\"" + value + "\"";
			}
			result.append("\"").append(key).append("\"").append(":").append(
					value);

			if (iterKey.hasNext()) {
				result.append(",");
			}

		}
		result.append("}");
		return result.toString();
	}

	public static boolean isNotEmpty(String s) {
		if (s != null && s.length() > 0)
			return true;
		else
			return false;
	}

	public static boolean isNotEmpty(Integer s) {
		if (s != null)
			return true;
		else
			return false;
	}

	public static boolean isNotEmpty(Object s) {
		if (s != null)
			return true;
		else
			return false;
	}

	public static String buildattributesFromMap(Map<String, String> attributes) {
		StringBuilder builder = new StringBuilder();

		Iterator<String> keyIte = attributes.keySet().iterator();

		while (keyIte.hasNext()) {
			String attr = keyIte.next();

			String value = attributes.get(attr);

			builder.append(" ").append(attr).append("=").append("").append(
					value).append("").append(" ");
		}

		return builder.toString();
	}
	
	public static String buildattributesFromMap(String[][] attributes) {
		StringBuilder builder = new StringBuilder();

		//Iterator<String> keyIte = attributes.keySet().iterator();
		
		if(attributes != null && attributes.length > 0){
			for(int i = 0; i < attributes.length; i ++){
				String attr = attributes[i][0];
	
				String value = attributes[i][1];
	
				builder.append(" ").append(attr).append("=").append("").append(
						value).append("").append(" ");
			}
		}

		return builder.toString();
	}

	public static String getFileNameFromPath(String path) {
		Assert.notNull(path, "path cannot be null");

		String[] parts = StringUtil.split(path, "/");
		if (parts != null) {
			return parts[parts.length - 1];
		}
		return "";
	}

	/*
	 * public static String getToString(Object bean) { try {
	 * java.lang.reflect.Method[] methods = bean.getClass().getMethods();
	 * 
	 * StringBuilder builder = new StringBuilder();
	 * 
	 * for(java.lang.reflect.Method m : methods) {
	 * if(m.getParameterTypes().length == 0) { if(m.getName().startsWith("get")
	 * || m.getName().startsWith("is")) { if(m.getAnnotation(StyleAttr.class) !=
	 * null) { Object result = m.invoke(bean);
	 * 
	 * if(result != null) {
	 * 
	 * String tmp = m.getAnnotation(StyleAttr.class).name();
	 * 
	 * builder.append(tmp).append(" : ").append(result).append(";\n"); } } } } }
	 * return builder.toString();
	 * 
	 * } catch(Exception e) { e.printStackTrace();
	 * 
	 * return "";
	 * 
	 * }
	 * 
	 * 
	 * }
	 */

	public static String[] split(String string, String delimiter) {
		StringTokenizer tokenizer = new StringTokenizer(string, delimiter);

		String[] result = new String[tokenizer.countTokens()];

		int index = 0;
		while (tokenizer.hasMoreTokens()) {
			result[index] = tokenizer.nextToken();
			index++;
		}

		return result;

	}

	protected static boolean isStringChar(char ch) {
		if (ch >= 'a' && ch <= 'z')
			return true;
		if (ch >= 'A' && ch <= 'Z')
			return true;
		if (ch >= '0' && ch <= '9')
			return true;
		switch (ch) {
		case '/':
		case '-':
		case ':':
		case '.':
		case ',':
		case '_':
		case '$':
		case '%':
		case '\'':
		case '(':
		case ')':
		case '[':
		case ']':
		case '<':
		case '>':
			return true;
		}
		return false;
	}

	/** Process one file */
	public static void process(String fileName, InputStream inStream) {
		try {
			int i;
			char ch;

			// This line alone cuts the runtime by about 66% on large files.
			BufferedInputStream is = new BufferedInputStream(inStream);

			StringBuffer sb = new StringBuffer();

			// Read a byte, cast it to char, check if part of printable string.
			while ((i = is.read()) != -1) {
				ch = (char) i;
				if (isStringChar(ch) || (sb.length() > 0 && ch == ' '))
					// If so, build up string.
					sb.append(ch);
				else {
					// if not, see if anything to output.
					if (sb.length() == 0)
						continue;
					if (sb.length() >= 4) {
						report(fileName, sb);
					}
					sb.setLength(0);
				}
			}
			is.close();
		} catch (IOException e) {
			//System.out.println("IOException: " + e);
		}
	}
	
	protected static void report(String fName, StringBuffer theString) {
	   // System.out.println(fName + ": " + theString);
	  }

}
