package org.castafiore.facebook;

import java.util.List;
import java.util.Map;

/**
 * Client for fql for facebook
 * @author acer
 *
 */
public interface FacebookGraphAPIClient {
	
	/**
	 * executes an fql query on the server
	 * @param fql
	 * @param accesstoken
	 * @return
	 */
	public List<Map<String,String>> executeFql(String fql, String authorizationcode, String clientId, String clientSecret, String redirectUri);
	
	
	public String getAuthorizationUrl(String clientId, String clientSecret, String redirectUri);

}
