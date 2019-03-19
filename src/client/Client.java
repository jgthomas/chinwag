package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
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
	private Handler handler;
	private User user;
	private ArrayList<String> loggedInUsers;
	private LoginController controller;
	
//	public Client(String hostname, int port, ClientGUI gui) {
//		this.hostname = hostname;
//		this.port = port;
//		try {
//			clientSocket = new Socket(hostname, port);
//			input = new ObjectInputStream(clientSocket.getInputStream());
//			output = new ObjectOutputStream(clientSocket.getOutputStream());
//		} catch (UnknownHostException uh) {
//			uh.printStackTrace();
//		} catch (IOException io) {
//			io.printStackTrace();
//		}
//		this.gui = gui;
//		executor = Executors.newCachedThreadPool();
//		ClientListener cl = new ClientListener(this);
//		executor.execute(cl);
//		in = new Scanner(System.in);
//		this.user = new User(this);
//		handler = new ClientHandler(this, gui, user);
//		loggedInUsers = new ArrayList<String>();
//	}
	
	public Client(String hostname, int port, LoginController controller) {
		loggedInUsers = new ArrayList<String>();
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
		executor = Executors.newCachedThreadPool();
		Listener cl = new Listener(this);
		executor.execute(cl);
		in = new Scanner(System.in);
		this.user = new User(this);
		handler = new Handler(this, controller, user);
		this.controller = controller;
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
	
	public Handler getHandler() {
		return handler;
	}
	
	public User getUser() {
		return user;
	}
	
	public ArrayList<String> getLoggedInUsers() {
		return loggedInUsers;
	}
}
