package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import protocol.Action;
import protocol.Data;
import protocol.MessageBox;

public class Client {
	private String hostname;
	private int port;
	private Socket clientSocket;
	private ExecutorService executor;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private Scanner in;
	private ClientSender sender;
	private ClientGUI gui;
	
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
		executor = Executors.newCachedThreadPool();
		ClientListener cl = new ClientListener(this, gui);
		executor.execute(cl);
		in = new Scanner(System.in);
		sender = new ClientSender(this);
		this.gui = gui;
	}
	
	public void runClient() {
		MessageBox login = new MessageBox(Action.LOGIN);
		login.add(Data.USER_NAME, "Bill");
		sender.sendMessage(login);
		try {
			executor.awaitTermination(2, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
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
	
	public ClientSender getSender() {
		return sender;
	}
}
