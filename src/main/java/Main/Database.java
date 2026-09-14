package Main;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {

	ArrayList<ArrayList<String>> applications = new ArrayList<>();
	private static final String url = "jdbc:sqlite:advanced.db";
	Database(){
		createTable();
	}
	
	//Tabelle erstellen
	public void createTable() {
		String sql = "CREATE TABLE IF NOT EXISTS applications (id integer PRIMARY KEY, company text, position text, location text, status text, application_date text, last_update text);";
		try(Connection conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement()){
			
			stmt.execute(sql);
			System.out.println("Tabelle erfolgreich geladen!");
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Hinzufügen
	public void insertTable(String company, String position, String location, String status, String application_date, String last_update) {
		String sql = "INSERT INTO applications (company,position,location,status,application_date,last_update) VALUES (?,?,?,?,?,?);";
		try(Connection conn = DriverManager.getConnection(url);
			PreparedStatement stmt = conn.prepareStatement(sql)){
						
			stmt.setString(1, company);
			stmt.setString(2, position);
			stmt.setString(3, location);
			stmt.setString(4, status);
			stmt.setString(5, application_date);
			stmt.setString(6, last_update);
			stmt.executeUpdate();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getId(String company, String location, String position, String application_date) {
		String sql = "SELECT id FROM applications WHERE company = ? AND location = ? AND position = ? AND application_date = ?";
		try(Connection conn = DriverManager.getConnection(url);
			PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setString(1, company);
			stmt.setString(2, location);
			stmt.setString(3, position);
			stmt.setString(4, application_date);
			ResultSet rs = stmt.executeQuery();  //return von SELECT
			if(rs.next()) {
				return rs.getInt("id");
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	//Update
	public void update(int id, String status, String last_update) {
		String sql = "UPDATE applications SET status = ?, last_update = ? WHERE id = ?";
		try(Connection conn = DriverManager.getConnection(url);
			PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setString(1,status);
			stmt.setString(2,last_update);
			stmt.setInt(3, id);
			stmt.executeUpdate();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	//Löschen(bestimmt)
	public void delete(int id, String company) {
		String sql = "DELETE FROM applications WHERE id = ? AND company = ?"; //ID reicht eigentlich, Company aber extra zur Bestätigung
		try(Connection conn = DriverManager.getConnection(url);
			PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setInt(1, id);
			stmt.setString(2, company);
			stmt.executeUpdate();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Löschen(Alles)
	public void clearTable() {
		String sql = "DELETE FROM applications";
		try(Connection conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement()){
			
			stmt.execute(sql);
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Printet die ganze Tabelle
	public ArrayList<ArrayList<String>> printTable() {
		applications.clear();
	    String sql = "SELECT * FROM applications;";
	    try (Connection conn = DriverManager.getConnection(url);
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {
	        while (rs.next()) {
	        		ArrayList<String> application = new ArrayList<>();
	        		application.add(Integer.toString(rs.getInt("id")));
	        		application.add(rs.getString("company"));
	        		application.add(rs.getString("position"));
	        		application.add(rs.getString("location"));
	        		application.add(rs.getString("status"));
	        		application.add(rs.getString("application_date"));
	        		application.add(rs.getString("last_update"));
	        		applications.add(application);
	        }
	        return applications;
	    } 
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return applications;
	}
}



