import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

public class SQLdatabase {

	private final String database, learn, test;
	// JDBC driver name and database URL
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/";
	private static final String DISABLE_SSL = "?useSSL=false";
	// Database credentials
	private static final String USER = "root";
	private static final String PASS = "root";

	private String sql = null;
	private List<Measurement> meas, measTest;

	public SQLdatabase(String database, String learn, String test) {
		this.meas = new ArrayList<Measurement>();
		this.measTest = new ArrayList<Measurement>();

		// Database and tables to get from MySQL
		this.database = database;
		this.learn = learn;
		this.test = test;

		create();
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
			sql = "USE " + this.database;
			stmt.executeUpdate(sql);

			// Import all measured values
			sql = "SELECT * FROM " + this.learn;
			ResultSet rs = stmt.executeQuery(sql); // execute query
			// Insert values into an ArrayList
			while (rs.next()) {
				this.meas.add(new Measurement(rs.getString("rdfid"), rs.getString("name"),
						Double.parseDouble(rs.getString("value")), rs.getString("sub_rdfid"),
						Double.parseDouble(rs.getString("time"))));
			}

			// Import the test set
			sql = "SELECT * FROM " + this.test;
			rs = stmt.executeQuery(sql); // execute query
			// Insert values into an ArrayList
			while (rs.next()) {
				this.measTest.add(new Measurement(rs.getString("rdfid"), rs.getString("name"),
						Double.parseDouble(rs.getString("value")), rs.getString("sub_rdfid"),
						Double.parseDouble(rs.getString("time"))));
			}

			System.out.println("Working database");

		} catch (MySQLSyntaxErrorException e) {
			JOptionPane.showMessageDialog(null, "Those tables are not in the database, please check your sintax.");
		} catch (CommunicationsException ce) {
			JOptionPane.showMessageDialog(null, "The SQL server may not have been initialized.");
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
	}

	public List<Measurement> getMeas() {
		return meas;
	}

	public List<Measurement> getMeasTest() {
		return measTest;
	}

	// Split list of measurements depending on the time variable and return the
	// system's points
	public static List<Point> splitByTime(List<Measurement> measurementList) {

		List<Point> pointList = new ArrayList<Point>();
		List<Measurement> measList = new ArrayList<Measurement>(measurementList);

		while (measList.size() != 0) {
			// Get time from the first element of the set
			double time = measList.get(0).getTime();
			// Create a placeholder which aggregates all measurements with the
			// same time
			List<Measurement> measTime = new ArrayList<Measurement>();
			// Iterate over all the measurements to group together those with
			// the same time
			for (Measurement meas : measList) {
				if (meas.getTime() == time) {
					measTime.add(meas);
				}
			}
			// Now there is a point with N+N voltage and angle measurements -
			// create a new point
			pointList.add(new Point(measTime, 0, time));
			// Remove the already added measurements from the list
			measList.removeAll(measTime);
		}

		return pointList;
	}

}
