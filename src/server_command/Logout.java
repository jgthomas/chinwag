package server_command;

import protocol.Action;
import protocol.Data;
import protocol.MessageBox;
import server.AllChatSessions;
import server.ConnectedClients;
import server.MessageHandler;
import server.MessageSender;
import server.UserState;

public class Logout extends Command {

	public Logout(MessageSender messageSender, UserState userState, AllChatSessions allChatSessions,
			ConnectedClients connectedClients) {
		super(messageSender, userState, allChatSessions, connectedClients);
	}

	@Override
	public void execute(MessageBox messageBox) {
        
		MessageBox mb = new MessageBox(Action.UPDATE_LOGGED_OUT);
		mb.add(Data.USER_NAME, getMessageSender().getUserName());

		for (MessageHandler user : getConnectedClients()) {
			user.getMessageSender().sendMessage(mb);
		}
		
		getConnectedClients().removeClientByUserName(getMessageSender().getUserName());
		getConnectedClients().removeClientByID(getMessageSender().id());
		
        getUserState().exitAllChats(getMessageSender());
	}

}
