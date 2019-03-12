package client_archive;

import client.ClientGUI;
import client.User;
import protocol.Action;
import protocol.MessageBox;

class CommandFactory {
	private ClientGUI gui;
	private User user;

	public CommandFactory(ClientGUI gui) {
		this.gui = gui;
	}

	
	//Add break after each switch case for speed?
	public Command buildCommand(MessageBox mb) {
		Action action = mb.getAction();
		switch (action) {
		case CHAT:
			return new MessageCommand(mb, gui);
		case SERVER_MESSAGE:
			return new MessageCommand(mb, gui);
		case DENY:
			return new DenyCommand(gui);
		case ACCEPT:
			return new AcceptCommand(mb, gui);
		case GIVE_MEMBERS:
			return new UpdateMembersCommand(mb, gui, user);
		case GIVE_LOGGED_IN:
			return new UpdateLoggedInCommand(mb, gui, user);
		default:
			throw new IllegalStateException("Unrecognised command: " + action);
		}
	}
}