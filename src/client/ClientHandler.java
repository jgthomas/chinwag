package client;

import server.ChatSession;
import server.CommandFactory;
import server.ConnectionTracker;
import server.MessageBox;
import server.Sender;
import server.Sessions;
import server.User;

public class ClientHandler {
	private Client client;
	private Sender messageSender;
	private ConnectionTracker tracker;
	
	public ClientHandler(Client client) {
		this.client = client;
		messageSender = new Sender(client.getSocket(), new User(client.getSocket()));
		tracker = new Sessions(messageSender, new ChatSession("global"));
	}
	
	public void handle(MessageBox mb) {
		CommandFactory.buildCommand(mb.getCommand(), tracker).execute(mb);
	}
}
