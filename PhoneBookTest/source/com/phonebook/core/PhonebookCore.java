package com.phonebook.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PhonebookCore {
	
	public static ArrayList<Contacts> getContacts(Connection conn) {
		ArrayList<Contacts> contacts = new ArrayList<Contacts>();
		String sql=null;
		PreparedStatement ps=null;
		ResultSet rs =null;
		try {
			sql = "select * from contacts";
			ps= conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()) {
				Contacts contact = new Contacts();
				contact.setFname(rs.getString("fname"));
				contact.setLname(rs.getString("lname"));
				contact.setPhone(rs.getString("phone"));
				contacts.add(contact);
			}
				
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		return contacts;
		
	}
	
	public static String addContact(Connection conn, Contacts contact) {
		String response =null;
		String sql,sql1=null;
		PreparedStatement ps,ps1=null;
		ResultSet rs,rs1 =null;
		Boolean tableExists = false;
		try {
			tableExists= checkIfTableExists(conn, contact);
			if(!tableExists) {
				String response1 = createTable(conn);
				if(response1.equalsIgnoreCase("success")) {
					tableExists = true;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(tableExists) {
			try {
				sql="select * from \"contacts\" where upper(fname)=upper(?) and upper(lname)=upper(?)";
				ps= conn.prepareStatement(sql);
				ps.setString(1, contact.getFname());
				ps.setString(2, contact.getLname());
				rs=ps.executeQuery();
				if(rs.next()) {
					sql1="update \"contacts\" set phone=? where upper(fname)=upper(?) and upper(lname)=upper(?)";
					ps1=conn.prepareStatement(sql1);
					ps1.setString(1, contact.getPhone());
					ps1.setString(2, contact.getFname());
					ps1.setString(3, contact.getLname());
					response="Success";
				}else {
					sql1="insert into \"contacts\" (fname,lname,phone) values (?,?,?)";
					ps1=conn.prepareStatement(sql1);
					ps1.setString(1, contact.getFname());
					ps1.setString(2, contact.getLname());
					ps1.setString(3, contact.getPhone());
					response="Success";
				}
				ps1.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
				response="Failed to save";
			}finally {
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		
		return response;
	}
	
	public static Contacts search(String input, Connection conn) {
		Contacts contact = new Contacts();
		String sql=null;
		PreparedStatement ps=null;
		ResultSet rs =null;
		try {
			if(isNumeric(input)) {
				sql= "select * from contacts where phone=?";
				ps= conn.prepareStatement(sql);
				ps.setString(1, input);
			}else {
				sql="select * from contacts where upper(fname)=upper(?) or upper(lname)=upper(?)";
				ps= conn.prepareStatement(sql);
				ps.setString(1, input);
				ps.setString(2, input);
			}
			
			rs=ps.executeQuery();
			while(rs.next()) {
				contact.setFname(rs.getString("fname"));
				contact.setLname(rs.getString("lname"));
				contact.setPhone(rs.getString("phone"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return contact;
		
	}
	
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        float i = Float.parseFloat(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	public static boolean checkIfTableExists(Connection conn, Contacts contact) {
		String sql=null;
		PreparedStatement ps=null;
		ResultSet rs =null;
		try {
			sql="select * from \"contacts\" ";
			ps= conn.prepareStatement(sql);
			rs=ps.executeQuery();
		}catch(SQLException e) {
			
			return false;
		}
		return true;
		
	}
	
	public static String createTable(Connection conn) {
		String sql=null;
		PreparedStatement ps=null;
		ResultSet rs =null;
		String response=null;
		try {
			sql = "Create table contacts (fname VARCHAR(50) NOT NULL,lname VARCHAR(50) NOT NULL,phone VARCHAR(20) NOT NULL)";
			ps= conn.prepareStatement(sql);
			ps.executeUpdate();
			response = "success";
		}catch(SQLException e) {
			e.printStackTrace();
			response="failed";
		}
		return response;
		
	}

}
