package client;

import protocol.MessageBox;

public interface Command {
	abstract void execute(ClientGUI gui, MessageBox mb);
}
