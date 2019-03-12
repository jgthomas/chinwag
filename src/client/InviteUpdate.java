package client;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import protocol.MessageBox;

public class InviteUpdate implements Runnable {
	private ClientGUI gui;
	private MessageBox mb;
	
	public InviteUpdate(ClientGUI gui, MessageBox mb) {
		this.gui = gui;
		this.mb = mb;
	}

	@Override
	public void run() {
		gui.drawInviteScreen(mb);
	}
}