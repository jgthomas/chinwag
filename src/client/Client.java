package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Client {
	private String hostname;
	private int port;
	private Socket clientSocket;
	private ExecutorService executor;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private Scanner in;
	private ClientSender sender;
	private ClientHandler handler;
	private ClientGUI gui;
	private User user;
	
	public Client(String hostname, int port, ClientGUI gui) {
		this.hostname = hostname;
		this.port = port;
		try {
			clientSocket = new Socket(hostname, port);
			input = new ObjectInputStream(clientSocket.getInputStream());
			output = new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (UnknownHostException uh) {
			uh.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}
		this.gui = gui;
		executor = Executors.newCachedThreadPool();
		ClientListener cl = new ClientListener(this);
		executor.execute(cl);
		in = new Scanner(System.in);
		sender = new ClientSender(this);
		handler = new ClientHandler(this, gui, user);
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
	
	public ClientSender getSender() {
		return sender;
	}
	
	public String getHostname() {
		return hostname;
	}
	
	public int getPort() {
		return port;
	}
	
	public ClientGUI getGUI() {
		return gui;
	}
	
	public ClientHandler getHandler() {
		return handler;
	}
	
}
