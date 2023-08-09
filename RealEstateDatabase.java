/*
Carlos Mendoza - Real Estate Database Value Insertion & Search Function
*/
//Importing Classes
import java.sql.*;
import java.io.*;
import java.util.Scanner;

public class RealEstateDatabase{
	public static void main (String[] args){
		//Have the user request to either [1] insert the data from the CSV files in the same repository, [2] search through the database, or [3] exit the program
		//REPL
		while (true){
			System.out.println("\nWelcome to the Real Estate Database! What would you like to do? Enter a number.");
			System.out.println("[1] UPDATE DATA VIA CSV FILES");
			System.out.println("[2] SEARCH DATABASE");
			System.out.println("[3] EXIT");
			Scanner scanner = new Scanner(System.in);
			String input = scanner.nextLine();
			if (input.matches("1")){
				dataInsert();
			}
			else if (input.matches("2")){
				searchHome();
			}
			else if (input.matches("3")){
				System.out.println("exiting now...");
				break;
			}
			else{
				System.out.println("Invalid Input.");
			}
		}
	}

	//Loads data into the tables using SQL insert commands executed in this Java app
	public static void dataInsert(){
		//Setting up the csv file and reader for it
		//Change path to csv file if necessary
		String currentDir = System.getProperty("user.dir");
		String sellerFile = currentDir + "/SELLER.csv";
		String agentFile = currentDir + "/AGENT.csv";
		String houseFile = currentDir + "/HOUSE.csv";
		String phoneNumberFile = currentDir + "/PHONE_NUMBER.csv";
		String buyerFile = currentDir + "/BUYER.csv";
		BufferedReader reader = null;
		String line = "";

		try{
			//Load JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Create database connection
			//Username and password should be changed depending on database being used
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/realestate", "root", "");
			//Creating statements
			Statement s = c.createStatement();
			//Issuing statements to database
			//Delete current entries in database so that the tables accurately reflect the included csv files which can be edited
			try{
				//Deleted in a specific order due to primary and secndary key placements
				s.executeUpdate("DELETE FROM House");
				s.executeUpdate("DELETE FROM Seller");
				s.executeUpdate("DELETE FROM PhoneNumber");
				s.executeUpdate("DELETE FROM Buyer");
				s.executeUpdate("DELETE FROM Agent");				
			} catch (Exception e){
				System.out.println("Delete from failed:\n" + e);
			}

			//Reads csv files line by line and inserts the data from that line into realestate schema
			try{
				//*****INSERTING SELLER VALUES*****
				//Creates the prepared statement
				String insertString1 = "INSERT INTO Seller VALUES(?, ?, ?, ?)";
				PreparedStatement insertSeller = c.prepareStatement(insertString1);
				//prepares reader for csv file
				reader = new BufferedReader(new FileReader(sellerFile));
				//Allows for the reader to ignore the first line of data as it is only meant to signify what each row represents
				String headerLine = reader.readLine();
				while ((line = reader.readLine()) != null){
					//Split based on comma due to csv files being used
					String[] row = line.split(",");
					int i = 1;
					for (String index : row){
						//in case of SSN, a string
						if (i == 1){
							insertSeller.setString(1, index);
						}
						//in case of SName, a string
						if (i == 2){
							insertSeller.setString(2, index);
						}
						//in case of Spouse, a string
						if (i == 3){
							insertSeller.setString(3, index);
						}
						//in case of SPhoneNumber, a string
						if (i == 4){
							insertSeller.setString(4, index);
							insertSeller.executeUpdate();
						}
					i++;
					}
				}

				//*****INSERTING AGENT VALUES*****
				//Creates the prepared statement
				String insertString2 = "INSERT INTO Agent VALUES(?, ?, ?)";
				PreparedStatement insertAgent = c.prepareStatement(insertString2);
				//prepares reader for csv file
				reader = new BufferedReader(new FileReader(agentFile));
				//Allows for the reader to ignore the first line of data as it is only meant to signify what each row represents
				headerLine = reader.readLine();
				while ((line = reader.readLine()) != null){
					//Split based on comma due to csv files being used
					String[] row = line.split(",");
					int i = 1;
					for (String index : row){
						//in case of AgentID, a number
						if (i == 1){
							insertAgent.setInt(1, Integer.parseInt(index));
						}
						//in case of AName, a string
						if (i == 2){
							insertAgent.setString(2, index);
						}
						//in case of Office, a string
						if (i == 3){
							insertAgent.setString(3, index);
							insertAgent.executeUpdate();
						}
					i++;
					}
				}

				//*****INSERTING HOUSE VALUES*****
				//Creates the prepared statement
				String insertString3 = "INSERT INTO House VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insertHouse = c.prepareStatement(insertString3);
				//prepares reader for csv file
				reader = new BufferedReader(new FileReader(houseFile));
				//Allows for the reader to ignore the first line of data as it is only meant to signify what each row represents
				headerLine = reader.readLine();
				while ((line = reader.readLine()) != null){
					//Split based on comma due to csv files being used
					String[] row = line.split(",");
					int i = 1;
					for (String index : row){
						//in case of HouseID, a number
						if (i == 1){
							insertHouse.setLong(1, Long.parseLong(index));
						}
						//in case of SquareFeet, a number
						if (i == 2){
							insertHouse.setLong(2, Long.parseLong(index));
						}
						//in case of StreetName, a string
						if (i == 3){
							insertHouse.setString(3, index);
						}
						//in case of State, a character
						//POTENTIAL PROBLEM
						if (i == 4){
							insertHouse.setString(4, index);
						}
						//in case of SellerSSN, a string
						if (i == 5){
							insertHouse.setString(5, index);
						}
						//in case of AgentID, a number
						if (i == 6){
							insertHouse.setLong(6, Long.parseLong(index));
						}
						//in case of Commission, a number
						if (i == 7){
							insertHouse.setLong(7, Long.parseLong(index));
						}
						//in case of Price, a number
						if (i == 8){
							insertHouse.setLong(8, Long.parseLong(index));
							insertHouse.executeUpdate();
						}
					i++;
					}
				}

				//*****INSERTING PHONENUMBER VALUES*****
				//Creates the prepared statement
				String insertString4 = "INSERT INTO PhoneNumber VALUES(?, ?)";
				PreparedStatement insertPhoneNumber = c.prepareStatement(insertString4);
				//prepares reader for csv file
				reader = new BufferedReader(new FileReader(phoneNumberFile));
				//Allows for the reader to ignore the first line of data as it is only meant to signify what each row represents
				headerLine = reader.readLine();
				while ((line = reader.readLine()) != null){
					//Split based on comma due to csv files being used
					String[] row = line.split(",");
					int i = 1;
					for (String index : row){
						//in case of AgentID, a number
						if (i == 1){
							insertPhoneNumber.setLong(1, Long.parseLong(index));
						}
						//in case of PPhoneNumber, a string
						if (i == 2){
							insertPhoneNumber.setString(2, index);
							insertPhoneNumber.executeUpdate();
						}
					i++;
					}
				}

				//*****INSERTING BUYER VALUES*****
				//Creates the prepared statement
				String insertString5 = "INSERT INTO Buyer VALUES(?, ?, ?, ?, ?, ?)";
				PreparedStatement insertBuyer = c.prepareStatement(insertString5);
				//prepares reader for csv file
				reader = new BufferedReader(new FileReader(buyerFile));
				//Allows for the reader to ignore the first line of data as it is only meant to signify what each row represents
				headerLine = reader.readLine();
				while ((line = reader.readLine()) != null){
					//Split based on comma due to csv files being used
					String[] row = line.split(",");
					int i = 1;
					for (String index : row){
						//in case of SSNb, a string
						if (i == 1){
							insertBuyer.setString(1, index);
						}
						//in case of BName, a string
						if (i == 2){
							insertBuyer.setString(2, index);
						}
						//in case of MinPrice, a number
						if (i == 3){
							insertBuyer.setLong(3, Long.parseLong(index));
						}
						//in case of MaxPrice, a number
						if (i == 4){
							insertBuyer.setLong(4, Long.parseLong(index));
						}
						//in case of BPhoneNumber, a string
						if (i == 5){
							insertBuyer.setString(5, index);
						}
						//in case of AgentID, a number
						if (i == 6){
							insertBuyer.setLong(6, Long.parseLong(index));
							insertBuyer.executeUpdate();
						}
					i++;
					}
				}

			}
			catch(Exception e){
				e.printStackTrace();
			}
			System.out.println("Table Values Updated");
			
			//Closing reader
			reader.close();
			//Closing database connection
			c.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	//HAVE THE USER REQUEST TO SEARCH THROUGH THE DATABASE WITH 6 SEARCH OPTIONS
	public static void searchHome(){
		while (true){
			try {
			//Establish connection to database
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/realestate", "root", "");
			//Request user input
			System.out.println("\nWhat would you like your search to be based on?");
			System.out.println("[1] SELLER BY NAME");
			System.out.println("[2] AGENT BY NAME");
			System.out.println("[3] AGENT BY OFFICE");
			System.out.println("[4] HOUSE BY SQUARE FEET");
			System.out.println("[5] HOUSE BY LOCATION");
			System.out.println("[6] HOUSE BY PRICE");
			System.out.println("[7] EXIT");
			Scanner scanner = new Scanner(System.in);
			String input = scanner.nextLine();
			//Searching for Sellers based on SName or Spouse
			if (input.matches("1")){
				System.out.println("\nEnter a name.");
				String input1 = scanner.nextLine();
				//Sellers in this database have two names associated with them, meaning that the search can be based on either of the two names
				String query1 = "SELECT * FROM Seller WHERE SName LIKE '%" + input1 + "%' OR Spouse LIKE '%" + input1 + "%';";
				Statement s1 = c.createStatement();
				ResultSet rs1 = s1.executeQuery(query1);
				sellerByName(rs1);
			}
			//Searching for Agents based on AName
			else if (input.matches("2")){
				System.out.println("\nEnter a name.");
				String input2 = scanner.nextLine();
				String query2 = "SELECT * FROM Agent WHERE AName LIKE '%" + input2 + "%';";
				//Since an Agent's PhoneNumber is included in another table, another query must be issued and handled in the called agentByName method
				String query2Number = "SELECT * FROM PhoneNumber";
				Statement s2 = c.createStatement();
				ResultSet rs2 = s2.executeQuery(query2);
				Statement s2Number = c.createStatement();
				ResultSet rs2Number = s2Number.executeQuery(query2Number);
				agentByName(rs2, rs2Number, s2Number, query2Number);
			}
			//Searhing for Agents by Office
			else if (input.matches("3")){
				System.out.println("\nEnter the name of the office/ agency.");
				String input3 = scanner.nextLine();
				String query3 = "SELECT * FROM Agent WHERE Office LIKE '%" + input3 + "%';";
				//Since an Agent's PhoneNumber is included in another table, another query must be issued and handled in the called agentByOffice method
				String query3Number = "SELECT * FROM PhoneNumber";
				Statement s3 = c.createStatement();
				ResultSet rs3 = s3.executeQuery(query3);
				Statement s3Number = c.createStatement();
				ResultSet rs3Number = s3Number.executeQuery(query3Number);
				agentByOffice(rs3, rs3Number, s3Number, query3Number);
			}
			//Searching for Houses by SquareFeet
			else if (input.matches("4")){
				//This search method relies on requesting the user to input a range
				System.out.println("\nEnter the minimum square feet of the house.");
				String input4Min = scanner.nextLine();
				System.out.println("\nEnter the maximum square feet of the house.");
				String input4Max = scanner.nextLine();
				//The query is based on the range given by the user
				String query4 = "SELECT * FROM House WHERE SquareFeet BETWEEN " + input4Min + " AND " + input4Max + ";";
				//To include info about the Seller of the House, another query must be issued and handled in the called houseBySquareFeet method
				String query4Seller = "SELECT * FROM Seller";
				//To include info about the Agent who listed the House, another query must be issued and handled in the called houseBySquareFeet method
				String query4Agent = "SELECT * FROM Agent";				
				Statement s4 = c.createStatement();
				ResultSet rs4 = s4.executeQuery(query4);
				Statement s4Seller = c.createStatement();
				ResultSet rs4Seller = s4Seller.executeQuery(query4Seller);
				Statement s4Agent = c.createStatement();
				ResultSet rs4Agent = s4Agent.executeQuery(query4Agent);
				houseBySqaureFeet(rs4, rs4Seller, s4Seller, query4Seller, rs4Agent, s4Agent, query4Agent);
			}
			//Searching for Houses by State and StreetName
			else if (input.matches("5")){
				String blank = "";
				String query5 = "";
				System.out.println("\nEnter the initials of the state where the house is located. Leave blank if desired.");
				String input5State = scanner.nextLine();
				System.out.println("\nEnter the name of the street where the house is located. Leave blank if desired.");
				String input5Street = scanner.nextLine();
				//The State or StreetName being searched for can be blank allowing for the query to search based on either one row, the other or both
				if (input5State.matches(blank) == true && input5Street.matches(blank) == false) {
					query5 = "SELECT * FROM House WHERE StreetName LIKE '%" + input5Street + "%';";
				}
				else if (input5Street.matches(blank) == true && input5State.matches(blank) == false) {
					query5 = "SELECT * FROM House WHERE State LIKE '%" + input5State + "%';";
				}
				//If both inputs are blank, ensure that no results are returned
				else if (input5Street.matches(blank) == true && input5State.matches(blank) == true) {
					query5 = "SELECT * FROM House WHERE State LIKE '%???%';";
				}
				else {
					query5 = "SELECT * FROM House WHERE State LIKE '%" + input5State + "%' AND StreetName LIKE '%" + input5Street + "%';";
				}
				//To include info about the Seller of the House, another query must be issued and handled in the called houseByLocation method
				String query5Seller = "SELECT * FROM Seller";
				//To include info about the Agent who listed the House, another query must be issued and handled in the called houseByLocation method
				String query5Agent = "SELECT * FROM Agent";				
				Statement s5 = c.createStatement();
				ResultSet rs5 = s5.executeQuery(query5);
				Statement s5Seller = c.createStatement();
				ResultSet rs5Seller = s5Seller.executeQuery(query5Seller);
				Statement s5Agent = c.createStatement();
				ResultSet rs5Agent = s5Agent.executeQuery(query5Agent);
				houseByLocation(rs5, rs5Seller, s5Seller, query5Seller, rs5Agent, s5Agent, query5Agent);
			}
			//Searching for Houses by Price
			else if (input.matches("6")){
				//This search method relies on requesting the user to input a range
				System.out.println("\nEnter the minimum price of the house.");
				String input6Min = scanner.nextLine();
				System.out.println("\nEnter the maximum price of the house.");
				String input6Max = scanner.nextLine();
				//The query is based on the range given by the user
				String query6 = "SELECT * FROM House WHERE Price BETWEEN " + input6Min + " AND " + input6Max + ";";
				//To include info about the Seller of the House, another query must be issued and handled in the called houseByPrice method
				String query6Seller = "SELECT * FROM Seller";
				//To include info about the Agent who listed the House, another query must be issued and handled in the called houseByPrice method
				String query6Agent = "SELECT * FROM Agent";				
				Statement s6 = c.createStatement();
				ResultSet rs6 = s6.executeQuery(query6);
				Statement s6Seller = c.createStatement();
				ResultSet rs6Seller = s6Seller.executeQuery(query6Seller);
				Statement s6Agent = c.createStatement();
				ResultSet rs6Agent = s6Agent.executeQuery(query6Agent);
				houseByPrice(rs6, rs6Seller, s6Seller, query6Seller, rs6Agent, s6Agent, query6Agent);
			}
			//Exit the search function part of the program
			else if (input.matches("7")){
				System.out.println("exiting now...");
				c.close();
				break;
			}
			else{
				System.out.println("Invalid Input.");
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		}
		}
	
	//[1] SELLER BY NAME
	//Sorts out the results of the search by printing the names of the Sellers and their phone number but NOT the SSN due to it being private data
	public static void sellerByName(ResultSet rs1){
		try {
		while(rs1.next()){
			System.out.println(rs1.getString(2) + " & " + rs1.getString(3) + ", " + rs1.getString(4));
		}
		}
		catch(Exception e){
			System.out.println("No results.");
			System.out.println(e);
		}
	}
	
	//[2] AGENT BY NAME
	//Sorts out the results of the search by printing the Agent's name, id, and agency in that order
	//Also prints PhoneNumber(s) if available
	public static void agentByName(ResultSet rs2, ResultSet rs2Number, Statement s2Number, String query2Number){
		try {
		while(rs2.next()) {
			System.out.print(rs2.getString(2) + " (ID: " + rs2.getInt(1) + "), " + rs2.getString(3));
			//To print the agent's phone number, the AgentIDs must match between the Agent and PhoneNumber tables
			while(rs2Number.next()) {
				if (rs2.getInt(1) == rs2Number.getInt(1)) {
					System.out.print(", " + rs2Number.getString(2));
				}
			}
			System.out.print("\n");
			//To point the reader back to the beginning of the PhoneNumber table so that the numbers can be compared to the next Agent being printed
			rs2Number = s2Number.executeQuery(query2Number);
		}
		}
		catch(Exception e) {
			System.out.println("No results.");
			System.out.println(e);
		}
	}
	
	//[3] AGENT BY OFFICE
	//Sorts out the results of the search by printing the Agent's name, id, and agency in that order
	//Also prints PhoneNumber(s) if available
	public static void agentByOffice(ResultSet rs3, ResultSet rs3Number, Statement s3Number, String query3Number){
		try {
			while(rs3.next()) {
				System.out.print(rs3.getString(2) + " (ID: " + rs3.getInt(1) + "), " + rs3.getString(3));
				//To print the agent's phone number, the AgentIDs must match between the Agent and PhoneNumber tables
				while(rs3Number.next()) {
					if (rs3.getInt(1) == rs3Number.getInt(1)) {
						System.out.print(", " + rs3Number.getString(2));
					}
				}
				System.out.print("\n");
				//To point the reader back to the beginning of the PhoneNumber table so that the numbers can be compared to the next Agent being printed
				rs3Number = s3Number.executeQuery(query3Number);
			}
			}
			catch(Exception e) {
				System.out.println("No results.");
				System.out.println(e);
			}
	}
	
	//[4] HOUSE BY SQUARE FEET
	//Sorts out the results of the search by printing the House's street, state, price, square feet, and id but NOT the commission price as that could be considered private information
	//Also prints the names and phone numbers of the Sellers of the Houses but NOT their SSNs due to it being private data
	//Also prints the names, ids, and agency names of the Agents that listed the Houses
	public static void houseBySqaureFeet(ResultSet rs4, ResultSet rs4Seller, Statement s4Seller, String query4Seller, ResultSet rs4Agent, Statement s4Agent, String query4Agent){
		try {
			while(rs4.next()) {
				//Separates each house from the search result due to each seperate result being three lines long
				System.out.print("[\n");
				System.out.print("House: " + rs4.getString(3) + ", " + rs4.getString(4) + ", $" + rs4.getInt(8) + ", " + rs4.getInt(2) + " sq ft., HouseID: " + rs4.getInt(1));
				while(rs4Seller.next()) {
					//To get the Seller's information, House.SellerSSN and Seller.SSN must match
					if(rs4Seller.getString(1).matches(rs4.getString(5))) {
						System.out.print("\nSold by: " + rs4Seller.getString(2) + " & " + rs4Seller.getString(3) + " @ " + rs4Seller.getString(4));
					}
				}
				while(rs4Agent.next()) {
					//To get the Agent's information, House.AgentID and Agent.ID must match
					if(rs4Agent.getInt(1) == rs4.getInt(6)) {
						System.out.print("\nListed by: " + rs4Agent.getString(2) + " (ID: " + rs4Agent.getInt(1) + "), " + rs4Agent.getString(3));
					}
				}
				System.out.print("\n]\n");
				//To point the reader back to the beginning of the Seller table so that the numbers can be compared to the next House being printed
				rs4Seller = s4Seller.executeQuery(query4Seller);
				//To point the reader back to the beginning of the Agent table so that the numbers can be compared to the next House being printed
				rs4Agent = s4Agent.executeQuery(query4Agent);
			}
			}
			catch(Exception e) {
				System.out.println("No results.");
				System.out.println(e);
			}
	}
	
	//[5] HOUSE BY LOCATION
	//Sorts out the results of the search by printing the House's street, state, price, square feet, and id but NOT the commission price as that could be considered private information
	//Also prints the names and phone numbers of the Sellers of the Houses but NOT their SSNs due to it being private data
	//Also prints the names, ids, and agency names of the Agents that listed the Houses
	public static void houseByLocation(ResultSet rs5, ResultSet rs5Seller, Statement s5Seller, String query5Seller, ResultSet rs5Agent, Statement s5Agent, String query5Agent){
		try {
			while(rs5.next()) {
				//Separates each house from the search result due to each seperate result being three lines long
				System.out.print("[\n");
				System.out.print("House: " + rs5.getString(3) + ", " + rs5.getString(4) + ", $" + rs5.getInt(8) + ", " + rs5.getInt(2) + " sq ft., HouseID: " + rs5.getInt(1));
				while(rs5Seller.next()) {
					//To get the Seller's information, House.SellerSSN and Seller.SSN must match
					if(rs5Seller.getString(1).matches(rs5.getString(5))) {
						System.out.print("\nSold by: " + rs5Seller.getString(2) + " & " + rs5Seller.getString(3) + " @ " + rs5Seller.getString(4));
					}
				}
				while(rs5Agent.next()) {
					//To get the Agent's information, House.AgentID and Agent.ID must match
					if(rs5Agent.getInt(1) == rs5.getInt(6)) {
						System.out.print("\nListed by: " + rs5Agent.getString(2) + " (ID: " + rs5Agent.getInt(1) + "), " + rs5Agent.getString(3));
					}
				}
				System.out.print("\n]\n");
				//To point the reader back to the beginning of the Seller table so that the numbers can be compared to the next House being printed
				rs5Seller = s5Seller.executeQuery(query5Seller);
				//To point the reader back to the beginning of the Agent table so that the numbers can be compared to the next House being printed
				rs5Agent = s5Agent.executeQuery(query5Agent);
			}
			}
			catch(Exception e) {
				System.out.println("No results.");
				System.out.println(e);
			}
	}
	
	//[6] HOUSE BY PRICE
	//Sorts out the results of the search by printing the House's street, state, price, square feet, and id but NOT the commission price as that could be considered private information
	//Also prints the names and phone numbers of the Sellers of the Houses but NOT their SSNs due to it being private data
	//Also prints the names, ids, and agency names of the Agents that listed the Houses
	public static void houseByPrice(ResultSet rs6, ResultSet rs6Seller, Statement s6Seller, String query6Seller, ResultSet rs6Agent, Statement s6Agent, String query6Agent){
		try {
			while(rs6.next()) {
				//Separates each house from the search result due to each seperate result being three lines long
				System.out.print("[\n");
				//Try getLong() if getInt() does not work
				System.out.print("House: " + rs6.getString(3) + ", " + rs6.getString(4) + ", $" + rs6.getInt(8) + ", " + rs6.getInt(2) + " sq ft., HouseID: " + rs6.getInt(1));
				while(rs6Seller.next()) {
					//To get the Seller's information, House.SellerSSN and Seller.SSN must match
					if(rs6Seller.getString(1).matches(rs6.getString(5))) {
						System.out.print("\nSold by: " + rs6Seller.getString(2) + " & " + rs6Seller.getString(3) + " @ " + rs6Seller.getString(4));
					}
				}
				while(rs6Agent.next()) {
					//To get the Agent's information, House.AgentID and Agent.ID must match
					if(rs6Agent.getInt(1) == rs6.getInt(6)) {
						System.out.print("\nListed by: " + rs6Agent.getString(2) + " (ID: " + rs6Agent.getInt(1) + "), " + rs6Agent.getString(3));
					}
				}
				System.out.print("\n]\n");
				//To point the reader back to the beginning of the Seller table so that the numbers can be compared to the next House being printed
				rs6Seller = s6Seller.executeQuery(query6Seller);
				//To point the reader back to the beginning of the Agent table so that the numbers can be compared to the next House being printed
				rs6Agent = s6Agent.executeQuery(query6Agent);
			}
			}
			catch(Exception e) {
				System.out.println("No results.");
				System.out.println(e);
			}
	}


}