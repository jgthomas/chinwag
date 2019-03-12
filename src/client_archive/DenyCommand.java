package client_archive;

import client.ClientGUI;
import client.DenyUpdate;
import javafx.application.Platform;

public class DenyCommand implements Command {
	private ClientGUI gui;
	
	public DenyCommand(ClientGUI gui) {
		this.gui = gui;
	}
	
	@Override
	public void execute() {
		DenyUpdate du = new DenyUpdate(gui);
		Platform.runLater(du);
	}
}
