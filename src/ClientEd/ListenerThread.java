package ClientEd;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import server.MessageBox;

public class ListenerThread implements Runnable {
	private Socket socket;
	private ObjectInputStream fromServer;
	private ClientDraft client;
	
	ListenerThread(ClientDraft client) {
		this.client = client;
		socket = client.getSocket();
		fromServer = client.getFromServer();
	}

	@Override
	public void run() {
		try {
			//Waits here for an object to appear in input
			while (true) {
				MessageBox messageBox = (MessageBox) fromServer.readObject();
				ReactThread reaction = new ReactThread(client, messageBox);
				
			//Create new thread to do the following:
			
			//check validity of message
			//check command
			//interpret command
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
