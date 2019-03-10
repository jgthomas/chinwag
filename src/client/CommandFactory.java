package client;

import protocol.Action;
import protocol.MessageBox;

class CommandFactory {
	private ClientGUI gui;
	private User user;

	public CommandFactory(ClientGUI gui) {
		this.gui = gui;
	}

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
		case UPDATE_LOGGED_IN:
			return new UpdateLoggedInCommand(mb, gui, user);
		default:
			throw new IllegalStateException("Unrecognised command: " + action);
		}
	}
}