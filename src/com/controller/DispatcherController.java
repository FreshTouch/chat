package com.controller;

import java.io.BufferedReader;
import com.controllerClasses.FriendController;
import com.controllerClasses.UserController;
import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.User;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/DispatcherController")
public class DispatcherController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*if (request.getParameter("button1") != null) {
            System.out.println(request.getParameter("button1"));
            response.sendRedirect("Pages/index.jsp");
        } else if (request.getParameter("button2") != null) {
        	 System.out.println("button 2");
        	 response.sendRedirect("https://www.google.am");
        } else if (request.getParameter("button3") != null) {
        	 System.out.println("button 3");
        	 response.sendRedirect("https://www.list.am");
        } else {
        	 System.out.println("unknown button");
        }*/
		PrintWriter out = response.getWriter();
		String jsonString = new String();
		UserController userCon = new UserController();
		FriendController friendCon=new FriendController();
		 try {
			    String line = "";
			    BufferedReader reader = request.getReader();
			    while ((line = reader.readLine()) != null)
			      jsonString += line;
			  } catch (Exception e) { 
			    e.printStackTrace();
			  }
		
		
		 JSONObject json=new JSONObject(jsonString);
		 String name = json.getString("Name");
		 String username = json.getString("username");
		 String token = json.getString("token");
		 long userId = json.getLong("userId");
		 switch(name){
		 case "channels": 
			 
			 JSONArray jsonLogo = userCon.getChannelLogoList(username, token);
			 JSONArray jsonName = userCon.getChannelNameList(username, token);
			 
			 JSONObject jsons=new JSONObject();
			 jsons.put("Logo", jsonLogo);
			 jsons.put("Name", jsonName);
			 out.print(jsons);
			 break;
		 case "friends": 
		 
		 JSONArray frList = friendCon.getFriendList(userId, token);
		 
		 out.print(frList);
		 
		 break;
		 case "delete": 
			 
			 
			 new UserController().deactivateAccount(username);
			 
			 
			 break;
		 case "update":
			 String password=json.getString("password");
			 String firstName=json.getString("firstName");
			 String lastName=json.getString("lastName");
			 String eMail=json.getString("eMail");
			 String avatar=json.getString("avatar");
			 User user=new User();
			 user.setUserId(userId);
			 user.setUsername(username);
			 user.setPassword(password);
			 user.setFirstName(firstName);
			 user.setLastName(lastName);
			 user.seteMail(eMail);
			 user.setAvatar(avatar);
			 new UserController().updateAccount(user, token);
		 }
		
	}

}