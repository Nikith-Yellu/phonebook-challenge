package com.phonebook.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import com.phonebook.core.Contacts;
import com.phonebook.core.PhonebookCore;
import com.phonebook.jdbc.JdbcPostgresqlConnection;

/**
 * Servlet implementation class PhonebookServlet
 */
@WebServlet("/phonebookServlet")
public class PhonebookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhonebookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		String method=null;
		try {
			method= "get";
			conn = JdbcPostgresqlConnection.establishConnection();
			response = processRequest(request,response,conn,method);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		String method=null;
		try {
			method= "post";
			conn = JdbcPostgresqlConnection.establishConnection();
			response = processRequest(request,response,conn, method);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private HttpServletResponse processRequest(HttpServletRequest request, HttpServletResponse response, Connection conn, String method) throws IOException {
		String param= request.getParameter("search");
		String responseMessage= null;
		String jsonData="";
		Gson inclusiveGson = new GsonBuilder().serializeNulls().create();
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		ObjectMapper mapper = new ObjectMapper();
		if(br!=null) {
			jsonData = br.readLine();
		}
		if(method.equalsIgnoreCase("get")){
			if(param!=null) {
				Contacts contact = new Contacts();
				try {
					contact = PhonebookCore.search(param, conn);
					if(contact.getFname()!=null) {
						responseMessage = inclusiveGson.toJson(contact);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}else {
				ArrayList<Contacts> contacts = new ArrayList<Contacts>();
				try {
					contacts = PhonebookCore.getContacts(conn);
					if(contacts.size()!=0) {
						responseMessage = inclusiveGson.toJson(contacts);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}else if(method.equalsIgnoreCase("post")) {
			Contacts contact = new Contacts();
			if(jsonData!=null) {
				try {
					contact = mapper.readValue(jsonData,Contacts.class);
					responseMessage = PhonebookCore.addContact(conn, contact);
					
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			
		}
		response.getWriter().write(responseMessage);
		response.getWriter().flush();
		return response;
		
	}

}
