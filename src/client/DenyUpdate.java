package client;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DenyUpdate implements Runnable {
	private ClientGUI gui;
	
	public DenyUpdate(ClientGUI gui) {
		this.gui = gui;
	}

	@Override
	public void run() {
		gui.refuseLogin();
		Text fuckoff = new Text("Fuck off.");
		Group root = new Group();
		VBox v = new VBox();
		v.getChildren().clear();
		v.getChildren().add(fuckoff);
		root.getChildren().add(v);
		Scene scene = new Scene(root);
		gui.getStage().setScene(scene);
		gui.getStage().show();
	}
}
