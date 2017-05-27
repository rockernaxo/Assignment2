import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class SQLdatabase {

	// JDBC driver name and database URL
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/";
	private static final String DISABLE_SSL = "?useSSL=false";
	// Database credentials
	private static final String USER = "root";
	private static final String PASS = "root"; 
	
	private String sql = null;
	private List<Substation> substation;
	private List<Measurement> meas, measTest;

	public SQLdatabase() {
		this.substation = new ArrayList<Substation>();
		this.meas = new ArrayList<Measurement>();
		this.measTest = new ArrayList<Measurement>();
		create();
	}
	
	public static void main (String args []){
		new SQLdatabase();	
	}

	public void create() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);
			// Open a connection
			System.out.println("Connecting to SQL server...");
			conn = DriverManager.getConnection(DB_URL + DISABLE_SSL, USER, PASS);

			// execute a query to create database
			System.out.println("Creating database...");
			stmt = conn.createStatement();
			conn = DriverManager.getConnection(DB_URL + "Subtables" + DISABLE_SSL, USER, PASS);
			sql = "USE subtables";
			stmt.executeUpdate(sql);

			// Import all substations
			sql = "SELECT * FROM SUBSTATIONS";
			ResultSet rs = stmt.executeQuery(sql); // execute query
			// Insert values into an ArrayList
			while (rs.next()) {
				this.substation.add(new Substation(rs.getString("rdfid"), rs.getString("name")));
			}

			// Import all measured values
			sql = "SELECT * FROM MEASUREMENTS";
			rs = stmt.executeQuery(sql); // execute query
			// Insert values into an ArrayList
			while (rs.next()) {
				this.meas.add(new Measurement(rs.getString("rdfid"), rs.getString("name"),
						Double.parseDouble(rs.getString("value")), rs.getString("sub_rdfid"),Double.parseDouble(rs.getString("time"))));
			}

			// Import the learning set
			sql = "SELECT * FROM ANALOG_VALUES";
			rs = stmt.executeQuery(sql); // execute query
			// Insert values into an ArrayList
			while (rs.next()) {
				this.meas.add(new Measurement(rs.getString("rdfid"), rs.getString("name"),
						Double.parseDouble(rs.getString("value")), rs.getString("sub_rdfid"),Double.parseDouble(rs.getString("time"))));
			}
			
			System.out.println("Working database");

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
	}

	public List<Substation> getSubstation() {
		return substation;
	}

	public List<Measurement> getMeas() {
		return meas;
	}

	public List<Measurement> getMeasTest() {
		return measTest;
	}
}
