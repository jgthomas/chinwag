package ClientEd;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/** need thread always listening for server communication */
/** thread to process received messages */
/** thread to send messages */

public class Client {
	private final int port;
	private final InetAddress serverHost;
	private Socket socket;
	
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;
	
	/**constructor opens socket to server */
	Client(InetAddress serverHost, int port) {
		
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


	public int getPort() {
		return port;
	}


	public InetAddress getServerHost() {
		return serverHost;
	}


	public Socket getSocket() {
		return socket;
	}
	
	public ObjectInputStream getFromServer() {
		return fromServer;
	}
	
	public void login() {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter user name, or \"Quit\" to cancel.");
		String userName = in.nextLine();
		if (userName.equals("Quit")) {
			System.out.println("Login cancelled.");
			return;
		}
		//obscure what they type?
		System.out.println("Enter password");
		String passWord = in.nextLine();
		
		
		
		//MessageBox loginMessage = new MessageBox(login);
	}

}
