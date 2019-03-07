package client;

public class LoginUpdate implements Runnable {
	private ClientGUI gui;
	
	public LoginUpdate(ClientGUI gui) {
		this.gui = gui;
	}
	
	@Override
	public void run() {
		gui.login();
	}
}
