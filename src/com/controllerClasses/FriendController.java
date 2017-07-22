package com.controllerClasses;

import java.io.IOException;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

public class FriendController {

    private String rootUrl="http://localhost:8080/IptvCommRest/rest/UserService/";
    
    
    
    public void deleteFriend(long userId,long friendId,String token) throws  IOException{
    	HttpClient frdClient = HttpClientBuilder.create().build();
		HttpDelete requestFrd = new HttpDelete(rootUrl + "deleteFrd/" + userId + ","+friendId+"," + token);
		HttpResponse responsFrd = frdClient.execute(requestFrd);
    }
 
	public JSONArray getFriendList(long userId,String token) throws  IOException{
		HttpClient frdClint = HttpClientBuilder.create().build();
		HttpGet requestFrd = new HttpGet(rootUrl + "frlist/" + userId + "," + token);
		HttpResponse responsFrd = frdClint.execute(requestFrd);
		
		HttpEntity frdEntity = responsFrd.getEntity();
		String frdList = EntityUtils.toString(frdEntity);
	
		JSONArray frdJson = new JSONArray(frdList);
		return frdJson;
	}
	
	

}
