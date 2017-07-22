package com.controller;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	LogController ob = new LogController();
	private String rootUrl = "http://localhost:8080/IptvCommRest/rest/UserService/";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsonString = new String();

		String line = "";
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null) {
			jsonString += line;
		}

		JSONObject json = new JSONObject(jsonString);
		String usern = json.getString("username");
		String pass = json.getString("password");
		request.setAttribute("username", usern);
		request.setAttribute("password", pass);

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost req = new HttpPost(rootUrl + "register");
		StringEntity params = new StringEntity(jsonString);
		req.addHeader("content-type", "application/json");
		req.setEntity(params);
		HttpResponse resp = client.execute(req);
		ob.doPost(request, response);
	}

}
