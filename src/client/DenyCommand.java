package client;

public class DenyCommand implements Command {
	private ClientGUI gui;
	
	public DenyCommand(ClientGUI gui) {
		this.gui = gui;
	}
	
	@Override
	public void execute() {
		gui.refuseLogin();
	}
}
