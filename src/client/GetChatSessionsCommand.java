package client;

import java.util.TreeMap;

import protocol.Data;
import protocol.MessageBox;
import protocol.Token;

public class GetChatSessionsCommand implements Command {
	private User user;
	private MessageBox mb;
	private ClientGUI gui;
	
	public GetChatSessionsCommand(MessageBox mb, ClientGUI gui, User user) {
		this.gui = gui;
		this.user = user;
		this.mb = mb; //do we need to bother with this?
		//Finds the chat session corresponding to the CHAT_NAME in the MessageBox
	}
	
	/**
	 * SHould also tell the GUI to populate the list of chats
	 */
	@Override
	public void execute() {
		String[] chatSessionNames = mb.get(Data.CHAT_SESSIONS).split(Token.SEPARATOR.getValue());
		TreeMap<String, ClientChatSession> chatSessions = new TreeMap<>();
		for (String name : chatSessionNames) {
			chatSessions.put(name, new ClientChatSession(name));
		}
		user.setChatSessions(chatSessions);
	}

}
