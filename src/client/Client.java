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

import protocol.MessageBox;

public class Client {
	private String hostname;
	private int port;
	private Socket clientSocket;
	private ExecutorService executor;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private Scanner in;
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
		this.user = new User(this);
		handler = new ClientHandler(this, gui, user);
	}
	
	public void sendMessage(MessageBox mb) {
		try {
			getOutput().writeObject(mb);
			getOutput().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	public User getUser() {
		return user;
	}
}
