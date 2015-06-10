import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PinConnection {
	
	private Connection connect = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	
	private String available = "";
	private String used 	 = "";
	private boolean isUpdated = false;
	
	public String getAvailable(){ return available; }
	public String getUsed(){ return used; }
	public boolean isUpdated(){ return isUpdated; }
	
	public void search(String pin){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ywc?user=root&password=GIBCO22jim");
			statement = connect.prepareStatement("SELECT * from pins where pin=?");
			statement.setString(1, pin);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()){
				available = resultSet.getString("available");
				used = resultSet.getString("used");
			}
			connect.close();
			statement.close();
			resultSet.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void activate(String pin){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ywc?user=root&password=GIBCO22jim");
			statement = connect.prepareStatement("UPDATE pins SET available='true' where pin=?");
			statement.setString(1, pin);
			statement.executeUpdate();
			isUpdated = true;
			connect.close();
			statement.close();
			return;
		}catch(Exception e){
			e.printStackTrace();
			isUpdated = false;
		}
		isUpdated = false;
	}
	
	public void addHistory(String pc_name, String name, String for_, String date, String pin){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ywc?user=root&password=GIBCO22jim");
			statement = connect.prepareStatement("INSERT INTO histories (pc_name, name, for_, date, pin) VALUES (?,?,?,?,?)");
			statement.setString(1, pc_name);
			statement.setString(2, name);
			statement.setString(3, for_);
			statement.setString(4, date);
			statement.setString(5, pin);
			statement.executeUpdate();
			connect.close();
			statement.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean exportPins(String filename){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ywc?user=root&password=GIBCO22jim");
			String SQL = "SELECT pin, available, used FROM pins INTO OUTFILE '" + filename + ".csv' FIELDS TERMINATED" +
					" BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\n' ";
			System.out.println(SQL);
			statement = connect.prepareStatement(SQL);
			statement.executeQuery();
			connect.close();
			statement.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean exportLogs(String filename){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ywc?user=root&password=GIBCO22jim");
			String SQL = "SELECT pc_name, name, for_, date, pin FROM histories INTO OUTFILE '" + filename + ".csv' FIELDS TERMINATED" +
					" BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\n' ";
			System.out.println(SQL);
			statement = connect.prepareStatement(SQL);
			statement.executeQuery();
			connect.close();
			statement.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public void reset(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ywc?user=root&password=GIBCO22jim");
			statement = connect.prepareStatement("UPDATE pins SET available='false', used='false'");
			statement.executeUpdate();
			connect.close();
			statement.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
//	public static void main(String args[]){
//		
//		new PinConnection().exportPins();
//	}

}
