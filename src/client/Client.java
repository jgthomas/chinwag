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
		executor.execute(cl);
		
		ClientSender cs = new ClientSender(this);
		MessageBox login = new MessageBox(Action.LOGIN);
		login.add(Data.USER_NAME, "Bill");
		cs.sendMessage(login);
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
}
