package com.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;






@WebServlet("/LogController")
public class LogController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpSession session;
    private String rootUrl="http://localhost:8080/IptvCommRest/rest/UserService/";
    
	

	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String jsonString = new String();
		String line = "";
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null) {
			jsonString += line;
		}
		JSONObject json=new JSONObject(jsonString);
	    String	username = json.getString("username");
		HttpClient client = HttpClientBuilder.create().build();
		HttpPut requesT = new HttpPut(rootUrl+"logout/"+username);
		HttpResponse responsE = client.execute(requesT);
		session.removeAttribute("username");
		session.removeAttribute("password");
		session.removeAttribute("lastname");
		session.removeAttribute("firstname");
		session.removeAttribute("token");
		session.removeAttribute("userId");
		session.removeAttribute("indexURL");
		response.getWriter().write("http://localhost:8080/IptvCommRest/Pages/index.jsp");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		String username;
		String password;
		
		if(request.getAttribute("username") == null && request.getAttribute("password") == null){
			
			String jsonString = new String();
			String line = "";
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				jsonString += line;
			}

			JSONObject json = new JSONObject(jsonString);
			username = json.getString("username");
			password = json.getString("password");
			
		}else{
			username = (String) request.getAttribute("username");
			password = (String) request.getAttribute("password");
		}
		
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet requesT = new HttpGet(rootUrl+"login/"+username+","+password);
		HttpResponse responsE = client.execute(requesT);
		
		 
		   HttpEntity entity = responsE.getEntity();

           String data = EntityUtils.toString(entity);
           JSONObject json=new JSONObject(data);
   		   String firstn=json.getString("firstName"); 
   		   String lastname = json.getString("lastName");
   		   String token = json.getString("token");
   		   long userId = json.getLong("userId");
   		   
   				   
		 
		if(responsE != null){
			session.setAttribute("data", data);
		session.setAttribute("username", username);
		session.setAttribute("password", password);
		session.setAttribute("firstname", firstn);
		session.setAttribute("lastname", lastname);
		session.setAttribute("token", token);
		session.setAttribute("userId", userId);
		session.setAttribute("indexURL", "http://localhost:8080/IptvCommRest/Pages/index.jsp");

		
		//response.getWriter().write("http://localhost:8080/IptvCommRest/Pages/main.jsp");
		response.getWriter().write(data);
		}
		
	}
	

}
