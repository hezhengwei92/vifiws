package net.eoutech.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	
	private String url = "jdbc:mysql://192.168.177.129:3306/ViFi?useUnicode=true&characterEncoding=utf8";
	private String user = "root";
	private String password = "root";
	
	
	public DBUtil() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Connection getConnection () {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url,user,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public int getEmployeeId (int id) {
		int n = -1 ;
		if (id == 249) {
			n = 2;
		}else if (id == 250) {
			n = 3;
		}else if (id == 251) {
			n = 4;
		}else if (id == 252) {
			n = 5;
		}else if (id == 253) {
			n = 6;
		}else if (id == 254) {
			n = 7;
		}else if (id == 576) {
			n = 8;
		}else if (id == 571) {
			n = 9;
		}else if (id == 572) {
			n = 10;
		}else if (id == 573) {
			n = 11;
		}
		return n;
	}
	
}
