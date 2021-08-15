package botje.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import botje.model.Person;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQLConnector {
	
    private static final Logger logger = LogManager.getLogger(MySQLConnector.class);
	
	public void Connect() {		
		// register JDBC driver and create connection
		try {
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			logger.info("connected to database " + DB_URL);
			
		} catch (ClassNotFoundException e) {
			logger.error("Error: unable to load driver class!", e);
		} catch (SQLException e) {
			logger.error("Couldn't connect to database", e);
			Platform.exit();
		}
	}
	
	public ObservableList<Person> getPoef(int product) {
		ObservableList<Person> results = FXCollections.observableArrayList();
		String sql = "SELECT members.memberName, COUNT(poef.memberId) AS amount FROM product INNER JOIN poef ON product.productId = poef.productId INNER JOIN members ON poef.memberId = members.memberId WHERE product.productId = " + product + " GROUP BY members.memberId";
		try {
			// execute query
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			
			while (result.next()) {
				String memberName = result.getString("memberName");
				int amount = result.getInt("amount");
				
				results.add(new Person(memberName, amount));
			}
			
			logger.info("Data loaded");
			
			result.close();
			stmt.close();
			
		} catch (SQLException e) {
			logger.error("Couldn't execute query", e);
		} catch (Exception e) {
			logger.error("Something went wrong", e);
		} finally {
			try {
				if(stmt!=null) stmt.close();
			}catch(SQLException se2){
			}
		}
		return results;
	}
	
	public void closeConnection() {
		try {
			if (!conn.isClosed()) conn.close();
		} catch (SQLException e) {
			logger.error("Couldn't close connection", e);
		}
	}
	
	private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private final String DB_URL = "jdbc:mysql://xx.xxx.xxx.xx";
	private final String PASS = "admin";
	private final String USER = "";
	private Connection conn = null;
	private Statement stmt = null;
		
}
