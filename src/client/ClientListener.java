package client;

import java.io.IOException;
import server.Action;
import server.MessageBox;

public class ClientListener implements Runnable {
	private Client client;
	private ClientGUI gui;
	private ClientHandler handler;

	public ClientListener(Client client, ClientGUI gui) {
		this.client = client;
		this.handler = new ClientHandler(client);
		this.gui = gui;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				MessageBox mb = (MessageBox)client.getInput().readObject();
				MessageCommand mc = new MessageCommand();
				mc.execute(gui, mb);
				System.out.println("Message received.");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
