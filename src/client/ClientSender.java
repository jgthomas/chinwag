package client;

import java.io.IOException;

import protocol.MessageBox;

public class ClientSender {
	private Client client;
	
	public ClientSender(Client client) {
		this.client = client;
	}

	public void sendMessage(MessageBox mb) {
		try {
			client.getOutput().writeObject(mb);
			client.getOutput().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
