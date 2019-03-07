package client;

public class AcceptCommand implements Command {
	private ClientGUI gui;
	
	public AcceptCommand(ClientGUI gui) {
		this.gui = gui;
	}
	
	@Override
	public void execute() {
		gui.login();
	}
}
