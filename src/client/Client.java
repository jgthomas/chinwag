package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
	private String hostname;
	private int port;
	private Socket clientSocket;
	private ExecutorService executor;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private Scanner in;
	
	public Client(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
		executor = Executors.newCachedThreadPool();
		in = new Scanner(System.in);
	}
	
	public void runClient() {
		try {
			clientSocket = new Socket(hostname, port);
			input = new ObjectInputStream(clientSocket.getInputStream());
			output = new ObjectOutputStream(clientSocket.getOutputStream());
		}
		catch(IOException io) {
			io.printStackTrace();
		}
		ClientListener cl = new ClientListener(this);
		ClientSender cs = new ClientSender(this);
		executor.execute(cl);
		executor.execute(cs);
		
	}
	
	public ObjectInputStream getInput() {
		return input;
	}
	
	public ObjectOutputStream getOutput() {
		return output;
	}
	
	public Socket getSocket() {
		return clientSocket;
	}
}
