import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ListenerThread implements Runnable {
	private Socket socket;
	private ObjectInputStream fromServer;
	
	ListenerThread(Socket socket, ObjectInputStream fromServer) {
		this.socket = socket;
		this.fromServer = fromServer;
	}

	@Override
	public void run() {
		try {
			//Waits here for an object to appear in input
			MessageBox messageBox = (MessageBox) fromServer.readObject();
			
			//Create new thread to do the following:
			//check validity of message
			//check command
			//interpret command
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
