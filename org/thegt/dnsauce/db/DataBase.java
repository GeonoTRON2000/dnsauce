package org.thegt.dnsauce.db;
import java.sql.*;

public class DataBase {
	private String host = "sql.thegt.org";
	private int port = 3306;
	private String db = "thegtorg_dnsauce";
	private String user = "thegtorg_dnsauce";
	private String pass = "SaucyPie2000";
	private String url = "jdbc:mysql://"+host+":"+port+"/"+db;
	private Connection dbconn;
	private Statement statement;
	
	public DataBase() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		dbconn = DriverManager.getConnection(url, user, pass);
		statement = dbconn.createStatement();
	}
	
	public ResultSet execute(String query) throws SQLException {
		return statement.executeQuery(query);
	}
	
	public void close () throws SQLException {
		statement.close();
		dbconn.close();
	}
}
