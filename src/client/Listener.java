package client;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;

import protocol.Action;
//import client_archive.CommandFactory;
import protocol.MessageBox;

public class Listener implements Runnable {
	private Client client;
//	private CommandFactory cf;

	public Listener(Client client) {
		this.client = client;
//		this.cf = new CommandFactory(client.getGUI());
	}
	
	@Override
	public void run() {
		MessageBox mb = new MessageBox(Action.CHAT);
		do {
			try {
				mb = (MessageBox)client.getInput().readObject();
				client.getHandler().handle(mb);
			} catch(EOFException eof) {
				//eof.printStackTrace();
				System.out.println("Connection to server dropped.");
				System.exit(1);
				break;
			} catch (ClassNotFoundException e) {
				//e.printStackTrace();
				System.out.println("Connection to server dropped.");
				System.exit(1);
				break;
			} catch (IOException e) {
				//e.printStackTrace();
				System.out.println("Connection to server dropped.");
				System.exit(1);
				break;
			}
		} while (mb.getAction() != Action.QUIT);
	}

}
