package client_archive;

import java.util.TreeSet;

import client.ClientChatSession;
import client.ClientGUI;
import client.User;
import protocol.Data;
import protocol.MessageBox;

public class UpdateMembersCommand implements Command {
	private User user;
	private ClientGUI gui;
	private ClientChatSession chatSession;
	private TreeSet<String> sessionMembers;
	
	public UpdateMembersCommand(MessageBox mb, ClientGUI gui, User user) {
		this.gui = gui;
		this.user = user;
		//Finds the chat session corresponding to the CHAT_NAME in the MessageBox
		this.chatSession = user.getChatSessions().get(mb.get(Data.CHAT_NAME));
		this.sessionMembers = sessionMembers;
		
	}

	@Override
	public void execute() {
		chatSession.setSessionMembers(sessionMembers);
		// TODO Auto-generated method stub
		
	}
	

}
