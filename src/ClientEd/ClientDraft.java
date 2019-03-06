import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/** need thread always listening for server communication */
/** thread to process received messages */
/** thread to send messages */

public class ClientDraft {
	private final int port;
	private final InetAddress serverHost;
	private Socket socket;
	
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;
	
	/**constructor opens socket to server */
	ClientDraft(InetAddress serverHost, int port) {
		
		this.serverHost = serverHost;
		this.port = port;
		
		try {
			socket = new Socket(serverHost, port);
			System.out.println("Connection to server established");
			
			fromServer = new ObjectInputStream(socket.getInputStream());
			toServer = new ObjectOutputStream(socket.getOutputStream());
			
			
		} catch (IOException e) {
			System.out.println("Connection to server could not be established.");
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public void Close() {
		//closes the client
	}
	

}
