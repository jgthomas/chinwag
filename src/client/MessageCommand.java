package client;

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
		if(mb.get(Data.MESSAGE).equals("Success!")) {
			gui.login();
			return;
		}
		gui.displayMessage(mb.get(Data.MESSAGE));
	}
}
