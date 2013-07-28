package org.castafiore.facebook;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.UIException;
import org.castafiore.utils.ResourceUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class DefaultFacebookGaphAPIClient implements FacebookGraphAPIClient{
	
	public final static String SERVER_URL = "https://graph.facebook.com/fql";
	@Override
	public List<Map<String, String>> executeFql(String fql, String authorizationcode, String clientId, String clientSecret, String redirectUri) {
		
		try{
			String accessToken = getAccessToken(authorizationcode, clientId, clientSecret, redirectUri);
			String url = SERVER_URL + "?q=" + URLEncoder.encode(fql, "ISO-8859-1") + "&" + accessToken;
		
			String json = ResourceUtil.readUrl(url);
			
			JSONArray array =  extractData(json);
			
			return convert(array);
			
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	public String getAuthorizationUrl(String clientId, String clientSecret, String redirectUri){
		try{
		String s = "https://www.facebook.com/dialog/oauth/?client_id=" +clientId + "&redirect_uri=" + URLEncoder.encode(redirectUri, "ISO-8859-1");
		return s;
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	private String getAccessToken(String authorizationcode, String clientId, String clientSecret, String redirectUri)throws Exception{
		String url = "https://graph.facebook.com/oauth/access_token?client_id=" + clientId + "&redirect_uri=" + URLEncoder.encode(redirectUri, "ISO-8859-1");
		
		url = url + "&client_secret=" + clientSecret + "&code=" + authorizationcode;
		
		
		return ResourceUtil.readUrl(url);
		
	}
	
public List<Map<String, String>> executeFql(String fql, String accesstoken) {
		
		try{
			String url = SERVER_URL + "?q=" + URLEncoder.encode(fql, "ISO-8859-1") + "&access_token=" + accesstoken;
		
			String json = ResourceUtil.readUrl(url);
			
			JSONArray array =  extractData(json);
			
			return convert(array);
			
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	private List<Map<String,String>> convert(JSONArray array)throws JSONException{
		List<Map<String,String>> result = new ArrayList<Map<String,String>>(array.length());
		for(int i =0; i < array.length(); i++){
			JSONObject user = array.getJSONObject(i);
			Iterator keys = user.keys();
			Map<String, String> data = new HashMap<String, String>(user.length());
			while(keys.hasNext()){
				
				String key = keys.next().toString();
				String value = user.getString(key);
				data.put(key, value);
				
			}
			result.add(data);
			
			//System.out.println(user);
		}
		
		return result;
	}
	
	private JSONArray extractData(String result)throws JSONException{
		//JSONArray obj = new JSONArray(result.replace("/**/ ___GraphExplorerAsyncCallback___", ""));
		
		JSONObject data = new JSONObject(result);//obj.getJSONObject(0);
		JSONArray array = data.getJSONArray("data");
		return array;
	}

}
