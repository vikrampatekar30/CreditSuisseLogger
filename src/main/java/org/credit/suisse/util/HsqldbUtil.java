package org.credit.suisse.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.credit.suisse.event.CreditSuisseEvent;

public class HsqldbUtil {

	private static Connection connection;
	private static Logger logger = LogManager.getLogger(HsqldbUtil.class);
	
	private static Connection getConnection() {
		logger.log(Level.DEBUG, "[HsqldbUtil] - start of getConnection() method");
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
		    connection = DriverManager.getConnection("jdbc:hsqldb:file:credit_suisse_logger", "SA", "");
		}  catch (Exception e) {
			logger.log(Level.ERROR, "[HsqldbUtil] - error while attempting to establish a connection to the given database");
			e.printStackTrace(System.out);
		}
		logger.log(Level.DEBUG, "[HsqldbUtil] - end of getConnection() method");
		return connection;
	}
	
	public static void insertDataList(List<CreditSuisseEvent> creditSuisseEventList) {
		logger.log(Level.DEBUG, "[HsqldbUtil] - start of insertDataList() method");
		try {
			if(connection == null) {
				connection = getConnection();
				Statement stmt = connection.createStatement();
		        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS credit_suisse_event (\r\n" + 
		        		"   id bigint NOT NULL,\r\n" + 
		        		"   eventId VARCHAR(50) NOT NULL,\r\n" + 
		        		"   duration bigint NOT NULL,\r\n" + 
		        		"   type VARCHAR(50),\r\n" + 
		        		"   host VARCHAR(20),\r\n" + 
		        		"   alert bit NOT NULL,\r\n" + 
		        		"   PRIMARY KEY (id) \r\n" + 
		        		");");
		        logger.log(Level.DEBUG, "[HsqldbUtil] - successfully created credit_suisse_event table");
			}
		
			for(CreditSuisseEvent event : creditSuisseEventList) {
			
				PreparedStatement stmt = connection.prepareStatement("insert into credit_suisse_event values(?,?,?,?,?,?)");  
				stmt.setInt(1,new Random().nextInt());
				stmt.setString(2,event.getId());
				stmt.setLong(3, event.getDuration()); 
				stmt.setString(4,event.getType());
				stmt.setString(5,event.getHost());
				stmt.setBoolean(6, event.isAlert());
				  
				stmt.executeUpdate();
			}
			logger.log(Level.DEBUG, "[HsqldbUtil] - data inserted successfully to credit_suisse_event table");
		} catch (SQLException e) {
			logger.log(Level.ERROR, "[HsqldbUtil] - error while inserting the data to credit_suisse_event table");
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.log(Level.ERROR, "[HsqldbUtil] - error while closing the connection");
				e.printStackTrace();
			}
		}
		logger.log(Level.DEBUG, "[HsqldbUtil] - end of insertDataList() method");
	}
}
