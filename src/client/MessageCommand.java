package client;

import server.Data;
import server.MessageBox;

public class MessageCommand implements Command {
	public void execute(ClientGUI gui, MessageBox mb) {
		if(mb.get(Data.MESSAGE).equals("Success!")) {
			gui.login();
			return;
		}
		gui.displayMessage(mb.get(Data.MESSAGE));
	}
}
