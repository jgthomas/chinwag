package client;

import javafx.application.Platform;
import protocol.Data;
import protocol.MessageBox;

public class AcceptCommand implements Command {
	private MessageBox mb;
	private ClientGUI gui;
	
	public AcceptCommand(MessageBox mb, ClientGUI gui) {
		this.mb = mb;
		this.gui = gui;
	}
	
	@Override
	public void execute() {
		gui.setLoggedInName(mb.get(Data.USER_NAME));
		LoginUpdate lu = new LoginUpdate(gui);
		Platform.runLater(lu);
	}
}
