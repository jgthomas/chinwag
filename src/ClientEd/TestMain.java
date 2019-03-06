package ClientEd;

import server.Server;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestMain {
	public static void main(String[] args) {
		InetAddress local;
		try {
			local = InetAddress.getLocalHost();
			Server.main(new String[0]);
			Client c1 = new Client(local, 6000);
			ListenerThread lt1 = new ListenerThread(c1);
			c1.login();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

				
	}

}
