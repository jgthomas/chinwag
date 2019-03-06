package ClientEd;

import server.Server;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestMain {
	public static void main(String[] args) {
		InetAddress local;
		try {
			local = InetAddress.getLocalHost();
			

			//Server.main(new String[0]);
			//Server s1 = new Server(6000);
			//System.out.println("Are we seeing this code?");
			//s1.runServer();

			Client c1 = new Client(local, 6000);

			ListenerThread lt1 = new ListenerThread(c1);

			c1.login();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

				
	}

}
