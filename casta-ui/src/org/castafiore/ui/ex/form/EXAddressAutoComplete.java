package org.castafiore.ui.ex.form;

import java.net.URLEncoder;

import org.castafiore.ui.js.JArray;
import org.castafiore.utils.ResourceUtil;
import org.json.JSONArray;
import org.json.JSONObject;


public class EXAddressAutoComplete extends EXAutoComplete implements  AutoCompleteSource{

	public EXAddressAutoComplete(String name, String value) {
		super(name, "div");
		
		setSource(this);
		
	}
	
	
	

	protected JArray getSuggestions(String txt)throws Exception{
		String url = "http://maps.googleapis.com/maps/api/geocode/json?address="+ URLEncoder.encode(txt, "UTF-8") +"&sensor=false";
		String result = ResourceUtil.readUrl(url);
		JSONArray array = new JSONObject(result).getJSONArray("results");
		JArray res = new JArray();
		for(int i = 0; i < array.length(); i++){
			String s = array.getJSONObject(i).get("formatted_address").toString();
			res.add(s);
			
		}
		return res;
		
	}

	@Override
	public JArray getSource(String param) {
		try{
		return getSuggestions(param);
		}catch(Exception e){
			return new JArray().add("Error:" + e.getMessage());
		}
	}
	
	
	

	
}
