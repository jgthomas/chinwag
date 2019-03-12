package client_archive;

import client.ClientGUI;
import protocol.Data;
import protocol.MessageBox;

public class MessageCommand implements Command {
	private MessageBox mb;
	private ClientGUI gui;
	
	public MessageCommand(MessageBox mb, ClientGUI gui) {
		this.mb = mb;
		this.gui = gui;
	}
	
	public void execute() {
		gui.displayMessage(mb);
	}
}
