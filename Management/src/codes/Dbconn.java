package codes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Dbconn {
	
	Connection conn = null;
	public static Connection dbConnection(){
		 
		    try {
		      Class.forName("com.mysql.jdbc.Driver");
		      Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/car","root","gautam");
		      return conn;
		    } catch ( Exception e ) {
		      JOptionPane.showMessageDialog(null, e);
		      return null;
		    }
	}
}
