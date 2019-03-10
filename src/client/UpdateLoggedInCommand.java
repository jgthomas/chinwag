package client;

import java.util.TreeSet;

import protocol.Data;
import protocol.MessageBox;

/**
 * When this executes, it updates the local client data on who is logged in for a given chat session.
 * 
 * Should then update the GUI accordingly
 * 
 * @author ed
 *
 */

public class UpdateLoggedInCommand implements Command {
	private Client client;
	private ClientGUI gui;
	private ClientChatSession chatSession;
	private TreeSet<String> onlineUsers;
	
	public UpdateLoggedInCommand(MessageBox mb, ClientGUI gui, Client client) {
		this.gui = gui;
		//Finds the chat session corresponding to the CHAT_NAME in the MessageBox
		this.chatSession = client.getChatSessions().get(mb.get(Data.CHAT_NAME));
		this.onlineUsers = onlineUsers;
		this.client = client;
	}
	
	
	@Override
	public void execute() {
		chatSession.setOnlineUsers(onlineUsers);
		// TODO call method in GUI to update logged in
		
	}

}
