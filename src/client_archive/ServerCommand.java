package client_archive;

import client.Client;
import protocol.MessageBox;

public class ServerCommand implements Command {
	private Client client;
	private MessageBox mb;
	
	public ServerCommand(Client client, MessageBox mb) {
		this.client = client;
		this.mb = mb;
	}
	
	@Override
	public void execute() {
		client.getGUI().displayMessage(mb);
	}

}
