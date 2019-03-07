package client;

import java.io.IOException;

import protocol.MessageBox;

public class ClientListener implements Runnable {
	private Client client;
	private CommandFactory cf;

	public ClientListener(Client client) {
		this.client = client;
		this.cf = new CommandFactory(client.getGUI());
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				MessageBox mb = (MessageBox)client.getInput().readObject();
				cf.buildCommand(mb).execute();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
