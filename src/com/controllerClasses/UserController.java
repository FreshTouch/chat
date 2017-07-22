package com.controllerClasses;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.User;

public class UserController {
	
	  private String rootUrl="http://localhost:8080/IptvCommRest/rest/UserService/";
	  
	  
	  
	  
	   
		public JSONArray getChannelLogoList(String username, String token) throws ServletException, IOException{
			
			
			HttpClient logoClint = HttpClientBuilder.create().build();
			HttpGet requestLogo = new HttpGet(rootUrl + "logo/" + username + "," + token);
			HttpResponse responsLogo = logoClint.execute(requestLogo);
			
			HttpEntity logoEntity = responsLogo.getEntity();
			String logoList = EntityUtils.toString(logoEntity);
			JSONArray jsonarray = new JSONArray(logoList);
		
			
			
		return jsonarray;
		}
		
		public JSONArray  getChannelNameList(String username, String token)  throws ServletException, IOException{
			HttpClient TvNameClint = HttpClientBuilder.create().build();
			HttpGet requestTvName = new HttpGet(rootUrl + "channelname/" + username + "," + token);
			HttpResponse responsTvName = TvNameClint.execute(requestTvName);
			
			HttpEntity TvNameEntity = responsTvName.getEntity();
			String TvNameList = EntityUtils.toString(TvNameEntity);
			JSONArray jsonTvName = new JSONArray(TvNameList);
			
		
			return jsonTvName;
		}

	  
	  
	  
	  
	  public void deactivateAccount(String username) throws  IOException{
		  
		  HttpClient client = HttpClientBuilder.create().build();
			HttpDelete request = new HttpDelete(rootUrl + "delete/" + username);
			HttpResponse respons = client.execute(request);
			
	  }
	  
	  public void updateAccount(User user,String token ) throws  IOException {
		  HttpClient client=HttpClientBuilder.create().build();
		  HttpPut req = new HttpPut(rootUrl + "update/" + token);
		  JSONObject json=new JSONObject();
		  json.put("userId",user.getUserId());
		  json.put("username",user.getUsername());
		  json.put("password",user.getPassword());
		  json.put("lastName",user.getLastName());
		  json.put("firstName",user.getFirstName());
		  json.put("eMail",user.geteMail());
		  json.put("avatar",user.getAvatar());
		  String jsonString =json.toString();
		  StringEntity params=null;
		try {
			params = new StringEntity(jsonString);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   req.addHeader("content-type", "application/json");
		   req.setEntity(params);
		   HttpResponse resp = client.execute(req);
		 // System.out.println(jsonString);
		  //json.put("username",user.getUsername());
		 // json.put("username",user.getUsername());
		  //json.put("username",user.getUsername());
		  
	  }

}
