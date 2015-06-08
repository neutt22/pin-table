import java.net.InetAddress;
import java.net.UnknownHostException;


public class Util {
	
	public static String getName(){
		return System.getProperty("user.name");
	}
	
	public static String getPCName(){
		String comp_name = "N/A";
		try {
			comp_name = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return comp_name;
	}

}
