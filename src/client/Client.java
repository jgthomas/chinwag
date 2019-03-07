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
import java.util.concurrent.TimeUnit;

import server.Action;
import server.Data;
import server.MessageBox;

public class Client {
	private String hostname;
	private int port;
	private Socket clientSocket;
	private ExecutorService executor;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private Scanner in;
	private ClientSender sender;
	
	public Client(String hostname, int port) {
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
		ClientListener cl = new ClientListener(this);
		executor.execute(cl);
		in = new Scanner(System.in);
		sender = new ClientSender(this);
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
