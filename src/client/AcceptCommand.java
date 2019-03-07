package client;

import javafx.application.Platform;

public class AcceptCommand implements Command {
	private ClientGUI gui;
	
	public AcceptCommand(ClientGUI gui) {
		this.gui = gui;
	}
	
	@Override
	public void execute() {
		LoginUpdate lu = new LoginUpdate(gui);
		Platform.runLater(lu);
	}
}
