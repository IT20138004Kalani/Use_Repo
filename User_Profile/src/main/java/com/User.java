package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement; 

public class User 
{ //A common method to connect to the DB
		private Connection connect(){ 
			
						Connection con = null; 
						
						try{ 
								Class.forName("com.mysql.jdbc.Driver"); 
 
								//Provide the correct details: DBServer/DBName, username, password 
								con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/users_database", "root", ""); 
						} 
						catch (Exception e) {
							e.printStackTrace();
							} 
						
						return con; 
			} 
		
		
		public String insertUser(String name, String email, String phoneNo){ 
			
					String output = ""; 
					
					try
					{ 
						Connection con = connect(); 
						
						if (con == null) 
						{
							return "Error while connecting to the database for inserting."; 
							
						} 
						// create a prepared statement
						
						String query = " insert into person (`userID`,`userName`,`userEmail`,`userPhoneNo`)"+" values (?, ?, ?, ?)"; 
						PreparedStatement preparedStmt = con.prepareStatement(query); 
						// binding values
						preparedStmt.setInt(1, 0); 
						preparedStmt.setString(2, name); 
						preparedStmt.setString(3, email); 
						preparedStmt.setString(4, phoneNo); 
						// execute the statement
 
						preparedStmt.execute(); 
						con.close(); 
						
						String newUser = readUser(); 
						output = "{\"status\":\"success\",\"data\":\""+newUser+"\"}"; 
					} 
					
					catch (Exception e) 
					{ 
						output = "{\"status\":\"error\", \"data\":\"Error while inserting the user.\"}"; 
						System.err.println(e.getMessage()); 
					} 
					return output; 
			} 
		
		
		
		public String readUser() 
		{ 
			String output = ""; 
			try
			{ 
				Connection con = connect(); 
		 if (con == null) 
		 { 
		 return "Error while connecting to the database for reading."; 
		 } 
		 // Prepare the html table to be displayed
		 output = "<table border=\"1\" class=\"table\"><tr><th>User ID</th>"
		 		+ "<th>User Name</th>"
		 		+ "<th>Item Email</th>"
		 		+ "<th>User Phone Number</th>"
		 		+ "<th>Update</th>"
		 		+ "<th>Remove</th></tr>"; 
		
		 String query = "select * from person"; 
		 Statement stmt = con.createStatement(); 
		 ResultSet rs = stmt.executeQuery(query); 
		 // iterate through the rows in the result set
		 while (rs.next()) 
		 { 
		 String userID = Integer.toString(rs.getInt("userID")); 
		 String userName = rs.getString("userName"); 
		 String userEmail = rs.getString("userEmail"); 
		 String userPhoneNo = rs.getString("userPhoneNo"); 
 
		 // Add into the html table
		 output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='"+userID+"'></td>"; 
		 output += "<td>" + userName + "</td>"; 
		 output += "<td>" + userEmail + "</td>"; 
		 output += "<td>" + userPhoneNo + "</td>"; 
		 // buttons
		 output += "<td><input name='btnUpdate' type='button' value='Update' "
				 + "class='btnUpdate btn btn-secondary' data-userid='" + userID + "'></td>"
				 + "<td><input name='btnRemove' type='button' value='Remove' "
				 + "class='btnRemove btn btn-danger' data-userid='" + userID + "'></td></tr>"; 
		 
		 } 
		 con.close(); 
		 // Complete the html table
		 output += "</table>"; 
		 } 
		 
		catch (Exception e) 
		 { 
		 output = "Error while reading the Users."; 
		 System.err.println(e.getMessage()); 
		 } 
		return output; 
		}

			
			
			public String updateUser(String ID, String name, String email, String phoneNo){ 
				
					String output = ""; 
					
					try{ 
							Connection con = connect(); 
							if (con == null){
								return "Error while connecting to the database for updating.";
								} 
							// create a prepared statement
							String query = "UPDATE person SET userName=?,userEmail=?,userPhoneNo=? WHERE userID=?"; 
							PreparedStatement preparedStmt = con.prepareStatement(query); 
							// binding values
							preparedStmt.setString(1, name); 
							preparedStmt.setString(2, email); 
							
							preparedStmt.setString(3, phoneNo); 
							preparedStmt.setInt(4, Integer.parseInt(ID)); 
							// execute the statement
							preparedStmt.execute(); 
							con.close(); 
							String newUser = readUser(); 
							output = "{\"status\":\"success\",\"data\":\""+newUser+"\"}"; 

					} 
					
					catch (Exception e){ 
						
						output = "{\"status\":\"error\",\"data\":\"Error while updating the user.\"}"; 

						System.err.println(e.getMessage()); 
						
					} 
					
					return output; 
			} 
			
			
			


			public String deleteUser(String userID) {
				// TODO Auto-generated method stub
				String output = ""; 
				
				try{ 
					Connection con = connect(); 
					
					if (con == null){
						return "Error while connecting to the database for deleting."; 
						} 
					// create a prepared statement
					String query = "delete from person where userID=?"; 
					PreparedStatement preparedStmt = con.prepareStatement(query); 
					// binding values
					preparedStmt.setInt(1, Integer.parseInt(userID)); 
					// execute the statement
					preparedStmt.execute(); 
					con.close(); 
					String newUser = readUser(); 
					 output = "{\"status\":\"success\",\"data\":\""+newUser+"\"}"; 

				} 
				
				catch (Exception e){ 
					output = "{\"status\":\"error\",\"data\":\"Error while deleting the user.\"}";
					System.err.println(e.getMessage()); 
				} 
				return output;
			} 
			
			
			
			
} 
