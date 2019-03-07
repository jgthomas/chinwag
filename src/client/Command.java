package client;

import server.MessageBox;

public interface Command {
	abstract void execute(ClientGUI gui, MessageBox mb);
}
